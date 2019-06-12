package 网络编程.test_TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/12 18:40
 * @project_Name: PersonalPractice
 * @Name: MyServer
 */
public class MyServer implements Runnable {

    public ServerSocket serverSocket;
    public Socket clientSocket;
    public boolean flag = true;
    public DataOutputStream fout;
    public BufferedReader fin;
    public Thread connThrean;

    public void ServerStart() {
        try {
            serverSocket = new ServerSocket(8086);
            System.out.println("服务器端口为：" + serverSocket.getLocalPort());
            while (flag) {
                clientSocket = serverSocket.accept();
                System.out.println("连接已经建立完毕！");
                InputStream ins = clientSocket.getInputStream();
                fin = new BufferedReader(new InputStreamReader(ins));
                OutputStream osp = clientSocket.getOutputStream();
                fout = new DataOutputStream(osp);
                connThrean = new Thread(this);
                connThrean.start();

                /*String.equals(object)
                * 若当前对象和比较的对象是同一个对象，即return true。也就是Object中的equals方法。

                 若当前传入的对象是String类型，则比较两个字符串的长度，即value.length的长度。

                 若长度不相同，则return false

                 若长度相同，则按照数组value中的每一位进行比较，不同，则返回false。若每一位都相同，则返回true。

                 若当前传入的对象不是String类型，则直接返回false
                * */

                /*注意：
                * （1）"=="是Java提供的关系运算符，主要的功能是进行数值相等判断的，
                * 如果用在了String对象上表示的是内存地址数值的比较。
                  （2）equals（）方法是由String提供的一个方法，此方法专门负责进行字符串内容的比较。
                * */
                String aline;
                while ((aline = fin.readLine()) != null) {
                    System.out.println(aline);
                    if (aline.equals("bye")) {
                        flag = false;
                        connThrean.interrupt();
                        break;
                    }
                }

                fout.close();
                fin.close();
                ins.close();
                osp.close();
                clientSocket.close();
                System.exit(0);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /*
    * System.in.read()返回一个整型字节数据，
	该数据表示的是字节因此是Unicode的第一个字节或是字符的ASCII码值。
	该方法是从一个流中一个一个的读取数据，因此是一个迭代的过程。
	我们可以看出in是一个静态的流，因此在该程序中只有一个流，
	重复调用System.in.read()实际上是在遍历该流中的每一个字节数据。
	最常见的流是键盘输入流。我们可以在键盘中输入一个字符串
	（其中按下回车键代表了两个字符\r\n,\r的ASCII码值是10，\n是13）。
	我们可以重复调用System.in.read()来读取从键盘输入的字符串中的字符所代表的字节（ASCII值）。
	从输入流中读取数据的下一个字节。返回 0 到 255 范围内的 int 字节值。
	如果因为已经到达流末尾而没有可用的字节，则返回值 -1。
	在输入数据可用、检测到流末尾或者抛出异常前，此方法一直阻塞。
    子类必须提供此方法的一个实现。
    * */
    @Override
    public void run() {
        int temp;
        try {
            while ((temp = System.in.read()) != -1) {
                fout.write((byte) temp);
                if (temp == '\n') {
                    fout.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        serverSocket.close();
    }
}
