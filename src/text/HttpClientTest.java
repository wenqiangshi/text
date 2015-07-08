package text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
public class HttpClientTest {

	//private static final JsonNodeFactory factory = new JsonNodeFactory(false);
	public static void main(String args[]) {
    	 //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
      // CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
    	CloseableHttpClient httpClient = WebClientDevWrapper.createSSLClientDefault();
        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        ObjectMapper mapper = new ObjectMapper();  
        ObjectNode queryStrNode1 = mapper.createObjectNode(); 
        queryStrNode1.put("ql", "select * where timestamp>" + senvenDayAgo + " and timestamp<" + currentTimestamp);
        String rest=null;
        String res="select * where timestamp>" + senvenDayAgo + " and timestamp<" + currentTimestamp;
		try {
			rest = "ql="+ java.net.URLEncoder.encode(queryStrNode1.get("ql").asText(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        HttpGet httpGet = new HttpGet("https://a1.easemob.com/quantumchannel/ulook/chatmessages?" + rest);
        System.out.println(httpGet.getRequestLine());
        try {
            //执行get请求
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
                System.out.println("contentEncoding:" + entity.getContentEncoding());
                System.out.println("response content:" + EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {                //关闭流并释放资源
            	httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}