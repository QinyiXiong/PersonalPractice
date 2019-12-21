package main.java.网络编程.test_TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/12 18:40
 * @project_Name: PersonalPractice
 * @Name: MyClient
 */
public class MyClient implements Runnable {

    public Socket clientSocket;
    public DataOutputStream fout;
    public BufferedReader fin;
    public boolean flag = true;
    public Thread connThread;


    public void clientStart() {
        try {
            clientSocket = new Socket("localhost", 8086);  //建立通讯套接字
            System.out.println("连接已经建立完毕！");
            while (flag) {
                fin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                fout = new DataOutputStream(clientSocket.getOutputStream());
                connThread = new Thread(this);
                connThread.start();

                String aline;
                while ((aline = fin.readLine()) != null) {
                    System.out.println(aline);
                    if (aline.equals("bye")) {
                        flag = false;
                        connThread.interrupt();
                        break;
                    }
                }
                fin.close();
                fout.close();
                clientSocket.close();
                System.exit(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
