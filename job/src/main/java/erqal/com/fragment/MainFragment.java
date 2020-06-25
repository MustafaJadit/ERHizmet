package erqal.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import erqal.job.R;
import erqal.job.act.ControlAct;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;

/**
 * Created by samijan on 2015/11/30.
 */
public class MainFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, null);
        view.setOnClickListener(null);
        setListener();
        return view;
    }

    private void setListener() {
        ImageView imageView = (ImageView) view.findViewById(R.id.top_title_user);
        LinearLayout get_find_person = (LinearLayout) view.findViewById(R.id.get_find_person);
        LinearLayout get_job_person = (LinearLayout) view.findViewById(R.id.get_job_person);
        LinearLayout publishResume = (LinearLayout) view.findViewById(R.id.publish_resume);
        LinearLayout publishJob = (LinearLayout) view.findViewById(R.id.publish_job);

        //处理user图标点击事件
        imageView.setOnClickListener(new EventListenet());
        //
        get_find_person.setOnClickListener(new EventListenet());

        get_job_person.setOnClickListener(new EventListenet());

        publishJob.setOnClickListener(new EventListenet());

        publishResume.setOnClickListener(new EventListenet());
    }

    public static Fragment getInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    //处理点击事件
    private class EventListenet implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_title_user:
                    //已经登录
                    if (!PreferenceUtil.getString("login_status", "not_login").equals("not_login")) {
                        ControlAct.startUp(getActivity(), Constants.MeFragment, null);
                    } else {
                        ControlAct.startUp(getActivity(), Constants.LoginFragment, null);
                    }
                    break;
                case R.id.get_find_person:
                    ControlAct.startUp(getActivity(), Constants.FindPersonFragment, null);
                    break;
                case R.id.get_job_person:
                    ControlAct.startUp(getActivity(), Constants.FindJObFragment, null);
                    break;
                case R.id.publish_job:
                    if (!PreferenceUtil.getString("login_status", "no_login").equals("no_login")) {
                        ControlAct.startUp(getActivity(), Constants.ReleaseRecruitmentInFoFragment, null);
                    } else {
                        ControlAct.startUp(getActivity(), Constants.LoginFragment, null);
                    }
                    break;
                case R.id.publish_resume:
                    if (!PreferenceUtil.getString("login_status", "no_login").equals("no_login")) {
                        ControlAct.startUp(getActivity(), Constants.ReleaseResumeBasicInfoFragment, null);
                    } else {
                        ControlAct.startUp(getActivity(), Constants.LoginFragment, null);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
