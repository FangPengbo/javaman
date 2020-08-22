package xyz.y1s1.javaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.y1s1.javaman.entity.AjaxObj;
import xyz.y1s1.javaman.entity.HttpClientRequest;
import xyz.y1s1.javaman.entity.HttpClientResult;
import xyz.y1s1.javaman.service.HttpClientService;


/**
 * @description:
 * @author: FangPengbo
 * @time: 2020/8/19 12:19
 */
@Controller
public class HttpClientController {

    @Autowired
    HttpClientService clientService;

    @ResponseBody
    @PostMapping("send")
    public AjaxObj send(@RequestBody HttpClientRequest requestModel){
        HttpClientResult result = clientService.send(requestModel);
        return new AjaxObj(1,"success",result);
    }



}
