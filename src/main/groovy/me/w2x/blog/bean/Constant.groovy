package me.w2x.blog.bean

import groovy.transform.Immutable

import java.nio.charset.Charset

/**
 * User: Gang Chen
 * Date: 2015/12/5 22:16
 */
@Immutable
class Constant {
    public static final DICT_MAP = 'dictMap'

    //JSON格式字典表
    public static final DICT_MAP_JSON = 'dictMapJson'

    public static final String DEFAULT_ENCODING = 'utf8'

    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING)

    public static final String MODULUS = 'modulus'

    public static final String EXPONENT = 'exponent'
}
