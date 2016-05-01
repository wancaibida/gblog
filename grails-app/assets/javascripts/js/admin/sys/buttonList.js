$(function () {
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
        url: adminPath + 'menus/' + menuId + '/buttons',
        parms: {
            where: JSON2.stringify({
                "op": "and",
                "rules": [{
                    "op": "equal",
                    "field": "menuAlias",
                    "value": menuAlias,
                    "type": "string"
                }]
            })
        },
        sortName: 'id',
        width: '98%',
        height: "98%",
        heightDiff: -10,
        rownumbers: false,
        checkbox: false,
        isScroll: false,
        toolbar: {}
    });

    var items = [];
    var data = [{
        "id": 1,
        "alias": "add",
        "icon": "add.gif",
        "menuAlias": "menus",
        "name": "添加",
        "viewSort": 0
    }, {
        "id": 2,
        "alias": "modify",
        "icon": "modify.gif",
        "menuAlias": "menus",
        "name": "修改",
        "viewSort": 1
    }, {
        "id": 3,
        "alias": "delete",
        "icon": "delete.gif",
        "menuAlias": "menus",
        "name": "删除",
        "viewSort": 2
    }, {
        "id": 4,
        "alias": "button",
        "icon": "home.gif",
        "menuAlias": "menus",
        "name": "一键增加增删改查",
        "viewSort": 3
    }];

    for (var i = 0, l = data.length; i < l; ++i) {
        var o = data[i];
        items[items.length] = {
            click: toolbarClick,
            text: o.name,
            img: basePath + "static/icons/32X32/" + o.icon,
            id: o.alias
        };
        items[items.length] = {line: true};
    }
    grid.toolbarManager.set('items', items);

    function toolbarClick(item) {
        var data = {_id: '', name: '', alias: '', icon: '', viewSort: ''};
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
                LG.confirm('确定一键增加增删改吗?', addButtons);
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
                        name: "viewSort",
                        newline: true,
                        type: "spinner",
                        options: {
                            type: 'int',
                            minValue: 0
                        },
                        validate: {
                            required: true
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

        dialog.set("title", item.text);

        if (data) {
            LG.loadDataForm(mainform, data);
        }
    }

    function saveOrUpdate() {

        $.metadata.setType("attr", "validate");
        LG.validate(mainform, {debug: true});

        if (!mainform.valid()) {
            LG.showInvalid();
            return;
        }

        var data = {};
        mainform.find('input').each(function (index, element) {
            var $element = $(element);
            data[element.name] = $element.val();
        });
        data['id'] = data['_id'];
        data['menuAlias'] = menuAlias;
        var url, method;
        if (data.id) {
            url = adminPath + 'menus/' + menuId + '/buttons/' + data.id;
            method = 'post'
        }
        else {
            url = adminPath + 'menus/' + menuId + '/buttons';
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
            url: adminPath + "menus/" + menuId + '/buttons/' + selected.id,
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

    function addButtons() {
        $.ajax({
            url: adminPath + "menus/" + menuId + '/buttons/all',
            dataType: 'json',
            type: 'post',
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