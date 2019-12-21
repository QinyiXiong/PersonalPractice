package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 20:23
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo6
 */

//GridBagConstraints
public class JFrame_Demo6 extends JFrame {
    JFrame f = new JFrame(); //主窗体
    Container c;  //主容器

    public static void main(String[] args) {
        JFrame_Demo6 d = new JFrame_Demo6();
        d.createFrame();
        //d.init();
        //d.init2();
        //d.init3();
//        d.init4();
//        d.init5();
//        d.init6();
        d.init7();
        d.createButton();
        d.f.setVisible(true);
    }

    void createFrame() {
        c = f.getContentPane();
        c.setLayout(new GridBagLayout());
        //设置大小
        f.setSize(800, 600);
        //居中，注意代码顺序不能变，这样才能实现自动居中
        //设置窗体相对于指定组件的位置
        //如果组件当前未显示或者 c 为 null，则此窗口将置于屏幕的中央。
        //中点可以使用 GraphicsEnvironment.getCenterPoint 确定。
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //gridx与gridy
    void init() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 0;
        g1.gridy = 0;
        c.add(new JButton("组件1"), g1);

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 1;
        g2.gridy = 1;
        c.add(new JButton("组件2"), g2);

        GridBagConstraints g3 = new GridBagConstraints();
        g3.gridx = 2;
        g3.gridy = 2;
        c.add(new JButton("组件3"), g3);
    }

    //gridwidth与gridheight
    void init2() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 1;
        g1.gridy = 1;
        c.add(new JButton("组件1"), g1);

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 2;
        g2.gridy = 2;
        g2.gridwidth = 2;
        g2.gridheight = 1;
        c.add(new JButton("组件2"), g2);

        GridBagConstraints g3 = new GridBagConstraints();
        g3.gridx = 4;
        g3.gridy = 4;
        g3.gridwidth = 2;
        g3.gridheight = 2;
        c.add(new JButton("组件3"), g3);
    }

    //fill
    void init3() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 1;
        g1.gridy = 1;
        g1.gridwidth = 2;
        g1.gridheight = 2;
        c.add(new JButton("none"), g1);

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 3;
        g2.gridy = 1;
        g2.gridwidth = 2;
        g2.gridheight = 2;
        g2.fill = GridBagConstraints.HORIZONTAL;  //水平填充
        c.add(new JButton("HORIZONTAL"), g2);

        GridBagConstraints g3 = new GridBagConstraints();
        g3.gridx = 5;
        g3.gridy = 1;
        g3.gridwidth = 2;
        g3.gridheight = 2;
        g3.fill = GridBagConstraints.VERTICAL;  //垂直填充
        c.add(new JButton("VERTICAL"), g3);

        GridBagConstraints g4 = new GridBagConstraints();
        g4.gridx = 7;
        g4.gridy = 1;
        g4.gridwidth = 2;
        g4.gridheight = 2;
        g4.fill = GridBagConstraints.BOTH;  //上下左右都填充
        c.add(new JButton("BOTH"), g4);

    }

    //anchor属性
    void init4() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 1;
        g1.gridy = 1;
        g1.gridwidth = 2;
        g1.gridheight = 2;
        g1.anchor = GridBagConstraints.NORTHEAST;  //显示在东北面
        c.add(new JButton("@"), g1);

        //为了方便查看，添加了个JPanel
        g1.fill = GridBagConstraints.BOTH;
        g1.anchor = GridBagConstraints.CENTER;
        JPanel p = new JPanel();
        p.setBackground(Color.GREEN);
        c.add(p, g1);
    }

    //insets属性
    void init5() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 1;
        g1.gridy = 1;
        g1.gridwidth = 1;
        g1.gridheight = 1;
        g1.insets = new Insets(20, 5, 5, 10);
        c.add(new JButton("@"), g1);

    }

    //ipadx与ipady属性
    void init6() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 2;
        g1.gridy = 2;
        g1.ipadx = 10;
        g1.ipady = 10;
        c.add(new JButton("组件"), g1);

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 4;
        g2.gridy = 2;
        g2.ipadx = -10;
        g2.ipady = -10;
        c.add(new JButton("组件"), g2);
    }

    //weightx与weighty属性
    void init7() {
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 2;
        g1.gridy = 2;
        g1.weightx = 10;
        g1.weighty = 10;
        c.add(new JButton("组件"), g1);
    }

    //为了方便观察效果
    void createButton() {
        for (int i = 0; i < 9; i++) {
            GridBagConstraints g1 = new GridBagConstraints();
            g1.gridx = i;
            g1.gridy = 0;
            c.add(new JButton("组件"), g1);

            GridBagConstraints g2 = new GridBagConstraints();
            g2.gridx = 0;
            g2.gridy = i;
            c.add(new JButton("组件"), g2);
        }
    }
}
