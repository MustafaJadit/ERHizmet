package erqal.job.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import erqal.job.model.Location;
import erqal.job.util.LocationSQLHelper;
/**
 * Created by samijan on 2015/12/8.
 * 地区数据库操作
 */
public class LocationDao  {


    private LocationSQLHelper helper;
    public LocationDao(Context context){
            helper = new LocationSQLHelper(context);
    }
    //添加数据
    public void add(Location location){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into location values(?,?)",new String[]{location.getId(),location.getName()});
    }
    //用名字读取数据
    public Location findName(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        //返回一个结果集
        Cursor cursor = db.rawQuery("select * from location where name = ?", new String[]{name});
        int _id = cursor.getInt(cursor.getColumnIndex("_id"));
        String names = cursor.getString(cursor.getColumnIndex("name"));
        return new Location(String.valueOf(_id),names);
    }
    //模糊查询  %name
    public List<Location> findBName(String name){
        List<Location> list = new ArrayList<Location>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from location where name like ?", new String[]{name + "%"});
        while (cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String names = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new Location(String.valueOf(_id),names));
        }
        return list;
    }

    //模糊查询  %name
    public List<String> findBNameS(String name){
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from location where name like ?", new String[]{name + "%"});
        while (cursor.moveToNext()){
            String names = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new String(names));
        }
        return list;
    }
    //返回所有数据
    public List<Location> getAll(){
        List<Location> list = new ArrayList<Location>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from location ",null);
        while (cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String names = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new Location(String.valueOf(_id),names));
        }
        return list;
    }
}
