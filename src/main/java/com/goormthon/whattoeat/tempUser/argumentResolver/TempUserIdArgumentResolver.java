package com.goormthon.whattoeat.tempUser.argumentResolver;

import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import com.goormthon.whattoeat.tempUser.utils.TempUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class TempUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final TempUserUtils tempUserUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TempUserId.class) &&
                parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        TempUserId annotation = parameter.getParameterAnnotation(TempUserId.class);

        try {
            return tempUserUtils.getCurrentTempUserId(request);
        } catch (IllegalStateException e) {
            // Interceptor에서 설정되지 않은 경우 직접 생성
            if (annotation.autoCreate()) {
                HttpSession session = request.getSession();
                String tempUserId = tempUserUtils.getOrCreateTempUserId(session);
                tempUserUtils.setCurrentTempUserId(request, tempUserId);
                return tempUserId;
            }
            throw e;
        }
    }
}
