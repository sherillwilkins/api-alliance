package com.w83ll43.alliance.apis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w83ll43.alliance.apis.model.entity.Sentence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    Sentence getRandomSentence();

    Sentence getRandomSentence(String type);
}




