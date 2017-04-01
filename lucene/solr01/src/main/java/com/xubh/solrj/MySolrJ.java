package com.xubh.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MySolrJ {
    //修改和添加
    @Test
    public void addIndex() throws Exception {
        //指定远程服务器索引库地址
        String url = "http://localhost:8080/solr/mycore1";
        //连接远程服务器
        SolrServer solrServer = new HttpSolrServer(url);
        //模拟创建一个文档对象
        SolrInputDocument doc = new SolrInputDocument();
        //给文档对象添加几个域
        doc.addField("id", "p1010110");
        doc.addField("product_name", "T恤");
        doc.addField("product_price", 6000);

        //添加索引库
        solrServer.add(doc);
        //提交
        solrServer.commit();

    }

    //删除
    @Test
    public void deleteIndex() throws Exception {
        //指定远程服务器索引库位置
        String url = "http://localhost:8080/solr/mycore1";
        //连接远程服务器
        SolrServer solrServer = new HttpSolrServer(url);

        //根据Id删除对象
        //solrServer.deleteById("p1010110");
        //根据查询来删除
        solrServer.deleteByQuery("id:p1010110");
        solrServer.commit();

    }

    //查询
    @Test
    public void queryIndex() throws Exception {
        //指定远程服务器索引库位置
        String url = "http://localhost:8080/solr/mycore1";
        //连接远程服务器
        SolrServer solrServer = new HttpSolrServer(url);
        //solrQuery封装参数。
        SolrQuery solrQuery = new SolrQuery();
        //查询所有
        //solrQuery.set("q", "*:*");
        solrQuery.setQuery("家天下");
        //fq:filterQuery
        //1>查询价格20以上
        solrQuery.addFilterQuery("product_price:[20 TO *]");
        //2.要求商品类别是：幽默杂货
        solrQuery.addFilterQuery("product_catalog_name:时尚卫浴");

        //sort
        //第一个参数：指定给那个域字段排序
        //第二个参数：指定排序是升序，降序。
        solrQuery.setSort("product_price", ORDER.asc);

        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(20);

        //设置过滤查询字段:字段直接使用逗号，或者空格分割都可
        //solrQuery.setFields("product_name,product_price");


        //设置高亮
        //开启高亮
        solrQuery.setHighlight(true);
        //对那个字段使用高亮
        solrQuery.addHighlightField("product_name");
        //设置前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //设置后缀
        solrQuery.setHighlightSimplePost("</font>");

        solrQuery.set("df", "product_keywords");
        //是solrServer查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取查询结果集
        SolrDocumentList solrDocumentList = response.getResults();
        //获取查询总记录数
        long numFound = solrDocumentList.getNumFound();
        System.out.println("命中总记录数:" + numFound);

        //循环文档集合
        for (SolrDocument doc : solrDocumentList) {
            String id = (String) doc.get("id");
            System.out.println("商品Id：" + id);
            String productName = (String) doc.get("product_name");

            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //第一个map的key是Id
            Map<String, List<String>> map = highlighting.get(id);
            //第二个map的key是Product_name域名称
            List<String> list = map.get("product_name");
            if (list != null && list.size() > 0) {
                productName = list.get(0);
            }

            System.out.println("商品名称：" + productName);
            Float productPrice = (Float) doc.get("product_price");
            System.out.println("商品价格：" + productPrice);
            String productDescription = (String) doc.get("product_description");
            System.out.println("商品描述：" + productDescription);
            String productCatalogName = (String) doc.get("product_catalog_name");
            System.out.println("商品类别：" + productCatalogName);

        }

    }

}
