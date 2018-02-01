package com.skilly.house.user.common;

import com.google.common.collect.ImmutableMap;
import com.skilly.house.user.exception.IllegalParamsException;
import com.skilly.house.user.exception.WithTypeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.omg.CORBA.UserException;

/**
 * Created by ${1254109699@qq.com} on 2018/1/31.
 */
public class Exception2CodeRepo {


    private static final ImmutableMap<Object, RestCode> MAP = ImmutableMap.<Object, RestCode>builder()
            .put(IllegalParamsException.Type.WRONG_PAGE_NUM,RestCode.WRONG_PAGE)
            .put(IllegalStateException.class,RestCode.UNKNOWN_ERROR)
            /*.put(UserException.Type.USER_NOT_LOGIN,RestCode.TOKEN_INVALID)
            .put(UserException.Type.USER_NOT_FOUND,RestCode.USER_NOT_EXIST)
            .put(UserException.Type.USER_AUTH_FAIL,RestCode.USER_NOT_EXIST)*/.build();

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

        RestCode restCode =  MAP.get(target);
        if (restCode != null) {
            return restCode;
        }


        Throwable rootCause = ExceptionUtils.getRootCause(throwable); //递归获取最底层异常
        if (rootCause != null) {
            return getCode(rootCause);
        }
        return RestCode.UNKNOWN_ERROR;
    }
}
