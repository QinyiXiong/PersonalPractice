package 设计模式.工厂模式.ShapeFactory;

/**
 * @author: 覃义雄
 * @dateTime: 2019/6/17 15:41
 * @project_Name: PersonalPractice
 * @Name: Square
 * @Describe：
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
