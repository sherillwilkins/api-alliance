package com.w83ll43.alliance.common.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户接口调用表
 */
@Data
@TableName(value = "user_api_invoke")
public class UserApiInvoke implements Serializable {
    /**
     * 接口调用 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 调用者 ID
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 调用的接口 ID
     */
    @TableField(value = "aid")
    private Long aid;

    /**
     * 总调用次数
     * 用户调用某个接口的总调用次数
     */
    @TableField(value = "total")
    private Long total;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}