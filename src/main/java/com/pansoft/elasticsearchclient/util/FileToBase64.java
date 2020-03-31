package com.pansoft.elasticsearchclient.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * @author: 覃义雄
 * @dateTime: 2020/3/9 5:59 下午
 * @project_Name: elasticsearchclient
 * @Name: FileToBase64
 */
public class FileToBase64 {
    /**
     * <p>将文件转成base64 字符串</p>
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(File file) throws Exception {
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        FileInputStream inputFile = new FileInputStream(file);
        String base64encode = "";
        byte[] byteBuf = new byte[3 * 1024 * 1024];
        byte[] base64ByteBuf;
        int count1; //每次从文件中读取到的有效字节数
        while ((count1 = inputFile.read(byteBuf)) != -1) {
            if (count1 != byteBuf.length) {//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count1); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = Base64.encodeBase64(copy); //对有效字节段进行编码
            } else {
                base64ByteBuf = Base64.encodeBase64(byteBuf);
            }
            os1.write(base64ByteBuf, 0, base64ByteBuf.length);
//            base64encode = base64encode + os1.toString();
//            System.out.println(os1.toString());
            os1.flush();
        }
//        System.out.println(os1.toString());
        inputFile.close();
        return os1.toString();
    }

    public static String strConvertBase(String str)throws Exception {
        if(null != str){
            return new BASE64Encoder().encodeBuffer(str.getBytes());
        }
        return null;
    }

    /**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer).replace("\n","").replace("\r","");
    }
    /**
     * <p>将base64字符解码保存文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    /**
     * <p>将base64字符保存文本文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code,String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    public static void main(String[] args) throws Exception {
//        try {
//            String base64Code =encodeBase64File("/Users/Crazy/Pictures/zyb2.jpg");
//            System.out.println(base64Code);
//            decoderBase64File(base64Code, "/Users/Crazy/Desktop/zyb.png");
//            toFile(base64Code, "/Users/Crazy/Desktop/zyb.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ElasticsearchUtil.testfastdfs();
    }
}
