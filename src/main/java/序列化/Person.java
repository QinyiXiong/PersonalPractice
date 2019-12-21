package main.java.序列化;

import java.io.Serializable;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 22:03
 * @project_Name: PersonalPractice
 * @Name: Person
 */
public class Person implements Serializable {
    //File -> Settings->Editor->Inspections->Java->Serialization issues->Serializable class without 'serialVersionUID' 打上勾
    //'serialVersionUID' field not declared 'private static final long'  打上勾
    private static final long serialVersionUID = -5090861933306306871L; //选中类名 Alt + Enter ->
    private String name;
    private String sex;
    private int age;

    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public static void main(String[] args) {
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }

        String[] arrs = {"aaa", "bbb", "ccc"};
        for (String str : arrs) {
            System.out.println(str);
        }

        try {
            System.out.println("Ctrl+Alt+t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
