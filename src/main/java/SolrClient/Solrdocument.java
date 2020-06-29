package SolrClient;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 覃义雄
 * @dateTime: 2020-01-10 09:43
 * @project_Name: PersonalPractice
 * @Name: SolrClient
 * @Describe：
 */
public class Solrdocument {
    private final static String SOLR_URL = "http://localhost:8983/solr/documents";

    /**
     * 根据ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public static int deleteDocumentById(String id){
        int res = 0; //失败
        HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();

        try {
            server.deleteById(id);
            server.commit();
            server.close();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res = 1; //成功
        return res;
    }

    /**
     * 文件上传
     * @param files
     * @return
     */

    public static int indexDocument(List<Map<String,Object>> files){
        int res = 0;
        SolrClient solr = new HttpSolrClient.Builder(SOLR_URL).build();
        ContentStreamUpdateRequest ups; //这行代码不要写到循环体里面，否则影响solr建立索引性能
        try {
            for (Map<String, Object> file : files) {
                ups=new ContentStreamUpdateRequest("/update/extract");
                File tempfile = (File) file.get("file");
                String fileType = tempfile.getName().substring(tempfile.getName().lastIndexOf(".")+1);

                String id = file.get("id") == null?"":file.get("id").toString();
                String description = file.get("description") == null?"":file.get("description").toString();
                String author = file.get("author") == null?"":file.get("author").toString();
                String title = file.get("title") == null?"":file.get("title").toString();
                String fastdfspath = file.get("fastdfspath") == null?"":file.get("fastdfspath").toString();

                ups.addFile(tempfile, SolrUtil.getFileContentType(tempfile.getName()));
                ups.setParam("literal.id", id);
                ups.setParam("literal.filename", tempfile.getName());
                ups.setParam("literal.description", description);
                ups.setParam("literal.author", author); // 文件路径
                ups.setParam("literal.title", title); // 文件上传时间
                ups.setParam("literal.filetype", fileType); // 文件类型，doc,pdf
                ups.setParam("literal.fastdfspath", fastdfspath);
                ups.setParam("literal.createtime", SolrUtil.GetCurrentDate());
                ups.setParam("fmap.content", "attr_content");//文件内容
                solr.request(ups);
            }
            solr.commit();
            solr.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } finally {
        }
        res = 1;
        return  res;
    }

    public static List<Map<String,Object>> queryDocument(String keyword,int start,int end){
        HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();
        SolrQuery query = new SolrQuery();
        //下面设置solr查询参数
        if(keyword == null){
            keyword = "";
        }
        String queryStr = "title:"+ keyword +" OR " +"filetype:" + keyword + " OR " +"filename:" + keyword +" OR " + "description:" + keyword + " OR " + "author:" + keyword + " OR " + "attr_content:" + keyword + " OR " + "createtime:" + keyword;
        query.setQuery(queryStr);
        query.setSort("id",SolrQuery.ORDER.desc);

        //设置分页参数
        query.setStart(start);
        query.setRows(end);

        List<Map<String,Object>> reslist = new ArrayList<Map<String, Object>>();

        try {
            //执行查询
            QueryResponse response = server.query(query);

            //获取返回结果
            SolrDocumentList resultList = response.getResults();

            for(SolrDocument document: resultList){
                Map<String,Object> temp = new HashMap<String, Object>();
                temp.put("id", document.get("id"));
                temp.put("filename",document.get("filename"));
                temp.put("description",document.get("description"));
                temp.put("author",document.get("author"));
                temp.put("title",document.get("title"));
                temp.put("filetype",document.get("filetype"));
                temp.put("fastdfspath",document.get("fastdfspath"));
                temp.put("createtime",document.get("createtime"));
                temp.put("attr_content",document.get("attr_content"));

                reslist.add(temp);
            }
            server.close();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return reslist;
    }

}
