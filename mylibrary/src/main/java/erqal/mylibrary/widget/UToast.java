package erqal.mylibrary.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import erqal.mylibrary.R;

/**
 * Created by samijan on 2015/12/1.
 */
public class UToast extends Toast{
    public UToast(Context context) {
        super(context);
    }

    public static Toast mText(Context context, int id, int duration) {
        Toast result = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //获取Layout
        View v = inflate.inflate(R.layout.toast_layout, null);
        //设置字体
        UTextView  tv = (UTextView)v.findViewById(R.id.toast_text);
        tv.setText(id);
        result.setView(v);
        result.setDuration(duration);
        return result;
    }


    public static Toast mText(Context context, String id, int duration) {
        Toast result = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //获取Layout
        View v = inflate.inflate(R.layout.toast_layout, null);
        //设置字体
        UTextView  tv = (UTextView)v.findViewById(R.id.toast_text);
        tv.setText(id);
        result.setView(v);
        result.setDuration(duration);
        return result;
    }
}
