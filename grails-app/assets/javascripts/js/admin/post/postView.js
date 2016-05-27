var testEditor;

$(function () {
    var isBusy = false;
    testEditor = editormd("test-editormd", {
        width: "90%",
        height: 640,
        syncScrolling: "single",
        path: assetPath + "editor.md/lib/",
        saveHTMLToTextarea: true,
        onchange: function () {
            if (isBusy) {
                return;
            }
            else {
                isBusy = true;
                var data = {};
                data.id = $("#id").val();
                data.title = $('#title').val();
                data.excerpt = $("#excerpt").val();
                data.categoryId = $('#categoryId').val();
                data.postStatus = 0;
                data.content = this.getHTML();
                data.raw = this.getMarkdown();

                saveOrUpdate(
                    data,
                    function (result) {
                        if (result.id) {
                            $("#id").val(result.id);
                        }
                        isBusy = false;
                    },
                    function (error) {
                        console.log(error);
                        isBusy = false;
                    });
            }
        }
    });

    $(":button").click(function () {
        var data = {};
        data.id = $("#id").val();
        data.title = $('#title').val();
        data.categoryId = $('#categoryId').val();
        data.postStatus = $('#postStatus').val();
        data.content = testEditor.getHTML();
        data.raw = testEditor.getMarkdown();
        data.excerpt = $("#excerpt").val();

        saveOrUpdate(
            data,
            function () {
                alert('保存成功!');
                if (!(window === window.parent)) {
                    window.parent.LG.closeCurrentTab(null);
                }
                else {
                    window.close();
                }
            },
            function (data) {
                var json = data['responseJSON'];
                alert(json.errorMessage);
            });
    });

    function saveOrUpdate(data, success, error) {
        $.ajax({
            url: adminPath + 'posts' + (data.id ? '/' + data.id : ''),
            data: data,
            dataType: 'json',
            type: 'post',
            converters: {
                "text json": JSON2.parse
            },
            success: function (result) {
                success(result);
            },
            error: function (data) {
                error();
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