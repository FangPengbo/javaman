package xyz.y1s1.javaman.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import xyz.y1s1.javaman.entity.HttpClientRequest;
import xyz.y1s1.javaman.entity.HttpClientResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @description: HTTP工具类
 * @author: FangPengbo
 * @time: 2020/7/30 10:35
 */
public class HttpClientUtils {

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url) throws Exception {
        return doPost(url, null, null,null,null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
        return doPost(url, null, params,null,null);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params,String rawType,String rawValue) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        // 设置请求头
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");

        if(StringUtils.isEmpty(rawValue)){
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        }else{
            if("Text".equalsIgnoreCase(rawType)){
                httpPost.setHeader("Content-Type","text/plain");
            }else if ("javaScript".equalsIgnoreCase(rawType)){
                httpPost.setHeader("Content-Type","application/javascript");
            }else if ("JSON".equalsIgnoreCase(rawType)){
                httpPost.setHeader("Content-Type","application/json");
            }else if ("HTML".equalsIgnoreCase(rawType)){
                httpPost.setHeader("Content-Type","text/html");
            }else if ("XML".equalsIgnoreCase(rawType)){
                httpPost.setHeader("Content-Type","application/xml");
            }
        }

        packageHeader(headers, httpPost);

        // 封装请求参数
        packageParam(params, httpPost,rawType,rawValue);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url) throws Exception {
        return doPut(url);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        packageParam(params, httpPut);

        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doDelete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        params.put("_method", "delete");
        return doPost(url, params);
    }

    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod, String type , String value)
            throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(value)){
            packageParam(params, httpMethod);
        }else{
            packageParam(httpMethod,value);
        }
    }


    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(HttpEntityEnclosingRequestBase httpMethod,String value) {
            // 设置到请求的http对象中
            httpMethod.setEntity(new StringEntity(value, ENCODING));
    }


    /**
     * Description: 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
                                                       CloseableHttpClient httpClient, HttpRequestBase httpMethod) {
        //记录开始时间
        long startTime = System.currentTimeMillis();
        long time = 0;
        // 执行请求
        try {
            httpResponse = httpClient.execute(httpMethod);
            time = System.currentTimeMillis() - startTime;
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                String content = "";
                if (httpResponse.getEntity() != null) {
                    content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                }
                return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(),time,content);
            }
        } catch (IOException e) {
            //记录结束时间
            time = System.currentTimeMillis() - startTime;
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR,time,"请求URL为:"+httpMethod.getURI()+"失败");
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }



    //获取域控userId
    public static String parseUserName(HttpServletRequest request){
        String userId = request.getRemoteUser(); // JAAS 认证通过
        if (userId != null && userId.indexOf("\\") > 0){
            userId = userId.substring(userId.indexOf("\\") + 1).trim();
        } else if (userId != null && userId.indexOf("@") > 0){
            userId = userId.substring(0, userId.indexOf("@")).trim();
        }
        return userId;
    }

    /**
     * 获取当前域账号
     * @param request
     * @return
     */
    public static String getAdUser(HttpServletRequest request){
        String adUser = parseUserName(request);
        return adUser;
    }


    /**
     * @Author FangPengbo
     * @Description 请求主入口
     * @Date 2020/7/30 10:41
     * @Param [url, method, json]
     * @return java.lang.String
     **/
    public static HttpClientResult send(HttpClientRequest requestModel){
        HttpClientResult result ;
        switch (requestModel.getType()){
            case "POST":
                result = sendPOST(requestModel);
                break;
            // TODO: DELETE PUT ...
            default:
                result = sendGET(requestModel);
        }
        return result;
    }


    private static HttpClientResult sendGET(HttpClientRequest requestModel){
        HttpClientResult result;
        String url = requestModel.getUrl();
        LinkedHashMap<String, String> params = requestModel.getParams();
        params.remove("");
        LinkedHashMap<String, String> headers = requestModel.getHeaders();
        headers.remove("");
        try {
            if(CollectionUtils.isEmpty(headers)){
                result = doGet(url, params);
            }else{
                result = doGet(url, headers, params);
            }
        } catch (Exception e) {
            result = new HttpClientResult(500,0L,"请求" + url + "失败");
            return result;
        }
        return result;
    }

    private static HttpClientResult sendPOST(HttpClientRequest requestModel){
        HttpClientResult result;
        String url = requestModel.getUrl();
        String rawType = requestModel.getRawType();
        String rawValue = requestModel.getRawValue();
        LinkedHashMap<String, String> headers = requestModel.getHeaders();
        headers.remove("");
        LinkedHashMap<String, String> formData = requestModel.getFormData();
        formData.remove("");
        try {
            result = doPost(url, headers, formData, rawType, rawValue);
        } catch (Exception e) {
            result = new HttpClientResult(500,0L,"请求" + url + "失败");
            return result;
        }
        return result;
    }



}
