package SolrClient;


import com.pansoft.solrclient.Solrdocument;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 覃义雄
 * @dateTime: 2020-01-10 10:59
 * @project_Name: PersonalPractice
 * @Name: TestSolr
 * @Describe：
 */
public class TestSolr {
    public static void main(String[] args) {
        //1.删除索引示例
//        Solrdocument.deleteDocumentById("1");
//        Solrdocument.deleteDocumentById("2");

        //2.上传文档至solr并建立索引 （支持多文件）
//        Map<String,Object> data1 = new HashMap<String, Object>();
//        Map<String,Object> data2 = new HashMap<String, Object>();
//        File f1 = new File("/Users/pe_qyx/Downloads/es.pdf");
//        File f2 = new File("/Users/pe_qyx/Downloads/jenkins.docx");
//
//        // 必须且唯一
//        data1.put("id", "1");
//        data2.put("id", "2");
//
//        // 非必须
//        data1.put("description","hehe");
//        data2.put("description","哈哈");
//        data1.put("author","peng");
//        data2.put("author","云");
//        data1.put("title", "我爱北京天安门");
//        data2.put("title", "我爱北京天安门");
//        data1.put("fastdfspath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4090.pdf");
//        data2.put("fastdfspath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4110.docx");
//
//        // 必须
//        data1.put("file",f1);
//        data2.put("file",f2);
//
//        List<Map<String,Object>> files = new ArrayList<Map<String,Object>>();
//        files.add(data1);
//        files.add(data2);
//
//        int res = Solrdocument.indexDocument(files);
//        System.out.println(res==1?"上传建立索引成功":"失败");

        // 3.查询示例  查询结果集中返回2条记录
//        List<Map<String,Object>> reslist =  Solrdocument.queryDocument("我爱",0,2);
//        for (Map<String, Object> res : reslist) {
//            System.out.println(res.get("title"));
//        }


    }
}
