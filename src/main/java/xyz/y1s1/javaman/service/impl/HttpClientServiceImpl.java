package xyz.y1s1.javaman.service.impl;

import org.springframework.stereotype.Service;
import xyz.y1s1.javaman.entity.HttpClientRequest;
import xyz.y1s1.javaman.entity.HttpClientResult;
import xyz.y1s1.javaman.service.HttpClientService;
import xyz.y1s1.javaman.util.HttpClientUtils;

/**
 * @description:
 * @author: FangPengbo
 * @time: 2020/8/19 12:23
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

    @Override
    public HttpClientResult send(HttpClientRequest requestModel) {
        return HttpClientUtils.send(requestModel);
    }
}
