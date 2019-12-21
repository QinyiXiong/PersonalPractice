package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 11:35
 * @project_Name: PersonalPractice
 * @Name: JLabel_Demo2
 */

/*
 * 使用标签JLabel实现展示图片
 *
 * */
public class JLabel_Demo2 extends JFrame {
    public JLabel_Demo2() throws HeadlessException {

        setBounds(100, 100, 500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();

        JLabel l = new JLabel("这是一个展示图片的标签");
        //获取图片url路径
//        URL url = JLabel_Demo2.class.getResource("./111.jpg") ;
//        System.out.println(url);
        Icon icon = new ImageIcon("src/main.java.JAVA_Swing/111.jpg"); //获取相应路径下的图片文件
        l.setIcon(icon); //添加图片

//        l.setSize(20,30);//设定标签大小，即使设置标签大小，也不会改变图片大小

        c.add(l);


        setVisible(true);
    }

    public static void main(String[] args) {
        new JLabel_Demo2();
    }
}
