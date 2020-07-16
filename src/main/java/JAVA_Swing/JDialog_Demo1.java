package JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 11:00
 * @project_Name: PersonalPractice
 * @Name: JDialog_Demo1
 */
public class JDialog_Demo1 extends JDialog {
    public JDialog_Demo1(JFrame frame) {
        //第一个参数是父窗体对象，第二个参数是对话框标题，第三个参数：是否阻塞父窗体即只能在当前弹出的窗体中操作
        super(frame, "对话框标题", true);
        Container c = getContentPane();//获取窗体容器
        c.add(new JLabel("这是一个对话框"));


    }

    public static void main(String[] args) {
        final JFrame f = new JFrame("父窗体");
        f.setBounds(960, 540, 660, 500);
        Container c = f.getContentPane();
        JButton btn = new JButton("弹出对话框");
        c.setLayout(new FlowLayout()); //设置布局,使用流布局
        c.add(btn);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //添加动作监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog_Demo1 d = new JDialog_Demo1(f);
                d.setBounds(980, 550, 360, 200);
                d.setVisible(true);

            }
        });

    }
}
