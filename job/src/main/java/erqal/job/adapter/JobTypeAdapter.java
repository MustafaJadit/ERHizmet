package erqal.job.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;
import erqal.job.R;
import erqal.job.model.JobType;
import erqal.job.util.JobTypeDialog;
import erqal.mylibrary.widget.UGridView;
import erqal.mylibrary.widget.UTextView;
import erqal.mylibrary.widget.UToast;

/**
 * Created by samijan on 2015/12/4.
 */
public class JobTypeAdapter extends BaseAdapter{
    private List<JobType> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view ;
    private UTextView textView;
    private int  INDEX;
    public JobTypeAdapter(List<JobType> list, Context context){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
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
        INDEX = position;
        layoutInflater = LayoutInflater.from(context);
        view =  layoutInflater.inflate(R.layout.job_type_list_item, null);
        ViewHolder vh = new ViewHolder();
        vh.gridView = (UGridView)view.findViewById(R.id.job_type_grid);
        vh.gridView.setAdapter(new mAdapter());
        vh.uTextView = (UTextView)view.findViewById(R.id.job_type_title);
        vh.uTextView.setText(this.list.get(INDEX).getTitle());
        return view;
    }
    class ViewHolder{
       public UTextView uTextView;
        public UGridView gridView;
    }
    class mAdapter extends  BaseAdapter{
        @Override
        public int getCount() {
            return list.get(INDEX).getData().size();
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
            view =  layoutInflater.inflate(R.layout.grid_item, null);
            UTextView tv = (UTextView)view.findViewById(R.id.grid_item_tv);
            tv.setTag(list.get(INDEX).getData().get(position).getId());
            tv.setText(list.get(INDEX).getData().get(position).getTitle());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JobTypeDialog jobTypeDialog = new JobTypeDialog(context,list.get(2).getData());
                    jobTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    jobTypeDialog.show();
                }
            });
            return view;
        }
    }
}
