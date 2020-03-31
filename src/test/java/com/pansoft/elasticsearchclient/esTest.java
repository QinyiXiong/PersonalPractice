//package com.pansoft.elasticsearchclient;
//
//import com.pansof.fastdfsclient.Fastdfsclient;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @author: 覃义雄
// * @dateTime: 2020/3/10 10:22 上午
// * @project_Name: elasticsearchclient
// * @Name: esTest
// */
//public class esTest {
//    @Test
//    public void testProperties() throws Exception {
//        Fastdfsclient fastdfsclient = new Fastdfsclient();
//        fastdfsclient.download_bytes("");
//    }
//
//
//    @Test
//    public void testUpdate(){
//
//        // 更新
//        Map<String,Object> data1 = new HashMap<String, Object>();
//
//        data1.put("id", "1");
////        data1.put("description","hehe");
////        data1.put("author","peng");
////        data1.put("title", "我爱北京天安门");
////        data1.put("keywords","测试文件关键字;关键字");
////        data1.put("fastdfspath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4090.pdf");
////
////        //权限
////        data1.put("flevel","0101");
////        data1.put("fsvrdir","0202");
//        data1.put("fstatus","ceshi123123");
//
//        List<Map<String,Object>> files = new ArrayList<Map<String,Object>>();
//        files.add(data1);
//        int res= Esdocument.updateDocument(files);
//        System.out.println(res==1?"上传建立索引成功":"失败");
//
//    }
//
//    @Test
//    public void testindex(){
//
//        // 插入
//        Map<String,Object> data1 = new HashMap<String, Object>();
//        Map<String,Object> data2 = new HashMap<String, Object>();
//
//        File f1 = new File("/Users/pe_qyx/Downloads/新建文件夹/2.pdf");
//        File f2 = new File("/Users/pe_qyx/Downloads/新建文件夹/3.pdf");
////        StringBuffer f2 = new StringBuffer();
////        f2.append("我爱北京天安门，朋总你最棒");
//
//        // 必须且唯一
//        data1.put("id", "testAddDataContent7");
//        data2.put("id", "testAddDataContent7");
//
//        // 非必须
//        data1.put("description","hehe");
//        data1.put("author","peng");
//        data1.put("title", "我爱北京天安门");
//        data1.put("keywords","政策大文本");
//        //权限
//        data1.put("flevel","0101");
//        data1.put("fsvrdir","0202");
//        data1.put("dataSource","1");
////        data1.put("fstatus","1111");
//
//        // 非必须
//        data2.put("description","hehe");
//        data2.put("author","peng");
//        data2.put("title", "我爱北京天安门");
//        data2.put("keywords","政策大文本");
//        //权限
//        data2.put("flevel","0101");
//        data2.put("fsvrdir","0202");
//        data2.put("dataSource","2");
//
//        data1.put("fastdfspath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4090.pdf");
//        data2.put("fastdfspath", "好的");
//        data2.put("filename", "也许可以完成");
//        // 必须
//        data1.put("file",f1);
//        data2.put("file",f2);
//
//        List<Map<String,Object>> files = new ArrayList<Map<String,Object>>();
//        files.add(data1);
//        files.add(data2);
//
//        //多文件建立索引
////        int res = Esdocument.indexDocument(files);
////        System.out.println(res==1?"上传建立索引成功":"失败");
//        //单文件建立索引
//        int res1 = Esdocument.indexDoucument(data1);
////        int res2 = Esdocument.indexDoucument(data2);
//        System.out.println(res1==1?"上传建立索引成功":"失败");
////        System.out.println(res2==1?"上传建立索引成功":"失败");
//    }
//
//    @Test
//    public void testupdatecontent(){
//        int res = Esdocument.updateDocumentContent("testAddDataContent7");
//        System.out.println(res==1?"上传建立索引成功":"失败");
//    }
//
//    @Test
//    public void testquery(){
//        //        fstatus:3
////           flevel:public
//        List<String> sql = new ArrayList<>();
//        /**
//         * flevel:public OR (fsvrdir:0502* AND ( flevel:public OR flevel:private))OR (fsvrdir:03* AND ( flevel:public OR flevel:private))OR (fsvrdir:0402* AND ( flevel:public OR flevel:private))OR (fsvrdir:0501* AND ( flevel:public OR flevel:private))OR (fsvrdir:0302* AND ( flevel:public OR flevel:private))OR (fsvrdir:0401* AND ( flevel:public OR flevel:private))OR (fsvrdir:0301* AND ( flevel:public OR flevel:private))OR (fsvrdir:0202* AND ( flevel:public OR flevel:private))OR (fsvrdir:0203* AND ( flevel:public OR flevel:private))OR (fsvrdir:01* AND ( flevel:public OR flevel:private))OR (fsvrdir:08* AND ( flevel:public OR flevel:private))OR (fsvrdir:02* AND ( flevel:public OR flevel:private))OR (fsvrdir:0101* AND ( flevel:public OR flevel:private))OR (fsvrdir:07* AND ( flevel:public OR flevel:private))OR (fsvrdir:06* AND ( flevel:public OR flevel:private))OR (fsvrdir:0102* AND ( flevel:public OR flevel:private))OR (fsvrdir:0201* AND ( flevel:public OR flevel:private))OR (fsvrdir:05* AND ( flevel:public OR flevel:private))OR (fsvrdir:04* AND ( flevel:public OR flevel:private))
//         */
////        sql.add("flevel:public OR (fsvrdir:0502* AND ( flevel:public OR flevel:private))OR (fsvrdir:03* AND ( flevel:public OR flevel:private))OR (fsvrdir:0402* AND ( flevel:public OR flevel:private))OR (fsvrdir:0501* AND ( flevel:public OR flevel:private))OR (fsvrdir:0302* AND ( flevel:public OR flevel:private))OR (fsvrdir:0401* AND ( flevel:public OR flevel:private))OR (fsvrdir:0301* AND ( flevel:public OR flevel:private))OR (fsvrdir:0202* AND ( flevel:public OR flevel:private))OR (fsvrdir:0203* AND ( flevel:public OR flevel:private))OR (fsvrdir:01* AND ( flevel:public OR flevel:private))OR (fsvrdir:08* AND ( flevel:public OR flevel:private))OR (fsvrdir:02* AND ( flevel:public OR flevel:private))OR (fsvrdir:0101* AND ( flevel:public OR flevel:private))OR (fsvrdir:07* AND ( flevel:public OR flevel:private))OR (fsvrdir:06* AND ( flevel:public OR flevel:private))OR (fsvrdir:0102* AND ( flevel:public OR flevel:private))OR (fsvrdir:0201* AND ( flevel:public OR flevel:private))OR (fsvrdir:05* AND ( flevel:public OR flevel:private))OR (fsvrdir:04* AND ( flevel:public OR flevel:private))");
//        sql.add("fsvrdir:0202");
//        sql.add("flevel:0101");
//        sql.add("datasource:1 OR datasource:2");
//
//        Map<String,Object> reslist = Esdocument.queryDocument("朋总",0,10,sql);
//        System.out.println(reslist);
////        for (Map<String, Object> res : reslist){
////            System.out.println(res.get("title"));
////        }
//    }
//
//}
