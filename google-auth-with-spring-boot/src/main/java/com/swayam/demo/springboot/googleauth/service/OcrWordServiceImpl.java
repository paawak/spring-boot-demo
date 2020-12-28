package com.swayam.demo.springboot.googleauth.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.googleauth.model.old.CorrectedWord;
import com.swayam.demo.springboot.googleauth.model.old.CorrectedWordEntity;
import com.swayam.demo.springboot.googleauth.model.old.CorrectedWordEntityTemplate;
import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.model.old.UserRole;
import com.swayam.demo.springboot.googleauth.repo.BookRepository;
import com.swayam.demo.springboot.googleauth.repo.CorrectedWordRepositoryTemplate;
import com.swayam.demo.springboot.googleauth.repo.OcrWordRepositoryTemplate;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;
import com.swayam.demo.springboot.googleauth.service.EntityClassUtil.EntityClassDetails;

@Service
public class OcrWordServiceImpl implements OcrWordService {

    private final BookRepository bookRepository;
    private final ApplicationContext applicationContext;

    public OcrWordServiceImpl(BookRepository bookRepository, ApplicationContext applicationContext) {
	this.bookRepository = bookRepository;
	this.applicationContext = applicationContext;
    }

    @Override
    public int getWordCount(long bookId, long pageImageId) {
	return getOcrWordRepositoryTemplate(bookId).countByOcrWordIdBookIdAndOcrWordIdPageImageId(bookId, pageImageId);
    }

    @Override
    public Collection<OcrWordOutputDto> getWords(long bookId, long pageImageId, UserDetails userDetails) {
	Collection<OcrWord> entities = getOcrWordRepositoryTemplate(bookId).findByOcrWordIdBookIdAndOcrWordIdPageImageIdOrderByOcrWordIdWordSequenceId(bookId, pageImageId);
	return entities.stream().map(entity -> {
	    OcrWordOutputDto dto = new OcrWordOutputDto();
	    BeanUtils.copyProperties(entity, dto);
	    List<? extends CorrectedWord> correctedWords = entity.getCorrectedWords();
	    if (correctedWords.size() > 0) {

		boolean isIgnored =
			correctedWords.stream().filter(correctedWord -> (correctedWord.getUser().getRole() == UserRole.ADMIN_ROLE) || (correctedWord.getUser().getId() == userDetails.getId()))
				.anyMatch(CorrectedWord::isIgnored);

		if (isIgnored) {
		    dto.setIgnored(true);
		} else {
		    Optional<? extends CorrectedWord> correctedWordWithText = correctedWords.stream().filter(correctedWord -> correctedWord.getCorrectedText() != null).findFirst();
		    if (correctedWordWithText.isPresent()) {
			dto.setCorrectedText(correctedWordWithText.get().getCorrectedText());
		    }
		}

	    }
	    return dto;
	}).filter(correctedWord -> !correctedWord.isIgnored()).collect(Collectors.toList());
    }

    @Override
    public OcrWord getWord(OcrWordId ocrWordId) {
	return getOcrWordRepositoryTemplate(ocrWordId.getBookId()).findByOcrWordId(ocrWordId).get();
    }

    @Transactional
    @Override
    public int markWordAsIgnored(OcrWordId ocrWordId, UserDetails user) {
	OcrWord ocrWord = getWord(ocrWordId);

	CorrectedWordRepositoryTemplate correctedWordRepositoryTemplate = getCorrectedWordRepositoryTemplate(ocrWordId.getBookId());
	Optional<CorrectedWord> existingCorrection = correctedWordRepositoryTemplate.findByOcrWordIdAndUser(ocrWord.getId(), user);

	if (existingCorrection.isPresent()) {
	    return correctedWordRepositoryTemplate.markAsIgnored(ocrWord.getId(), user);
	}

	correctedWordRepositoryTemplate.save(toEntity(Optional.empty(), ocrWord.getId(), user));

	return 1;
    }

    @Override
    public OcrWord addOcrWord(OcrWord ocrWord) {
	return getOcrWordRepositoryTemplate(ocrWord.getOcrWordId().getBookId()).save(toEntity(ocrWord));
    }

    @Transactional
    @Override
    public int updateCorrectTextInOcrWord(OcrWordId ocrWordId, String correctedText, UserDetails user) {

	OcrWord ocrWord = getWord(ocrWordId);

	CorrectedWordRepositoryTemplate correctedWordRepositoryTemplate = getCorrectedWordRepositoryTemplate(ocrWordId.getBookId());
	Optional<CorrectedWord> existingCorrection = correctedWordRepositoryTemplate.findByOcrWordIdAndUser(ocrWord.getId(), user);

	if (existingCorrection.isPresent()) {
	    return correctedWordRepositoryTemplate.updateCorrectedText(ocrWord.getId(), correctedText, user);
	}

	correctedWordRepositoryTemplate.save(toEntity(Optional.of(correctedText), ocrWord.getId(), user));

	return 1;
    }

    private OcrWordRepositoryTemplate getOcrWordRepositoryTemplate(long bookId) {
	EntityClassDetails entityClassDetails = getEntityClassDetails(bookId);
	return applicationContext.getBean(entityClassDetails.getOcrWordEntityRepository(), OcrWordRepositoryTemplate.class);
    }

    private CorrectedWordRepositoryTemplate getCorrectedWordRepositoryTemplate(long bookId) {
	EntityClassDetails entityClassDetails = getEntityClassDetails(bookId);
	// TODO; find based on name
	return applicationContext.getBean(CorrectedWordRepositoryTemplate.class);
    }

    private EntityClassDetails getEntityClassDetails(long bookId) {
	String baseTableName = bookRepository.findById(bookId).get().getBaseTableName();
	return new EntityClassUtil().getEntityClassDetails(baseTableName);
    }

    private OcrWord toEntity(OcrWord ocrWord) {
	EntityClassDetails entityClassDetails = getEntityClassDetails(ocrWord.getOcrWordId().getBookId());
	OcrWord entity;
	try {
	    entity = (OcrWord) Class.forName(entityClassDetails.getOcrWordEntity()).getDeclaredConstructor().newInstance();
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	    throw new RuntimeException(e);
	}
	BeanUtils.copyProperties(ocrWord, entity);
	return entity;
    }

    private CorrectedWordEntity toEntity(Optional<String> correctedText, long ocrWordId, UserDetails user) {
	CorrectedWordEntityTemplate correctedWord = new CorrectedWordEntityTemplate();
	if (correctedText.isPresent()) {
	    correctedWord.setCorrectedText(correctedText.get());
	    correctedWord.setIgnored(false);
	} else {
	    correctedWord.setIgnored(true);
	}
	correctedWord.setOcrWordId(ocrWordId);
	correctedWord.setUser(user);
	// TODO:: make this into a proxy
	CorrectedWordEntity entity = new CorrectedWordEntity();
	BeanUtils.copyProperties(correctedWord, entity);
	return entity;
    }

}
