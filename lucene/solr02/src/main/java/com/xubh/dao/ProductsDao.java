package com.xubh.dao;

import com.xubh.domain.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;

public interface ProductsDao {
    //使用封装参数solrQuery查询
    ResultModel queryProducts(SolrQuery solrQuery) throws Exception;
}
