package main.java.JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 22:43
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo12
 */

//动作监听
public class JFrame_Demo12 extends JFrame {
    public JFrame_Demo12() throws HeadlessException {
        setBounds(100, 100, 500, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();

        JPanel centerP = new JPanel();
        centerP.setLayout(new FlowLayout());
        c.add(centerP, BorderLayout.CENTER);

        JPanel southP = new JPanel();
        final JLabel console = new JLabel("点击组件");
        southP.add(console);
        c.add(southP, BorderLayout.SOUTH);

        JButton btn = new JButton("按钮");
        centerP.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("按钮被点击了");

            }
        });

        JTextField jt = new JTextField(10);
        jt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                JTextField jtTmp = (JTextField) obj;
                System.out.println(jtTmp.getText());
                console.setText("文本框中点击回车");
            }
        });
        centerP.add(jt);


        JCheckBox jc = new JCheckBox("多选框");
        jc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("多选框被点击了");
            }
        });
        centerP.add(jc);

        JRadioButton jr = new JRadioButton("单选框");
        jr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("单选框被点击了");
            }
        });
        centerP.add(jr);

        String[] values = {"选项1", "选项2", "选项3"};
        JComboBox jb = new JComboBox(values);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("下拉列表被选则");
            }
        });
        centerP.add(jb);

        c.validate(); // 重新验证一下容器中的组件

        setVisible(true);
    }

    public static void main(String[] args) {

        new JFrame_Demo12();
    }
}
