$(function () {
    $("#layout1").ligerLayout({leftWidth: 200});

    $('#menuTree').ligerTree({
        url: adminPath + 'menu',
        idFieldName: 'id',
        parentIDFieldName: 'parentId',
        ajaxType: 'get',
        textFieldName: 'name',
        topParentIDValue: 0,
        checkbox: false,
        onBeforeAppend: function (a, list) {
            list.push({id: 0, name: '所有菜单', parentId: -1, alias: 'root', icon: 'world.gif'});
            $.each(list, function (index, item) {
                item.icon = basePath + 'static/icons/32X32/' + item.icon;
            })
        },
        onClick: function (obj) {
            if (obj && obj.data) {
                var id = obj.data.id;
                if (id) {
                    grid.set('parms', {
                        where: JSON2.stringify({
                            "op": "and",
                            "rules": [{
                                "op": "equal",
                                "field": "parentId",
                                "value": id,
                                "type": "number"
                            }]
                        })
                    });

                    grid.loadData();
                }
                else {
                    grid.set('parms', {});
                    grid.loadData();
                }
            }
        }
    });

    var grid = $("#maingrid").ligerGrid({
        columns: [
            {
                display: "编号",
                name: "id",
                type: "int",
                width: 50
            },
            {
                display: "名称",
                name: "name",
                type: "text",
                validate: {
                    required: true
                }
            },
            {
                display: "别名",
                name: "alias",
                type: "text"
            },
            {
                display: "链接",
                name: "url",
                type: "text"
            },
            {
                display: "图标",
                name: "icon",
                type: "text",
                render: function (a, b, val) {
                    return "<img src=" + basePath + "static/icons/32X32/" + val + ">";
                }
            }
        ],
        dataAction: 'server',
        method: 'get',
        pageSize: 10,
        url: adminPath + 'menus',
        sortName: 'id',
        width: '98%',
        height: "98%",
        heightDiff: -10,
        rownumbers: false,
        checkbox: false,
        isScroll: false,
        toolbar: {}
    });

    LG.loadToolbar(grid, toolbarClick);


    function toolbarClick(item) {
        var data = {_id: '', parentId: '', name: '', alias: '', url: '', icon: '', _sort: ''};
        var selected;
        switch (item.id) {
            case 'add':
                showDialog(item, data);
                break;
            case 'modify':
                selected = grid.getSelected();
                if (!selected) {
                    return LG.tip("请选择行!");
                }
                data = $.extend({}, data, selected);
                data._id = selected.id;
                showDialog(item, data);
                break;
            case 'delete':
                selected = grid.getSelected();
                if (!selected) {
                    return LG.tip("请选择行!");
                }
                LG.confirm('确定删除吗?', del, selected);
                break;
            case 'button':
                selected = grid.getSelected();
                if (!selected) {
                    return LG.tip("请选择行!");
                }
                LG.addTab(selected.name + ' 按钮管理', adminPath + 'menus/' + selected.id + '/buttons');
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
                prefixID: '',
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
                            url: adminPath + "menus/parents",
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
                    },
                    {
                        display: "链接",
                        name: "url",
                        newline: true,
                        type: "text"
                    },
                    {
                        display: "图标",
                        name: "icon",
                        newline: true,
                        type: "text",
                        validate: {
                            required: true,
                            maxlength: 200
                        }
                    },
                    {
                        display: "排序",
                        name: "_sort",
                        newline: true,
                        type: "spinner",
                        options: {
                            type: 'int',
                            minValue: 0
                        }
                    }
                ]
            });

            liger.get('icon').set('onFocus', function () {
                f_openIconsWin(function (src) {
                    liger.get('icon').setValue(src);
                });
            });

            dialog = $.ligerDialog.open({
                target: $("#detail"),
                width: 350,
                height: 250,
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

        liger.get('my_parentId').set('url', adminPath + "menus/parents?menuId=" + data.id);
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
            url = adminPath + 'menus/' + data.id;
            method = 'post'
        }
        else {
            url = adminPath + 'menus';
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
            url: adminPath + "menus/" + selected.id,
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