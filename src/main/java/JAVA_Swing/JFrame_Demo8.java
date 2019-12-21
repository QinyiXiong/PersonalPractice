package JAVA_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/16 21:44
 * @project_Name: PersonalPractice
 * @Name: JFrame_Demo8
 */

//创建一个列表框JList
public class JFrame_Demo8 extends JFrame {
    public JFrame_Demo8() throws HeadlessException {
        setBounds(100, 100, 217, 167);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);

        String[] items = {"元素1", "元素2", "元素3", "元素4", "元素5", "元素6", "元素7", "元素8"};
//        JList<String> jl = new JList<>(items);

        DefaultListModel<String> modelJL = new DefaultListModel<String>(); //列表框数据模型
        for (String item : items) {
            modelJL.addElement(item);  //向数据模型添加元素
        }
        final JList<String> jl = new JList<String>();
        jl.setModel(modelJL); //列表框载入数据模型
        /*参数说明：
         * ListSelectionModel.SINGLE_SELECTION：单选模式
         * ListSelectionModel.SINGLE_INTERVAL_SELECTION：只能连续选择相邻的元素
         * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION：单选或多选或随机选
         * */
        jl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //设置选择模式

        JScrollPane jscp = new JScrollPane(jl); //为列表框添加滚动条
        jscp.setBounds(10, 10, 100, 100);
        c.add(jscp);

        JButton btn = new JButton("确认");
        btn.setBounds(120, 90, 70, 25);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取列表框中选中的所有元素
                java.util.List<String> values = jl.getSelectedValuesList();
                for (String value : values) {
                    System.out.println(value);
                }

                System.out.println("------------end-----------");
            }
        });

        c.add(btn);

        setVisible(true);
    }

    public static void main(String[] args) {
        new JFrame_Demo8();
    }
}
