package erqal.job.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import erqal.job.R;
import erqal.job.util.RefreshUIData;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/12 0012.
 */
public class SelectSalary extends Dialog implements AdapterView.OnItemClickListener {
    RefreshUIData refreshActivities;
    UTextView mTitle;
    ListView mListView;
    final String[] sex = {"男", "女"};

    public SelectSalary(Context context, RefreshUIData refreshActivities) {
        super(context);
        this.refreshActivities = refreshActivities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dialog);
        setListener();
    }

    private void setListener() {
        mTitle = (UTextView) findViewById(R.id.select_dialog_title);
        mListView = (ListView) findViewById(R.id.select_dialog_listview);
        mTitle.setText(R.string.select_salary);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, sex);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = sex[position];
        refreshActivities.refreshSalary(selected);
        dismiss();
    }
}
