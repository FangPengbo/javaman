package xyz.y1s1.javaman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: FangPengbo
 * @time: 2020/8/19 11:24
 */
@Controller
public class IndexController {

    /**
     * 主页
     * @return
     */
    @GetMapping(value = {"index","/"})
    public String toIndex(){
        return "index";
    }


}
