package 多线程.Test_Runnable;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 22:27
 * @project_Name: PersonalPractice
 * @Name: Runnable_test
 */
public class Runnable_test implements Runnable {

    private int tickets = 100;

    public static void main(String[] args) {
        Runnable_test t = new Runnable_test();
        Thread t1 = new Thread(t, "售票窗口1");
        Thread t2 = new Thread(t, "售票窗口2");
        Thread t3 = new Thread(t, "售票窗口3");
        Thread t4 = new Thread(t, "售票窗口4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public synchronized int SaleTickets() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tickets--;
    }

    public void run() {
        while (true) {
            if (tickets == 0) System.exit(0);
            System.out.println(Thread.currentThread().getName() + " 出售了第 " + SaleTickets() + " 张机票");
        }

    }
}
