package com.jeeplus.qc.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.database.datamodel.utils.DataSourceUtils;

import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.service.DataRuleService;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.service.dto.UserDTO;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.*;
import java.util.stream.Collectors;


public class ApiCommonUtils {
    public static List<String> getFactoryRule(UserDTO userDTO){
        JSONArray jsonArray = DataSourceUtils.getDataBySql("GET_FACTORY_LIST",new HashMap<>());
        List<String> dataList = jsonArray.stream().map(item -> ((JSONObject)item).getString("factory")).collect(Collectors.toList());
        if(!userDTO.isAdmin()){
            List<String> data = new ArrayList<>();
            List<DataRuleDTO> dataRuleList = ((DataRuleService) SpringUtil.getBean(DataRuleService.class)).findByUserId(userDTO);
            for(DataRuleDTO dataRule : dataRuleList){
                if(StringUtils.isNotBlank(dataRule.getName())
                        && dataList.contains(dataRule.getName())
                        && !data.contains(dataRule.getName())){
                    data.add(dataRule.getName());
                }
            }
            return data;
        }
        return dataList;
    }
    public static List<DictValue> getFactoryRuleByDictValue(UserDTO userDTO){
        List<DictValue> list = new ArrayList<>();
        List<String> factorys = getFactoryRule(userDTO);
        if(factorys.size() > 0){
            for(String factory:factorys){
                DictValue dictValue = new DictValue();
                dictValue.setValue(factory);
                dictValue.setLabel(factory);
                list.add(dictValue);
            }
        }
        return list;
    }
    public static boolean isExistenceMap(Map<String, String> param,String key){
        if (param.containsKey(key)) {
            if (StringUtils.isNotBlank(param.get(key))) {
                return true;
            }
        }
        return false;
    }
    public static String getUTCTimeByPattern(String pattern){
        String datetime = null;
        try {
            datetime = DateFormatUtils.format(getUTCTime(),pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }
    public static String getUTCTimeByString(){
        return getUTCTimeByPattern("yyyy-MM-dd HH:mm:ss");
    }
    public static Date getUTCTime(){
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return cal.getTime();
    }
    public static Date getTodayStartTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    public static Date addDayToDate(Integer day,Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(java.util.Calendar.DATE,calendar.get(Calendar.DATE)+day);
        return calendar.getTime();
    }
    public static Date addHourToDate(Integer hour,Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+hour);
        return calendar.getTime();
    }
    public static Date addMinuteToDate(Integer minute,Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+minute);
        return calendar.getTime();
    }
}
