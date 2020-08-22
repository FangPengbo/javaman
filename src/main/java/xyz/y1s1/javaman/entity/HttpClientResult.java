package xyz.y1s1.javaman.entity;

import java.io.Serializable;

/**
 * @description:
 * @author: FangPengbo
 * @time: 2020/8/18 15:59
 */
public class HttpClientResult implements Serializable {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应时间
     */
    private Long duration;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public HttpClientResult() {
    }

    public HttpClientResult(Integer code, Long duration, String content) {
        this.code = code;
        this.duration = duration;
        this.content = content;
    }

    public HttpClientResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
