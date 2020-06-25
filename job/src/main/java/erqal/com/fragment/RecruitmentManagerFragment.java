package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.act.ControlAct;
import erqal.job.net.Constants;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecruitmentManagerFragment extends Fragment {
    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.organize_info)
    UTextView organizeInfo;
    @Bind(R.id.recruit_info)
    UTextView recruitInfo;
    @Bind(R.id.recruit_record)
    UTextView recruitRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.recruitment_manager, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.recruitment_manager);
        topBack.setOnClickListener(mOnclick);
        organizeInfo.setOnClickListener(mOnclick);
        recruitInfo.setOnClickListener(mOnclick);
        recruitRecord.setOnClickListener(mOnclick);
    }

    View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.organize_info:
                    ControlAct.startUp(getActivity(), Constants.OrganizeInfoFragment, null);
                    break;
                case R.id.recruit_info:
                    ControlAct.startUp(getActivity(), Constants.RecruitmentInfoFragment, null);
                    break;
                default:
                    break;
            }
        }
    };

    public static Fragment getInstance() {
        RecruitmentManagerFragment fragment = new RecruitmentManagerFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
