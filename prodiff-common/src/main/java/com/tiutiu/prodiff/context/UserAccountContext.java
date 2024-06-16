package com.tiutiu.prodiff.context;

public class UserAccountContext {
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();
    public static Long getUserId() {
        return currentUserId.get();
    }
    public static void setUserId(Long id) {
        currentUserId.set(id);
    }
    public static void removeUserId() {
        currentUserId.remove();
    }

    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();
    public static String getToken() {
        return currentToken.get();
    }
    public static void setToken(String token) {
        currentToken.set(token);
    }
    public static void removeToken() {
        currentToken.remove();
    }
}
