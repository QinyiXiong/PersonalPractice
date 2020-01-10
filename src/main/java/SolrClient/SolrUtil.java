package SolrClient;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 覃义雄
 * @dateTime: 2020-01-06 13:52
 * @project_Name: PersonalPractice
 * @Name: SolrUtil
 * @Describe：
 */
public class SolrUtil {
    //solr服务器所在的地址，new_core为自己创建的文档库目录
    private final static String SOLR_URL = "http://192.168.248.130:8983/solr/documents";

    /**
     * 获取客户端的连接
     *
     * @return
     */
    public HttpSolrClient createSolrServer() {
        HttpSolrClient solr = null;
        solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000).withSocketTimeout(60000).build();
        return solr;
    }

    /**
     * 往索引库添加文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void addDoc() throws SolrServerException, IOException {
        SolrDocumentList solrlsit = new SolrDocumentList();
        SolrInputDocument document = new SolrInputDocument();

        document.addField("id", "123");
        document.addField("filename", "钢铁侠");
        document.addField("title", "钢铁侠");
        document.addField("description", "一个逗比的码农");
        document.addField("filepath", "group1/M00/00/00/wKj4gl4UHtyAGvGtAA8lRFOCqy4090.pdf");

        HttpSolrClient solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();
        solr.add(document);
        solr.commit();
        solr.close();
        System.out.println("添加成功");
    }

    /**
     * 根据ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void deleteDocumentById() {
        HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();
        try {
            server.deleteById("6");
            server.commit();
            server.close();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 根据设定的查询条件进行文档字段的查询
     * @throws Exception
     */
    public void querySolr() throws Exception {

        HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();
        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数

        //query.set("q", "*:*");// 参数q  查询所有
        query.set("q", "钢铁侠");//相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
        query.addFacetQuery("id:[0 TO 9]");
        query.addFilterQuery("description:一个逗比的码农");

        //参数df,给query设置默认搜索域，从哪个字段上查找
        query.set("df", "filename");

        //参数sort,设置返回结果的排序规则
        query.setSort("id",SolrQuery.ORDER.desc);

        //设置分页参数
        query.setStart(0);
        query.setRows(10);

        //设置高亮显示以及结果的样式
        query.setHighlight(true);
        query.addHighlightField("filename");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //执行查询
        QueryResponse response = server.query(query);

        //获取返回结果
        SolrDocumentList resultList = response.getResults();

        for(SolrDocument document: resultList){
            System.out.println("id:"+document.get("id")+"   document:"+document.get("filename")+"    description:"+document.get("description"));
        }
        server.close();


    }

    /**
     * @Description：获取系统当天日期yyyy-mm-dd
     */
    public static String GetCurrentDate(){
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day =sdf.format(dt);
        return day;
    }

    public static String getFileContentType(String filename){
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

    public static void main(String[] args) throws Exception {
        SolrUtil solr = new SolrUtil();
        solr.addDoc();
        solr.querySolr();
    }

}
