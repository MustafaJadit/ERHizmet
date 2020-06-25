package erqal.job.util;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import java.util.List;
import erqal.job.R;
import erqal.job.adapter.JobListAdapter;
import erqal.job.model.Data;
/**
 * Created by samijan on 2015/12/5.
 */
public class JobTypeDialog extends Dialog {
    private ListView listView;
    private List<Data> list;
    private Context context;
    public JobTypeDialog(Context context,List<Data> list)
    {
        super(context);
        this.context = context;
        this.list = list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_dialog_layout);
        listView = (ListView)findViewById(R.id.job_dialog_listview);
        listView.setAdapter(new JobListAdapter(list,context));
    }

}
