package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 11:26
 * @project_Name: PersonalPractice
 * @Name: JLabel_Demo1
 */
public class JLabel_Demo1 extends JFrame {
    public JLabel_Demo1() throws HeadlessException {
        setBounds(100, 100, 200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        setVisible(true);

        JLabel l = new JLabel("这是一个标签");
        l.setText("更改标签内容");
        //获取标签的内容
        System.out.println(l.getText());
        /*
         * 设置标签字体
         * */
        l.setFont(new Font("微软雅黑", Font.BOLD, 15));
        l.setForeground(Color.RED); //更改前景色，更改字体颜色


        c.add(l);


    }

    public static void main(String[] args) {
        new JLabel_Demo1();
    }
}
