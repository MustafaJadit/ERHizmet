package erqal.job.model;

/**
 * Created by samijan on 2015/12/4.
 */
public class Data{
    private String title;
    private String id;
    public Data(String title,String id){
        this.id = id;
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}