package com.w83ll43.alliance.apis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.w83ll43.alliance.apis.model.entity.Joke;

public interface JokeService extends IService<Joke> {
    /**
     * 随机获取笑话
     * @return 笑话
     */
    Joke getRandomJoke();
}
