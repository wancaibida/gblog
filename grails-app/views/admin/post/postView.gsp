<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>文章管理</title>
    <asset:stylesheet src="editor.md/style.css"/>
    <asset:stylesheet src="editor.md/css/editormd.min.css"/>
    <asset:javascript src="json2/json2.js"/>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var adminPath = basePath + "admin/";
        var dictMap = '${application.dictMapJson}';
        window.JSON = JSON2;
        var postId = '${params.postId}';
        var assetPath = "${assetPath(src: '')}";
    </script>
</head>

<body>
<div id="layout">

    <form>
        <input type="hidden" name="id" id="id" value="${post?.id}">
        <input type="hidden" name="draftId" id="draftId">

        <div style="width:90%;margin: 10px auto;">
            <g:if test="${drafts}">
                草稿:<g:select name="drafts" from="${drafts}" optionKey="id" optionValue="title"
                             noSelection="['null': 'Select one to recover']"/>
                <br>
            </g:if>

            标题: <input type="text" name="title" id="title" style="padding: 5px;width:300px;"
                       value="${post?.title}"><br>
            栏目:
            <g:select name="categoryId" from="${categorys}"
                      value="${post?.category?.id}" optionKey="id" optionValue="name"/>
            <br>
            状态:
            <g:select name="postStatus" from="${application.dictMap['postStatus']}"
                      value="${post?.status ?: 2}" optionKey="key" optionValue="value"/>
            <br>
            摘要:
            <textarea style="width: 90%;height: 100px" name="excerpt"
                      id="excerpt">${post?.excerpt}</textarea>
        </div>

        <div id="test-editormd">
            <textarea style="display:none;" name="raw" id="raw">${post?.raw}</textarea>
        </div>

        <div style="width:90%;margin: 10px auto;">
            <input type="button" name="submit" value="发布文章" style="padding: 5px;">
        </div>
    </form>
</div>

<asset:javascript src="jQuery/jquery-1.11.3.js"/>
<asset:javascript src="editor.md/editormd.min.js"/>
<asset:javascript src="js/admin/post/postView.js"/>

</body>
</html>