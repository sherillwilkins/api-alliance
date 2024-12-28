package com.w83ll43.alliance.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 元对象处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 配置插入时需要填充的字段
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 注意该写法是 3.3.2 版本之后的
        // 插入数据填充更新时间
        this.strictInsertFill(metaObject, "updatedTime", Date::new, Date.class);
        // 插入数据填充插入时间
        this.strictInsertFill(metaObject, "createdTime", Date::new, Date.class);
        // 插入数据填充默认版本号为1
        // 只支持 int,Integer,long,Long,Date,Timestamp,LocalDateTime这些数据类型
        this.strictInsertFill(metaObject, "version", () -> 1, Integer.class);
        this.strictInsertFill(metaObject, "isDelete", () -> 0, Integer.class);
    }

    /**
     * 配置更新时需要填充的字段
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新数据填充更新时间
        this.strictInsertFill(metaObject, "updatedTime", Date::new, Date.class);
    }
}