package com.w83ll43.alliance.apis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w83ll43.alliance.apis.mapper.SentenceMapper;
import com.w83ll43.alliance.apis.model.entity.Sentence;
import com.w83ll43.alliance.apis.service.SentenceService;
import org.springframework.stereotype.Service;

@Service
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence> implements SentenceService {

    /**
     * 获取随机句子
     * @return 随机句子
     */
    @Override
    public Sentence getRandomSentence() {
        return this.baseMapper.getRandomSentence();
    }

    /**
     * 获取指定类型的随机句子
     * @param type 句子类型
     * @return 随机句子
     */
    @Override
    public Sentence getRandomSentenceByType(String type) {
        return this.baseMapper.getRandomSentence(type);
    }
}




