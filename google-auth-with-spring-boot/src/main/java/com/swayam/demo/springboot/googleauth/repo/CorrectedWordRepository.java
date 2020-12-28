package com.swayam.demo.springboot.googleauth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.swayam.demo.springboot.googleauth.model.old.CorrectedWord;
import com.swayam.demo.springboot.googleauth.model.old.CorrectedWordEntity;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;

public interface CorrectedWordRepository extends CrudRepository<CorrectedWordEntity, Long>, CorrectedWordRepositoryTemplate {

    @Override
    Optional<CorrectedWord> findByOcrWordIdAndUser(long ocrWordId, UserDetails user);

    @Override
    @Modifying
    @Query("update CorrectedWordEntity set correctedText = :correctedText where ocrWordId = :ocrWordId and user = :user")
    int updateCorrectedText(@Param("ocrWordId") long ocrWordId, @Param("correctedText") String correctedText, @Param("user") UserDetails user);

    @Override
    @Modifying
    @Query("update CorrectedWordEntity set ignored = TRUE where ocrWordId = :ocrWordId and user = :user")
    int markAsIgnored(@Param("ocrWordId") long ocrWordId, @Param("user") UserDetails user);

}
