package DataStructure;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author: 覃义雄
 * @dateTime: 2020/7/2 2:31 下午
 * @project_Name: personalPractice
 * @Name: EnumerationTester
 * @Describe：Java Enumeration接口 【数据结构】
 */
public class EnumerationDemo {
    public static void main(String args[]) {
        Enumeration<String> days;
        Vector<String> dayNames = new Vector<String>();
        dayNames.add("Sunday");
        dayNames.add("Monday");
        dayNames.add("Tuesday");
        dayNames.add("Wednesday");
        dayNames.add("Thursday");
        dayNames.add("Friday");
        dayNames.add("Saturday");
        days = dayNames.elements();
        while (days.hasMoreElements()){
            System.out.println(days.nextElement());
        }
    }
}
