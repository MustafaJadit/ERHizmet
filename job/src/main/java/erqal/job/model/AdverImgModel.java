package erqal.job.model;

/**
 * Created by samijan on 2015/12/12.
 */
public class AdverImgModel {
    /**
     * adversion :
     * appversion :
     * img_url :
     * type :
     * jump_id :
     * jump_url :
     * time :  显示的时间
     * new_message :
     * emergency_message :
     */
    private String adversion;
    private String appversion;
    private String img_url;
    private String type;
    private String jump_id;
    private String jump_url;
    private int time;
    private int new_message;
    private int emergency_message;
    public void setAdversion(String adversion) {
        this.adversion = adversion;
    }
    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setJump_id(String jump_id) {
        this.jump_id = jump_id;
    }
    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void setNew_message(int new_message) {
        this.new_message = new_message;
    }
    public void setEmergency_message(int emergency_message) {
        this.emergency_message = emergency_message;
    }
    public String getAdversion() {
        return adversion;
    }
    public String getAppversion() {
        return appversion;
    }
    public String getImg_url() {
        return img_url;
    }

    public String getType() {
        return type;
    }
    public String getJump_id() {
        return jump_id;
    }
    public String getJump_url() {
        return jump_url;
    }
    public int getTime() {
        return time;
    }
    public int getNew_message() {
        return new_message;
    }
    public int getEmergency_message() {
        return emergency_message;
    }
}
