package 设计模式.工厂模式.ShapeFactory;

/**
 * @author: 覃义雄
 * @dateTime: 2019/6/17 15:48
 * @project_Name: PersonalPractice
 * @Name:shape_factory
 * @Describe：
 */
public class shape_factory {
    //使用 getShape 方法获取形状类型的对象
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

}
