package com.swayam.demo.springboot.googleauth.rest.train;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrCorrectionInputDto;
import com.swayam.demo.springboot.googleauth.service.OcrWordService;
import com.swayam.demo.springboot.googleauth.service.PageService;

@RestController
@RequestMapping("/ocr/train/correction")
public class OCRCorrectionController {

    private final PageService pageService;
    private final OcrWordService ocrDataStoreService;

    public OCRCorrectionController(PageService pageService, OcrWordService ocrDataStoreService) {
	this.pageService = pageService;
	this.ocrDataStoreService = ocrDataStoreService;
    }

    @PutMapping(value = "/page/ignore/{pageImageId}")
    public ResponseEntity<Integer> markPageAsIgnored(@PathVariable("pageImageId") final long pageImageId) {
	int rowsAffected = pageService.markPageAsIgnored(pageImageId);
	return ResponseEntity.ok(rowsAffected);
    }

    @PutMapping(value = "/page/complete/{pageImageId}")
    public ResponseEntity<Integer> markPageAsCorrectionCompleted(@PathVariable("pageImageId") final long pageImageId) {
	int rowsAffected = pageService.markPageAsCorrectionCompleted(pageImageId);
	return ResponseEntity.ok(rowsAffected);
    }

    @PostMapping(value = "/word", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> applyCorrectionToOcrWords(@AuthenticationPrincipal Authentication authentication, @RequestBody final List<OcrCorrectionInputDto> ocrWordsForCorrection) {
	UserDetails userDetails = (UserDetails) authentication.getDetails();
	return ocrWordsForCorrection.stream()
		.map(ocrWordForCorrection -> ocrDataStoreService.updateCorrectTextInOcrWord(ocrWordForCorrection.getOcrWordId(), ocrWordForCorrection.getCorrectedText(), userDetails))
		.collect(Collectors.toUnmodifiableList());
    }

    @PostMapping(value = "/word/ignore", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> markOcrWordAsIgnored(@AuthenticationPrincipal Authentication authentication, @RequestBody final List<OcrWordId> wordsToIgnore) {
	UserDetails userDetails = (UserDetails) authentication.getDetails();
	return wordsToIgnore.stream().map(ocrWordId -> ocrDataStoreService.markWordAsIgnored(ocrWordId, userDetails)).collect(Collectors.toUnmodifiableList());
    }

}
