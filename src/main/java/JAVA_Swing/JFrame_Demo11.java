package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 22:29
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo11
 */
//JTextArea
public class JFrame_Demo11 extends JFrame {

    public JFrame_Demo11() throws HeadlessException {
        setBounds(100, 100, 400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        JTextArea area = new JTextArea();
        area.setText("heheda"); //设定文本内容
        area.setRows(5);
        area.setColumns(20);
        area.append("添加内容"); //添加内容
        area.insert("插入的内容", 2); //在第二个字符后面插入内容
        area.setFont(new Font("楷体", Font.PLAIN, 20));

        JScrollPane jscp = new JScrollPane(area); //给文本域添加滚动条


        c.add(jscp);

        setVisible(true);
    }

    public static void main(String[] args) {

        new JFrame_Demo11();
    }
}
