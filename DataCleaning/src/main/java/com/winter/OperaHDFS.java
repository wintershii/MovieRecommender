package com.winter;

import com.winter.model.UserData;
import com.winter.util.Tool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperaHDFS {

    private static Configuration conf = new Configuration();

    private static FileSystem fs = null;

    private static String path = "/nginx-ori/";

    static {
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");

        try {
            fs = FileSystem.get(conf);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void readHDFSFile(String filePath) {
        try {
             FSDataInputStream inputStream = fs.open(new Path(filePath));
             BufferedReader bf =  new BufferedReader(new InputStreamReader(inputStream));
             String line = null;
             Pattern pattern = Pattern.compile("(?<=\\?).\\S*");

             while ((line = bf.readLine()) != null) {
                 String[] lineArr = line.split(" ");
                 String _data = lineArr[6];
                 String data = null;
//                 System.out.println(_data);
                 if (_data.startsWith("/upload")) {
                      Matcher matcher = pattern.matcher(_data);
                      if (matcher.find()) {
                          data = matcher.group();
                          //System.out.println(data);
                          OperaFormat.doFormat(data);
                      }
                 }
             }
             bf.close();
             inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readHDFSFiles() throws IOException {
        // 遍历当天的所有日志
        Map<String, String> dateMap = Tool.dataFormat(new Date().getTime());
        String _path = dateMap.get("year") + "/" +
                dateMap.get("month") + "/" +
                dateMap.get("day");
        String wholePath = path + _path;

        RemoteIterator<LocatedFileStatus> ri = fs.listFiles(new Path(wholePath), true);
        while (ri.hasNext()) {
            LocatedFileStatus lfs = ri.next();
            // 获取每个日志文件的上报数据
            //System.out.println(lfs.getPath().toString());
            readHDFSFile(lfs.getPath().toString());
        }
    }


    public static void writeHDFSFile(String fileName, String line) throws IOException {
        Path file = new Path(fileName);
        FSDataOutputStream out = null;
        byte[] bytes = line.getBytes("UTF-8");
        if (!fs.exists(file)) {
//            System.out.println("debug-------------");
            out = createFile(fileName);
            out.write(bytes);
        } else {
            out = fs.append(file);
//        String _line = line + "\n";
            out.write(bytes);
        }
        out.close();
    }

    private static FSDataOutputStream createFile(String filePath) throws IOException {
        FSDataOutputStream fsDataOutputStream = fs.create(new Path(filePath));
        return fsDataOutputStream;
    }
}
