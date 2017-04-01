package com.xubh.search.index;

import com.xubh.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class SearchIndex {
    //查询索引库
    @Test
    public void queryIndex() throws Exception {
        //指定索引库位置
        String path = "F:\\indexs";
        //读取索引库
        DirectoryReader reader =
                DirectoryReader.open(FSDirectory.open(new File(path)));
        //创建一个查询核心类
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //1.查询什么？根据域查询，查询title域关键词：（经典）
        String qName = "solr";
        //使用查询解析器，解析查询词语(分词)
        //QueryParser qParser = new QueryParser("content", new IKAnalyzer());

        //MultiFieldQueryParser查询多个域
        MultiFieldQueryParser mpParser
                = new MultiFieldQueryParser(new String[]{"title", "content"}, new IKAnalyzer());

        //使用基本分词器分词查询关键词
        Query query = mpParser.parse(qName);

        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            System.out.println("文档域Id：" + id);
            String title = doc.get("title");
            System.out.println("文档域title:" + title);
            String content = doc.get("content");
            System.out.println("文档域Content:" + content);

            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }


    }

    //使用IK分词器查询
    @Test
    public void queryIkIndex() throws Exception {

        //获取indexSearcher
        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
        //查询什么？
        //1.查询基本分词器？不能成功。
        //1.查询什么？根据域查询，查询title域关键词：（经典）
        String qName = "神雕侠侣";
        //使用查询解析器，解析查询词语(分词)
        QueryParser qParser = new QueryParser("films", new IKAnalyzer());
        //使用基本分词器分词查询关键词
        Query query = qParser.parse(qName);

        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, 10);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String username = doc.get("username");
            System.out.println("姓名：" + username);
            String alias = doc.get("alias");
            System.out.println("别名:" + alias);
            String hwife = doc.get("hwife");
            System.out.println("hwife:" + hwife);
            String films = doc.get("films");
            System.out.println("films:" + films);


            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }


    }

    //查询索引库
    @Test
    public void termQueryIndex() throws Exception {
        //指定索引库位置
        String path = "F:\\indexs";
        //读取索引库
        DirectoryReader reader =
                DirectoryReader.open(FSDirectory.open(new File(path)));
        //创建一个查询核心类
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //1.查询什么？根据域查询，查询title域关键词：（经典）
        String qName = "luceoo";

        //使用termQuery查询
        //1.TermQuery query = new TermQuery(new Term("content", qName));
        //2.FuzzyQuery 相似度查询:最多经过2次变化，能变回原词，查询成功。
        // luceoo --luceno--lucene
        FuzzyQuery query = new FuzzyQuery(new Term("title", qName));

        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, 10);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            System.out.println("文档域Id：" + id);
            String title = doc.get("title");
            System.out.println("文档域title:" + title);
            String content = doc.get("content");
            System.out.println("文档域Content:" + content);

            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }


    }

    //查询索引库
    @Test
    public void numericRangeQueryIndex() throws Exception {

        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();

        //创建组合查询
        BooleanQuery query = new BooleanQuery();
        //创建查询所有
        Query query1 = new MatchAllDocsQuery();
        //第一个参数：指定查询域
        //第二个参数：指定查询开始位置
        //第三个参数：指定查询结束位置
        //第四个参数：true:左闭区间
        //第五个参数：true:右闭区间
        NumericRangeQuery query2 =
                NumericRangeQuery.newLongRange("id", 5L, 15L, false, true);

        query.add(query1, Occur.MUST);
        query.add(query2, Occur.MUST_NOT);

        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            System.out.println("文档域Id：" + id);
            String title = doc.get("title");
            System.out.println("文档域title:" + title);
            String content = doc.get("content");
            System.out.println("文档域Content:" + content);

            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }


    }

    //查询索引库:高亮显示
    @Test
    public void queryIndexHighLight() throws Exception {
        //指定索引库位置
        String path = "F:\\indexs";
        //读取索引库
        DirectoryReader reader =
                DirectoryReader.open(FSDirectory.open(new File(path)));
        //创建一个查询核心类
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //1.查询什么？根据域查询，查询title域关键词：（经典）
        String qName = "solr";
        //使用查询解析器，解析查询词语(分词)
        //QueryParser qParser = new QueryParser("content", new IKAnalyzer());

        //MultiFieldQueryParser查询多个域
        MultiFieldQueryParser mpParser
                = new MultiFieldQueryParser(new String[]{"title", "content"}, new IKAnalyzer());

        //使用基本分词器分词查询关键词
        Query query = mpParser.parse(qName);

        //设置高亮
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        Scorer fragmentScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
        //设置摘要长度
        highlighter.setTextFragmenter(new SimpleFragmenter(20));


        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            System.out.println("文档域Id：" + id);
            String title = doc.get("title");
            title = highlighter.getBestFragment(new IKAnalyzer(), "title", qName);

            System.out.println("文档域title:" + title);
            String content = doc.get("content");
            System.out.println("文档域Content:" + content);

            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }


    }

    //模拟资源管理器，搜索索引库
    @Test
    public void findResourceManager() throws Exception {

        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();

        //1.查询什么？根据域查询，查询title域关键词：（经典）
        String qName = "iReport";
        //使用查询解析器，解析查询词语(分词)
        //QueryParser qParser = new QueryParser("content", new IKAnalyzer());

        //MultiFieldQueryParser查询多个域
        MultiFieldQueryParser mpParser
                = new MultiFieldQueryParser(new String[]{"fileName", "fileConent"}, new IKAnalyzer());

        //使用基本分词器分词查询关键词
        Query query = mpParser.parse(qName);
        //搜索索引库
        //第一个参数：查询条件
        //第二个参数：查询得分最高的条数。匹配度高，得分就高。
        //topDocs：文档概要信息：文档得分，文档总条数，文档Id。
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //获取文档查询总条数
        int totalHits = topDocs.totalHits;
        System.out.println("查询文档总记录数：" + totalHits);
        //获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //循环数组，根据文档Id查询文档。
        for (ScoreDoc sdoc : scoreDocs) {
            //获取文档Id
            int docId = sdoc.doc;
            System.out.println("文档Id：" + docId);

            //根据Id查询文档
            Document doc = indexSearcher.doc(docId);
            String fileName = doc.get("fileName");
            System.out.println("文件名称：" + fileName);
            String filePath = doc.get("filePath");
            System.out.println("文档路径:" + filePath);
            String fileConent = doc.get("fileConent");
            System.out.println("文档Content:" + fileConent);

            //获取文档得分
            float score = sdoc.score;
            System.out.println("文档得分:" + score);

        }

    }

}
