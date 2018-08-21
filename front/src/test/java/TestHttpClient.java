import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TestHttpClient {



    //不带参数的get请求
    //@Test
    public void testGet() throws IOException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建get请求  相当于在浏览器输入网址
        HttpGet httpGet = new HttpGet("http://admin.yufan.com/rest/rpc/item/41");
        //发送get请求，并获取相应的数据
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = response.getEntity();
            String s=EntityUtils.toString(entity);
            System.out.println(s);
        }
        response.close();
    }


    //带参数的get请求
    //@Test
    public void testGet2() throws IOException, URISyntaxException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URIBuilder("http://admin.yufan.com/rest/item/list").
                setParameter("page", "1").setParameter("rows", "1").build();
        //创建get请求  相当于在浏览器输入网址
        HttpGet httpGet = new HttpGet(uri);
        //发送get请求，并获取相应的数据
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = response.getEntity();
            String s=EntityUtils.toString(entity);
            System.out.println(s);
        }
        response.close();
    }


    //post请求
    //@Test
    public void testPost() throws IOException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //构造post请求
        HttpPost httpPost = new HttpPost("http://admin.yufan.com/rest/item/list");
        //设置参数
        List<NameValuePair>  pairList=new ArrayList<NameValuePair>();
        pairList.add(new BasicNameValuePair("page","1"));
        pairList.add(new BasicNameValuePair("rows","1"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairList);
        //设置表单到post请求中
        httpPost.setEntity(formEntity);
        //发送get请求，并获取相应的数据
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = response.getEntity();
            String s=EntityUtils.toString(entity);
            System.out.println(s);
        }
        response.close();

    }

}
