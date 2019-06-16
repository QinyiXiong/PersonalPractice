package JAVA泛型;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 19:40
 * @project_Name: PersonalPractice
 * @Name: MaximumTest
 */

/*
 *对于下面定义的泛型方法maximum中,Comparable指的是一个接口而不是一个类，这点应该注重注意下。
 *如果想调用这个方法，最关键的是传入的T类型必须已经是实现了Comparable接口中compareTo()这个方法。
 * */
public class MaximumTest {

    public static <T extends Comparable<T>> T maximum(T x, T y, T z) {
        T max = x;
        if (y.compareTo(max) > 0) {
            max = y;
        }
        if (z.compareTo(max) > 0) {
            max = z;
        }
        return max;
    }


    public static void main(String[] args) {
        System.out.printf("%d, %d 和 %d 中最大的数为 %d\n\n",
                3, 4, 5, maximum(3, 4, 5));

        System.out.printf("%.1f, %.1f 和 %.1f 中最大的数为 %.1f\n\n",
                6.6, 8.8, 7.7, maximum(6.6, 8.8, 7.7));

        System.out.printf("%s, %s 和 %s 中最大的数为 %s\n", "pear",
                "apple", "orange", maximum("pear", "apple", "orange"));
    }
}
