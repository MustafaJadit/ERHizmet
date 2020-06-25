package erqal.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

import erqal.mylibrary.R;
import erqal.mylibrary.util.UyghurCharUtilities;

/**
 * Created by Administrator on 2015/11/26 0026.
 */
public class UCheckBox extends CheckBox {

    private boolean isUyghurLanguage;

    public UCheckBox(Context context) {
        super(context);
        UyghurCharUtilities.setTypeFace(this);

    }

    public UCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.LanguageType);
        isUyghurLanguage=array.getBoolean(R.styleable.LanguageType_is_uyghur_language,true);
        if (isUyghurLanguage){
            UyghurCharUtilities.setTypeFace(this);
        }
        array.recycle();
    }

    public UCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.LanguageType,defStyleAttr,R.style.default_user_control_style);
        if (isUyghurLanguage){
            UyghurCharUtilities.setTypeFace(this);
        }
        array.recycle();
    }
}
