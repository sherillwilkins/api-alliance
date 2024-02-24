package com.w83ll43.alliance.apis.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "joke")
public class Joke implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 4459413523818912420L;

    /**
     * 文本ID
     */
    @TableId(type = IdType.AUTO)
    private Integer tid;

    /**
     * 文本
     */
    private String text;
}