package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.keyboard.KeyboardUtil;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UEditText;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class OrganizeInfoFragment extends Fragment {
    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.organize_picture)
    ImageView organizePicture;
    @Bind(R.id.organize_name)
    UEditText organizeName;
    @Bind(R.id.area)
    UTextView area;
    @Bind(R.id.phone_number)
    UEditText phoneNumber;
    @Bind(R.id.organize_info)
    UEditText organizeInfo;
    @Bind(R.id.save)
    UButton save;
    KeyboardUtil keyboardUtil;
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.organize_info, null);
        ButterKnife.bind(this, mainView);
        setOnClickListener();
        return mainView;
    }

    private void setOnClickListener() {
        topTitle.setText(R.string.organize_info);
        topBack.setOnClickListener(mOnclick);
        area.setOnClickListener(mOnclick);
        save.setOnClickListener(mOnclick);
        if (PreferenceUtil.getString("language", "default").equals(Constants.lang_ug)) {
            organizeName.setInputType(InputType.TYPE_NULL);
            organizeName.setOnFocusChangeListener(mFocus);
            organizeInfo.setInputType(InputType.TYPE_NULL);
            organizeInfo.setOnFocusChangeListener(mFocus);
        }
    }

    View.OnFocusChangeListener mFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.organize_name:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), organizeName, mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.organize_info:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), organizeInfo, mainView);
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
                case R.id.area:
                    showArea();
                    break;
                default:
                    break;

            }
        }
    };

    private void showArea() {

    }

    private boolean validate() {
        if (organizeName.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_organize_name, Toast.LENGTH_LONG).show();
            return false;
        } else if (organizeName.getText().toString().length() < 2) {
            UToast.mText(getContext(), R.string.organize_name_format_error, Toast.LENGTH_LONG).show();
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

    public static Fragment getInstance() {
        OrganizeInfoFragment fragment = new OrganizeInfoFragment();
        return fragment;
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
