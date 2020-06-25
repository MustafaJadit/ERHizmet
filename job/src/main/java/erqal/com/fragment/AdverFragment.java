package erqal.com.fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;
import erqal.job.R;
import erqal.job.act.MainAct;
/**
 * Created by samijan on 2015/12/12.
 */
public class AdverFragment  extends Fragment {
    private  Timer mTimer = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adver_layout,null);
        ImageView  imageView = (ImageView)view.findViewById(R.id.before_adver_img);
        String  url ="http://img2.imgtn.bdimg.com/it/u=2180641648,4098405848&fm=21&gp=0.jpg";
        Glide.with(getActivity()).load(url).into(imageView);
        init();
        return view;
    }
    /**
     * 当用户退出的时候
     * 停止Timer和Handler
     * 防止闪退
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
        }
        mHandler.removeMessages(0);
    }
    public void init(){
        mHandler.sendEmptyMessage(0);
    }
    //更新UI
    Handler mHandler =  new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //等一秒在打开
            mTimer =  new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MainAct mainAct = (MainAct) getActivity();
                    mainAct.openFragment(new MainFragment());
                }
            }, 3000);
            return  false;
        }
    });
}