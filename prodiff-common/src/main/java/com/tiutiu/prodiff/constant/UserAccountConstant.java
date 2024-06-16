package com.tiutiu.prodiff.constant;

public class UserAccountConstant {
    public static final Long GUEST_USER_ID = 0L;
    public static final String LOGIN_ERROR = "邮箱或密码错误";
    public static final String ACCOUNT_NULL = "用户不存在";
    public static final String REGISTER_CODE_NULL = "验证码无效，请重新获取";
    public static final String REGISTER_CODE_ERROR = "验证码错误，请重新输入";
    public static final String REGISTER_EMAIL_EXIST = "邮箱已被注册";

    public static final String VERIFY_EMAIL_DATA = "verify:email:data:";
    public static final String INVALID_TOKEN = "token:invalid:";

    public static final String MQ_MAIL = "mail";
}
