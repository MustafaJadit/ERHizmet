package erqal.job.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import erqal.job.R;
import erqal.job.util.RefreshUIData;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/12 0012.
 */
public class SelectJobSearchArea extends Dialog {
    RefreshUIData refreshActivities;
    UTextView mTitle;
    ListView mListView;

    public SelectJobSearchArea(Context context, RefreshUIData refreshActivities) {
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
        mTitle.setText(R.string.select_area);

    }
}
