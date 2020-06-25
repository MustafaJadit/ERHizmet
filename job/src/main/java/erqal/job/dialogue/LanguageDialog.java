package erqal.job.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.act.WelcomeAct;
import erqal.job.net.Constants;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UButton;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class LanguageDialog extends Dialog {


    @Bind(R.id.ug)
    UButton ug;
    @Bind(R.id.zh)
    UButton zh;

    public LanguageDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_language);
        ButterKnife.bind(this);
        setLanguage();
    }

    private void setLanguage() {
    ug.setOnClickListener(onClickListener);
        zh.setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ug:
                    if (PreferenceUtil.getString("language", "defaultLan").equals(Constants.lang_ug)){
                        dismiss();
                    }else {
                        switchLanguage(Constants.lang_ug);
                        restart();
                    }

                    break;
                case R.id.zh:
                    if (PreferenceUtil.getString("language","defaultLan").equals(Constants.lang_zh)){
                        dismiss();
                    }else {
                        switchLanguage(Constants.lang_zh);
                       restart();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void restart() {
        Intent intent=new Intent();
        intent.setClass(getContext(), WelcomeAct.class);
        getContext().startActivity(intent);
    }

    //设置语言
    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getContext().getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals(Constants.lang_ug)) {
            config.locale = Locale.ROOT; //new Locale(Constants.lang_ug,"Uyghur");
        } else {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);
        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }

}
