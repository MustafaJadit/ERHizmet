package erqal.job.net;

/**
 * 保存有关的常量
 */
public interface Constants {
    //启动广告显示还是不现实
    public static int ADVER_SHOW_YES = 1;
    public static int ADVER_SHOW_NO = 0;
    //数据库
    public static String DATABASE = "YES";
    //密码错误
    public static int PASSWORD_ERROR = 90;
    //用户不存在
    public static int USER_NOT_EXIT = 91;
    //用户被锁定
    public static int USER_LOCKED = 92;
    //网络错误
    public static int NETWORK_ERROR = 93;
    //验证码错误
    public static int VARIFY_CODE_ERROR = 94;
    //用户已存在
    public static int ACCOUNT_ALREADY_EXIT = 95;
    //成功代码
    public static int SUCCESS = 99;
    //维吾尔语
    public String lang_ug = "ZH_ug";
    //汉语
    public String lang_zh = "ZH_cn";
    //当前语言
    public String lang = "ZH_ug";

    public static int FindPasswordFragment = 1;
    public static int FindPersonFragment = 2;
    public static int LoginFragment = 3;
    public static int MainFragment = 4;
    public static int MeFragment = 5;
    public static int RegisterFragment = 6;
    public static int ResetPasswordFragment = 7;
    public static int SettingFragment = 8;
    public static int FindJObFragment = 9;
    public static int DegreeFragment = 13;
    public static int RecentWorkFragment = 14;
    public static int ResumeManagerFragment = 15;
    public static int RecruitmentRecordFragment = 16;
    public static int RecruitmentInfoFragment = 17;
    public static int OrganizeInfoFragment = 18;
    public static int RecruitmentManagerFragment = 19;
    public static int ReleaseResumeBasicInfoFragment = 20;
    public static int ReleaseRecruitmentInFoFragment = 21;
    public static int AccountFragment = 23;
}
