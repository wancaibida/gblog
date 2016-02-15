<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:include view="/admin/base.gsp"/>
    <script type="text/javascript"
            src="${webRequest.baseUrl}/static/js/admin/sys/buttonList.js"></script>
    <script type="text/javascript"
            src="${webRequest.baseUrl}/static/js/common/iconselector.js"></script>
    <title>按钮管理</title>
</head>

<body>

<div id="detail" style="display: none;">
    <form id="mainform">
    </form>
</div>

<div id="maingrid"></div>

<script type="text/javascript">
    var menuId = "${params.menuId}";
    var menuAlias = '${alias}';
</script>
</body>
</html>
