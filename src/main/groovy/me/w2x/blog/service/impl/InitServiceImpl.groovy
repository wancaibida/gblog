package me.w2x.blog.service.impl

import com.alibaba.fastjson.JSON
import me.w2x.blog.util.RSAUtils
import me.w2x.blog.enu.Dict
import me.w2x.blog.bean.Constant
import me.w2x.blog.service.InitService
import org.apache.commons.codec.binary.Hex
import org.apache.commons.collections.map.CaseInsensitiveMap
import org.reflections.Reflections

import javax.servlet.ServletContext
import java.security.interfaces.RSAPublicKey

/**
 * User: Gang Chen
 * Date: 2015/12/5 21:56
 */
class InitServiceImpl implements InitService {

    ServletContext servletContext

    @SuppressWarnings('NestedForLoop')
    @Override
    def init() {
        if (!servletContext) {
            return
        }

        def reflections = new Reflections('me.w2x.blog.enu')
        def classes = reflections.getSubTypesOf(Dict)
        def dictMap = new CaseInsensitiveMap()
        for (def clazz : classes) {
            def dict = [:]
            dictMap.put(clazz.simpleName, dict)
            def enums = clazz.enumConstants
            for (Object o : enums) {
                def d = (Dict) o
                dict.put(d.key, d.text)
            }
        }

        servletContext.setAttribute(Constant.DICT_MAP, dictMap)
        servletContext.setAttribute(Constant.DICT_MAP_JSON, JSON.toJSONString(dictMap))

        def keyPair = RSAUtils.keyPair
        def publicKey = (RSAPublicKey) keyPair.public
        servletContext.setAttribute(Constant.MODULUS, new String(Hex.encodeHex(publicKey.modulus.toByteArray())))
        servletContext.setAttribute(Constant.EXPONENT, new String(Hex.encodeHex(publicKey.publicExponent.toByteArray())))
    }

    @Override
    void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext
        init()
    }
}
