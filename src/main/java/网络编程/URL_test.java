package 网络编程;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/12 15:42
 * @project_Name: PersonalPractice
 * @Name: URL_test
 */
public class URL_test {

    public static void main(String[] args) {
        try {
            String urlStr = "https://www.baidu.com/";
            if (args.length > 0) urlStr = args[0];
            URL baiduUrl = new URL(urlStr);
            InputStreamReader ins = new InputStreamReader(baiduUrl.openStream());
            BufferedReader buf = new BufferedReader(ins);
            String aline;
            while ((aline = buf.readLine()) != null) {
                System.out.println(aline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
