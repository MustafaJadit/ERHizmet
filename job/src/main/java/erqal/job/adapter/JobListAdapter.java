package erqal.job.adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

import erqal.job.R;
import erqal.job.model.Data;
import erqal.mylibrary.widget.UTextView;
/**
 * Created by samijan on 2015/12/5.
 */
public class JobListAdapter extends BaseAdapter {
    private List<Data> list;
    private Context context;
    private UTextView uTextView;
    public JobListAdapter(List<Data> list, Context context){
            this.list = list;
            this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_dialog_list_item,null);
        uTextView = (UTextView)view.findViewById(R.id.job_dialog_item_textview);
        uTextView.setText(list.get(position).getTitle());
        return view;
    }
}
