package main.java.设计模式.工厂模式.ShapeFactory;

/**
 * @author: 覃义雄
 * @dateTime: 2019/6/17 15:40
 * @project_Name: PersonalPractice
 * @Name: Rectangle
 * @Describe：
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
