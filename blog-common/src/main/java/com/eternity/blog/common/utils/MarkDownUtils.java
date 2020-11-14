package com.eternity.blog.common.utils;

import java.util.*;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Collections;

/**
 * @Description markdown 工具类
 * @Author eternity
 * @Date 2020/5/1 23:57
 */
public class MarkDownUtils {
    /**
     * 将markdown转为html
     *
     * @param markDown markdown
     * @return html
     */
    public static String toHtml(String markDown) {
        Set<Extension> headingAnchorExtension = Collections.singleton(HeadingAnchorExtension.create());
        List<Extension> tablesExtension = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tablesExtension)
                .build();
        Node node = parser.parse(markDown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProviderFactory(context -> new CustomizeAttributeProvider())
                .extensions(headingAnchorExtension)
                .extensions(tablesExtension)
                .softbreak("<br/>")
                .build();
        return renderer.render(node);
    }

    static class CustomizeAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof TableBlock) {
                attributes.put("class", "responsive-table");
            }
//            if (node instanceof Image) {
//                attributes.put("class", "enlargeImage");
//            }
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
        }
    }
}
