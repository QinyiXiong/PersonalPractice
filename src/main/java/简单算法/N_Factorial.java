package main.java.简单算法;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 20:46
 * @project_Name: PersonalPractice
 * @Name: N_Factorial
 */

/*n的阶乘
 * eg:  5! = 5*4*3*2*1
 * */
public class N_Factorial {

    public static void main(String[] args) {
        int n = 5;
        System.out.println("result: " + new N_Factorial().resN(n));
    }

    public long resN(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return n * resN(n - 1);
        }
    }
}
