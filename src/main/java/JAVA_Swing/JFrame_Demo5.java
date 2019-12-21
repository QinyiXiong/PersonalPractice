package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 17:52
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo5
 */
/*
 * 网格布局
 * */
public class JFrame_Demo5 extends JFrame {
    public JFrame_Demo5() throws HeadlessException {
        setBounds(100, 100, 300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        //设置为网格布局,7行3列,水平间距5px,垂直间距5px(可以只有两个参数)
        c.setLayout(new GridLayout(7, 3, 5, 5));

        for (int i = 0; i < 20; i++) {
            c.add(new JButton("按钮" + i));
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new JFrame_Demo5();
    }
}
