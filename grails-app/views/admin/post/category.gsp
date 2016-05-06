<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:include view="/admin/base.gsp"/>
    <asset:javascript src="js/admin/post/categoryList.js?v=${me.w2x.blog.bean.Constant.SYSTEM_STARTUP_TIME}"/>

    <title>分类管理</title>
</head>

<body>
<div id="mainsearch" style="width: 98%">
    <div class="searchtitle">
        <span>搜索</span><asset:image src="icons/32X32/searchtool.gif"/>

        <div class="togglebtn"></div>
    </div>

    <div class="navline" style="margin-bottom: 4px; margin-top: 4px;"></div>

    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>

<div id="detail" style="display: none;">
    <form id="mainform">
    </form>
</div>

<div id="maingrid"></div>

</body>
</html>
