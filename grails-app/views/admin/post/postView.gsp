<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>Simple example - Editor.md examples</title>
    <asset:stylesheet src="editor.md/style.css"/>
    <asset:stylesheet src="editor.md/css/editormd.min.css"/>
    <asset:javascript src="json2/json2.js"/>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var adminPath = basePath + "admin/";
        var dictMap = '${application.dictMapJson}';
        window.JSON = JSON2;
        var postId = '${params.postId}';
    </script>
</head>

<body>
<div id="layout">

    <form>
        <input type="hidden" name="id" id="id" value="${post?.id}">

        <div style="width:90%;margin: 10px auto;">
            标题: <input type="text" name="title" id="title" style="padding: 5px;width:300px;"
                       value="${post?.title}"><br>
            栏目:
            <g:select name="categoryId" from="${categorys}"
                      value="${post?.category?.id}" optionKey="id" optionValue="name"/>
            <br>
            状态:
            <g:select name="postStatus" from="${application.dictMap['postStatus']}"
                      value="${post?.status ?: 2}" optionKey="key" optionValue="value"/>

        </div>

        <div id="test-editormd">
            <textarea style="display:none;">${post?.raw}</textarea>
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