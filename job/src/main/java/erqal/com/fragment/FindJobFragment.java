package erqal.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.adapter.JobTypeAdapter;
import erqal.job.keyboard.KeyboardUtil;
import erqal.job.model.JobType;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by samijan on 2015/12/1.
 */
public class FindJobFragment extends Fragment {

    @Bind(R.id.top_back)
    ImageView back;
    private View view;
    private UTextView textView;
    private ListView listView;
    private JobTypeAdapter adapter;
    private EditText editText;
    private KeyboardUtil keyboardUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_job_fragment, null);
        ButterKnife.bind(this, view);
        editText = (EditText)view.findViewById(R.id.searchViewEdit);
        keyboardUtil =  new KeyboardUtil(getActivity(),editText,view);


        listView = (ListView) view.findViewById(R.id.job_type_listview);
        List<JobType> list = new ArrayList<JobType>();
        list.add(new JobType("مەمەتجان قدىم", "sami1", "1"));
        list.add(new JobType("samijan2", "يومشاق دېتال ئېنجىنىرى", "5"));
        list.add(new JobType("samijan3", "يومشاق دېتال ئېنجىنىرى", "15"));
        list.add(new JobType("samijan4", "يومشاق دېتال ئېنجىنىرى", "9"));
        list.add(new JobType("samijan5", "يومشاق دېتال ئېنجىنىرى", "16"));
        list.add(new JobType("samijan6", "يومشاق دېتال ئېنجىنىرى", "20"));
        list.add(new JobType("samijan7", "sami7", "5"));
        list.add(new JobType("samijan8", "sami8", "4"));
        list.add(new JobType("samijan0", "يومشاق دېتال ئېنجىنىرى", "8"));
        list.add(new JobType("samijan11", "sam0i", "6"));
        list.add(new JobType("samijan22", "sam-i", "48"));
        list.add(new JobType("samijan33", "يومشاق دېتال ئېنجىنىرى مىكى", "5"));
        list.add(new JobType("samijan44", "يومشاق دېتال ئېنجىنىرى مىكى", "2"));
        list.add(new JobType("samijan55", "يومشاق دېتال ئېنجىنىرى مىكى", "1"));
        list.add(new JobType("samijan66", "يومشاق دېتال ئېنجىنىرى مىكى", "1"));
        list.add(new JobType("samijan77", "يومشاق دېتال ئېنجىنىرى مىكى", "9"));
        list.add(new JobType("samijan88", "يومشاق دېتال ئېنجىنىرى مىكى", "9"));
        adapter = new JobTypeAdapter(list, getContext());
        listView.setAdapter(adapter);
        textView = (UTextView) view.findViewById(R.id.top_title);
        back.setOnClickListener(new EventListenet());
        getTitle();
        return view;
    }
    //添加监听器
    private class EventListenet implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }
    //设置标题
    private void getTitle() {
        textView.setText(R.string.findjob);
    }
    public static Fragment getInstance() {
        FindJobFragment fragment = new FindJobFragment();
        return fragment;
    }
}
