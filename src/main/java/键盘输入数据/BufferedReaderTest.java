package 键盘输入数据;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 20:24
 * @project_Name: PersonalPractice
 * @Name: BufferedReaderTest
 */
public class BufferedReaderTest {
    public static void main(String[] args) {
        InputStreamReader ins = new InputStreamReader(System.in);
        BufferedReader buf = new BufferedReader(ins);
        try {
            String str = buf.readLine();
            System.out.println("Str: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
