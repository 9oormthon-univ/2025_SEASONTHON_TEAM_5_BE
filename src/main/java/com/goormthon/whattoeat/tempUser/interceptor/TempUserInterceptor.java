package com.goormthon.whattoeat.tempUser.interceptor;

import com.goormthon.whattoeat.tempUser.utils.TempUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TempUserInterceptor implements HandlerInterceptor {

    private final TempUserUtils tempUserUtils;

    public TempUserInterceptor(TempUserUtils tempUserUtils) {
        this.tempUserUtils = tempUserUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        String tempUserId = tempUserUtils.getOrCreateTempUserId(session);
        tempUserUtils.setCurrentTempUserId(request, tempUserId);

        return true;
    }
}
