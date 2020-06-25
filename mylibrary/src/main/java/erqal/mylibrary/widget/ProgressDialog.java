package erqal.mylibrary.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import erqal.mylibrary.R;

/**
 * Created by Administrator on 2015/11/27 0027.
 */
public class ProgressDialog extends AlertDialog implements DialogInterface.OnDismissListener {

    private Activity parent;
    private UTextView progressText;
    private int themeId;


    public Activity getParent() {
        return parent;
    }

    public void setParent(Activity parent) {
        this.parent = parent;
    }

    protected ProgressDialog(Context context) {
        //上下文 根据样式显示
        super(context, R.style.Theme_Progress_Dialog);
    }

    protected ProgressDialog(Context context, int themeId) {
        //上下文 根据样式显示
        super(context, R.style.Theme_Progress_Dialog);
        this.themeId=themeId;
    }

    protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, false, cancelListener);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        this.progressText=(UTextView)findViewById(R.id.progress_text);
        if (themeId>0&&progressText!=null){
            progressText.setText(getContext().getString(themeId));
        }
        setOnDismissListener(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN
                &&event.getKeyCode()==KeyEvent.KEYCODE_BACK
                &&event.getRepeatCount()==0
                ){
            if (getParent()!=null){
                dismiss();
                getParent().onBackPressed();
            }
            return false;
        }

        return super.dispatchKeyEvent(event);
    }


}
