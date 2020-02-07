package Activiti;

import com.pansoft.activiticlient.activitiapi.WorkflowAction;

import java.util.Map;

/**
 * @author: 覃义雄
 * @dateTime: 2020-02-04 15:02
 * @project_Name: PersonalPractice
 * @Name: TestActiviti
 * @Describe：
 */
public class TestActiviti {
    public static void main(String[] args) {
//        Map<String,Object> hehe =  WorkflowAction.getTaskList("documentcheckflow",null,"9999");
//        System.out.println(hehe);

        String ss = WorkflowAction.complateTask(null,"documentcheckflow","我启动一个看看","9999");
        System.out.println(ss);
    }
}
