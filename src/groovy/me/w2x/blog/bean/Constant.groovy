package me.w2x.blog.bean

import groovy.transform.Immutable

import java.nio.charset.Charset

/**
 * User: Gang Chen
 * Date: 2015/12/5 22:16
 */
@Immutable
class Constant {
    def public static final DICT_MAP = "dictMap"

    //JSON格式字典表
    def public static final DICT_MAP_JSON = "dictMapJson"

    def public static final String DEFAULT_ENCODING = "utf8"

    def public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING)

    public static final String MODULUS = "modulus";

    public static final String EXPONENT = "exponent";
}
