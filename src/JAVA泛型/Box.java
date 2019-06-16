package JAVA泛型;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 19:00
 * @project_Name: PersonalPractice
 * @Name: Box
 */
public class Box<T> {
    private T t;

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        Box<String> stringBox = new Box<String>();

        integerBox.add(new Integer(10));
        stringBox.add(new String("认真,努力,加油"));

        System.out.printf("整型值为 :%d\n\n", integerBox.get());
        System.out.printf("字符串为 :%s\n", stringBox.get());
    }

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}
