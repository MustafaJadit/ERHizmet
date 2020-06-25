package erqal.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.util.DataCleanManager;
import erqal.job.dialogue.LanguageDialog;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class SettingFragment extends Fragment {

    View mainView;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.top_back)
    ImageView back;
    @Bind(R.id.language_change)
    UTextView languageChange;
    @Bind(R.id.clear_cache)
    UTextView clearCache;
    @Bind(R.id.chech_update)
    UTextView chechUpdate;
    @Bind(R.id.share_friend)
    UTextView shareFriend;
    @Bind(R.id.app_evaluate)
    UTextView appEvaluate;
    @Bind(R.id.advice_feedback)
    UTextView adviceFeedback;
    @Bind(R.id.about)
    UTextView about;
    @Bind(R.id.cache_size)
    TextView textView;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private double cacheSize = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.setting, null);
        ButterKnife.bind(this, mainView);
        //设置标题
        setTitle();
        //添加监听事件
        setListener();
        return mainView;
    }

    //设置标题
    private void setTitle() {
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setText(cacheSize+"kb");
        topTitle.setText(R.string.setting);
    }
    //添加监听事件
    private void setListener() {
        languageChange.setOnClickListener(new mclickListener());
        clearCache.setOnClickListener(new mclickListener());
        chechUpdate.setOnClickListener(new mclickListener());
        shareFriend.setOnClickListener(new mclickListener());
        appEvaluate.setOnClickListener(new mclickListener());
        adviceFeedback.setOnClickListener(new mclickListener());
        about.setOnClickListener(new mclickListener());
        back.setOnClickListener(new mclickListener());
    }

    //初始化
    public static Fragment getInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }
    //监听Cilck事件
    public class mclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.language_change:
                    LanguageDialog dialog = new LanguageDialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    break;
                case R.id.clear_cache:
                    //缓存清理
                    clearache();
                    break;
                case R.id.chech_update:
                    break;
                case R.id.share_friend:
                    break;
                case R.id.app_evaluate:
                    break;
                case R.id.advice_feedback:
                    break;
                case R.id.about:
                    break;
                case R.id.top_back:
                    getActivity().onBackPressed();
                default:
                    break;
            }
        }
    }
    //缓存清理
    public void clearache(){
        try {
            if(cacheSize == 0){
                UToast.mText(getContext(), R.string.clear_cache, Toast.LENGTH_SHORT).show();
            }else{
                DataCleanManager.clearAllCache(getContext());
                UToast.mText(getContext(), R.string.clear_cache, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
