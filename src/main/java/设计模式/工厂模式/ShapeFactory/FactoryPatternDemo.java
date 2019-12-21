package main.java.设计模式.工厂模式.ShapeFactory;

/**
 * @author: 覃义雄
 * @dateTime: 2019/6/17 15:52
 * @project_Name: PersonalPractice
 * @Name: FactoryPatternDemo
 * @Describe：
 */
public class FactoryPatternDemo {
    public static void main(String[] args) {
        shape_factory shapeFactory = new shape_factory();

        //获取 Circle 的对象，并调用它的 draw 方法
        Shape shape1 = shapeFactory.getShape("CIRCLE");

        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取 Rectangle 的对象，并调用它的 draw 方法
        Shape shape2 = shapeFactory.getShape("RECTANGLE");

        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取 Square 的对象，并调用它的 draw 方法
        Shape shape3 = shapeFactory.getShape("SQUARE");

        //调用 Square 的 draw 方法
        shape3.draw();
    }
}
