package com.w83ll43.alliance.apis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.w83ll43.alliance.apis.model.entity.Sentence;

public interface SentenceService extends IService<Sentence> {

    /**
     * 获取随机句子
     * @return 随机句子
     */
    Sentence getRandomSentence();

    /**
     * 获取指定类型的随机句子
     * @param type 句子类型
     * @return 随机句子
     */
    Sentence getRandomSentenceByType(String type);
}
