<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<asset:stylesheet src="ligerUI/skins/Aqua/css/ligerui-all.css"/>
<asset:stylesheet src="ligerUI/skins/Gray/css/all.css"/>
<asset:stylesheet src="ligerUI/skins/ligerui-icons.css"/>

<asset:stylesheet src="css/common.css"/>
<asset:stylesheet src="css/index.css"/>

<asset:javascript src="jQuery/jquery-1.11.3.js"/>
<asset:javascript src="json2/json2.js"/>
<asset:javascript src="ligerUI/js/core/base.js"/>
<asset:javascript src="ligerUI/js/ligerui.all.js"/>
<asset:javascript src="js/common/LG.js"/>
<asset:javascript src="jquery-validation/jquery.validate.min.js"/>
<asset:javascript src="jquery-validation/jquery.metadata.js"/>
<asset:javascript src="jquery-validation/messages_cn.js"/>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var adminPath = basePath + "admin/";
    var dictMap = '${application.dictMapJson}';
    window.JSON = JSON2;
</script>
