package com.renaren.tools;

public class ConstUtil {
    //外网地址
    public final static String CONSTANT = "http://www.renaren.com/";
    //测试地址
//    public final static String CONSTANT = "http://192.168.8.229/test/";
    // 我要测评
    public final static String CP = CONSTANT + "api/app/cp.php";
    // 登录
    public final static String MEMBER = CONSTANT + "api/app/member.php";
    // 主页
    public final static String TEST = CONSTANT;
    // domain
    public final static String COOKIE_DOMAIN = ".renaren.com";
    // 论坛
    public final static String FORUM = CONSTANT + "bbs/forum.php?client=app";
    // 报告
    public final static String REPORT_PRINT = CONSTANT + "cp/index.php?action=report_print&filetype=png&codenum=";
    // 继续测评
    public final static String DETAIL_CODENUM = CONSTANT + "cp/index.php?action=cp&client=app&codenum=";
    // 测评说明
    public final static String DESCRIPTION = CONSTANT + "page/description.php?client=app";
    // 验证码
    public final static String CAPTCHA = CONSTANT + "captcha.php";
    // 注册
    public final static String REGISTER = CONSTANT + "api/app/member.php";
    // 培训
    public final static String TRAIN = CONSTANT + "page/train.php?client=app";
    // 忘记密码
    public final static String FORGET_PASSWORD = CONSTANT + "/member/forget.php?client=app&user_auth=";
    //版本升级
    public final static String VERUP = "http://192.168.8.204/test/api/app/version.php?device=android";

}
