package main.java.多线程.Test_Thread;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 22:42
 * @project_Name: PersonalPractice
 * @Name: consumer
 */
public class consumer extends Thread {
    Thread_test t = null;

    public consumer(Thread_test t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (t.number <= t.size) {
            t.sell();
        }
    }
}
