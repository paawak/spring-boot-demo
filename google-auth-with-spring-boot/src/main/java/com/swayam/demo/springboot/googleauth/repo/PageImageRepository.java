package com.swayam.demo.springboot.googleauth.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.swayam.demo.springboot.googleauth.model.old.PageImage;

public interface PageImageRepository extends CrudRepository<PageImage, Long> {

    int countByBookId(long bookId);

    List<PageImage> findByBookIdAndIgnoredIsFalseAndCorrectionCompletedIsFalseOrderById(long bookId);

    @Modifying
    @Query("update PageImage set ignored = TRUE where id = :id")
    int markPageAsIgnored(@Param("id") long id);

    @Modifying
    @Query("update PageImage set correctionCompleted = TRUE where id = :id")
    int markPageAsCorrectionCompleted(@Param("id") long id);

}
