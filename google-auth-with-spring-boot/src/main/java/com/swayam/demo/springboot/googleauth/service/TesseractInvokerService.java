package com.swayam.demo.springboot.googleauth.service;

import java.io.IOException;
import java.nio.file.Path;

import com.swayam.demo.springboot.googleauth.model.old.Language;

public interface TesseractInvokerService {

    String submitToOCR(Language language, Path imagePath) throws IOException;

}
