package com.example.demo.config;

import com.example.demo.config.ResourceConfigs.ProductDto;
public class testConfig {

    public void test(){
        var t = new ProductDto.Request.Create("ss", 0.2, 0.3);
        System.out.println(t);
        var s = ProductDto.getMarkup(t);
        System.out.println(s);

    }
}
