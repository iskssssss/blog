package com.eternity.blog.common.enums;

/**
 * @Description 博客类型
 * @Author eternity
 * @Date 2020/4/24 21:24
 */
public enum BlogType {
    /**
     * 原创 original
     */
    ORIGINAL(1, "原创"),
    /**
     * 转载 repost
     */
    REPRINT(2, "转载"),
    /**
     * 翻译 translated
     */
    TRANSLATION(3, "翻译");

    private final int type;
    private final String strType;

    BlogType(int type, String strType) {
        this.type = type;
        this.strType = strType;

    }

    public int type() {
        return type;
    }

    public String strType() {
        return strType;
    }

    public static BlogType valueOf(int type) {
        for (BlogType value : BlogType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("该博客类型不存在.");
    }
}
