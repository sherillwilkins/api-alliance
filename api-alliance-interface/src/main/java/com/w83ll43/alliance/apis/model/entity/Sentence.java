package com.w83ll43.alliance.apis.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sentence")
public class Sentence implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 6355628054045687685L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 句子UUID
     */
    private String uuid;

    /**
     * 句子
     */
    private String hitokoto;

    /**
     * 类型
     */
    private String type;

    /**
     * 来源
     * from 是 MySQL 的关键字、需要添加反引号
     */
    @TableField(value = "`from`")
    private String from;

    /**
     * 来自谁
     */
    private String fromWho;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建者ID
     */
    private Integer creatorUid;

    /**
     *
     */
    private Integer reviewer;

    /**
     *
     */
    private String commitFrom;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 句子长度
     */
    private Integer length;
}