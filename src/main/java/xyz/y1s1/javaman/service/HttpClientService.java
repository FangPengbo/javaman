package xyz.y1s1.javaman.service;

import xyz.y1s1.javaman.entity.HttpClientRequest;
import xyz.y1s1.javaman.entity.HttpClientResult;

/**
 * @description:
 * @author: FangPengbo
 * @time: 2020/8/19 12:22
 */
public interface HttpClientService {

    HttpClientResult send(HttpClientRequest requestModel);

}
