package erqal.com.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.dialogue.SelectDate;
import erqal.job.keyboard.KeyboardUtil;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;
import erqal.job.util.RefreshUIData;
import erqal.job.widget.UAutoCompleteTextView;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UEditText;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class DegreeFragment extends Fragment {

    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.school_name)
    UEditText schoolName;
    @Bind(R.id.major)
    UTextView major;
    @Bind(R.id.graduate_year)
    UTextView graduateYear;
    @Bind(R.id.extra)
    UEditText extra;
    @Bind(R.id.save)
    UButton save;
    KeyboardUtil keyboardUtil;

    public static Fragment getInstance() {
        DegreeFragment fragment = new DegreeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.degree_list, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.degree);
        topBack.setOnClickListener(mOnclick);
        major.setOnClickListener(mOnclick);
        graduateYear.setOnClickListener(mOnclick);
        save.setOnClickListener(mOnclick);

        if (PreferenceUtil.getString("language", "default").equals(Constants.lang_ug)) {
            schoolName.setInputType(InputType.TYPE_NULL);
            schoolName.setOnFocusChangeListener(mFocus);
            extra.setInputType(InputType.TYPE_NULL);
            extra.setOnFocusChangeListener(mFocus);
        }
    }

    View.OnFocusChangeListener mFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.school_name:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), schoolName,mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.extra:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), extra,mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };


    View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.save:
                    if (validate()) {
                        UToast.mText(getContext(), R.string.release_success, Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.major:
                    showMajor();
                    break;
                case R.id.graduate_year:
                    showDate();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDate() {
        SelectDate dialog = new SelectDate(getContext(), refreshActivities);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showMajor() {

    }

    private boolean validate() {
        if (schoolName.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_scholl_name, Toast.LENGTH_LONG).show();
            return false;
        } else if (schoolName.getText().length() < 2) {
            UToast.mText(getContext(), R.string.school_name_format_error, Toast.LENGTH_LONG).show();
            return false;
        } else if (major.getText().toString().equals(getResources().getString(R.string.major))) {
            UToast.mText(getContext(), R.string.please_input_major, Toast.LENGTH_LONG).show();
            return false;
        } else if (graduateYear.getText().toString().equals(getResources().getString(R.string.graduate_time))) {
            UToast.mText(getContext(), R.string.please_input_graduate_time, Toast.LENGTH_LONG).show();
            return false;
        } else if (extra.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_extra, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //创建一个刷新activity的接口实例
    private RefreshUIData refreshActivities = new RefreshUIData() {

        @Override
        public void refreshSex(String text) {

        }

        @Override
        public void refreshDegree(String text) {

        }

        @Override
        public void refreshWorkExperience(String text) {

        }

        @Override
        public void refreshSalary(String text) {

        }

        @Override
        public void refreshArea(String text) {

        }

        @Override
        public void refreshYear(int year) {
            graduateYear.setText(String.valueOf(year));
        }

        @Override
        public void refreshCareer(String text) {
            major.setText(text);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
