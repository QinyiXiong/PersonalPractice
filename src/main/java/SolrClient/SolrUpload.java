package SolrClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;

/**
 * @author: 覃义雄
 * @dateTime: 2020-01-06 18:21
 * @project_Name: PersonalPractice
 * @Name: SolrUpload
 * @Describe：索引pdf等文本文件
 */
public class SolrUpload {

    public static void main(String[] args)
    {

        String fileName = "/Users/pe_qyx/Downloads/es.pdf";
        String solrId = "2020221099cn.pdf";

        try
        {
            indexFilesSolrCell(solrId, solrId,fileName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SolrServerException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @Description：获取系统当天日期yyyy-mm-dd
     */
    public static String GetCurrentDate(){
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day =sdf.format(dt);
        return day;
    }

    public static void indexFilesSolrCell(String fileName, String solrId, String path)
            throws IOException, SolrServerException
    {
        String urlString = "http://192.168.248.130:8983/solr/documents";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();

        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");

        String contentType = getFileContentType(fileName);

        up.addFile(new File(path),contentType);

        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);

        up.setParam("literal.id", fileName);
        up.setParam("literal.filename", "es");
        up.setParam("literal.description", "~~~~~~");
        up.setParam("literal.author", "大鹏");//文件路径
        up.setParam("literal.title", "测试上传pdf2");//文件上传时间
        up.setParam("literal.filetype", fileType);//文件类型，doc,pdf
        up.setParam("literal.filepath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4090.pdf");//文件类型，doc,pdf
        up.setParam("fmap.content", "attr_content");//文件内容
        up.setAction(ACTION.COMMIT, true, true);
        solr.request(up);
        solr.close();
        System.out.println("上传成功！～～～");
        // 多文件上传示例
//        ContentStreamUpdateRequest ups; //这行代码不要写到循环体里面，否则影响solr建立索引性能

//        for(File file:files){
//            ups=new ContentStreamUpdateRequest("/update/extract");
//            ups.addFile(new File("mailing_lists.pdf"),contentType);
//            ups.setParam("literal.id", "mailing_lists.pdf");
//            solr.request(ups);
//        }
//        solr.commit();
//        solr.close();
    }


    public static String getFileContentType(String filename) {
        String contentType = "";
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);
        if (prefix.equals("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (prefix.equals("pdf")) {
            contentType = "application/pdf";
        } else if (prefix.equals("doc")) {
            contentType = "application/msword";
        } else if (prefix.equals("txt")) {
            contentType = "text/plain";
        } else if (prefix.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (prefix.equals("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (prefix.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (prefix.equals("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }

        else {
            contentType = "othertype";
        }

        return contentType;
    }
}
