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

    @Override
    def init() {
        if (!servletContext) {
            return
        }

        def reflections = new Reflections("m2.w2x.blog.enu")
        def classes = reflections.getSubTypesOf(Dict.class)
        def dictMap = new CaseInsensitiveMap()
        for (def clazz : classes) {
            def dict = new HashMap<String, String>()
            dictMap.put(clazz.getSimpleName(), dict)
            def enums = clazz.getEnumConstants()
            for (Object o : enums) {
                def d = (Dict) o
                dict.put(d.getKey(), d.getText())
            }
        }

        servletContext.setAttribute(Constant.DICT_MAP, dictMap)
        servletContext.setAttribute(Constant.DICT_MAP_JSON, JSON.toJSONString(dictMap))

        def keyPair = RSAUtils.getKeyPair()
        def publicKey = (RSAPublicKey) keyPair.getPublic()
        servletContext.setAttribute(Constant.MODULUS, new String(Hex.encodeHex(publicKey.getModulus().toByteArray())))
        servletContext.setAttribute(Constant.EXPONENT, new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())))
    }

    @Override
    void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext
        init()
    }
}
