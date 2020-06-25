package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.adapter.RecruitmentInfoAdapter;
import erqal.job.model.RecruitInfoItem;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecruitmentInfoFragment extends Fragment {
    View mainView;
    @Bind(R.id.top_back)
    ImageView topBack;
    @Bind(R.id.top_title)
    UTextView topTitle;
    @Bind(R.id.recruit_listview)
    ListView recruitListview;
    RecruitmentInfoAdapter adapter;
    RecruitInfoItem list;
    private String url = "http://job.erqal.com/api.php?os=ios&lg=zh&m=company&a=list";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.recruitment_info, null);
        ButterKnife.bind(this, mainView);
        setListener();
        return mainView;
    }

    private void setListener() {
        topTitle.setText(R.string.recruitment_info);
        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        recruitListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SUCCESS", response);
                Gson gson = new Gson();
                list = gson.fromJson(response, RecruitInfoItem.class);
                adapter = new RecruitmentInfoAdapter(getContext(), list.getList());
                recruitListview.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);


    }

    public static Fragment getInstance() {
        RecruitmentInfoFragment fragment = new RecruitmentInfoFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
