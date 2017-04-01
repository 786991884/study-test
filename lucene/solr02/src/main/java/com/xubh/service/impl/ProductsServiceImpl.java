package com.xubh.service.impl;

import com.xubh.dao.ProductsDao;
import com.xubh.domain.ResultModel;
import com.xubh.service.ProductsService;
import com.xubh.utils.Commons;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Resource
    private ProductsDao productsDao;

    public ResultModel queryProduts(String qName, String productCatalogName,
                                    String price, String sort, Integer pageNo) throws Exception {
        //使用solrQuery封装参数：主查询条件
        SolrQuery solrQuery = new SolrQuery();
        if (qName != null && !"".equals(qName)) {
            solrQuery.setQuery(qName);
        } else {
            solrQuery.setQuery("*:*");
        }
        //添加过滤查询条件
        if (productCatalogName != null && !"".equals(productCatalogName)) {

            solrQuery.addFilterQuery("product_catalog_name:" + productCatalogName);
        }
        //添加价格过滤查询条件
        if (price != null && !"".equals(price)) {
            //切割价格区间
            String[] str = price.split("-");

            solrQuery.addFilterQuery("product_price:[" + str[0] + " TO " + str[1] + "]");

        }
        //排序
        if (sort != null && "1".equals(sort)) {
            solrQuery.setSort("product_price", ORDER.asc);
        } else {
            solrQuery.setSort("product_price", ORDER.desc);
        }
        //分页
        if (pageNo == null) {
            pageNo = 1;
        }
        int startNo = (pageNo - 1) * Commons.PAGESIZE;
        //设置开始页
        solrQuery.setStart(startNo);
        //设置每页显示条数
        solrQuery.setRows(Commons.PAGESIZE);

        //设置高亮
        //开启高亮
        solrQuery.setHighlight(true);
        //对那个字段使用高亮
        solrQuery.addHighlightField("product_name");
        //设置前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //设置后缀
        solrQuery.setHighlightSimplePost("</font>");


        //设置默认查询字段
        solrQuery.set("df", "product_keywords");

        ResultModel resultModel = productsDao.queryProducts(solrQuery);

        //计算页码
        int recordCount = resultModel.getRecordCount().intValue();
        int pages = recordCount / Commons.PAGESIZE;
        if (recordCount % Commons.PAGESIZE > 0) {
            pages++;
        }

        //设置当前页
        resultModel.setCurPage(pageNo);
        //设置总页数
        resultModel.setPageCount(pages);

        return resultModel;
    }

}
