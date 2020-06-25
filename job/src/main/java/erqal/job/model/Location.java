package erqal.job.model;

/**
 * Created by samijan on 2015/12/8.
 * 地区
 */
public class Location {
    private String  id;
    private String name;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Location(){}

    public Location(String id,String name){
        this.id = id;
        this.name = name;
    }
}
