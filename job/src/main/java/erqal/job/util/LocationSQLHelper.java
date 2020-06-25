package erqal.job.util;
/**
 * Created by samijan on 2015/12/8.
 * 地址数据库
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class LocationSQLHelper extends SQLiteOpenHelper{
    public LocationSQLHelper(Context context) {
        super(context,"location.db",null, 1);
    }
    //新建数据表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location (_id integer,name varchar(50))");
    }
    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}