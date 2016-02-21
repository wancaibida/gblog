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
                display: "名称",
                name: "name",
                type: "text"
            },
            {
                display: "别名",
                name: "alias",
                type: "text"
            }
        ],
        dataAction: 'server',
        method: 'get',
        pageSize: 10,
        url: adminPath + 'categorys',
        sortName: 'id',
        width: '98%',
        height: "98%",
        heightDiff: -10,
        rownumbers: false,
        checkbox: false,
        isScroll: false,
        tree: {
            columnId: 'name',
            idField: 'id',
            parentIDField: 'parentId'
        },
        toolbar: {}
    });

    //查询表单
    $("#formsearch").ligerForm({
        fields: [
            {
                display: "名称",
                name: "NAME",
                type: "text",
                newline: true,
                attr: {
                    op: "like",
                    vt: "string"
                },
                cssClass: "field"
            },
            {
                display: "别名",
                name: "alias",
                newline: false,
                type: "text",
                attr: {
                    op: "like",
                    vt: "string"
                },
                cssClass: "field"
            }
        ],
        toJSON: JSON2.stringify
    });

    LG.appendSearchButtons("#formsearch", grid);

    LG.loadToolbar(grid, toolbarClick);

    function toolbarClick(item) {
        var data = {
            id: "",
            name: "",
            my_parentId: "无上级栏目",
            parentId: "0",
            alias: ""
        };

        var selected = grid.getSelected();

        switch (item.id) {
            case 'add':
                showDialog(item, data);
                break;
            case 'modify':
                if (!selected) {
                    LG.tip("请选择行!");
                    return;
                }

                $.extend(data, selected);
                data._id = selected.id;
                showDialog(item, data);
                break;
            case 'delete':
                if (!selected) {
                    LG.tip("请选择行!");
                    return;
                }

                LG.confirm("确定要删除吗?", del, selected);
                break;
        }
    }

    var dialog, mainform;

    function showDialog(item, data) {
        if (dialog) {
            dialog.show();
        }
        else {
            mainform = $("#mainform");
            mainform.ligerForm({
                inputWidth: 170,
                labelWidth: 90,
                space: 20,
                fields: [
                    {
                        name: "_id",
                        type: "hidden"
                    },
                    {
                        display: "上级栏目",
                        name: "parentId",
                        newline: true,
                        type: "select",
                        comboboxName: "my_parentId",
                        options: {
                            initValue: 0,
                            initText: "无上级栏目",
                            url: adminPath + "categorys/parents",
                            ajaxType: 'get'
                        }
                    },
                    {
                        display: "名称",
                        name: "name",
                        newline: true,
                        type: "text",
                        validate: {
                            required: true,
                            maxlength: 200
                        }
                    },
                    {
                        display: "别名",
                        name: "alias",
                        newline: true,
                        type: "text",
                        validate: {
                            required: true,
                            maxlength: 200
                        }
                    }
                ]
            });

            dialog = $.ligerDialog.open({
                target: $("#detail"),
                width: 350,
                height: 200,
                top: 90,
                isResize: true,
                buttons: [
                    {
                        text: '确定',
                        onclick: function () {
                            saveOrUpdate();
                        }
                    },
                    {
                        text: '取消',
                        onclick: function (flag, dialog) {
                            dialog.hide();
                        }
                    }
                ]
            });
        }

        liger.get('my_parentId').set('url', adminPath + 'categorys/parents?categoryId=' + data.id);
        dialog.set("title", item.text);

        if (data) {
            LG.loadDataForm(mainform, data);
        }
    }

    function saveOrUpdate() {
        var data = {};
        mainform.find('input').each(function (index, element) {
            var $element = $(element);
            data[element.name] = $element.val();
        });
        data['id'] = data['_id'];

        var url, method;
        if (data.id) {
            url = adminPath + 'categorys/' + data.id;
            method = 'post'
        }
        else {
            url = adminPath + 'categorys';
            method = 'post'
        }

        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: method,
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                LG.showSuccess('操作成功!', function () {
                    dialog.hide();
                    grid.loadData();
                })
            },
            error: function (data) {
                var json = data['responseJSON'];
                LG.showError(json.errorMessage);
            }
        });
    }

    function del(selected) {
        $.ajax({
            url: adminPath + "categorys/" + selected.id,
            data: {id: selected.id},
            dataType: 'json',
            type: 'delete',
            converters: {
                "text json": JSON2.parse
            },
            success: function () {
                LG.showSuccess('操作成功!', function () {
                    grid.loadData();
                });
            }
        });
    }
});