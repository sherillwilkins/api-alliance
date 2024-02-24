package com.w83ll43.alliance.apis.controller;

import com.w83ll43.alliance.apis.model.entity.Joke;
import com.w83ll43.alliance.apis.service.JokeService;
import com.w83ll43.alliance.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joke")
public class JokeController {

    @Autowired
    private JokeService jokeService;

    @GetMapping
    public Result<Joke> getRandomJoke() {
        return Result.success(jokeService.getRandomJoke());
    }
}
