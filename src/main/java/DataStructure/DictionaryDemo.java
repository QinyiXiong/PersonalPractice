package DataStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 覃义雄
 * @dateTime: 2020/7/2 2:46 下午
 * @project_Name: personalPractice
 * @Name: DictionalDemo
 * @Describe： 字典类是一个抽象类，它定义了键映射到值的数据结构
 */
public class DictionaryDemo {
    // 已过时  一般使用 map
    public static void main(String[] args) {
        Map map = new HashMap<String,String>();
        map.put("hehe","hehe");
        System.out.println(map.get("hehe"));
    }
}
