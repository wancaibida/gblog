<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:include view="/admin/base.gsp"/>
    <script src="${webRequest.baseUrl}/static/js/common/LG.js" type="text/javascript"></script>
    <script src="${webRequest.baseUrl}/static/js/common/common.js" type="text/javascript"></script>
    <script src="${webRequest.baseUrl}/static/js/admin/index/adminIndex.js"
            type="text/javascript"></script>
    <script src="${webRequest.baseUrl}/static/js/common/changepassword.js"
            type="text/javascript"></script>
    <script src="${webRequest.baseUrl}/static/js/common/security.js"
            type="text/javascript"></script>
    <title>后台管理</title>
    <script type="text/javascript">
        var modulus = '${application.modulus}';
        var exponent = '${application.exponent}';
    </script>
</head>

<body style="text-align:center; background:#F0F0F0; overflow:hidden;">
<div id="pageloading" style="display:block;"></div>

<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo">${grailsApplication.config.my.project.name}管理系统</div>

    <div class="l-topmenu-welcome">
        <span class="l-topmenu-username"></span>欢迎您 &nbsp;
    [<a href="javascript:LG.changepassword()">修改密码</a>] &nbsp;
    [<a href="${webRequest.baseUrl}/admin/user/j_spring_security_logout">退出</a>]
    </div>

</div>

<div id="mainbody" class="l-mainbody" style="width:99.2%; margin:0 auto; margin-top:3px;">
    <div position="left" title="主要菜单" id="mainmenu"></div>

    <div position="center" id="framecenter">
        <div tabid="home" title="我的主页">
        </div>
    </div>
</div>

<div class="l-hidden">
</div>
</body>

</html>