package erqal.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import erqal.mylibrary.R;
import erqal.mylibrary.util.UyghurCharUtilities;

/**
 * Created by Administrator on 2015/11/26 0026.
 */
public class URadioButton extends RadioButton {

    private boolean isUyghurLanguage;

    public URadioButton(Context context) {
        super(context);
        UyghurCharUtilities.setTypeFace(this);
    }

    public URadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.LanguageType);
        isUyghurLanguage=array.getBoolean(R.styleable.LanguageType_is_uyghur_language,true);
        if (isUyghurLanguage){
            UyghurCharUtilities.setTypeFace(this);
        }
        array.recycle();
    }

    public URadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.LanguageType,defStyleAttr,R.style.default_user_control_style);
        isUyghurLanguage=array.getBoolean(R.styleable.LanguageType_is_uyghur_language,true);
        if (isUyghurLanguage){
            UyghurCharUtilities.setTypeFace(this);
        }
        array.recycle();
    }
}
