package com.w83ll43.alliance.apis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w83ll43.alliance.apis.mapper.JokeMapper;
import com.w83ll43.alliance.apis.model.entity.Joke;
import com.w83ll43.alliance.apis.service.JokeService;
import org.springframework.stereotype.Service;

@Service
public class JokeServiceImpl extends ServiceImpl<JokeMapper, Joke> implements JokeService {

    /**
     * 随机获取笑话
     * @return 笑话
     */
    @Override
    public Joke getRandomJoke() {
        return this.baseMapper.getRandomJoke();
    }
}




