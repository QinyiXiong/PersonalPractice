package JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 22:20
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo10
 */

//JPasswordField
public class JFrame_Demo10 extends JFrame {
    public JFrame_Demo10() throws HeadlessException {
        setBounds(100, 100, 400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        final JPasswordField jp = new JPasswordField();
        jp.setColumns(20);  //设置密码框长度，20个字符
//        jp.setFont(new Font("Arial",Font.BOLD,18));
        jp.setEchoChar('#'); //设置回显字符
        jp.setEchoChar('\u2605'); //星星

        jp.addActionListener(new ActionListener() {//添加动作监听,回车时触发
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] ch = jp.getPassword(); //获取密码的字符数组
                String str = new String(ch);
                System.out.println(str);
            }
        });

        c.add(jp);

        setVisible(true);
    }

    public static void main(String[] args) {

        new JFrame_Demo10();
    }
}
