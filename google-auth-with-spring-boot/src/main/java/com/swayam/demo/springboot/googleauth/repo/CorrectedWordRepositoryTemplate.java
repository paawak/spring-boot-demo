package com.swayam.demo.springboot.googleauth.repo;

import java.util.Optional;

import com.swayam.demo.springboot.googleauth.model.old.CorrectedWord;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;

public interface CorrectedWordRepositoryTemplate {

    Optional<CorrectedWord> findByOcrWordIdAndUser(long ocrWordId, UserDetails user);

    int updateCorrectedText(long ocrWordId, String correctedText, UserDetails user);

    int markAsIgnored(long ocrWordId, UserDetails user);

    CorrectedWord save(CorrectedWord entity);

}
