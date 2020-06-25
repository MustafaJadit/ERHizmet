package erqal.job.widget;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.List;
import erqal.job.R;
import erqal.job.dao.LocationDao;

/**
 * 地区
 * Created by samijan on 2015/12/9.
 * 输入提示View
 */

public class UAutoCompleteTextView extends AutoCompleteTextView {
    public UAutoCompleteTextView(Context context) {
        super(context);
        init(context);
    }
    public UAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public UAutoCompleteTextView(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    //初始化
    public  void init(final Context context){
        EditText editText = new EditText(context);
        View view = new View(context);
            //输入一个字符以后开始提示
            setThreshold(1);
            //监听输入时间
           this.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               //输入的状态
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   //从数据库获取数据，按名字前符获取
                   LocationDao dao = new LocationDao(context);
                   List<String> list = dao.findBNameS(s.toString());
                   //List转Array
                   if(list.size()>0){
                       String[] temp = list.toArray(new String[1]);
                       //重新初始化Adapter
                       ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.autoview_list_item,R.id.auto_view_list_item, temp);
                       //设置Adapter
                       setAdapter(adapter);
                   }
               }
               @Override
               public void afterTextChanged(Editable s) {
               }
           });
    }

}
