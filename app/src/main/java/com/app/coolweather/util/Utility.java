package com.app.coolweather.util;

import android.text.TextUtils;
import android.text.style.TtsSpan;

import com.app.coolweather.model.City;
import com.app.coolweather.model.CoolWeatherDB;
import com.app.coolweather.model.County;
import com.app.coolweather.model.Province;

/**
 * Created by hai on 2016/4/21.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
        if (!TextUtils.isEmpty(response)){//处理网上数据中的province部分，并且存入province对象，之后又存入数据库
            String[] allProvinces=response.split(",");
            if (allProvinces!=null&&allProvinces.length>0) {
                for (String p : allProvinces) {
                    String[] array =p.split("\\|");
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }

         return false;
    }
    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){//首先判断网上获得的数据是否存在
            String[] allCities=response.split(",");//之后用（，）号把数据分成城市
            if (allCities!=null&&allCities.length>0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");//最后把城市的代号和城市名分开
                    City city = new City();
                    city.setProvinceId(provinceId);
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }


        return false;
    }
    public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties=response.split(",");
            if (allCounties!=null&&allCounties.length>0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }

        }

        return false;
    }
}
