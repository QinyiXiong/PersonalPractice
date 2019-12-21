package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 17:17
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo2
 */

/*绝对布局：
 * 组件的位置不会随着窗口的变化而变化，位置是固定死的。
 * 既是优势又是劣势
 * */
public class JFrame_Demo2 extends JFrame {
    public JFrame_Demo2() throws HeadlessException {
        setBounds(100, 100, 200, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        c.setLayout(null); //将容器的布局设为绝对布局
        JButton b1 = new JButton("按钮1"), b2 = new JButton("按钮2"); //创建按钮
        b1.setBounds(10, 30, 80, 30); //设置按钮在容器中的坐标和大小
        b2.setBounds(60, 70, 100, 20); //设置按钮在容器中的坐标和大小
        c.add(b1);
        c.add(b2);

        setVisible(true);
    }

    public static void main(String[] args) {
        new JFrame_Demo2();
    }
}
