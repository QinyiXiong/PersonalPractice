package FastClient;

import org.csource.fastdfs.FileInfo;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author: 覃义雄
 * @dateTime: 2020-01-03 14:41
 * @project_Name: PersonalPractice
 * @Name: testFastdfs
 * @Describe： fastdfs 测试类
 */
public class testFastdfs {
    //上传文件  group1/M00/00/00/wKj4gl4O-PuAKUxYAAROQnOZ3eI936.png
    public void uploadFast(String request)throws Exception {
        // 1、把FastDFS提供的jar包添加到工程中
        // 2、初始化全局配置。加载一个配置文件。
        String confUrl = this.getClass().getClassLoader().getResource("fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //上传文件
        String filePath = fastDFSClient.uploadFile("/Users/pe_qyx/Desktop/hehe.png");
        System.out.println("返回路径：" + filePath);
        //省略其他

    }

    public void deleteFast(String request)throws Exception{
        String confUrl = this.getClass().getClassLoader().getResource("fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //删除文件
        int flag=fastDFSClient.delete_file("group1/M00/00/00/wKgrPFpf94KASn3vAAsC7gailiI018.jpg");
        System.out.println("删除结果：" +(flag==0?"删除成功":"删除失败"));

    }

    public void downloadFast(String request)throws Exception{
        String confUrl = this.getClass().getClassLoader().getResource("fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //下载文件到用户桌面位置
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory(); //读取桌面路径
        int downFlag=fastDFSClient.download_file("group1/M00/00/00/wKj4gl4OuMKAYsHnAADcCb28qS0100.png",new BufferedOutputStream(new FileOutputStream(com.getPath()+"\\aa.png")));
        System.out.println("下载结果为：" +(downFlag==0?"下载文件成功":"下载文件失败"));

    }

    ///group1/M00/00/00/wKj4gl4OuMKAYsHnAADcCb28qS0100.png
    public void getInfofast(String request)throws Exception{
        String confUrl = this.getClass().getClassLoader().getResource("fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //获取文件信息
        FileInfo file=fastDFSClient.getFile("group1","M00/00/00/wKj4gl4OuMKAYsHnAADcCb28qS0100.png");
        System.out.println("获取文件信息成功："+file.getFileSize());

    }

        public static void main(String[] args) {
            testFastdfs test = new testFastdfs();
            try {
//                test.getInfofast("hehe");
//                test.downloadFast("hehe");
                test.uploadFast("hehe");
            }catch (Exception e){
                e.printStackTrace();
            }
    }
}
