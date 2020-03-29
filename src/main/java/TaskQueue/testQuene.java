package TaskQueue;

/**
 * @author: pe_qyx
 * @dateTime: 2020/3/23 11:25 上午
 * @project_Name: PersonalPractice
 * @Name: testQuene
 */
public class testQuene {

    public static void main(String[] args) {
// 在方法中调用与传参的方式
        QueueGenerationService queueGenerationService = new QueueGenerationService();
        queueGenerationService.init();
        queueGenerationService.addData(new TestServiceHandler("小明",5));
        queueGenerationService.addData(new TestServiceHandler("小朋",5));
        queueGenerationService.addData(new TestServiceHandler("小花",5));
        queueGenerationService.addData(new TestServiceHandler("小张",5));
        queueGenerationService.destory();

    }
}
