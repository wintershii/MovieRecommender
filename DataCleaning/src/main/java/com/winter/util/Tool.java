package com.winter.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tool {

    public static Map<String, String> dataFormat(Long timestamp) {
        Map<String, String> map = new HashMap<String, String>();
        String format = "yyyy-MM-dd-HH";
        SimpleDateFormat sf = new SimpleDateFormat(format);
        String dataString = sf.format(new Date(Long.valueOf(timestamp)));
        String[] dateArr = dataString.split("-");
        map.put("year", dateArr[0]);
        map.put("month", dateArr[1]);
        map.put("day", dateArr[2]);
        map.put("hour", dateArr[3]);
        return map;
    }
}
