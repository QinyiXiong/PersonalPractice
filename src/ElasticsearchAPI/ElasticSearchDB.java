package ElasticsearchAPI;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 覃义雄
 * @dateTime: 2019-12-20 15:30
 * @project_Name: PersonalPractice
 * @Name: ElasticSearchDB
 * @Describe：
 */
public class ElasticSearchDB {

    private final static int ES_Port = 9200;
    private final static String ES_Server = "192.168.248.158";


    /**
     * 高阶Rest Client
     */
    private RestHighLevelClient client = null;
    /**
     * 低阶Rest Client
     */
    private RestClient restClient = null;

    /**
     * 这里使用饿汉单例模式创建RestHighLevelClient
     */
    public ElasticSearchDB() {
        if (client == null) {
            synchronized (RestHighLevelClient.class) {
                if (client == null) {
                    client = getClient();
                }
            }
        }
    }

    private RestHighLevelClient getClient() {
        RestHighLevelClient client = null;

        try {
            client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(ES_Server, ES_Port, "http"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    private RestClient getRestClient() {
        RestClient client = null;

        try {
            client = RestClient.builder(
                    new HttpHost(ES_Server, ES_Port, "http")
            ).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public void closeClient() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 增，插入记录
     * 插入操作有四种方式，分同步异步操作，可选参数设置，结果返回IndexResponse，抛出异常
     * @throws Exception
     */
    public void insertInterFaceLog(String uuid,String reqAddress, String reqMess, String resMess,String type,String dataFrom,String codelog) throws Exception{
        //组织数据Map
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("F_GUID", uuid);
        jsonMap.put("F_REQ_MESSAGE", reqMess);
        jsonMap.put("F_REQ_ADDRESS", reqAddress);
        jsonMap.put("F_RES_MESSAGE", resMess);
        jsonMap.put("F_DATE", new Date());
        jsonMap.put("F_DATA_FROM", dataFrom);
        jsonMap.put("F_TYPE", type);
        jsonMap.put("F_CODE", codelog);
        IndexRequest indexRequest = new IndexRequest(_INDEX, _DOC, uuid).source(jsonMap);

        indexRequest.timeout(TimeValue.timeValueSeconds(5)); //设置超时

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        //Index Response
//        String index = indexResponse.getIndex();
//        String type1 = indexResponse.getType();
//        String id = indexResponse.getId();
//        long version = indexResponse.getVersion();
        System.out.println(indexResponse.getId());

    }

    //测试ES
    public static void main(String[] args) {
        RestClientUtil ESDB = new RestClientUtil();
        try {
            ESDB.insertInterFaceLog("123","222","333","444","555","666","777");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ESDB.closeClient();
        }

    }
}
