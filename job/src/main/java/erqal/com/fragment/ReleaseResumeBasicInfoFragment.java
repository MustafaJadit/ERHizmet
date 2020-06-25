package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.keyboard.KeyboardUtil;
import erqal.job.net.Constants;
import erqal.job.pickerview.OptionsPopupWindow;
import erqal.job.util.PreferenceUtil;
import erqal.job.util.RefreshUIData;
import erqal.job.widget.UAutoCompleteTextView;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UEditText;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class ReleaseResumeBasicInfoFragment extends Fragment {


    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.user_picture)
    ImageView userPicture;
    @Bind(R.id.person_name)
    UEditText personName;
    @Bind(R.id.sex)
    UTextView sex;
    @Bind(R.id.birth_date)
    UTextView birthDate;
    @Bind(R.id.degree)
    UTextView degree;
    @Bind(R.id.work_experience)
    UTextView workExperience;
    @Bind(R.id.phone_number)
    UEditText phoneNumber;
    @Bind(R.id.salary)
    UTextView salary;
    @Bind(R.id.desire_occupation)
    UAutoCompleteTextView desireOccupation;
    @Bind(R.id.job_search_area)
    UTextView jobSearchArea;
    @Bind(R.id.publish)
    UButton publish;
    @Bind(R.id.introduce_myself)
    UEditText introduceMyself;
    KeyboardUtil keyboardUtil;
    UTextView pvTitle;

    OptionsPopupWindow areaSelection, timeSelection, sexSelection, degreeSelection, workExperienceSelection, salarySelection;
    private ArrayList<String> sexArray = new ArrayList<String>();
    private ArrayList<String> degreeArray = new ArrayList<String>();
    private ArrayList<String> workExperienceArray = new ArrayList<String>();
    private ArrayList<String> salaryArray = new ArrayList<String>();
    private ArrayList<String> timeArray = new ArrayList<String>();
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static Fragment getInstance() {
        ReleaseResumeBasicInfoFragment fragment = new ReleaseResumeBasicInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.release_resume_basic_info, null);
        pvTitle = (UTextView) mainView.findViewById(R.id.pv_title);
        ButterKnife.bind(this, mainView);
        timeListener();
        areaListener();
        sexListener();
        degreeListener();
        workExperienceListener();
        salaryListener();
        setListener();
        return mainView;
    }

    private void areaListener() {
        //地址选择器
        areaSelection = new OptionsPopupWindow(getContext(),getResources().getString(R.string.job_area));
        //选项1
        options1Items.add("广东");
        options1Items.add("湖南");
        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        ArrayList<String> options2Items_02 = new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);

        //选项3
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();

        ArrayList<String> options3Items_01_03 = new ArrayList<String>();
        ArrayList<String> options3Items_01_01 = new ArrayList<String>();

        ArrayList<String> options3Items_01_02 = new ArrayList<String>();
        ArrayList<String> options3Items_02_01 = new ArrayList<String>();
        ArrayList<String> options3Items_02_02 = new ArrayList<String>();

        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);


        options3Items_01_02.add("南海");
        options3Items_01.add(options3Items_01_02);
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01.add(options3Items_01_03);

        options3Items_02_01.add("长沙1");
        options3Items_02.add(options3Items_02_01);
        options3Items_02_02.add("岳1");
        options3Items_02.add(options3Items_02_02);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);


        //三级联动效果
        areaSelection.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        areaSelection.setLabels("省", "市()", "区");
        //设置默认选中的三级项目
        areaSelection.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        areaSelection.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1) +
                        options2Items.get(options1).get(option2) +
                        options3Items.get(options1).get(option2).get(options3);
                jobSearchArea.setText(tx);
            }
        });

        //点击弹出选项选择器
        jobSearchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaSelection.showAtLocation(jobSearchArea, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void timeListener() {
        timeSelection = new OptionsPopupWindow(getContext(),getResources().getString(R.string.birth_date));
        ArrayList<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for (int year = currentYear; year > currentYear - 60; year--) {
            list.add(String.valueOf(year));
        }
        timeArray = list;
        timeSelection.setPicker(timeArray);
        timeSelection.setSelectOptions(25);
        timeSelection.setOnoptionSingleSelect(new OptionsPopupWindow.OnOptionSingleSelect() {
            @Override
            public void onOptionsSelect(int options1) {
                String tx = timeArray.get(options1);
                birthDate.setText(tx);
            }
        });
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelection.showAtLocation(birthDate, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void sexListener() {
        sexSelection = new OptionsPopupWindow(getContext(), getResources().getString(R.string.sex));
        sexArray.add("342343");
        sexArray.add("4324234234");
        sexSelection.setPicker(sexArray);
        sexSelection.setOnoptionSingleSelect(new OptionsPopupWindow.OnOptionSingleSelect() {
            @Override
            public void onOptionsSelect(int options1) {
                String tx = sexArray.get(options1);
                sex.setText(tx);
            }
        });

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sexSelection.showAtLocation(sex, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void degreeListener() {
        degreeSelection = new OptionsPopupWindow(getContext(),getResources().getString(R.string.degree));
        degreeArray.add("男");
        degreeArray.add("女");
        degreeSelection.setPicker(degreeArray);
        degreeSelection.setOnoptionSingleSelect(new OptionsPopupWindow.OnOptionSingleSelect() {
            @Override
            public void onOptionsSelect(int options1) {
                String tx = degreeArray.get(options1);
                degree.setText(tx);
            }
        });
        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degreeSelection.showAtLocation(degree, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void workExperienceListener() {
        workExperienceSelection = new OptionsPopupWindow(getContext(),getResources().getString(R.string.work_experience));
        workExperienceArray.add("男");
        workExperienceArray.add("女");
        workExperienceSelection.setPicker(workExperienceArray);
        workExperienceSelection.setOnoptionSingleSelect(new OptionsPopupWindow.OnOptionSingleSelect() {
            @Override
            public void onOptionsSelect(int options1) {
                String tx = workExperienceArray.get(options1);
                workExperience.setText(tx);
            }
        });
        workExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workExperienceSelection.showAtLocation(workExperience, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void salaryListener() {
        salarySelection = new OptionsPopupWindow(getContext(),getResources().getString(R.string.salary));
        salaryArray.add("男");
        salaryArray.add("女");
        salarySelection.setPicker(salaryArray);
        salarySelection.setOnoptionSingleSelect(new OptionsPopupWindow.OnOptionSingleSelect() {
            @Override
            public void onOptionsSelect(int options1) {
                String tx = salaryArray.get(options1);
                salary.setText(tx);
            }
        });
        salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salarySelection.showAtLocation(salary, Gravity.BOTTOM, 0, 0);
            }
        });
    }


    private void setListener() {

        topTitle.setText(R.string.basic_info);
        topBack.setOnClickListener(mOnclick);
        publish.setOnClickListener(mOnclick);
        desireOccupation.setOnClickListener(mOnclick);
        if (PreferenceUtil.getString("language", "default").equals(Constants.lang_ug)) {
            personName.setInputType(InputType.TYPE_NULL);
            personName.setOnFocusChangeListener(mFoucusListenr);
            introduceMyself.setInputType(InputType.TYPE_NULL);
            introduceMyself.setOnFocusChangeListener(mFoucusListenr);
            desireOccupation.setInputType(InputType.TYPE_NULL);
            desireOccupation.setOnFocusChangeListener(mFoucusListenr);
        }

    }

    View.OnFocusChangeListener mFoucusListenr = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.person_name:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), personName, mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.introduce_myself:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), introduceMyself, mainView);
                        keyboardUtil.showKeyboard();
                    } else {
                        if (keyboardUtil != null) {
                            keyboardUtil.hideCustomKeyboard();
                        }
                    }
                    break;
                case R.id.desire_occupation:
                    if (hasFocus) {
                        keyboardUtil = new KeyboardUtil(getActivity(), desireOccupation, mainView);
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


    //创建一个刷新activity的接口实例
    private RefreshUIData refreshActivities = new RefreshUIData() {
        @Override
        public void refreshSex(String text) {
            sex.setText(text);

        }

        @Override
        public void refreshDegree(String text) {
            degree.setText(text);
        }

        @Override
        public void refreshWorkExperience(String text) {
            workExperience.setText(text);
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
            birthDate.setText(String.valueOf(year));
        }

        @Override
        public void refreshCareer(String text) {

        }


    };

    private boolean validate() {

        if (personName.getText().toString().equals("")) {
            UToast.mText(getContext(), R.string.please_input_name, Toast.LENGTH_LONG).show();
            return false;
        } else if (personName.getText().toString().length() < 2) {
            UToast.mText(getContext(), R.string.please_input_right_name, Toast.LENGTH_LONG).show();
            return false;
        } else if (sex.getText().toString().equals(getResources().getString(R.string.sex))) {
            UToast.mText(getContext(), R.string.select_sex, Toast.LENGTH_LONG).show();
            return false;
        } else if (birthDate.getText().toString().equals(getResources().getString(R.string.birth_date))) {
            UToast.mText(getContext(), R.string.select_date, Toast.LENGTH_LONG).show();
            return false;
        } else if (degree.getText().toString().equals(getResources().getString(R.string.degree))) {
            UToast.mText(getContext(), R.string.select_degree, Toast.LENGTH_LONG).show();
            return false;
        } else if (workExperience.getText().toString().equals(getResources().getString(R.string.work_experience))) {
            UToast.mText(getContext(), R.string.work_experience, Toast.LENGTH_LONG).show();
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
