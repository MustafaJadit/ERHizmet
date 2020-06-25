package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class ResumeManagerFragment extends Fragment {


    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.pregress_num)
    UTextView pregressNum;
    @Bind(R.id.progress_bar)
    View progressBar;
    @Bind(R.id.progress_bar_left)
    View progressBarLeft;
    @Bind(R.id.basic_info_status)
    UTextView basicInfoStatus;
    @Bind(R.id.basic_info)
    LinearLayout basicInfo;
    @Bind(R.id.recent_work_status)
    UTextView recentWorkStatus;
    @Bind(R.id.recent_work)
    LinearLayout recentWork;
    @Bind(R.id.degree_status)
    UTextView degreeStatus;
    @Bind(R.id.degree)
    LinearLayout degree;
    @Bind(R.id.resume_status)
    ImageView resumeStatus;
    @Bind(R.id.resume_record)
    UTextView resumeRecord;

    public static Fragment getInstance() {
        ResumeManagerFragment fragment = new ResumeManagerFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.resume_manager, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.resume);
        topBack.setOnClickListener(mOnclick);
        basicInfo.setOnClickListener(mOnclick);
        recentWork.setOnClickListener(mOnclick);
        degree.setOnClickListener(mOnclick);
    }

    View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.basic_info:
                    ControlAct.startUp(getActivity(), Constants.ReleaseResumeBasicInfoFragment, null);
                    break;
                case R.id.recent_work:
                    ControlAct.startUp(getActivity(), Constants.RecentWorkFragment, null);
                    break;
                case R.id.degree:
                    ControlAct.startUp(getActivity(), Constants.DegreeFragment, null);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
