package com.winter;

import com.winter.model.CollectData;
import com.winter.model.ScoreData;
import com.winter.util.DBTool;
import com.winter.util.Tool;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataClean {
    public static void main(String[] args) throws IOException {
        // 清洗日志数据
         OperaHDFS.readHDFSFiles();
        // 清晰DB中的数据
        DBDataClean();
    }


    public static void DBDataClean() {
        // 从mysql读取movie_scores 和 movie_collect数据
        // 写入对应的表中
        Map<String, String> dateMap = Tool.dataFormat(new Date().getTime());
        String startTime = dateMap.get("year") + "-" +
                dateMap.get("month") + "-" +
                dateMap.get("day");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Map<String, String> afterDateMap = Tool.dataFormat(cal.getTime().getTime());
        String endTime = afterDateMap.get("year") + "-" +
                afterDateMap.get("month") + "-" +
                afterDateMap.get("day");
        List<ScoreData> scoreData = DBTool.readScoreData(startTime, endTime);
        List<CollectData> collectData = DBTool.readCollectData(startTime, endTime);
        String _path = "/hive/external/ods/behavior/";

        writeScoreData2HDFS(_path, scoreData);
        writeCollectData2HDFS(_path, collectData);
    }

    private static void writeScoreData2HDFS(String _path, List<ScoreData> scoreData) {
        System.out.println(scoreData);
        for (ScoreData data : scoreData) {

            String[] parts = data.getDate().split(" ");
            String[] dates = parts[0].split("-");
            String year = dates[0];
            String month = dates[1];
            String day = dates[2];
            String hour = parts[1].split(":")[0];

            String line = data.getUid() + ","
                    + data.getMid() + ","
                    + "0,"
                    + data.getScore() + ","
                    + "0,"
                    + String.valueOf(24 - Integer.parseInt(hour)) + ","
                    + year + "," + month + "," + day + "\n";
            String dirPath = "year=" + year + "/" +
                    "month=" + month + "/" +
                    "day=" + day + "/";
            String fileName = _path + dirPath + year + month + day;
            try {
                OperaHDFS.writeHDFSFile(fileName, line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeCollectData2HDFS(String _path, List<CollectData> collectData) {
        System.out.println(collectData);
        for (CollectData data : collectData) {

            String[] parts = data.getDate().split(" ");
            String[] dates = parts[0].split("-");
            String year = dates[0];
            String month = dates[1];
            String day = dates[2];
            String hour = parts[1].split(":")[0];

            String line = data.getUid() + ","
                    + data.getMid() + ","
                    + "0,"
                    + "0,"
                    + "3,"
                    + String.valueOf(24 - Integer.parseInt(hour)) + ","
                    + year + "," + month + "," + day + "\n";
            String dirPath = "year=" + year + "/" +
                    "month=" + month + "/" +
                    "day=" + day + "/";
            String fileName = _path + dirPath + year + month + day;
            try {
                OperaHDFS.writeHDFSFile(fileName, line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
