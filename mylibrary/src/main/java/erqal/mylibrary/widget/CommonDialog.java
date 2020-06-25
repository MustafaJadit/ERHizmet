package erqal.mylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import erqal.mylibrary.R;

/**
 * Created by Administrator on 2015/11/27 0027.
 */
public class CommonDialog extends Dialog {

    UButton confirm;
    UButton cancel;
    private View.OnClickListener onClickListener;


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public  CommonDialog(Context context,View.OnClickListener clickListener){
        super(context, R.style.common_dialog);
        this.onClickListener=clickListener;
        setContentView(R.layout.common_dialog);
        confirm =(UButton)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cancel=(UButton)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

}
