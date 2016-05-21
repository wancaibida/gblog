package me.w2x.blog.enu

/**
 * User: Gang Chen
 * Date: 2015/11/26 23:31
 */
enum PostStatus implements Dict {
    PUBLISH(2, '已发布'),
    DRAFT(0, '草稿'),
    PENDING(1, '待复审')

    private final int key
    private final String text

    PostStatus(Integer key, String text) {
        this.key = key
        this.text = text
    }

    @Override
    Integer getKey() {
        key
    }

    @Override
    String getText() {
        text
    }
}
