package com.pansoft.elasticsearchclient.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author: 覃义雄
 * @dateTime: 2020/3/9 6:21 下午
 * @project_Name: elasticsearchclient
 * @Name: elasticsearch
 */
public class ElasticsearchUtil {
    public static String getProperties(String key) throws Exception {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ElasticsearchUtil.class.getClassLoader().getResourceAsStream("Config/elasticsearch.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值
        return properties.getProperty(key);
    }

    /**
     * @Description：获取系统当天日期yyyy-mm-dd
     */
    public static String GetCurrentDate(){
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day =sdf.format(dt);
        return day;
    }
}
