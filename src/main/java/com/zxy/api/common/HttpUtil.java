package com.zxy.api.common;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.*;

public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    //表示请求器是否已经做了初始化工作
    private static volatile boolean hasInit = false;

    //传输超时时间，默认120秒即2分钟
    private static int socketTimeout = 120000;

    //建立连接超时时间，默认60秒
    private static int connectTimeout = 60000;

    //获取连接超时时间，30秒
    private static int connectRequest = 30000;

    //请求器的配置
    private static RequestConfig requestConfig;

    //HTTP请求器
    private static CloseableHttpClient httpClient;

    public HttpUtil() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        init();
    }

    private static void init() {
        httpClient = HttpClients.custom().setMaxConnPerRoute(500).setMaxConnTotal(800).build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectRequest)
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    /**
     * http GET 请求
     *
     * @param url 请求url
     * @return 请求结果
     */
    public static String httpGet(String url) {

        if (!hasInit) {
            init();
        }

        String result = null;

        HttpGet httpGet = new HttpGet(url);
        log.info("executing GET request" + httpGet.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException");

        } catch (Exception e) {
            log.error("http get throw Exception");
            e.printStackTrace();

        } finally {
            httpGet.abort();
        }
        return result;
    }

    /**
     * 发送json的字符串
     *
     * @param url      请求url
     * @param postData 请求体
     * @return 返回字符串
     */
    public static String sendPostString(String url, String postData) {

        if (!hasInit) {
            init();
        }

        HttpPost httpPost = new HttpPost(url);

        log.info("POST过去的数据是：");
        log.info(postData);

        //得指明使用UTF-8编码，
        StringEntity postEntity = new StringEntity(postData, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(postEntity);

        return httpPost(httpPost);
    }

    private static String httpPost(HttpPost httpPost) {
        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        String result = null;

        log.info("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http post throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.error("http post throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.error("http post throw SocketTimeoutException");

        } catch (Exception e) {
            log.error("http post throw Exception");
            e.printStackTrace();

        } finally {
            httpPost.abort();
        }
        return result;
    }


    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public static void setSocketTimeout(int socketTimeout) {
        HttpUtil.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public static void setConnectTimeout(int connectTimeout) {
        HttpUtil.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private static void resetRequestConfig() {
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public static void setRequestConfig(RequestConfig requestConfig) {
        HttpUtil.requestConfig = requestConfig;
    }

    public static String postForm(String url, Map<String, Object> params) throws Exception {
        if (!hasInit) {
            init();
        }
        HttpPost httpPost = new HttpPost(url);
        if (params != null && params.size() > 0) {
            List<NameValuePair> nvps = new ArrayList();
            for (String paramName : params.keySet()) {
                Object val = params.get(paramName);

                if ((val instanceof Map || val instanceof Collection)) {

                    nvps.add(new BasicNameValuePair(paramName, new Gson().toJson(val)));
                } else {
                    nvps.add(new BasicNameValuePair(paramName, val.toString()));
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }

        return httpPost(httpPost);
    }
}