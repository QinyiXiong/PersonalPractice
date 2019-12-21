package main.java.设计模式.工厂模式.ShapeFactory;

/**
 * @author: 覃义雄
 * @dateTime: 2019/6/17 15:42
 * @project_Name: PersonalPractice
 * @Name: Circle
 * @Describe：
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
