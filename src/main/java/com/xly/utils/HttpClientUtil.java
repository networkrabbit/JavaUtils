package com.xly.utils;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;


/**
 * @author rabbit
 */
public class HttpClientUtil {

    /**
     * 发送get请求（不可自定义请求头）
     *
     * @param url 所请求的url
     * @return 响应内容
     */
    public static String sendGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 通过址默认配置创建一个httpClient实例
        // 创建httpGet远程连接实例
        HttpGet httpGet = new HttpGet(url);
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        return executeRequest(httpClient, httpGet);
    }

    /**
     * 发送带自定义请求头的get请求
     *
     * @param headerMap Map集合形式的请求头参数
     * @param url       要请求的地址
     * @return 响应内容
     */
    public static String sendGet(Map<String, String> headerMap, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 通过址默认配置创建一个httpClient实例
        // 创建httpGet远程连接实例
        HttpGet httpGet = new HttpGet(url);
        // 遍历参数设置请求头信息
        for (String key : headerMap.keySet()) {
            httpGet.setHeader(key, headerMap.get(key));
        }
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);

        return executeRequest(httpClient, httpGet);
    }

    /**
     * 发送post请求（不可自定义请求头）
     *
     * @param url      要请求的地址
     * @param paramMap 请求所带的参数 Map格式
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> paramMap) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        addPostParam(httpPost, paramMap);
        return executeRequest(httpClient, httpPost);
    }

    /**
     * 发送带自定义请求头的post请求
     *
     * @param headerMap Map集合形式的请求头参数
     * @param url       要请求的地址
     * @param paramMap  请求所带的参数 Map格式
     * @return 响应内容
     */
    public static String sendPost(Map<String, String> headerMap, String url,
                                  Map<String, String> paramMap) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 遍历参数设置请求头信息
        for (String key : headerMap.keySet()) {
            httpPost.setHeader(key, headerMap.get(key));
        }
        addPostParam(httpPost, paramMap);
        return executeRequest(httpClient, httpPost);
    }


    /**
     * 发送post请求（不可自定义请求头）
     *
     * @param url      要请求的地址
     * @param json 请求所带的参数 json格式
     * @return 响应内容
     */
    public static String sendPostWithJson(String url, JSONObject json) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        addPostParam(httpPost, json);
        return executeRequest(httpClient, httpPost);
    }

    /**
     * 发送带自定义请求头的post请求
     *
     * @param headerMap Map集合形式的请求头参数
     * @param url       要请求的地址
     * @param json      请求所带的参数 JSON格式
     * @return 响应内容
     */
    public static String sendPostWithJson(Map<String, String> headerMap, String url, JSONObject json) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");
        // 设置配置请求参数依次为：连接主机服务超时时间、请求超时时间、数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 遍历参数设置请求头信息
        for (String key : headerMap.keySet()) {
            httpPost.setHeader(key, headerMap.get(key));
        }
        addPostParam(httpPost, json);
        return executeRequest(httpClient, httpPost);
    }


    /**
     * 发送请求并返回响应内容
     *
     * @param httpClient CloseableHttpClient对象
     * @param request    HttpUriRequest对象
     * @return 响应内容
     */
    private static String executeRequest(CloseableHttpClient httpClient, HttpUriRequest request) {
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            response = httpClient.execute(request);
            // 获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("状态码:---------------->>" + statusCode);
            if (statusCode== HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }else {
                System.out.println("响应异常");
                System.out.println( EntityUtils.toString(response.getEntity()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 将Map集合格式的参数放入httpPost
     *
     * @param httpPost HttpPost
     * @param paramMap Map<String, String>
     */
    private static void addPostParam(HttpPost httpPost, Map<String, String> paramMap) {
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> paramList = new ArrayList<>();

            // 循环遍历，获取迭代器
            for (String keyName : paramMap.keySet()) {
                paramList.add(new BasicNameValuePair(keyName, paramMap.get(keyName)));
            }
            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将json格式的参数放入httpPost
     *
     * @param httpPost HttpPost
     * @param json     Map<String, String>
     */
    private static void addPostParam(HttpPost httpPost, JSONObject json) {
        if (null != json && json.size() > 0) {

            // 为httpPost设置封装好的请求参数
            try {
                StringEntity strEntity = new StringEntity(json.toString());
                httpPost.setEntity(strEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 给httpClient加上ssl认证的并返回
     *
     * @return CloseableHttpClient对象
     */
    private static CloseableHttpClient sslHttpClient() {
        //TODO 完善SSL验证
        SSLContext sslcontext = null;
        try {
            // 获取私钥
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File("my.keystore"), "nopassword".toCharArray(),
                            new TrustSelfSignedStrategy())
                    .build();

        } catch (NoSuchAlgorithmException | CertificateException | KeyManagementException | IOException | KeyStoreException e) {
            e.printStackTrace();
        }
        // Allow TLSv1 protocol only
        assert sslcontext != null;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        return HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
    }
}
