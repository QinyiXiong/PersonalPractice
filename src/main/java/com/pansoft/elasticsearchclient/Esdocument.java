package com.pansoft.elasticsearchclient;

import com.pansoft.elasticsearchclient.consts.DocConstant;
import com.pansoft.elasticsearchclient.util.ElasticsearchUtil;
import com.pansoft.elasticsearchclient.util.FileToBase64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author: 覃义雄
 * @dateTime: 2019-12-20 15:30
 * @project_Name: elasticsearchclient
 * @Name: Esdocument
 * @Describe：
 */
public class Esdocument {

    private static RestHighLevelClient getClient() {
        RestHighLevelClient client = null;
        try {
            //初始化ES操作客户端
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            String elasticUser = ElasticsearchUtil.getProperties("elasticUser");
            String elasticPassword = ElasticsearchUtil.getProperties("elasticPassword");
            String[] eshost = ElasticsearchUtil.getProperties("elasticsearch.rest.hostNames").split(":");
            String hostname = eshost[0];
            int port = Integer.parseInt(eshost[1]);
            if (elasticUser == null) {
                client = new RestHighLevelClient(
                        RestClient.builder(
                                new HttpHost(hostname, port)
                        )
                );
            } else {
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(elasticUser, elasticPassword));  //es账号密码
                client = new RestHighLevelClient(
                        RestClient.builder(
                                new HttpHost(hostname, port)
                        ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })/*.setMaxRetryTimeoutMillis(2000)*/
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }


    /**
     * 根据ID从索引库删除文档
     *
     * @throws
     * @throws IOException
     */
    public static int deleteDocumentById(String id) {
        int res = 0;
        DeleteRequest delrequest = new DeleteRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id);
        RestHighLevelClient client = getClient();
        try {
            DeleteResponse delresponse = client.delete(delrequest, RequestOptions.DEFAULT);
//            System.out.println(delresponse.status());
            if ("OK".equals(delresponse.status().name())) {
                res = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

//    private static int updateDocumentfile(List<Map<String,Object>> files){
//        int res = 0;
//        return res;
//    }

    public static int updateDocumentContent(String id){
        int res = 0;
        RestHighLevelClient client = getClient();
        try {
            Map<String,Object> params = new HashMap<>();
            String inlineScript = "ctx._source.datacontent = ctx._source.datacontent + ctx._source.attachment.content";
            Script script = new Script(ScriptType.INLINE,"painless",inlineScript,params);
//            Script script = new Script("add_datacontent");
            UpdateRequest uprequest = new UpdateRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).script(script);
            UpdateResponse upresponse = client.update(uprequest,RequestOptions.DEFAULT);
            if ("OK".equals(upresponse.status().name())) {
                res = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;
    }
    public static int updateDocument(List<Map<String, Object>> files) {
        int res = 0;
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> file : files) {
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            String id = file.get(DocConstant.DOC_ID) == null ? "" : file.get(DocConstant.DOC_ID).toString();
            String description = file.get(DocConstant.DOC_DESC) == null ? "" : file.get(DocConstant.DOC_DESC).toString();
            String author = file.get(DocConstant.DOC_AUTHOR) == null ? "" : file.get(DocConstant.DOC_AUTHOR).toString();
            String title = file.get(DocConstant.DOC_TITLE) == null ? "" : file.get(DocConstant.DOC_TITLE).toString();
            String fastdfspath = file.get(DocConstant.DOC_PATH) == null ? "" : file.get(DocConstant.DOC_PATH).toString();
            String keywords = file.get(DocConstant.DOC_KEYWORDS) == null ? "" : file.get(DocConstant.DOC_KEYWORDS).toString();
            //文档权限管理(F_LEVEL,F_SVC_DIR)
            String flevel = file.get(DocConstant.DOC_FLEVEL) == null ? "" : file.get(DocConstant.DOC_FLEVEL).toString();
            String fsvrdir = file.get(DocConstant.DOC_FSVRDIR) == null ? "" : file.get(DocConstant.DOC_FSVRDIR).toString();
            String fstatus = file.get(DocConstant.DOC_FSTATUS) == null ? "" : file.get(DocConstant.DOC_FSTATUS).toString();
            String filename = file.get(DocConstant.DOC_FILENAME) == null ? "" : file.get(DocConstant.DOC_FILENAME).toString();
            String dataSource = file.get("dataSource") == null ? "" : file.get("dataSource").toString();
            if (id.equals("")) {
                return res;
            }

            if (!keywords.equals("")) {
                jsonMap.put(DocConstant.DOC_KEYWORDS, keywords);
            }
            if (!description.equals("")) {
                jsonMap.put(DocConstant.DOC_DESC, description);
            }
            if (!author.equals("")) {
                jsonMap.put(DocConstant.DOC_AUTHOR, author);
            }
            if (!title.equals("")) {
                jsonMap.put(DocConstant.DOC_TITLE, title);
            }
            if (!fastdfspath.equals("")) {
                jsonMap.put(DocConstant.DOC_PATH, fastdfspath);
            }

            if (!filename.equals("")) {
                jsonMap.put(DocConstant.DOC_FILENAME, filename);
            }
            if (!dataSource.equals("")) {
                jsonMap.put(DocConstant.DOC_DATASOURCE, dataSource);
            }

            jsonMap.put(DocConstant.DOC_CRTIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            if (!flevel.equals("")) {
                jsonMap.put(DocConstant.DOC_FLEVEL, flevel);
            }
            if (!fsvrdir.equals("")) {
                jsonMap.put(DocConstant.DOC_FSVRDIR, fsvrdir);
            }
            if (!fstatus.equals("")) {
                jsonMap.put(DocConstant.DOC_FSTATUS, fstatus);
            }

            UpdateRequest uprequest = new UpdateRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).doc(jsonMap);
            bulkRequest.add(uprequest);
////        UpdateResponse upresponse = client.update(uprequest,RequestOptions.DEFAULT);

        }
        RestHighLevelClient client = getClient();
        try {
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if ("OK".equals(bulkResponse.status().name())) {
                res = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    /**
     * 单文件上传
     * @param file
     * @return
     */
    public static int indexDoucument(Map<String, Object> file){
        int res = 0;
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String id = file.get(DocConstant.DOC_ID) == null ? "" : file.get(DocConstant.DOC_ID).toString();
        String description = file.get(DocConstant.DOC_DESC) == null ? "" : file.get(DocConstant.DOC_DESC).toString();
        String author = file.get(DocConstant.DOC_AUTHOR) == null ? "" : file.get(DocConstant.DOC_AUTHOR).toString();
        String title = file.get(DocConstant.DOC_TITLE) == null ? "" : file.get(DocConstant.DOC_TITLE).toString();
        String fastdfspath = file.get(DocConstant.DOC_PATH) == null ? "" : file.get(DocConstant.DOC_PATH).toString();
        String keywords = file.get(DocConstant.DOC_KEYWORDS) == null ? "" : file.get(DocConstant.DOC_KEYWORDS).toString();
        //文档权限管理(F_LEVEL,F_SVC_DIR)
        String flevel = file.get(DocConstant.DOC_FLEVEL) == null ? "" : file.get(DocConstant.DOC_FLEVEL).toString();
        String fsvrdir = file.get(DocConstant.DOC_FSVRDIR) == null ? "" : file.get(DocConstant.DOC_FSVRDIR).toString();
        String fstatus = file.get(DocConstant.DOC_FSTATUS) == null ? "" : file.get(DocConstant.DOC_FSTATUS).toString();
        String filename = file.get(DocConstant.DOC_FILENAME) == null ? "" : file.get(DocConstant.DOC_FILENAME).toString();
        String dataSource = file.get("dataSource") == null ? "" : file.get("dataSource").toString();

        if (file.get("file") == null) {
            return res;
        }
        if (id.equals("")) {
            return res;
        }
        IndexRequest indexRequest = null;
        RestHighLevelClient client = getClient();
        try {
            jsonMap.put("createtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jsonMap.put(DocConstant.DOC_DESC, description);
            jsonMap.put(DocConstant.DOC_AUTHOR, author);
            jsonMap.put(DocConstant.DOC_TITLE, title);
            jsonMap.put(DocConstant.DOC_PATH, fastdfspath);
            jsonMap.put(DocConstant.DOC_KEYWORDS, keywords);
            jsonMap.put(DocConstant.DOC_FLEVEL, flevel);
            jsonMap.put(DocConstant.DOC_FSVRDIR, fsvrdir);
            jsonMap.put(DocConstant.DOC_FSTATUS, fstatus);
            jsonMap.put(DocConstant.DOC_ID, id);
            jsonMap.put(DocConstant.DOC_DATASOURCE, dataSource);

            if(file.get("file") instanceof File){
                File tempfile = (File) file.get("file");
                String fileType = tempfile.getName().substring(tempfile.getName().lastIndexOf(".") + 1);
                jsonMap.put(DocConstant.DOC_FILETYPE, fileType);
                jsonMap.put(DocConstant.DOC_FILENAME, tempfile.getName());
                jsonMap.put("data", FileToBase64.encodeBase64File(tempfile));
                indexRequest = new IndexRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).setPipeline("attachment").source(jsonMap);
            }else if(file.get("file") instanceof StringBuffer){
                StringBuffer tempfile = (StringBuffer) file.get("file");
                String fileType = "text";
                jsonMap.put(DocConstant.DOC_FILETYPE, fileType);
                jsonMap.put(DocConstant.DOC_FILENAME, filename);
                jsonMap.put("datacontent",tempfile.toString());
                indexRequest = new IndexRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).source(jsonMap);
            }else {
                return 0;
            }
            indexRequest.timeout(TimeValue.timeValueSeconds(30)); //设置超时
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            if ("OK".equals(indexResponse.status().name())) {
                res = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 多文件上传
     *
     * @param files
     * @return
     */
    public static int indexDocument(List<Map<String, Object>> files) {
        int res = 0;
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> file : files) {
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            String id = file.get(DocConstant.DOC_ID) == null ? "" : file.get(DocConstant.DOC_ID).toString();
            String description = file.get(DocConstant.DOC_DESC) == null ? "" : file.get(DocConstant.DOC_DESC).toString();
            String author = file.get(DocConstant.DOC_AUTHOR) == null ? "" : file.get(DocConstant.DOC_AUTHOR).toString();
            String title = file.get(DocConstant.DOC_TITLE) == null ? "" : file.get(DocConstant.DOC_TITLE).toString();
            String fastdfspath = file.get(DocConstant.DOC_PATH) == null ? "" : file.get(DocConstant.DOC_PATH).toString();
            String keywords = file.get(DocConstant.DOC_KEYWORDS) == null ? "" : file.get(DocConstant.DOC_KEYWORDS).toString();
            //文档权限管理(F_LEVEL,F_SVC_DIR)
            String flevel = file.get(DocConstant.DOC_FLEVEL) == null ? "" : file.get(DocConstant.DOC_FLEVEL).toString();
            String fsvrdir = file.get(DocConstant.DOC_FSVRDIR) == null ? "" : file.get(DocConstant.DOC_FSVRDIR).toString();
            String fstatus = file.get(DocConstant.DOC_FSTATUS) == null ? "" : file.get(DocConstant.DOC_FSTATUS).toString();
            String filename = file.get(DocConstant.DOC_FILENAME) == null ? "" : file.get(DocConstant.DOC_FILENAME).toString();
            String dataSource = file.get("dataSource") == null ? "" : file.get("dataSource").toString();

            if (file.get("file") == null) {
                return res;
            }
            if (id.equals("")) {
                return res;
            }
            IndexRequest indexRequest = null;
            try {
                jsonMap.put("createtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                jsonMap.put(DocConstant.DOC_DESC, description);
                jsonMap.put(DocConstant.DOC_AUTHOR, author);
                jsonMap.put(DocConstant.DOC_TITLE, title);
                jsonMap.put(DocConstant.DOC_PATH, fastdfspath);
                jsonMap.put(DocConstant.DOC_KEYWORDS, keywords);
                jsonMap.put(DocConstant.DOC_FLEVEL, flevel);
                jsonMap.put(DocConstant.DOC_FSVRDIR, fsvrdir);
                jsonMap.put(DocConstant.DOC_FSTATUS, fstatus);
                jsonMap.put(DocConstant.DOC_ID, id);
                jsonMap.put(DocConstant.DOC_DATASOURCE, dataSource);

                if(file.get("file") instanceof File){
                    File tempfile = (File) file.get("file");
                    String fileType = tempfile.getName().substring(tempfile.getName().lastIndexOf(".") + 1);
                    jsonMap.put(DocConstant.DOC_FILETYPE, fileType);
                    jsonMap.put(DocConstant.DOC_FILENAME, tempfile.getName());
                    jsonMap.put("data", FileToBase64.encodeBase64File(tempfile));
                    indexRequest = new IndexRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).setPipeline("attachment").source(jsonMap);
                }else if(file.get("file") instanceof StringBuffer){
                    StringBuffer tempfile = (StringBuffer) file.get("file");
                    String fileType = "text";
                    jsonMap.put(DocConstant.DOC_FILETYPE, fileType);
                    jsonMap.put(DocConstant.DOC_FILENAME, filename);
                    jsonMap.put("datacontent",tempfile.toString());
                    indexRequest = new IndexRequest(DocConstant._DOC_INDEX, DocConstant._DOC_TYPE, id).source(jsonMap);
                }else {
                    return 0;
                }
                indexRequest.timeout(TimeValue.timeValueSeconds(5)); //设置超时
                bulkRequest.add(indexRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
//            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        }
        RestHighLevelClient client = getClient();
        try {
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if ("OK".equals(bulkResponse.status().name())) {
                res = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static Map<String, Object> queryDocument(String keyword, int start, int end) {
        // 设置查询参数  配置字段的查询权重，适应业务的排序
        QueryBuilder qbtitle = QueryBuilders.matchQuery(DocConstant.DOC_TITLE, keyword).boost(80);
        QueryBuilder qbfilename = QueryBuilders.matchQuery(DocConstant.DOC_FILENAME, keyword);
        QueryBuilder qbdescription = QueryBuilders.matchQuery(DocConstant.DOC_DESC, keyword);
        QueryBuilder qbauthor = QueryBuilders.matchQuery(DocConstant.DOC_AUTHOR, keyword);
        QueryBuilder qbdata = QueryBuilders.matchQuery(DocConstant.DOC_DATA, keyword).boost(1);
        QueryBuilder qbdatacontent = QueryBuilders.matchQuery("datacontent", keyword).boost(1);
        QueryBuilder qbkeywords = QueryBuilders.matchQuery(DocConstant.DOC_KEYWORDS, keyword).boost(100);

        // 查询条件设置  should 代表 OR
        QueryBuilder resqb = QueryBuilders.boolQuery()
                .should(qbtitle)
                .should(qbfilename)
                .should(qbdescription)
                .should(qbauthor)
                .should(qbdata)
                .should(qbdatacontent)
                .should(qbkeywords);

        // 高亮显示配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_TITLE).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_DATA).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field("datacontent").fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_KEYWORDS).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.requireFieldMatch(false);   //如果要多个字段高亮,这项要为false
        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .query(resqb)
                .highlighter(highlightBuilder)
                .from(start)
                .size(end)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
//                .fetchSource(new String[]{"id","filename"},new String[]{}); //设置返回值

        SearchRequest searchRequest = new SearchRequest(DocConstant._DOC_INDEX);
        searchRequest.types(DocConstant._DOC_TYPE);
        searchRequest.source(searchSourceBuilder);

        List<Map<String,Object>> datalist = new ArrayList<Map<String, Object>>();
        Map<String,Object> resMap = new HashMap<String,Object>();
        RestHighLevelClient client = getClient();
        try {
            long startqueryTime = System.currentTimeMillis();
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            long endqueryTime = System.currentTimeMillis();
            SearchHits searchHits = searchResponse.getHits();
            // 总命中结果数
            double resNum = (double) searchHits.getTotalHits();

            for (SearchHit searchHit : searchHits.getHits()) {
                Map<String,Object> temp = new HashMap<String, Object>();
//                System.out.println("document的id:" + searchHit.getId());
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                temp.put(DocConstant.DOC_ID, searchHit.getId());
                temp.put(DocConstant.DOC_FILENAME,sourceAsMap.get(DocConstant.DOC_FILENAME));
                temp.put(DocConstant.DOC_DESC,sourceAsMap.get(DocConstant.DOC_DESC));
                temp.put(DocConstant.DOC_AUTHOR,sourceAsMap.get(DocConstant.DOC_AUTHOR));
                temp.put(DocConstant.DOC_FILETYPE,sourceAsMap.get(DocConstant.DOC_FILETYPE));
                temp.put(DocConstant.DOC_PATH,sourceAsMap.get(DocConstant.DOC_PATH));
                temp.put(DocConstant.DOC_CRTIME,sourceAsMap.get(DocConstant.DOC_CRTIME));
                temp.put(DocConstant.DOC_FSTATUS,sourceAsMap.get(DocConstant.DOC_FSTATUS));
                temp.put(DocConstant.DOC_FLEVEL,sourceAsMap.get(DocConstant.DOC_FLEVEL));
                temp.put(DocConstant.DOC_FSVRDIR,sourceAsMap.get(DocConstant.DOC_FSVRDIR));
                temp.put("dataSource",sourceAsMap.get(DocConstant.DOC_DATASOURCE));
                // 高亮的字段
                temp.put(DocConstant.DOC_TITLE,sourceAsMap.get(DocConstant.DOC_TITLE));
                temp.put(DocConstant.DOC_KEYWORDS,sourceAsMap.get(DocConstant.DOC_KEYWORDS));
                temp.put("attr_content","");
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                if (highlightFields != null || highlightFields.size() != 0) {
                    HighlightField highlightDATA = highlightFields.get(DocConstant.DOC_DATA);
                    HighlightField highlightDATACONTENT = highlightFields.get("datacontent");
                    HighlightField highlightTITLE = highlightFields.get(DocConstant.DOC_TITLE);
                    HighlightField highlightKEYWORDS = highlightFields.get(DocConstant.DOC_KEYWORDS);
                    if (highlightDATA != null) {
                        Text[] fragments = highlightDATA.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put("attr_content",stringBuilder.toString());
//                        System.out.println("高亮显示的content:" + stringBuilder.toString());
                    }else {
                        if(highlightDATACONTENT != null){
                            Text[] fragments = highlightDATACONTENT.getFragments();
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Text text : fragments) {
                                stringBuilder.append(text);
                            }
                            temp.put("attr_content",stringBuilder.toString());
                        }else {
                            temp.put("attr_content","");
                        }

                    }
                    if (highlightTITLE != null) {
                        Text[] fragments = highlightTITLE.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put(DocConstant.DOC_TITLE,stringBuilder.toString());
//                        System.out.println("高亮显示的TITLE:" + stringBuilder.toString());
                    }else {
                        temp.put(DocConstant.DOC_TITLE,sourceAsMap.get(DocConstant.DOC_TITLE));
                    }
                    if (highlightKEYWORDS != null) {
                        Text[] fragments = highlightKEYWORDS.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put(DocConstant.DOC_KEYWORDS,stringBuilder.toString());
//                        System.out.println("高亮显示的KEYWORDS:" + stringBuilder.toString());
                    }else {
                        temp.put(DocConstant.DOC_KEYWORDS,sourceAsMap.get(DocConstant.DOC_KEYWORDS));
                    }
                }
                datalist.add(temp);
            }
            resMap.put("datalist",datalist);
            int pageNo = (int) (Math.ceil((double)start / (double)end))+1;
            int sumPage = (int) (Math.ceil(resNum/end));
            resMap.put("pageNo",pageNo); //当前页号（如 第1页）
            resMap.put("pageNum",datalist.size()); //当前页结果数
            resMap.put("resNum",(int) resNum); // 总命中个数
            resMap.put("sumPage",sumPage); // 总页数
            resMap.put("requestime",endqueryTime - startqueryTime);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resMap;
    }

    public static Map<String, Object> queryDocument(String keyword, int start, int end, List<String> lstSql) {
        // 设置查询参数  配置字段的查询权重，适应业务的排序
        QueryBuilder qbtitle = QueryBuilders.matchQuery(DocConstant.DOC_TITLE, keyword).boost(800);
        QueryBuilder qbfilename = QueryBuilders.matchQuery(DocConstant.DOC_FILENAME, keyword);
        QueryBuilder qbdescription = QueryBuilders.matchQuery(DocConstant.DOC_DESC, keyword);
        QueryBuilder qbauthor = QueryBuilders.matchQuery(DocConstant.DOC_AUTHOR, keyword);
        QueryBuilder qbdata = QueryBuilders.matchQuery(DocConstant.DOC_DATA, keyword).boost(2);
        QueryBuilder qbdatacontent = QueryBuilders.matchQuery("datacontent", keyword).boost(2);
        QueryBuilder qbkeywords = QueryBuilders.matchQuery(DocConstant.DOC_KEYWORDS, keyword).boost(300);

//        fstatus:3
//        flevel:public
        List<QueryBuilder> permision = new ArrayList<>();
        for(String sql:lstSql){
            QueryBuilder tembuiler = QueryBuilders.queryStringQuery(sql);
            permision.add(tembuiler);
        }

        // 权限查询过滤
        QueryBuilder perqb = null;
        if(permision.size() == 2){
            perqb = QueryBuilders.boolQuery().must(permision.get(0)).must(permision.get(1));
        }else {
            perqb = QueryBuilders.boolQuery().must(permision.get(0)).must(permision.get(1)).must(permision.get(2));
        }

        // 查询条件设置  should 代表 OR
        QueryBuilder resqb = QueryBuilders.boolQuery()
                .should(qbtitle)
                .should(qbfilename)
                .should(qbdescription)
                .should(qbauthor)
                .should(qbdata)
                .should(qbkeywords)
                .should(qbdatacontent);

        // 高亮显示配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_TITLE).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_DATA).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field("datacontent").fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.fields().add(new HighlightBuilder.Field(DocConstant.DOC_KEYWORDS).fragmentSize(150).fragmentOffset(0).numOfFragments(1));
        highlightBuilder.requireFieldMatch(false);   //如果要多个字段高亮,这项要为false
        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .postFilter(perqb)
                .query(resqb)
                .highlighter(highlightBuilder)
                .from(start)
                .size(end)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));
//                .fetchSource(new String[]{"id","filename"},new String[]{}); //设置返回值

        SearchRequest searchRequest = new SearchRequest(DocConstant._DOC_INDEX);
        searchRequest.types(DocConstant._DOC_TYPE);
        searchRequest.source(searchSourceBuilder);

        List<Map<String,Object>> datalist = new ArrayList<Map<String, Object>>();
        Map<String,Object> resMap = new HashMap<String,Object>();
        RestHighLevelClient client = getClient();
        try {
            long startqueryTime = System.currentTimeMillis();
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            long endqueryTime = System.currentTimeMillis();
            SearchHits searchHits = searchResponse.getHits();
            // 总命中结果数
            double resNum = (double) searchHits.getTotalHits();

            for (SearchHit searchHit : searchHits.getHits()) {
                Map<String,Object> temp = new HashMap<String, Object>();
                System.out.println("document的id:" + searchHit.getId());
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                temp.put(DocConstant.DOC_ID, searchHit.getId());
                temp.put(DocConstant.DOC_FILENAME,sourceAsMap.get(DocConstant.DOC_FILENAME));
                temp.put(DocConstant.DOC_DESC,sourceAsMap.get(DocConstant.DOC_DESC));
                temp.put(DocConstant.DOC_AUTHOR,sourceAsMap.get(DocConstant.DOC_AUTHOR));
                temp.put(DocConstant.DOC_FILETYPE,sourceAsMap.get(DocConstant.DOC_FILETYPE));
                temp.put(DocConstant.DOC_PATH,sourceAsMap.get(DocConstant.DOC_PATH));
                temp.put(DocConstant.DOC_CRTIME,sourceAsMap.get(DocConstant.DOC_CRTIME));
                temp.put(DocConstant.DOC_FSTATUS,sourceAsMap.get(DocConstant.DOC_FSTATUS));
                temp.put(DocConstant.DOC_FLEVEL,sourceAsMap.get(DocConstant.DOC_FLEVEL));
                temp.put(DocConstant.DOC_FSVRDIR,sourceAsMap.get(DocConstant.DOC_FSVRDIR));
                temp.put("dataSource",sourceAsMap.get(DocConstant.DOC_DATASOURCE));
                // 高亮的字段
                temp.put(DocConstant.DOC_TITLE,sourceAsMap.get(DocConstant.DOC_TITLE));
                temp.put(DocConstant.DOC_KEYWORDS,sourceAsMap.get(DocConstant.DOC_KEYWORDS));
                temp.put("attr_content","");
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                if (highlightFields != null || highlightFields.size() != 0) {
                    HighlightField highlightDATA = highlightFields.get(DocConstant.DOC_DATA);
                    HighlightField highlightDATACONTENT = highlightFields.get("datacontent");
                    HighlightField highlightTITLE = highlightFields.get(DocConstant.DOC_TITLE);
                    HighlightField highlightKEYWORDS = highlightFields.get(DocConstant.DOC_KEYWORDS);
                    if (highlightDATA != null) {
                        Text[] fragments = highlightDATA.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put("attr_content",stringBuilder.toString());
//                        System.out.println("高亮显示的content:" + stringBuilder.toString());
                    }else {
                        if(highlightDATACONTENT != null){
                            Text[] fragments = highlightDATACONTENT.getFragments();
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Text text : fragments) {
                                stringBuilder.append(text);
                            }
                            temp.put("attr_content",stringBuilder.toString());
                        }else {
                            temp.put("attr_content","");
                        }
                    }
                    if (highlightTITLE != null) {
                        Text[] fragments = highlightTITLE.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put(DocConstant.DOC_TITLE,stringBuilder.toString());
//                        System.out.println("高亮显示的TITLE:" + stringBuilder.toString());
                    }else {
                        temp.put(DocConstant.DOC_TITLE,sourceAsMap.get(DocConstant.DOC_TITLE));
                    }
                    if (highlightKEYWORDS != null) {
                        Text[] fragments = highlightKEYWORDS.getFragments();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Text text : fragments) {
                            stringBuilder.append(text);
                        }
                        temp.put(DocConstant.DOC_KEYWORDS,stringBuilder.toString());
//                        System.out.println("高亮显示的KEYWORDS:" + stringBuilder.toString());
                    }else {
                        temp.put(DocConstant.DOC_KEYWORDS,sourceAsMap.get(DocConstant.DOC_KEYWORDS));
                    }
                }
                datalist.add(temp);
            }
            resMap.put("datalist",datalist);
            int pageNo = (int) (Math.ceil((double)start / (double)end))+1;
            int sumPage = (int) (Math.ceil(resNum/end));
            resMap.put("pageNo",pageNo); //当前页号（如 第1页）
            resMap.put("pageNum",datalist.size()); //当前页结果数
            resMap.put("resNum",(int) resNum); // 总命中个数
            resMap.put("sumPage",sumPage); // 总页数
            resMap.put("requestime",endqueryTime - startqueryTime);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resMap;
    }

}
