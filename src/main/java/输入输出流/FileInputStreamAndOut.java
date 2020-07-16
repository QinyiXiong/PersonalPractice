package 输入输出流;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: Pe_Qyx
 * @dateTime: 2019/6/11 21:52
 * @project_Name: PersonalPractice
 * @Name: FileInputStreamAndOut
 */


/*
 * 复制文件
 *
 * */
public class FileInputStreamAndOut {
    public static void main(String[] args) {
        try {
            FileInputStream fin = new FileInputStream("C:\\Users\\Pe_Qyx\\Pictures\\壁纸\\111.jpg");
            FileOutputStream fout = new FileOutputStream("C:\\Users\\Pe_Qyx\\Pictures\\111.jpg");
            byte[] copyPicture = new byte[fin.available()];
            fin.read(copyPicture); //将文件读入byte数组
            fout.write(copyPicture);  //将数组写入文件
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
