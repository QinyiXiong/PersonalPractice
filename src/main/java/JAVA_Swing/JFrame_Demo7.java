package JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 21:22
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo7
 */

//JComboBox
public class JFrame_Demo7 extends JFrame {
    public JFrame_Demo7() throws HeadlessException {
        setBounds(100, 100, 190, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

//        JComboBox<String> comboBox = new JComboBox<>();
//        comboBox.addItem("身份证");//第一种方式：在下拉列表中添加数据
//        comboBox.addItem("学生证");
//        comboBox.addItem("工作证");
//        comboBox.addItem("报到证");

//        String [] items = {"数组元素1","数组元素2","数组元素3"};
//        JComboBox<String> comboBox = new JComboBox<>(items); //第二种方式：使用String数组添加下拉列表元素


//        final JComboBox<String> comboBox = new JComboBox<String>();
//        String[] items = {"数组元素1", "数组元素2", "数组元素3"};
//        ComboBoxModel cm = new DefaultComboBoxModel<>(items); //第三种方式：创建下拉列表模型
//        comboBox.setModel(cm); //向下拉列表添加数据模型
//
//        JButton btn = new JButton("打印");

//        btn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("选中的索引：" + comboBox.getSelectedIndex());//获取选中的索引
//                System.out.println("选中的值：" + comboBox.getSelectedItem()); //获取选中的值
//            }
//        });
//
//        btn.setBounds(100, 10, 60, 20);
//
//        comboBox.setEditable(true); //设置下拉框可编辑
//        comboBox.setBounds(10, 10, 80, 21); //设定坐标和大小
//        c.add(comboBox);
//        c.add(btn);
//
//        setVisible(true);

    }

    public static void main(String[] args) {
        new JFrame_Demo7();

    }
}
