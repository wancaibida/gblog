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
                data.id = $("#draftId").val();
                data.postId = $("#id").val();
                data.title = $('#title').val() || 'draft';
                data.excerpt = $("#excerpt").val();
                data.categoryId = $('#categoryId').val();
                data.content = this.getHTML();
                data.raw = this.getMarkdown();

                saveDraft(
                    data,
                    function (result) {
                        if (result.id) {
                            $("#draftId").val(result.id);
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
        data.draftId = $("#draftId").val();

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

    $('#drafts').change(function () {
        var draftId = this.value;
        if (draftId) {
            var result = confirm('Do you want to recover [' + $(this).find("option:selected").text() + ']');
            if (result) {
                getDraft(draftId);
            }
        }
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

    function saveDraft(data, success, error) {
        $.ajax({
            url: adminPath + 'drafts',
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

    function getDraft(draftId) {
        $.get(adminPath + 'drafts/' + draftId, function (result) {
            if (!result) {
                return
            }

            $("#draftId").val(result.id);
            if (result.post && result.post.id) {
                $("#id").val(result.post.id);
            }
            $("#title").val(result.title);
            $("#excerpt").val(result.excerpt);
            testEditor.setMarkdown(result.raw);
            if (result.category && result.category.id) {
                $("#categoryId").val(result.category.id);
            }

        }, 'json');

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