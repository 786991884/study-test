package com.xubh.create.index;

import com.xubh.utils.LuceneUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.List;


public class CreateIndex {

    //创建索引库
    @Test
    public void addIndex() throws Exception {
        //指定索引库位置
        String path = "F:\\indexs";
        //关联索引库位置
        FSDirectory directory = FSDirectory.open(new File(path));
        //配置IndexWriterLucene版本，分词器
        //使用基本分词器
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //创建索引库核心类
        IndexWriter indexWriter = new IndexWriter(directory, conf);

        //模拟一个文档对象:文章对象
        Document doc = new Document();
        //StringField:域对象，不分词
        //Store.NO:在索引库不存储，Store.YES:索引库存储。
        doc.add(new StringField("id", "p1010110", Store.NO));
        //TextField:域对象，分词。
        //第一个参数：域名称
        //第二个参数：域值，域所对应的内容
        //第三个参数：是否存储        -----------如果不存储，查询不到。
        doc.add(new TextField("title", "Lucene经典教程", Store.YES));
        doc.add(new TextField("content", "	Lucene是一套用于全文检索和搜寻的开源程式库"
                + "，由Apache软件基金会支持和提供", Store.YES));
        //添加索引库
        indexWriter.addDocument(doc);

        //提交
        indexWriter.commit();
        indexWriter.close();
    }

    //模拟抽象文档对象，添加索引库
    @Test
    public void addIKAnalyzerIndex() throws Exception {

        //直接获取IndexWriter。
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();

        //模拟文档对象
        Document doc = new Document();
        doc.add(new StringField("username", "黄晓明", Store.YES));
        doc.add(new StringField("alias", "黄教主", Store.YES));
        //域类型
        FieldType testType = new FieldType();
        //手动设置域类型
        //是否索引：true：索引
        testType.setIndexed(true);
        //设置是否存储
        testType.setStored(true);
        //是否分词
        testType.setTokenized(true);
        Field field = new Field("hwife", "anglebaby", testType);
        doc.add(field);
        doc.add(new TextField("films", "神雕侠侣，鹿鼎记，上海滩", Store.YES));

        //创建索引库
        indexWriter.addDocument(doc);


    }

    //NumericRangeQuery:创建30个文档。
    @Test
    public void addNumericRangeQueryIndex() throws Exception {

        //获取IndexWriter
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();

        for (int i = 1; i < 30; i++) {
            Document document = new Document();
            // 为document添加字段
            document.add(new LongField("id", (long) i, Store.YES));
            FieldType testType = new FieldType();
            testType.setIndexed(true);
            testType.setStored(true);
            testType.setTokenized(true);
            Field test = new Field("title", "如何学习solr", testType);
            //i等于22时候，文档22设置得分
            if (i == 22) {
                test.setBoost(10);
            }

            document.add(test);
            document.add(new TextField("content", "solr是基于lucene的技术", Store.YES));
            indexWriter.addDocument(document);
        }

    }

    //模拟资源管理器。读取磁盘文件，创建索引
    @Test
    public void managerResources() throws Exception {

        //获取IndexWriter
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        //获取文档对象集合
        List<Document> list = LuceneUtils.fileDocumet("F:\\searchsource");
        for (Document document : list) {

            indexWriter.addDocument(document);

        }


    }

}
