package me.w2x.blog.bean

/**
 * Created by chengang on 12/27/15.
 */
class QueryResult<E> {
    final Integer total
    final List<E> data

    private static final QueryResult<E> EMPTY = new QueryResult<>(0, Collections.emptyList())

    static final QueryResult<E> emptyResult() {
        EMPTY
    }

    QueryResult(Integer total, List<E> data) {
        this.total = total
        this.data = data
    }
}
