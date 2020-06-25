package erqal.job.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import erqal.job.R;
import erqal.job.model.RecruitInfoBaseItem;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecruitmentInfoAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<RecruitInfoBaseItem> list;

    public RecruitmentInfoAdapter(Context context, ArrayList<RecruitInfoBaseItem> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.recruitment_info_listitem, null);
            viewHolder.title = (UTextView) convertView.findViewById(R.id.recruit_title);
            viewHolder.update = (UTextView) convertView.findViewById(R.id.recruit_update);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle().toString());
//        viewHolder.update.setText(String.valueOf(list.get(position).getTime()));
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String date = dateFormat.format((long) list.  get(position).getTime() * 1000);
        viewHolder.update.setText(date);


        return convertView;
    }

    public final class ViewHolder {
        UTextView title;
        UTextView update;
    }
}
