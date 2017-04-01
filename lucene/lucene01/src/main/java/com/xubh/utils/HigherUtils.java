package com.xubh.utils;

import java.io.IOException;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class HigherUtils {
    private Highlighter highlighter;

    /**
     * 在构造函数中初始化高亮器
     *
     * @param query
     */
    public HigherUtils(Query query) {
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>",
                "</font>");
        Scorer fragmentScorer = new QueryScorer(query);
        highlighter = new Highlighter(formatter, fragmentScorer);
        // 设置摘要长度
        highlighter.setTextFragmenter(new SimpleFragmenter(20));
    }

    /**
     * 将对应的field转换为高亮显示
     *
     * @param field
     * @param value
     * @return
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    public String changeHigner(String field, String value)
            throws IOException, InvalidTokenOffsetsException {

        String titleBestFragment = highlighter.getBestFragment(
                new IKAnalyzer(), field, value);
        return titleBestFragment;

    }
}
