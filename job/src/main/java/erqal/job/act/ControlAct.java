package erqal.job.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;

import erqal.com.fragment.AccountFragment;
import erqal.com.fragment.DegreeFragment;
import erqal.com.fragment.FindJobFragment;
import erqal.com.fragment.FindPasswordFragment;
import erqal.com.fragment.FindPersonFragment;
import erqal.com.fragment.LoginFragment;
import erqal.com.fragment.MainFragment;
import erqal.com.fragment.MeFragment;
import erqal.com.fragment.OrganizeInfoFragment;
import erqal.com.fragment.RecentWorkFragment;
import erqal.com.fragment.RecruitmentInfoFragment;
import erqal.com.fragment.RecruitmentManagerFragment;
import erqal.com.fragment.RegisterFragment;
import erqal.com.fragment.ReleaseRecruitmentInfoFragment;
import erqal.com.fragment.ResetPasswordFragment;
import erqal.com.fragment.ReleaseResumeBasicInfoFragment;
import erqal.com.fragment.ResumeManagerFragment;
import erqal.com.fragment.SettingFragment;
import erqal.job.R;
import erqal.job.net.Constants;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class ControlAct extends BaseActivity {
    private Fragment mFragment;
    private int mCode = -1;

    /**
     * @param baseAct
     * @param code
     * @param bundle  启动activity
     */
    public static void startUp(Activity baseAct, int code, Bundle bundle) {
        Intent intent = new Intent(baseAct, ControlAct.class);
        intent.putExtra("code", code);
        intent.putExtra("bundle", bundle);
        baseAct.startActivity(intent);
    }
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //管理Fragment
        switchFragment();
    }
    /**
     * 打开Fragment
     */
    private void switchFragment() {
        mCode = getIntent().getIntExtra("code", -1);
        switch (mCode) {
            case Constants.FindPasswordFragment:
                mFragment = FindPasswordFragment.getInstance();
                break;
            case Constants.FindPersonFragment:
                mFragment = FindPersonFragment.getInstance();
                break;
            case Constants.LoginFragment:
                //打开登陆
                mFragment = LoginFragment.getInstance();
                break;
            case Constants.MainFragment:
                mFragment = MainFragment.getInstance();
                break;
            case Constants.MeFragment:
                mFragment = MeFragment.getInstance(getIntent().getBundleExtra("bundle"));
                break;
            case Constants.RegisterFragment:
                mFragment = RegisterFragment.getInstance();
                break;
            case Constants.ResetPasswordFragment:
                mFragment = ResetPasswordFragment.getInstance();
                break;
            case Constants.SettingFragment:
                mFragment = SettingFragment.getInstance();
                break;
            case Constants.FindJObFragment:
                mFragment = FindJobFragment.getInstance();
                break;
            case Constants.ReleaseResumeBasicInfoFragment:
                mFragment = ReleaseResumeBasicInfoFragment.getInstance();
                break;
            case Constants.ReleaseRecruitmentInFoFragment:
                mFragment = ReleaseRecruitmentInfoFragment.getInstance();
                break;
            case Constants.AccountFragment:
                mFragment = AccountFragment.getInstance();
                break;
            case Constants.RecruitmentManagerFragment:
                mFragment = RecruitmentManagerFragment.getInstance();
                break;
            case Constants.OrganizeInfoFragment:
                mFragment = OrganizeInfoFragment.getInstance();
                break;
            case Constants.RecruitmentInfoFragment:
                mFragment = RecruitmentInfoFragment.getInstance();
                break;
            case Constants.ResumeManagerFragment:
                mFragment = ResumeManagerFragment.getInstance();
                break;
            case Constants.RecentWorkFragment:
                mFragment = RecentWorkFragment.getInstance();
                break;
            case Constants.DegreeFragment:
                mFragment = DegreeFragment.getInstance();
                break;
            default:
                break;
        }
        //提交
        getSupportFragmentManager().beginTransaction().add(R.id.main_act_fram, mFragment).commit();
    }
}
