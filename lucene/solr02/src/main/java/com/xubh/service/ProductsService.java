package com.xubh.service;

import com.xubh.domain.ResultModel;

public interface ProductsService {
    //接受页面参数，查询索引库：solrQuery封装参数
    ResultModel queryProduts(String qName, String productCatalogName,
                             String price, String sort, Integer pageNo) throws Exception;


}
