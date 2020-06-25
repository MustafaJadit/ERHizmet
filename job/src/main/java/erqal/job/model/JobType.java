package erqal.job.model;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

/**
 * Created by samijan on 2015/12/4.
 */
public class JobType {
      private String title;
      private List<Data> data = new ArrayList<Data>();
    public JobType(String title,String t,String id){
        this.title = title;
        Integer a = Integer.valueOf(id);
        for(int i=0;i<a;i++){
            this.data.add(new Data(t+"-"+a,id));
        }
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Data> getData() {
        return data;
    }
    public void setData(List<Data> data) {
        this.data = data;
    }
}
