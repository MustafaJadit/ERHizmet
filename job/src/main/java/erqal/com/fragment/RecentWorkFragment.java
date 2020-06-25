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

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.keyboard.KeyboardUtil;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;
import erqal.job.widget.UAutoCompleteTextView;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UEditText;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecentWorkFragment extends Fragment {

    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.company_name)
    UEditText companyName;
    @Bind(R.id.career)
    UAutoCompleteTextView career;
    @Bind(R.id.work_time)
    UEditText workTime;
    @Bind(R.id.extra)
    UEditText extra;
    @Bind(R.id.save)
    UButton save;
    KeyboardUtil keyboardUtil;

    public static Fragment getInstance() {
        RecentWorkFragment fragment = new RecentWorkFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.recent_work, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.recent_work);
        topBack.setOnClickListener(mOnclick);
        save.setOnClickListener(mOnclick);

        if (PreferenceUtil.getString("language", "default").equals(Constants.lang_ug)) {
            companyName.setInputType(InputType.TYPE_NULL);
            companyName.setOnFocusChangeListener(mOnfocus);
            career.setInputType(InputType.TYPE_NULL);
            career.setOnFocusChangeListener(mOnfocus);
            workTime.setInputType(InputType.TYPE_NULL);
            workTime.setOnFocusChangeListener(mOnfocus);
            extra.setInputType(InputType.TYPE_NULL);
            extra.setOnFocusChangeListener(mOnfocus);
        }
    }

    View.OnFocusChangeListener mOnfocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.company_name:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), companyName,mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.career:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), career,mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.work_time:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), workTime,mainView);
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
                default:
                    break;
            }
        }
    };

    private boolean validate() {
        if (companyName.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_company_name, Toast.LENGTH_LONG).show();
            return false;
        } else if (companyName.getText().length() < 2) {
            UToast.mText(getContext(), R.string.company_name_format_error, Toast.LENGTH_LONG).show();
            return false;
        } else if (career.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_career, Toast.LENGTH_LONG).show();
            return false;
        } else if (workTime.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_time, Toast.LENGTH_LONG).show();
            return false;
        }else if (extra.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_extra, Toast.LENGTH_LONG).show();
            return false;
        }else if (extra.getText().toString().length() < 10) {
            UToast.mText(getContext(), R.string.extra_format, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
