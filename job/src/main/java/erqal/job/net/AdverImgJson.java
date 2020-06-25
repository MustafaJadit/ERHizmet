package erqal.job.net;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.BitSet;
import erqal.job.act.WelcomeAct;
import erqal.job.model.AdverImgModel;
/**
 * Created by samijan on 2015/12/11.
 * 关于启动广告所有的网络请求
 */
public class AdverImgJson {
    private Activity  activity;
    private String url = "http://192.168.1.173:8080/ArifBlog/index";
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @param activity
     * 构造方法
     * 初始化Activity
     */
    public AdverImgJson(final Activity activity){
        this.activity = activity;
    }
    //获取JSON数据
    public void getJson(){
        //创建对象返回值为Stirng
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            //成功获取的时候调用此方法
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AdverImgModel adverImgModel = gson.fromJson(response, AdverImgModel.class);
                WelcomeAct welcomeAct = (WelcomeAct) activity;
                welcomeAct.showAdver();
            }
        }, new Response.ErrorListener() {
            //遇到错误的时候调用此方法
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.getMessage());
            }
        });
        //创建队列
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        //发起请求
        requestQueue.add(stringRequest);
    }

    /**
     * 单独处理图片
     *
     *
     *
     */
    public void getImg(){
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response)
                    {

                    }
                }, 0, 0, Bitmap.Config.ARGB_4444,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(imageRequest);
    }
}
