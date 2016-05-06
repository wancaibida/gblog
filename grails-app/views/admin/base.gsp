<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<asset:stylesheet src="ligerUI/skins/Aqua/css/ligerui-all.css?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:stylesheet src="ligerUI/skins/Gray/css/all.css?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:stylesheet src="ligerUI/skins/ligerui-icons.css?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>

<asset:stylesheet src="css/common.css?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:stylesheet src="css/index.css?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>

<asset:javascript src="jQuery/jquery-1.11.3.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="json2/json2.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="ligerUI/js/core/base.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="ligerUI/js/ligerui.all.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="js/common/LG.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="jquery-validation/jquery.validate.min.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="jquery-validation/jquery.metadata.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
<asset:javascript src="jquery-validation/messages_cn.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var adminPath = basePath + "admin/";
    var dictMap = '${application.dictMapJson}';
    window.JSON = JSON2;
    var assetPath = "${assetPath(src: '')}";
    var iconPath = "${assetPath(src: 'icons/32X32/')}";
    var dialogImgPrefix = "${assetPath(src: 'ligerUI/skins/')}";
</script>
