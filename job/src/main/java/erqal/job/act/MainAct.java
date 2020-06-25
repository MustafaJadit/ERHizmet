package erqal.job.act;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.os.Handler;
import butterknife.Bind;
import erqal.com.fragment.AdverFragment;
import erqal.com.fragment.MainFragment;
import erqal.job.R;
import erqal.job.dao.LocationDao;
import erqal.job.model.Location;
import erqal.job.net.Constants;
import erqal.job.util.BaseActivity;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UToast;
/**
 * Created by Administrator on 2015/11/22
 */
public class MainAct extends BaseActivity {
    @Bind(R.id.main_act_fram)
    FrameLayout mContainer;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private static boolean isExit = false;
    private int mCode = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        initView();
    }
    //初始化
    public void initView() {
        mCode = getIntent().getIntExtra("code", -1);
        switch (mCode){
            case Constants.ADVER_SHOW_YES:
                openFragment(new AdverFragment());
                break;
            default:
                openFragment(new MainFragment());
                break;
        }
        //还没有创建数据库
        if(PreferenceUtil.getString("DATABASE","NO").equals("NO")){
            //添加数据
            LocationDao dao = new LocationDao(MainAct.this);
            dao.add(new Location("15","samijan"));
            dao.add(new Location("15","samij"));
            dao.add(new Location("15","saiiii"));
            dao.add(new Location("15","mkkkkkkk"));
            dao.add(new Location("15","tttt"));
            dao.add(new Location("15","llllll"));
            dao.add(new Location("15","hhhhhh"));
            //备注已添加数据
            PreferenceUtil.commitString("DATABASE", Constants.DATABASE);
        }
    }
    //打开Fragment
    public void openFragment(Fragment fragment) {

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_act_fram, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    /**
     * @param keyCode
     * @param event
     * @return
     * 功能：连续点击两次退出
     * 策略：当用户第一次点击的时候记录，isExit == true 并给用户提示
     *       如果两秒内在此点击直接退出
     *       如果两秒内不点击就 isExit ==  false
     */
    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //退出
    private void exit() {
        if (!isExit) {
            isExit = true;
            UToast.mText(getApplicationContext(), R.string.finsh, Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
        }
    }
    //线程
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
