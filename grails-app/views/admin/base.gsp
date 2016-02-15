<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<link type="text/css" rel="stylesheet"
      href="<%=basePath%>static/ligerUI/skins/Aqua/css/ligerui-all.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/ligerUI/skins/Gray/css/all.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/ligerUI/skins/ligerui-icons.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/common.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/index.css"/>
<script type="text/javascript" src="<%=basePath%>static/jQuery/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=basePath%>static/json2/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>static/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="<%=basePath%>static/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common/LG.js"></script>

<script type="text/javascript"
        src="<%=basePath%>static/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript"
        src="<%=basePath%>static/jquery-validation/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=basePath%>static/jquery-validation/messages_cn.js"></script>


<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var adminPath = basePath + "admin/";
    var dictMap = '${application.dictMapJson}';
    window.JSON = JSON2;
</script>
