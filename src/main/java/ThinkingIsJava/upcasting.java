package ThinkingIsJava;

/**
 * @author: 覃义雄
 * @dateTime: 2020/7/2 5:42 下午
 * @project_Name: personalPractice
 * @Name: upcasting
 * @Describe： 编程技巧 ： 向上转型 面向对象：多态
 */
public class upcasting {
    public void doSomething(Shape shape){
        System.out.println(shape.draw());
    }

    public static void main(String[] args) {
        Circle circle = new Circle();
        Line line = new Line();
        upcasting up = new upcasting();
        up.doSomething(circle);
        up.doSomething(line);
    }

}

class Circle extends Shape{ // 圆
    public String draw(){
        return "Circle";
    }
}

class Line extends Shape{ // 线
    public String draw(){
        return "Line";
    }
}
class Shape{  // 图形
    public String draw(){
        return "shape";
    }
}
