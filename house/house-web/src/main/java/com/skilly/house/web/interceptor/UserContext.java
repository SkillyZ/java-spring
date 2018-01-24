package com.skilly.house.web.interceptor;

import com.skilly.house.common.model.User;

/**
 * Created by ${1254109699@qq.com} on 2018/1/24.
 */
public class UserContext {
    private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_HODLER.set(user);
    }

    public static void remove() {
        USER_HODLER.remove();
    }

    public static User getUser() {
        return USER_HODLER.get();
    }
}
