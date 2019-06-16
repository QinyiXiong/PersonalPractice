package JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 17:38
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo4
 */

/*
边界布局(东南西北中 五个区域)
* 如果在同一区域添加多个组件，那么新组件会覆盖就组件
* */
public class JFrame_Demo4 extends JFrame {
    public JFrame_Demo4() throws HeadlessException {

        setBounds(100, 100, 350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout()); //设置为边界布局

        JButton b1 = new JButton("中"),
                b2 = new JButton("东"),
                b3 = new JButton("西"),
                b4 = new JButton("南"),
                b5 = new JButton("北");

        c.add(b1, BorderLayout.CENTER);   //中部添加按钮
        c.add(b2, BorderLayout.EAST);
        c.add(b3, BorderLayout.WEST);
        c.add(b4, BorderLayout.SOUTH);
        c.add(b5, BorderLayout.NORTH);

        c.add(new JButton("覆盖"), BorderLayout.CENTER); //新组件覆盖旧组件


        setVisible(true);
    }

    public static void main(String[] args) {
        new JFrame_Demo4();
    }
}
