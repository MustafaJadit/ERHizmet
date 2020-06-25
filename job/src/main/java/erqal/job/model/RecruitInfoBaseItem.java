package erqal.job.model;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecruitInfoBaseItem {

    private int propId;
    private String title;
    private String imgUrl;
    private String str;
    private String location;
    private String fullLocation;
    private int time;
    private boolean isCurrent;

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFullLocation(String fullLocation) {
        this.fullLocation = fullLocation;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public int getPropId() {
        return propId;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getStr() {
        return str;
    }

    public String getLocation() {
        return location;
    }

    public String getFullLocation() {
        return fullLocation;
    }

    public int getTime() {
        return time;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

}
