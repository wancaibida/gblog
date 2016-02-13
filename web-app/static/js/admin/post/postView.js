var testEditor;

$(function () {
    testEditor = editormd("test-editormd", {
        width: "90%",
        height: 640,
        syncScrolling: "single",
        path: basePath + "static/editor.md/lib/",
        saveHTMLToTextarea: true
    });

    $(":button").click(function () {
        var data = {};
        data.id = $("#id").val();
        data.title = $('#title').val();
        data.categoryId = $('#categoryId').val();
        data.postStatus = $('#postStatus').val();
        data.content = testEditor.getHTML();
        data.raw = testEditor.getMarkdown();

        saveOrUpdate(data);
    });

    function saveOrUpdate(data) {
        $.ajax({
            url: adminPath + 'posts' + (postId ? '/' + postId : ''),
            data: data,
            dataType: 'json',
            type: 'post',
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                alert('操作成功');
                window.close();
            },
            error: function (data) {
                var json = data['responseJSON'];
                alert(json.errorMessage);
            }
        });
    }

    /*
     // or
     testEditor = editormd({
     id      : "test-editormd",
     width   : "90%",
     height  : 640,
     path    : "../lib/"
     });
     */
});