package com.swayam.demo.springboot.googleauth.service;

import java.util.Collection;

import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;

public interface OcrWordService {

    int getWordCount(long bookId, long rawImageId);

    Collection<OcrWordOutputDto> getWords(long bookId, long pageImageId, UserDetails userDetails);

    OcrWord addOcrWord(OcrWord rawOcrWord);

    int updateCorrectTextInOcrWord(OcrWordId ocrWordId, String correctedText, UserDetails user);

    OcrWord getWord(OcrWordId ocrWordId);

    int markWordAsIgnored(OcrWordId ocrWordId, UserDetails user);

}
