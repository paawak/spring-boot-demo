package com.swayam.demo.springboot.googleauth.repo;

import java.util.List;
import java.util.Optional;

import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;

public interface OcrWordRepositoryTemplate {

    Optional<OcrWord> findByOcrWordId(OcrWordId ocrWordId);

    int countByOcrWordIdBookIdAndOcrWordIdPageImageId(long bookId, long pageImageId);

    List<OcrWord> findByOcrWordIdBookIdAndOcrWordIdPageImageIdOrderByOcrWordIdWordSequenceId(long bookId, long pageImageId);

    OcrWord save(OcrWord entity);

}
