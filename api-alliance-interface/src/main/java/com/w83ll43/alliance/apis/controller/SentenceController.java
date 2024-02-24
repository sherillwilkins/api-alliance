package com.w83ll43.alliance.apis.controller;

import com.w83ll43.alliance.apis.model.entity.Sentence;
import com.w83ll43.alliance.apis.service.SentenceService;
import com.w83ll43.alliance.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentence")
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    @GetMapping
    public Result<Sentence> getRandomSentence() {
        return Result.success(sentenceService.getRandomSentence());
    }

    @GetMapping("{type}")
    public Result<Sentence> getRandomSentenceByType(@PathVariable String type) {
        return Result.success(sentenceService.getRandomSentenceByType(type));
    }
}
