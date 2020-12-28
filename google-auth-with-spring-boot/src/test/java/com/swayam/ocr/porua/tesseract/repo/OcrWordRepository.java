package com.swayam.ocr.porua.tesseract.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.repo.OcrWordRepositoryTemplate;
import com.swayam.ocr.porua.tesseract.model.OcrWordEntity;

public interface OcrWordRepository extends CrudRepository<OcrWordEntity, Long>, OcrWordRepositoryTemplate {

    @Override
    Optional<OcrWord> findByOcrWordId(OcrWordId ocrWordId);

    @Override
    int countByOcrWordIdBookIdAndOcrWordIdPageImageId(long bookId, long pageImageId);

    @Override
    List<OcrWord> findByOcrWordIdBookIdAndOcrWordIdPageImageIdOrderByOcrWordIdWordSequenceId(long bookId, long pageImageId);

}
