package com.xubh.dao.impl;

import com.xubh.dao.ProductsDao;
import com.xubh.domain.Products;
import com.xubh.domain.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductsDaoImpl implements ProductsDao {

    //把solrServer交给spring管理
    @Resource
    private SolrServer solrServer;

    public ResultModel queryProducts(SolrQuery solrQuery) throws Exception {

        List<Products> pList = new ArrayList<Products>();
        //创建resultModel
        ResultModel model = new ResultModel();

        //使用solrServer查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取查询结果集
        SolrDocumentList solrDocumentList = response.getResults();
        //获取查询总记录数
        long numFound = solrDocumentList.getNumFound();
        //给resultModel设置查询总计里数
        model.setRecordCount(numFound);

        //循环文档集合
        for (SolrDocument doc : solrDocumentList) {
            Products p = new Products();
            //获取文档对象，把文档字段封装javaBean,然后javabean设置Products
            //多个商品使用list集合封装

            String id = (String) doc.get("id");
            p.setPid(id);
            String productName = (String) doc.get("product_name");

            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //第一个map的key是Id
            Map<String, List<String>> map = highlighting.get(id);
            //第二个map的key是Product_name域名称
            List<String> list = map.get("product_name");
            if (list != null && list.size() > 0) {
                productName = list.get(0);
            }

            p.setName(productName);
            Float productPrice = (Float) doc.get("product_price");
            p.setPrice(productPrice);

            String productCatalogName = (String) doc.get("product_catalog_name");
            p.setCatalogName(productCatalogName);
            String productPicture = (String) doc.get("product_picture");
            p.setPicture(productPicture);

            pList.add(p);

        }

        //商品列表设置到resultModel
        model.setProductList(pList);

        return model;
    }

}
