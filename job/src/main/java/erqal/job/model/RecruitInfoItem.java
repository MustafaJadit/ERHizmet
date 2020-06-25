package erqal.job.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/13 0013.
 */
public class RecruitInfoItem implements Serializable {

    /**
     * propId : 1587
     * title : كومپيۇتېر مۇلازىمەت مەركىزى
     * imgUrl : http://imgs.erbamu.com/avatar/avatar.jpg
     * str : 媒体/出版/影视/文化传播
     * location : 乌鲁木齐市 天山区
     * fullLocation : 新疆 乌鲁木齐市 天山区
     * time : 1449982882
     * isCurrent : false
     */

    private ArrayList<RecruitInfoBaseItem> list;

    public void setList(ArrayList<RecruitInfoBaseItem> list) {
        this.list = list;
    }

    public ArrayList<RecruitInfoBaseItem> getList() {
        return list;
    }

}
