package ElasticsearchAPI;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;


import java.io.IOException;

/**
 * @author: 覃义雄
 * @dateTime: 2019-12-20 15:30
 * @project_Name: PersonalPractice
 * @Name: ElasticSearchDB
 * @Describe：
 */
public class ElasticSearchDB {
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
                    RestClient.builder(new HttpHost("192.168.187.201", 9300, "http"))
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
                    new HttpHost("192.168.187.201", 9300, "http")
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
     * document API 主要是些简单的增删改查操作
     */
    public void documentAPI() {
        //...
    }

    /**
     * Search API 主要是些复杂查询操作
     */
    public void searchAPI() {
        //...
    }
}
