$(function () {
    var grid = $("#maingrid").ligerGrid({
        columns: [
            {
                display: "编号",
                name: "id",
                type: "text",
                width: 50
            },
            {
                display: "标题",
                name: "title",
                type: "text"
            },
            {
                display: "栏目",
                name: "category",
                type: "text",
                render: function (item, index, val) {
                    return val.name;
                }
            },
            {
                display: "状态",
                name: "status",
                type: "text",
                render: function (item, index, val) {
                    return window.dictMap['poststatus'][val];
                }
            },
            {
                display: "评论数",
                name: "commentCount",
                type: "text"
            },
            {
                display: "发布日期",
                name: "dateCreated",
                type: "text"
            }
        ],
        dataAction: 'server',
        method: 'get',
        pageSize: 10,
        url: adminPath + 'posts',
        sortName: 'dateCreated',
        width: '98%',
        height: "98%",
        heightDiff: -10,
        rownumbers: false,
        checkbox: false,
        isScroll: false,
        toolbar: {}
    });

    //查询表单
    $("#formsearch").ligerForm({
        fields: [
            {
                display: "标题",
                name: "title",
                type: "text",
                newline: true,
                attr: {
                    op: "like",
                    vt: "string"
                },
                cssClass: "field"
            },
            {
                display: "分类",
                name: "post_category_id",
                newline: false,
                type: "select",
                comboboxName: "my_post_category_id",
                options: {
                    valueFieldID: "post_category_id",
                    url: adminPath + "categorys/parents",
                    ajaxType: 'get'
                },
                attr: {
                    op: "equal",
                    vt: "long"
                },
                cssClass: "field"
            }
        ],
        toJSON: JSON2.stringify
    });

    LG.appendSearchButtons("#formsearch", grid);

    LG.loadToolbar(grid, toolbarClick);

    function toolbarClick(item) {
        var selected = grid.getSelected();

        switch (item.id) {
            case 'add':
                LG.addTab("发布文章", adminPath + "posts/view");
                break;
            case 'modify':
                if (!selected) {
                    LG.tip("请选择行!");
                    return;
                }
                LG.addTab("修改文章", adminPath + "posts/view?postId=" + selected.id);
                break;
            case 'delete':
                if (!selected) {
                    LG.tip("请选择行!");
                    return;
                }
                LG.confirm("确定要删除吗?", del, selected);
                break;
            case 'staticAll':
                LG.confirm("确定要全站静态化吗?", staticAll);
                break;
            case 'staticOther':
                LG.confirm("确定要其他静态化吗?", staticOther);
                break;
        }
    }

    function del(selected) {
        $.ajax({
            url: adminPath + "posts/" + selected.id,
            data: {id: selected.id},
            dataType: 'json',
            type: 'delete',
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                grid.loadData();
            }
        });
    }

    function staticAll() {
        $.ajax({
            url: adminPath + "posts/staticAll",
            data: {},
            dataType: 'json',
            type: 'post',
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                LG.showSuccess('静态化成功!')
            }
        });
    }

    function staticOther() {
        $.ajax({
            url: adminPath + "posts/staticOther",
            data: {},
            dataType: 'json',
            type: 'post',
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                LG.showSuccess('静态化成功!')
            }
        });
    }
});