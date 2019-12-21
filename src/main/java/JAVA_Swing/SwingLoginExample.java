package main.java.JAVA_Swing;

import javax.swing.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 9:36
 * @project_Name: PersonalPractice
 * @Name: SwingLoginExample
 */


/*
JFrame – java的GUI程序的基本思路是以JFrame为基础，它是屏幕上window的对象，能够最大化、最小化、关闭。

JPanel – Java图形用户界面(GUI)工具包swing中的面板容器类，包含在javax.swing 包中，可以进行嵌套，功能是对窗体中具有相同逻辑功能的组件进行组合，是一种轻量级容器，可以加入到JFrame窗体中。。

JLabel – JLabel 对象可以显示文本、图像或同时显示二者。可以通过设置垂直和水平对齐方式，指定标签显示区中标签内容在何处对齐。默认情况下，标签在其显示区内垂直居中对齐。默认情况下，只显示文本的标签是开始边对齐；而只显示图像的标签则水平居中对齐。

JTextField –一个轻量级组件，它允许编辑单行文本。

JPasswordField – 允许我们输入了一行字像输入框，但隐藏星号(*) 或点创建密码(密码)

JButton – JButton 类的实例。用于创建按钮类似实例中的 "Login"。
*/
public class SwingLoginExample {
    private static void placeComponents(JPanel panel) {

        /*
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("User:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
    }

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Login Example");
        // 设置窗体的大小 单位：像素
        frame.setSize(350, 200);

        //设置窗体的起始位置
        frame.setLocation(960, 540);

        //也可以合并为一句话：frame.setBounds(960,540,350,200);

        /**
         * 窗体关闭规则
         * EXIT_ON_CLOSE：隐藏窗口并停止程序
         * DO_NOTHING_ON_CLOSE:点击关闭窗口，无任何反应（简单理解为屏蔽窗口的关闭按钮）
         * HIDE_ON_CLOSE:隐藏窗口，但不停止程序
         * DISPOSE_ON_CLOSE:释放窗体资源
         *
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置窗体界面可见
        frame.setVisible(true);

        //获取窗体的位置
        System.out.println("X = " + frame.getX() + " Y = " + frame.getY());

//        //获取窗体容器
//        Container c = frame.getContentPane();
//        c.setBackground(Color.white);//设置背景颜色
//        JLabel l = new JLabel("这是一个窗体");
//        c.add(l);//添加组件
//        c.remove(l);//删除组件
//        c.validate();//验证容器中的组件(刷新)
//
//        frame.setResizable(false);//设置窗体不能改变大小

    }
}
