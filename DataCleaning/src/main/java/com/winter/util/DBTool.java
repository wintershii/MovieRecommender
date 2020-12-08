package com.winter.util;

import com.winter.model.BehaviorData;
import com.winter.model.CollectData;
import com.winter.model.ScoreData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTool {

    public static final String url = "jdbc:mysql://127.0.0.1/movies?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "990708";


    public static Connection conn = null;
    public static PreparedStatement pst = null;


    public static void main(String[] args) {
        System.out.println(readScoreData("2020-12-05", "2020-12-06"));
    }

    public static List<ScoreData> readScoreData(String startTime, String endTime) {
        String sql = String.format("select * from movie_scores where create_time >= '%s' and create_time <= '%s'", startTime, endTime);
//        System.out.println(sql);
        List<ScoreData> scoreList = new ArrayList<>();
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);

            ResultSet result = pst.executeQuery();
            while (result.next()) {
                int uid = result.getInt(2);
                int mid = result.getInt(3);
                int score = result.getInt(4);
                String date = result.getString(5);
                ScoreData data = new ScoreData();
                data.setUid(uid);
                data.setMid(mid);
                data.setScore(score);
                data.setDate(date);
                scoreList.add(data);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public static List<CollectData> readCollectData(String startTime, String endTime) {
        String sql = String.format("select * from movie_collect where create_time >= '%s' and create_time <= '%s'", startTime, endTime);
        List<CollectData> collectList = new ArrayList<>();
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);

            ResultSet result = pst.executeQuery();
            while (result.next()) {
                int uid = result.getInt(2);
                int mid = result.getInt(3);
                String date = result.getString(5);

                CollectData data = new CollectData();
                data.setUid(uid);
                data.setMid(mid);
                data.setDate(date);
                collectList.add(data);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collectList;
    }

}
