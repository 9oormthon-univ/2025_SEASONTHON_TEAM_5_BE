package com.goormthon.whattoeat.tempUser.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TempUserId {
    /**
     * 임시 사용자 ID가 없을 때 자동 생성 여부
     */
    boolean autoCreate() default true;
}
