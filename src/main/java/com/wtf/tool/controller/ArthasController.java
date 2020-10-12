package com.wtf.tool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/arthas")
public class ArthasController {

    @GetMapping("/test")
    public List arthas() {
        List list = new ArrayList(3);
        list.add("1");
        list.add("2");
        list.add("3");
        return list;
    }
}
