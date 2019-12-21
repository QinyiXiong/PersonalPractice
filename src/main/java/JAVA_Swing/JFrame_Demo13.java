package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 23:09
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo13
 */

//焦点事件监听
public class JFrame_Demo13 extends JFrame {
    public JFrame_Demo13() throws HeadlessException {
        setBounds(100, 100, 230, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextField jt1 = new JTextField(10);
        jt1.setBounds(10, 10, 66, 21);
        contentPane.add(jt1);

        JTextField jt2 = new JTextField(10);
        jt2.setBounds(10, 41, 66, 21);
        contentPane.add(jt2);

        JTextField jt3 = new JTextField(10);
        jt3.setBounds(10, 72, 66, 21);
        contentPane.add(jt3);

        JLabel jl1 = new JLabel("未获得焦点");
        jl1.setBounds(86, 13, 100, 15);
        contentPane.add(jl1);

        JLabel jl2 = new JLabel("未获得焦点");
        jl2.setBounds(86, 44, 100, 15);
        contentPane.add(jl2);

        JLabel jl3 = new JLabel("未获得焦点");
        jl3.setBounds(86, 75, 100, 15);
        contentPane.add(jl3);


        jt1.addFocusListener(new MyFocusListenter(jl1));  //使用自定义的监听实现类
        jt2.addFocusListener(new MyFocusListenter(jl2));  //使用自定义的监听实现类
        jt3.addFocusListener(new MyFocusListenter(jl3));  //使用自定义的监听实现类

        setVisible(true);
    }

    public static void main(String[] args) {

        new JFrame_Demo13();
    }

    class MyFocusListenter implements FocusListener { //自定义的焦点事件监听实现类


        public JLabel jtemp;

        public MyFocusListenter(JLabel jtemp) {
            this.jtemp = jtemp;
        }

        @Override
        public void focusGained(FocusEvent e) {
            JTextField temp = (JTextField) e.getSource(); //获取触发事件的组件
            temp.setBorder(BorderFactory.createLineBorder(Color.green)); //获取焦点的文本框设置绿色边框

            jtemp.setText("获得焦点");
            jtemp.setForeground(Color.green);

        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField temp = (JTextField) e.getSource(); //获取触发事件的组件
            temp.setBorder(BorderFactory.createLineBorder(Color.RED)); //获取焦点的文本框设置红色边框
            jtemp.setText("失去焦点");
            jtemp.setForeground(Color.RED);
        }
    }
}
