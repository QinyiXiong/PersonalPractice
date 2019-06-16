package JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 10:50
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo1
 */
public class JFrame_Demo1 extends JFrame {
    public JFrame_Demo1() throws HeadlessException {
        setVisible(true);//设置窗体可见
        setTitle("窗体标题");//设置窗体标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(960, 540, 350, 200);
        Container c = getContentPane();
        c.setBackground(Color.white);//设置背景颜色
        JLabel l = new JLabel("这是一个窗体");
        c.add(l);//添加组件
        //c.remove(l);//删除组件
        c.validate();//验证容器中的组件(刷新)
        //setResizable(false);//设置窗体不能改变大小

    }

    public static void main(String[] args) {
        new JFrame_Demo1();
    }
}
