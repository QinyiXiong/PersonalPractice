package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 17:27
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo3
 */

/*
 * 流式布局
 * 默认居中对齐，按照顺序依次从左往右排列，遇到边缘
 * 折回第二行，重新排列。
 * 默认的居中对齐可以修改
 * eg: new FlowLayout(FlowLayout.LEFT)
 * */
public class JFrame_Demo3 extends JFrame {
    public JFrame_Demo3() throws HeadlessException {
        setBounds(100, 100, 300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        /*
         * FlowLayout.LEFT   左对齐
         * 20   水平间距
         * 20   垂直间距
         * */
        c.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));  //给容器设置流布局

        for (int i = 0; i < 10; i++) {
            c.add(new JButton("按钮" + i));  //循环添加按钮组件
        }


        setVisible(true);
    }

    public static void main(String[] args) {
        new JFrame_Demo3();
    }
}
