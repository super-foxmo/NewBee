/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2022 程序员十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package foxmo.user.cloud.newbee.config.handler;

import foxmo.common.cloud.newbee.enums.ServiceResultEnum;
import foxmo.common.cloud.newbee.exception.NewBeeMallException;
import foxmo.common.cloud.newbee.pojo.AdminUserToken;
import foxmo.user.cloud.newbee.config.annotation.TokenToAdminUser;
import foxmo.user.cloud.newbee.dao.AdminUserMapper;
import foxmo.user.cloud.newbee.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AdminUserMapper adminUserMapper;

    public TokenToAdminUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == 32) {
                ValueOperations<String, AdminUserToken> opsForAdminUserToken = redisTemplate.opsForValue();
                AdminUserToken adminUserToken = opsForAdminUserToken.get(token);
                if (adminUserToken == null) {
                    NewBeeMallException.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
                }
                AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminUserToken.getAdminUserId());
                if (adminUser == null){
                    NewBeeMallException.fail(ServiceResultEnum.ADMIN_NULL_ERROR.getResult());
                }
                if (adminUser.getLocked().intValue() == 1){
                    NewBeeMallException.fail(ServiceResultEnum.ADMIN_LOGIN_LOCKED_ERROR.getResult());
                }
                return adminUserToken;
            } else {
                NewBeeMallException.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
            }
        }
        return null;
    }

}
