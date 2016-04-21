package com.app.coolweather.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.coolweather.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hai on 2016/4/21.
 */
public class CoolWeatherDB {
    public static final String DB_NAME="cool_weather";
    public static final int version=1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,null,version);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static CoolWeatherDB getInstance(Context context){//只有一个CoolWeatherDB对象实例去给外界使用，加上同步锁，只有在一个线程才可用
        if (coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());//在调用数据库时保存province的名字和代码
            values.put("province_code",province.getProvinceCode());//province数据从网上获得
            db.insert("Province",null,values);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
             list.add(province);//循环设置数据库中province的数据到新建的对象身上，并且加入到一个list集合；
            }while (cursor.moveToNext());
        }

        return list;
    }
    public void saveCity(City city){
        if (city!=null) {
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
        }


    }
    public List<City> loadCities(int provinceId){//查询城市时要有省份的Id所以要传入一个Id值
       List<City> list=new ArrayList<City>();
        Cursor cursor=db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);//访问传入省份id
        //的城市
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }

        return list;
    }
    public void saveCounty(County county){
        if (county!=null){
            ContentValues values=new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }
    public List<County> loadCounties(int cityId){
        List<County> list=new ArrayList<County>();
        Cursor cursor=db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCityId(cityId);
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                list.add(county);
            }while (cursor.moveToNext());

        }
        return list;
    }

}
