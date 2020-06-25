package erqal.job.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;

import erqal.job.R;
import erqal.job.util.RefreshUIData;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class SelectDate extends Dialog {

    DatePicker datePicker;
    UTextView title;
    UButton commit;
    int year;
    Date date;
    RefreshUIData refreshResumeBase;


    public SelectDate(Context context, RefreshUIData refreshActivities) {
        super(context, R.style.custom_data_picker_dialog);
        refreshResumeBase = refreshActivities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_dialog);
        setListener();
    }

    private void setListener() {
        datePicker = (DatePicker) findViewById(R.id.dp1);
        title = (UTextView) findViewById(R.id.select_dialog_title);
        commit = (UButton) findViewById(R.id.commit);
        title.setText(R.string.select_date);
        date = new Date();
        datePicker.setMaxDate(date.getTime());
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = datePicker.getYear();
                refreshResumeBase.refreshYear(year);
                dismiss();
            }
        });
    }
}
