package TaskQueue;

/**
 * @author: 覃义雄
 * @dateTime: 2020/3/23 11:14 上午
 * @project_Name: PersonalPractice
 * @Name: TestServiceHandler
 */
public class TestServiceHandler implements QueueTaskHandler {
    // ******* start 这一段并不是必要的，这是示范一个传值的方式
    private String name;

    private Integer age;

    public TestServiceHandler(String name) {
        this.name = name;
    }

    public TestServiceHandler(Integer age) {
        this.age = age;
    }

    public TestServiceHandler(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // ****** end

    // 这里也就是我们实现QueueTaskHandler的处理接口
    public void processData() {
        // 可以去做你想做的业务了
        // 这里需要引用spring的service的话，我写了一个工具类，下面会贴出来
        // ItestService testService = SpringUtils.getBean(ItestService.class);
        System.out.println("name > " + name + "," + "age > " + age);
    }
}
