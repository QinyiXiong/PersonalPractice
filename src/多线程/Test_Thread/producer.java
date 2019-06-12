package 多线程.Test_Thread;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 22:42
 * @project_Name: PersonalPractice
 * @Name: producer
 */
public class producer extends Thread {
    Thread_test t = null;

    public producer(Thread_test t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (t.number < t.size){
            t.put();
        }
    }
}
