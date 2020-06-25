package erqal.com.fragment;

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

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.dialogue.SelectSalary;
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
 * Created by Administrator on 2015/12/12 0012.
 */
public class ReleaseRecruitmentInfoFragment extends Fragment {

    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.career)
    UAutoCompleteTextView career;
    @Bind(R.id.area)
    UTextView area;
    @Bind(R.id.salary)
    UTextView salary;
    @Bind(R.id.phone_number)
    UEditText phoneNumber;
    @Bind(R.id.introduce_career)
    UEditText introduceCareer;
    @Bind(R.id.publish)
    UButton publish;
    KeyboardUtil keyboardUtil;

    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static Fragment getInstance() {
        ReleaseRecruitmentInfoFragment fragment = new ReleaseRecruitmentInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.release_recruitment_info, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.release_recruitment);
        topBack.setOnClickListener(mOnclickListener);
        area.setOnClickListener(mOnclickListener);
        salary.setOnClickListener(mOnclickListener);
        publish.setOnClickListener(mOnclickListener);

        if (PreferenceUtil.getString("language", "default").equals(Constants.lang_ug)) {
            career.setInputType(InputType.TYPE_NULL);
            career.setOnFocusChangeListener(mOnFocus);
            introduceCareer.setInputType(InputType.TYPE_NULL);
            introduceCareer.setOnFocusChangeListener(mOnFocus);
        }
    }

    View.OnFocusChangeListener mOnFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.career:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), career, mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.introduce_career:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), introduceCareer, mainView);
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

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.area:
                    showArea();
                    break;
                case R.id.salary:
                    showSalary();
                    break;
                case R.id.publish:
                    if (validate()) {
                        UToast.mText(getContext(), R.string.release_success, Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void showArea() {

    }

    private void showSalary() {
        SelectSalary dialog = new SelectSalary(getContext(), refreshActivities);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    public RefreshUIData refreshActivities = new RefreshUIData() {
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
            salary.setText(text);
        }

        @Override
        public void refreshArea(String text) {

        }

        @Override
        public void refreshYear(int year) {

        }

        @Override
        public void refreshCareer(String text) {

        }
    };

    private boolean validate() {
        if (career.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_career, Toast.LENGTH_LONG).show();
            return false;
        } else if (salary.getText().toString().equals(getResources().getString(R.string.salary))) {
            UToast.mText(getContext(), R.string.please_input_salary, Toast.LENGTH_LONG).show();
            return false;
        } else if (phoneNumber.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_phone_num, Toast.LENGTH_LONG).show();
            return false;
        } else if (!isMobile(phoneNumber.getText().toString())) {
            UToast.mText(getContext(), R.string.login_message_text_login_error_username_format, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
