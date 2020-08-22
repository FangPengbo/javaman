package xyz.y1s1.javaman.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @description: Http请求实体类
 * @author: FangPengbo
 * @time: 2020/8/18 13:46
 */
public class HttpClientRequest implements Serializable {

    //请求方法类型
    private String type;

    //请求url
    private String url;

    //GET请求参数
    private LinkedHashMap<String,String> params;

    //请求头
    private LinkedHashMap<String,String> headers;

    //请求体参数
    private LinkedHashMap<String,String> formData;

    //rawType
    private String rawType;

    //rawValue
    private String rawValue;


    public HttpClientRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    public void setParams(LinkedHashMap<String, String> params) {
        this.params = params;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(LinkedHashMap<String, String> headers) {
        this.headers = headers;
    }

    public LinkedHashMap<String, String> getFormData() {
        return formData;
    }

    public void setFormData(LinkedHashMap<String, String> formData) {
        this.formData = formData;
    }

    public String getRawType() {
        return rawType;
    }

    public void setRawType(String rawType) {
        this.rawType = rawType;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }
}
