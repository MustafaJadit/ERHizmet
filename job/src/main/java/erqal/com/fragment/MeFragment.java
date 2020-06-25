package erqal.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.act.ControlAct;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/11/30 0030.
 */
public class MeFragment extends Fragment {

    private String id;
    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.account_text)
    UTextView accountText;
    @Bind(R.id.account)
    LinearLayout account;
    @Bind(R.id.recruit_info)
    LinearLayout recruitInfo;
    @Bind(R.id.resume_info)
    LinearLayout resumeInfo;
    @Bind(R.id.setting)
    LinearLayout setting;
    @Bind(R.id.exit)
    UButton exit;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        readArgument(getArguments());
        mainView = inflater.inflate(R.layout.me_list, null);
        ButterKnife.bind(this, mainView);
        mainView.setOnClickListener(null);
        setListener();
        setTitle();
        return mainView;
    }

    private void readArgument(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    //打开Fragment
    private void openFragment(Fragment fragment) {
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_exchange_right_in, R.anim.activity_exchange_no_anim, R.anim.activity_exchange_no_anim, R.anim.activity_exchange_right_out);
        transaction.add(R.id.main_act_fram, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setListener() {
        exit.setOnClickListener(new mOnclickListener());
        topBack.setOnClickListener(new mOnclickListener());
        account.setOnClickListener(new mOnclickListener());
        setting.setOnClickListener(new mOnclickListener());
        recruitInfo.setOnClickListener(new mOnclickListener());
        resumeInfo.setOnClickListener(new mOnclickListener());
    }


    //初始化Fragment
    public static Fragment getInstance(Bundle bundle) {
        MeFragment fragment = new MeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public class mOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.exit:
                    PreferenceUtil.commitString("login_status", null);
                    ControlAct.startUp(getActivity(), Constants.LoginFragment, null);
                    break;
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.account:
                    ControlAct.startUp(getActivity(), Constants.AccountFragment, null);
                    break;
                case R.id.setting:
                    ControlAct.startUp(getActivity(), Constants.SettingFragment, null);
                    break;
                case R.id.recruit_info:
                    ControlAct.startUp(getActivity(), Constants.RecruitmentManagerFragment, null);
                    break;
                case R.id.resume_info:
                    ControlAct.startUp(getActivity(), Constants.ResumeManagerFragment, null);
                    break;
                default:
                    break;
            }
        }
    }

    private void setTitle() {
        topTitle.setText(R.string.me);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
