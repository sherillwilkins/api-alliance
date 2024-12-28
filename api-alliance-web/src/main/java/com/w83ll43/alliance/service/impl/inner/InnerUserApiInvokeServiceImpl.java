package com.w83ll43.alliance.service.impl.inner;

import com.w83ll43.alliance.common.exception.BusinessException;
import com.w83ll43.alliance.common.model.entity.User;
import com.w83ll43.alliance.common.model.entity.UserApiInvoke;
import com.w83ll43.alliance.common.service.InnerUserApiInvokeService;
import com.w83ll43.alliance.service.UserApiInvokeService;
import com.w83ll43.alliance.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@DubboService
public class InnerUserApiInvokeServiceImpl implements InnerUserApiInvokeService {

    @Resource
    private UserService userService;

    @Resource
    private UserApiInvokeService userApiInvokeService;

    /**
     * 执行接口调用
     * @param aid    接口 ID
     * @param user   用户
     * @param reduce 扣除余额
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invoke(Long aid, User user, Long reduce) {
        // 判断余额是否充足
        if (user.getBalance() - 2 < 0) {
            throw new BusinessException(10001, "余额不足！");
        }

        // 查询用户调用该接口的调用记录
        Long uid = user.getId();
        UserApiInvoke userApiInvoke = userApiInvokeService.lambdaQuery()
                .eq(UserApiInvoke::getAid, aid)
                .eq(UserApiInvoke::getUid, uid)
                .one();
        // 如果调用记录不存在 则创建一条新调用记录
        if (userApiInvoke == null) {
            userApiInvoke = new UserApiInvoke();
            userApiInvoke.setAid(aid);
            userApiInvoke.setUid(uid);
            userApiInvoke.setTotal(1L);
            userApiInvoke.setCreateTime(new Date());
            userApiInvoke.setUpdateTime(new Date());
            userApiInvoke.setIsDelete(0);
            userApiInvokeService.save(userApiInvoke);
        } else {
            userApiInvokeService.lambdaUpdate()
                    .eq(UserApiInvoke::getAid, aid)
                    .eq(UserApiInvoke::getUid, uid)
                    .set(UserApiInvoke::getUpdateTime, new Date())
                    .set(UserApiInvoke::getTotal, userApiInvoke.getTotal() + 1)
                    .update();
        }

        // 更新用户钱包积分
        userService.reduceWalletBalance(uid, reduce);
        return true;
    }
}
