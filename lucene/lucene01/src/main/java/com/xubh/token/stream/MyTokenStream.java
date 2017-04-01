package com.xubh.token.stream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MyTokenStream {
    // 测试分词器
    @Test
    public void testAnalyzer() throws Exception {
        // 测试基本分词器
        //Analyzer analyzer = new StandardAnalyzer();
        //Analyzer analyzer = new CJKAnalyzer();
        //Analyzer analyzer = new SmartChineseAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //词汇列表
        TokenStream tokenStream =
                analyzer.tokenStream("title", "白富美凤姐在传智播客学习c++,lucene");
        // 指针指向开始位置
        tokenStream.reset();
        // 分词索引引用
        CharTermAttribute charTermAttribute = tokenStream
                .addAttribute(CharTermAttribute.class);
        //分词偏移量引用
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //相当于jdbc里面resultset里的next
        while (tokenStream.incrementToken()) {

            //索引开始位置
            System.out.println("索引开始位置：" + offsetAttribute.startOffset());
            //索引分词
            System.out.println("索引分词：" + charTermAttribute);
            //索引结束位置
            System.out.println("索引结束位置：" + offsetAttribute.endOffset());


        }

    }

}
