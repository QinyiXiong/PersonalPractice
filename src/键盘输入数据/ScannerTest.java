package 键盘输入数据;

import java.util.Scanner;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 20:24
 * @project_Name: PersonalPractice
 * @Name: ScannerTest
 */
public class ScannerTest {
    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);
        String str = sca.nextLine();
        System.out.println("str: " + str);
    }
}
