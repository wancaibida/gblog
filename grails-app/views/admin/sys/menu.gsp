<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:include view="/admin/base.gsp"/>
    <asset:javascript src="js/admin/sys/menuList.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
    <asset:javascript src="js/common/common.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
    <asset:javascript src="js/common/iconselector.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>
    <title>菜单管理</title>
</head>

<body>
<div id="layout1">
    <div position="left" title="菜单">
        <ul id="menuTree">
        </ul>
    </div>

    <div position="center" title="子菜单">
        <div id="maingrid"></div>

        <div id="detail" style="display: none;">
            <form id="mainform">
            </form>
        </div>
    </div>
</div>

</body>
</html>
