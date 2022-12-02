package foxmo.goods.cloud.newbee.config.handler;

import foxmo.common.cloud.newbee.dto.Result;
import foxmo.common.cloud.newbee.exception.NewBeeMallException;
import foxmo.goods.cloud.newbee.config.annotation.TokenToAdminUser;
import foxmo.user.cloud.newbee.openfeign.NewBeeCloudUserServiceFeign;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;



@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private NewBeeCloudUserServiceFeign newBeeCloudAdminUserServiceFeign;

    public TokenToAdminUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == 32) {
                // 通过用户中心获取用户信息
                Result result = newBeeCloudAdminUserServiceFeign.getAdminUserByToken(token);

                if (result == null || result.getResultCode() != 200 || result.getData() == null) {
                    NewBeeMallException.fail("ADMIN_NOT_LOGIN_ERROR");
                }

//                LinkedHashMap resultData = (LinkedHashMap) result.getData();
//
//                // 将返回的字段封装到LoginAdminUser对象中
//                LoginAdminUser loginAdminUser = new LoginAdminUser();
//                loginAdminUser.setAdminUserId(Long.valueOf(resultData.get("adminUserId").toString()));
//                loginAdminUser.setLoginUserName((String) resultData.get("loginUserName"));
//                loginAdminUser.setNickName((String) resultData.get("nickName"));
//                loginAdminUser.setLocked(Byte.valueOf(resultData.get("locked").toString()));
                return result.getData();
            } else {
                NewBeeMallException.fail("ADMIN_NOT_LOGIN_ERROR");
            }
        }
        return null;
    }

}


