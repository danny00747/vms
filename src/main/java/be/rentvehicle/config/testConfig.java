package be.rentvehicle.config;

import be.rentvehicle.config.ResourceConfigs.ProductDto;
public class testConfig {

    public void test(){
        var t = new ProductDto.Request.Create("ss", 0.2, 0.3);
        System.out.println(t);
        var s = ProductDto.getMarkup(t);
        System.out.println(s);

    }
}
