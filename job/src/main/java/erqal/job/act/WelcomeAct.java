package erqal.job.act;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ViewFlipper;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.net.Constants;
import erqal.job.util.BaseActivity;
import erqal.job.util.Config;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UButton;
/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class WelcomeAct extends BaseActivity {
    private static final int WELCOME = 1;
    private static final int ACTIVITY = 2;
    private Timer mTimer =null;
    @Bind(R.id.language_select_ug)
    UButton languageSelectUg;
    @Bind(R.id.language_select_cn)
    UButton languageSelectCn;
    @Bind(R.id.welcome_flipper)
    ViewFlipper welcomeFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        showAdver();
    }
    //打开MianActivity
    private void openMainActivity(){
        Intent mIntent = new Intent(this,MainAct.class);
        mIntent.putExtra("code", Constants.ADVER_SHOW_YES);
        startActivity(mIntent);
        finish();
    }
    //弹出广告AdverImgModel model
    public void showAdver(){
       // if(model.equals("") || model == null){
            show();
       // }
    }
    //显示按钮
    private void show() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                    //已经设置语言
             if (!PreferenceUtil.getString("language","defaultLan").equals("defaultLan")){
                 //根据上次的语言设置，重新设置语言
                 switchLanguage(PreferenceUtil.getString("language","defaultLan"));
                 mHandler.sendEmptyMessage(ACTIVITY);
                }else {//没有设置语言
                    //显示按钮
                     mHandler.sendEmptyMessage(WELCOME);
                    intiBtnEven();
                }
            }
        }, 100);
    }
    //点击事件初始化
    private void intiBtnEven() {
        languageSelectUg.setOnClickListener(new BtnClickListener());
        languageSelectCn.setOnClickListener(new BtnClickListener());
    }
    //按钮监听class
    class BtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.language_select_ug:
                    Config.lan = Constants.lang_ug;
                    break;
                case R.id.language_select_cn:
                    Config.lan = Constants.lang_zh;
                    break;
                default:
                    Config.lan = Constants.lang_ug;
                    break;
            }
            switchLanguage(Config.lan);
            //打开MianActivity
            openMainActivity();
        }
    }

    @Override
    public void finish() {
        super.finish();
        mHandler.removeMessages(0);
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    //更新UI
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 1){
                welcomeFlipper.showNext();
            }else{
                //等一秒在打开
                mTimer  =   new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        openMainActivity();
                    }
                }, 1000);
            }
            return false;
        }
    });
}
