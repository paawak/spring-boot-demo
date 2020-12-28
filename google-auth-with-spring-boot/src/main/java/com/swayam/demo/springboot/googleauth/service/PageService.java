package com.swayam.demo.springboot.googleauth.service;

import java.util.List;

import com.swayam.demo.springboot.googleauth.model.old.PageImage;

public interface PageService {

    PageImage addPageImage(PageImage pageImage);

    PageImage getPageImage(long pageImageId);

    int getPageCount(long bookId);

    List<PageImage> getPages(long bookId);

    int markPageAsIgnored(long pageImageId);

    int markPageAsCorrectionCompleted(long pageImageId);

}
