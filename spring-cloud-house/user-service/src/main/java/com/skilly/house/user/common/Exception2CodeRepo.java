package com.skilly.house.user.common;

import com.skilly.house.user.exception.WithTypeException;
import org.apache.commons.lang.reflect.FieldUtils;

/**
 * Created by ${1254109699@qq.com} on 2018/1/31.
 */
public class Exception2CodeRepo {

    private static Object getType(Throwable throwable){
        try {
            return FieldUtils.readDeclaredField(throwable, "type", true);
        } catch (Exception e) {
            return null;
        }
    }


    public static RestCode getCode(Throwable throwable) {
        if (throwable == null) {
            return RestCode.UNKNOWN_ERROR;
        }
        Object target = throwable;
        if (throwable instanceof WithTypeException) {
            Object type = getType(throwable);
            if (type != null) {
                target = type;
            }
        }
        return null;
    }
}
