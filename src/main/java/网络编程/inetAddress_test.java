package 网络编程;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/12 15:52
 * @project_Name: PersonalPractice
 * @Name: inetAddress_test
 */
public class inetAddress_test {
    public static void main(String[] args) {

        try {
            InetAddress myIPaddress = null;
            InetAddress myServer = null;
            myIPaddress = InetAddress.getLocalHost();
            myServer = InetAddress.getByName("peqyx365.cn");
            System.out.println("我的IP地址为：" + myIPaddress);
            System.out.println("服务器的IP地址为：" + myServer);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
