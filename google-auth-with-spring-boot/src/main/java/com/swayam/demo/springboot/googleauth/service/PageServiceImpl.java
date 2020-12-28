package com.swayam.demo.springboot.googleauth.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.googleauth.model.old.PageImage;
import com.swayam.demo.springboot.googleauth.repo.PageImageRepository;

@Service
public class PageServiceImpl implements PageService {

    private final PageImageRepository pageImageRepository;

    public PageServiceImpl(PageImageRepository pageImageRepository) {
	this.pageImageRepository = pageImageRepository;
    }

    @Override
    public PageImage addPageImage(PageImage pageImage) {
	return pageImageRepository.save(pageImage);
    }

    @Override
    public PageImage getPageImage(long pageImageId) {
	return pageImageRepository.findById(pageImageId).get();
    }

    @Override
    public int getPageCount(long bookId) {
	return pageImageRepository.countByBookId(bookId);
    }

    @Override
    public List<PageImage> getPages(long bookId) {
	return pageImageRepository.findByBookIdAndIgnoredIsFalseAndCorrectionCompletedIsFalseOrderById(bookId);
    }

    @Transactional
    @Override
    public int markPageAsIgnored(long pageImageId) {
	return pageImageRepository.markPageAsIgnored(pageImageId);
    }

    @Transactional
    @Override
    public int markPageAsCorrectionCompleted(long pageImageId) {
	return pageImageRepository.markPageAsCorrectionCompleted(pageImageId);
    }

}
