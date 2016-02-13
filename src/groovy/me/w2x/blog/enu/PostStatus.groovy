package me.w2x.blog.enu

/**
 * User: Gang Chen
 * Date: 2015/11/26 23:31
 */
enum PostStatus implements Dict {
    PUBLISH(2, "立即发布"),
    DRAFT(0, "草稿"),
    PENDING(1, "待复审");

    private final int key;
    private final String text;

    PostStatus(int key, String text) {
        this.key = key;
        this.text = text;
    }

    @Override
    public String getKey() {
        return key as String;
    }

    @Override
    public String getText() {
        return text;
    }
}
