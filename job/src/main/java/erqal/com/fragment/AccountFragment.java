package erqal.com.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.act.ControlAct;
import erqal.job.net.Constants;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UTextView;
/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class AccountFragment extends Fragment {
    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.account_text)
    UTextView accountText;
    @Bind(R.id.account)
    LinearLayout account;
    @Bind(R.id.bundle_weixin)
    UButton bundleWeixin;
    @Bind(R.id.weixin_already_bundled)
    UTextView weixinAlreadyBundled;
    @Bind(R.id.weixin)
    LinearLayout weixin;
    @Bind(R.id.bundle_phone_number)
    UButton bundlePhoneNumber;
    @Bind(R.id.phone_number_already_bundled)
    UTextView phoneNumberAlreadyBundled;
    @Bind(R.id.phone_number)
    LinearLayout phoneNumber;
    @Bind(R.id.reset_password)
    LinearLayout resetPassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.account_list, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }
    private void setListener() {
        topTitle.setText(R.string.account);
        topBack.setOnClickListener(mOnclickListener);
        resetPassword.setOnClickListener(mOnclickListener);
    }
    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                case R.id.reset_password:
                    ControlAct.startUp(getActivity(), Constants.ResetPasswordFragment, null);
                    break;

                default:
                    break;
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    public static Fragment getInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }
}
