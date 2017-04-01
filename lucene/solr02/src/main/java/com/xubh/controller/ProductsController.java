package com.xubh.controller;

import com.xubh.domain.ResultModel;
import com.xubh.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Resource
    private ProductsService productsService;

    // 调转到商品页面
    @RequestMapping("toList")
    public String toList() {
        return "product_list";
    }

    // 接受页面参数，查询索引库
    @RequestMapping("list")
    public String list(String qName, String productCatalogName, String price,
                       String sort, Integer pageNo, Model model) throws Exception {

        ResultModel resultModel = productsService.queryProduts(qName,
                productCatalogName, price, sort, pageNo);
        //页面回显
        model.addAttribute("result", resultModel);
        return "product_list";
    }

}
