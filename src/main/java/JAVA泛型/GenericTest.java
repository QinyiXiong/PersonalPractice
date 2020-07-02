package JAVA泛型;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 19:04
 * @project_Name: PersonalPractice
 * @Name: GenericTest
 */
public class GenericTest {
    public static void getData(List<?> data) {
        System.out.println("Data: " + data.get(0));
    }

    public static void main(String[] args) {
        List<Integer> age = new ArrayList<Integer>();
        List<String> name = new ArrayList<String>();
        List<Number> num = new ArrayList<Number>();

        age.add(18);
        name.add("张三");
        num.add(110);

        getData(age);
        getData(name);
        getData(num);

    }
}
