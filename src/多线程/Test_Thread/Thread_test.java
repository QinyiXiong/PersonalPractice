package 多线程.Test_Thread;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 22:38
 * @project_Name: PersonalPractice
 * @Name: Thread_test
 */

/*
 * 生产一张票，出售一张票
 * */
public class Thread_test {

    public int size; //总票数
    public int number = 0; //票号
    public boolean available = false;  //票是否可出售


    public Thread_test(int tickets) {
        this.size = tickets;
    }

    public synchronized void sell() {
        try {
            if (!available) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("出售第 " + (number) + " 号票");
        available = false;
        if (number == size) number = size + 1;  //终止消费者进程
        notify();
    }

    public synchronized void put() {
        try {
            if (available) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("存入第 " + (++number) + " 号票");
        available = true;
        notify();
    }

    public static void main(String[] args) {
        Thread_test t = new Thread_test(10);
        consumer c1 = new consumer(t);
        producer p1 = new producer(t);
        c1.start();
        p1.start();
    }
}
