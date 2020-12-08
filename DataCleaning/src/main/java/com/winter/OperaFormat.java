package com.winter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winter.model.UserData;
import com.winter.util.Tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class OperaFormat {

    private static Base64.Decoder decoder = Base64.getDecoder();

    // Base64解密
    private static String doDecode(String code) throws UnsupportedEncodingException {
            String string = new String(decoder.decode(code), "UTF-8");
            return string;
    }

    // Json数据格式化
    private static void doJson(String jsonData) throws IOException {
//        List<String> list = JSON.parseArray(jsonData, String.class);
//        for (int i = 0; i < list.size(); ++i) {
//            JSONObject object = JSON.parseObject(list.get(i));
//            JSON.toJavaObject(object.getJSONObject())
//        }
        if (jsonData.equals("[object Object]")) {
            return;
        }
        System.out.println(jsonData);
        JSONObject object = JSON.parseObject(jsonData);
        UserData userData = JSON.toJavaObject(object, UserData.class);

        Long time = (Long)object.get("time");
        System.out.println(userData + " " + time);
        writeToHDFS(userData, time);
    }

    public static void doFormat(String data) throws IOException {
        String decodeString = doDecode(data);
//        System.out.println(decodeString);
        doJson(decodeString);
    }

    // 数据组装
    public static void writeToHDFS(UserData userData, Long timestamp) throws IOException {
        Map<String, String> map = Tool.dataFormat(timestamp);
        String _path = "/hive/external/ods/behavior/";
        String year = map.get("year");
        String month = map.get("month");
        String day = map.get("day");
        String hour = map.get("hour");
        String dirPath = "year=" + year + "/" +
                "month=" + month + "/" +
                "day=" + day + "/";
        String fileName = year + month + day;
        String wholePath = _path + dirPath + fileName;
        String line = null;
        // 写入的内容
        line = userData.getUuid() + "," +
                userData.getMid() + "," +
//                userData.getAgent() + "," +
                "1," + "0," + "0," + String.valueOf(24-Integer.parseInt(hour)) + "," +
                year + "," + month + "," + day + "\n";
        OperaHDFS.writeHDFSFile(wholePath, line);
    }
}
