package com.w83ll43.alliance.apis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w83ll43.alliance.apis.model.entity.Joke;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JokeMapper extends BaseMapper<Joke> {

    Joke getRandomJoke();
}




