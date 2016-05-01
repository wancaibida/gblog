﻿(function ($) {

    //全局系统对象
    window['LG'] = {
        Defaults: {}
    };

    LG.cookies = (function () {
        var fn = function () {
        };
        fn.prototype.get = function (name) {
            var cookieValue = "";
            var search = name + "=";
            if (document.cookie.length > 0) {
                offset = document.cookie.indexOf(search);
                if (offset != -1) {
                    offset += search.length;
                    end = document.cookie.indexOf(";", offset);
                    if (end == -1) {
                        end = document.cookie.length;
                    }
                    cookieValue = decodeURIComponent(document.cookie.substring(offset, end))
                }
            }
            return cookieValue;
        };
        fn.prototype.set = function (cookieName, cookieValue, DayValue) {
            var expire = "";
            var day_value = 1;
            if (DayValue != null) {
                day_value = DayValue;
            }
            expire = new Date((new Date()).getTime() + day_value * 86400000);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";path=/" + expire;
        };
        fn.prototype.remvoe = function (cookieName) {
            var expire = "";
            expire = new Date((new Date()).getTime() - 1);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + escape("") + ";path=/" + expire;
            /*path=/*/
        };

        return new fn();
    })();

    //右下角的提示框
    LG.tip = function (message) {
        if (LG.wintip) {
            LG.wintip.set('content', message);
            LG.wintip.show();
        }
        else {
            LG.wintip = $.ligerDialog.tip({content: message});
        }
        setTimeout(function () {
            LG.wintip.hide()
        }, 4000);
    };

    //预加载图片
    LG.prevLoadImage = function (rootpath, paths) {
        for (var i in paths) {
            $('<img />').attr('src', rootpath + paths[i]);
        }
    };
    //显示loading
    LG.showLoading = function (message) {
        message = message || "正在加载中...";
        $('body').append("<div class='jloading'>" + message + "</div>");
        $.ligerui.win.mask();
    };
    //隐藏loading
    LG.hideLoading = function (message) {
        $('body > div.jloading').remove();
        $.ligerui.win.unmask({id: new Date().getTime()});
    };
    //显示成功提示窗口
    LG.showSuccess = function (message, callback) {
        if (typeof (message) == "function" || arguments.length == 0) {
            callback = message;
            message = "操作成功!";
        }
        $.ligerDialog.success(message, '提示信息', callback);
    };
    //显示失败提示窗口
    LG.showError = function (message, callback) {
        if (typeof (message) == "function" || arguments.length == 0) {
            callback = message;
            message = "操作失败!";
        }
        $.ligerDialog.error(message, '提示信息', callback);
    };

    //预加载dialog的图片
    LG.prevDialogImage = function (rootPath) {
        rootPath = rootPath || "";
        LG.prevLoadImage(basePath + dialogImgPrefix + 'Aqua/images/win/', ['dialog-icons.gif']);
        LG.prevLoadImage(basePath + dialogImgPrefix + 'Gray/images/win/', ['dialogicon.gif']);
    };

    //提交服务器请求
    //返回json格式
    //1,提交给类 options.type  方法 options.method 处理
    //2,并返回 AjaxResult(这也是一个类)类型的的序列化好的字符串
    LG.ajax = function (options) {
        var p = options || {};
        $.ajax({
            cache: false,
            async: true,
            url: p.url,
            data: p.data,
            dataType: 'json', type: 'post',
            beforeSend: function () {
                LG.loading = true;
                if (p.beforeSend) {
                    p.beforeSend();
                }
                else {
                    LG.showLoading(p.loading);
                }
            },
            complete: function () {
                LG.loading = false;
                if (p.complete) {
                    p.complete();
                }
                else {
                    LG.hideLoading();
                }
            },
            success: function (result) {
                if (!result) {
                    return;
                }
                if (!result.IsError) {
                    if (p.success) {
                        p.success(result.Data, result.Message);
                    }
                }
                else {
                    if (p.error) {
                        p.error(result.Message);
                    }
                }
            },
            error: function (result, b) {
                if (console) {
                    console.log(arguments);
                }
                LG.tip('发现系统错误 <BR>错误码：' + result.status);
            }
        });
    };

    //获取当前页面的MenuNo
    //优先级1：如果页面存在MenuNo的表单元素，那么加载它的值
    //优先级2：加载QueryString，名字为MenuNo的值
    LG.getPageAlias = function () {
        var alias = $("#alias").val();
        if (!alias) {
            alias = getQueryStringByName("alias");
        }
        return alias;
    };

    //创建按钮
    LG.createButton = function (options) {
        var p = $.extend({
            appendTo: $('body')
        }, options || {});
        var btn = $('<div class="button button2 buttonnoicon" style="width:60px"><div class="button-l"> </div><div class="button-r"> </div> <span></span></div>');
        if (p.icon) {
            btn.removeClass("buttonnoicon");
            btn.append('<div class="button-icon"> <img src="../' + p.icon + '" /> </div> ');
        }
        //绿色皮肤
        if (p.green) {
            btn.removeClass("button2");
        }
        if (p.width) {
            btn.width(p.width);
        }
        if (p.click) {
            btn.click(p.click);
        }
        if (p.text) {
            $("span", btn).html(p.text);
        }
        if (typeof (p.appendTo) == "string") {
            p.appendTo = $(p.appendTo);
        }
        btn.appendTo(p.appendTo);
    };

    //创建过滤规则(查询表单)
    LG.bulidFilterGroup = function (form) {
        if (!form) {
            return null;
        }
        var group = {op: "and", rules: []};
        $(":input", form).not(":submit, :reset, :image,:button, [disabled]")
            .each(function () {
                if (!this.name) {
                    return;
                }
                if (!$(this).hasClass("field")) {
                    return;
                }
                if ($(this).val() == null || $(this).val() == "") {
                    return;
                }

                var op = $(this).attr("op") || "like";
                var type = $(this).attr("vt") || "string";
                var value = $(this).val();
                var name = this.name;

                var lthis = liger.get(name);
                if (lthis instanceof $.ligerui.controls.ComboBox) {
                    name = lthis.get("valueFieldID");
                    value = $("#" + name).val();
                }

                //if (name.indexOf("_") != -1)
                //{
                //    name = name.replace("_", ".");
                //}

                group.rules.push({
                    op: op,
                    field: name,
                    value: value,
                    type: type
                });
            });
        return group;
    };

    //附加表单搜索按钮：搜索、高级搜索
    LG.appendSearchButtons = function (form, grid) {
        if (!form) {
            return;
        }
        form = $(form);
        //搜索按钮 附加到第一个li  高级搜索按钮附加到 第二个li
        var container = $('<ul><li style="margin-right:8px"></li><li></li></ul><div class="l-clear"></div>').appendTo(form);
        LG.addSearchButtons(form, grid, container.find("li:eq(0)"), container.find("li:eq(1)"));

    };

    //创建表单搜索按钮：搜索、高级搜索
    LG.addSearchButtons = function (form, grid, btn1Container, btn2Container) {
        if (!form) {
            return;
        }
        if (btn1Container) {
            LG.createButton({
                appendTo: btn1Container,
                text: '搜索',
                click: function () {
                    var rule = LG.bulidFilterGroup(form);
                    if (rule.rules.length) {
                        grid.set('parms', {where: JSON2.stringify(rule)});
                    }
                    else {
                        grid.set('parms', {});
                    }
                    grid.loadData();
                }
            });
        }
        if (btn2Container) {
            LG.createButton({
                appendTo: btn2Container,
                width: 80,
                text: '高级搜索',
                click: function () {
                    grid.showFilter();
                }
            });
        }
    };

    //快速设置表单底部默认的按钮:保存、取消
    LG.setFormDefaultBtn = function (cancleCallback, savedCallback) {
        //表单底部按钮
        var buttons = [];
        if (cancleCallback) {
            buttons.push({text: '取消', onclick: cancleCallback});
        }
        if (savedCallback) {
            buttons.push({text: '保存', onclick: savedCallback});
        }
        LG.addFormButtons(buttons);
    };

    //增加表单底部按钮,比如：保存、取消
    LG.addFormButtons = function (buttons) {
        if (!buttons) {
            return;
        }
        var formbar = $("body > div.form-bar");
        if (formbar.length == 0) {
            formbar = $('<div class="form-bar"><div class="form-bar-inner"></div></div>').appendTo('body');
        }
        if (!(buttons instanceof Array)) {
            buttons = [buttons];
        }
        $(buttons).each(function (i, o) {
            var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
            $("div.l-dialog-btn-inner:first", btn).html(o.text || "BUTTON");
            if (o.onclick) {
                btn.bind('click', function () {
                    o.onclick(o);
                });
            }
            if (o.width) {
                btn.width(o.width);
            }
            $("> div:first", formbar).append(btn);
        });
    };

    //填充表单数据
    LG.loadForm = function (mainform, options, callback) {
        options = options || {};
        if (!mainform) {
            mainform = $("form:first");
        }
        var p = $.extend({
            beforeSend: function () {
                LG.showLoading('正在加载表单数据中...');
            },
            complete: function () {
                LG.hideLoading();
            },
            success: function (data) {
                var preID = options.preID || "";
                //根据返回的属性名，找到相应ID的表单元素，并赋值
                for (var p in data) {
                    var ele = $("[name=" + (preID + p) + "]", mainform);
                    //针对复选框和单选框 处理
                    if (ele.is(":checkbox,:radio")) {
                        ele[0].checked = data[p] ? true : false;
                    }
                    else {
                        ele.val(data[p]);
                    }
                }
                //下面是更新表单的样式
                var managers = $.ligerui.find($.ligerui.controls.Input);
                for (var i = 0, l = managers.length; i < l; i++) {
                    //改变了表单的值，需要调用这个方法来更新ligerui样式
                    var o = managers[i];
                    o.updateStyle();
                    if (managers[i] instanceof $.ligerui.controls.TextBox) {
                        o.checkValue();
                    }
                }
                if (callback) {
                    callback(data);
                }
            },
            error: function (message) {
                LG.showError('数据加载失败!<BR>错误信息：' + message);
            }
        }, options);
        LG.ajax(p);
    };

    //带验证、带loading的提交
    LG.submitForm = function (mainform, success, error) {
        if (!mainform) {
            mainform = $("form:first");
        }
        if (mainform.valid()) {
            mainform.ajaxSubmit({
                dataType: 'json',
                success: success,
                beforeSubmit: function (formData, jqForm, options) {
                    //针对复选框和单选框 处理
                    $(":checkbox,:radio", jqForm).each(function () {
                        if (!existInFormData(formData, this.name)) {
                            formData.push({name: this.name, type: this.type, value: this.checked});
                        }
                    });
                    for (var i = 0, l = formData.length; i < l; i++) {
                        var o = formData[i];
                        if (o.type == "checkbox" || o.type == "radio") {
                            o.value = $("[name=" + o.name + "]", jqForm)[0].checked ? "true" : "false";
                        }
                    }
                },
                beforeSend: function (a, b, c) {
                    LG.showLoading('正在保存数据中...');

                },
                complete: function () {
                    LG.hideLoading();
                },
                error: function (result) {
                    if (console) {
                        console.log(arguments);
                    }
                    LG.tip('发现系统错误 <BR>错误码：' + result.status);
                }
            });
        }
        else {
            LG.showInvalid();
        }
        function existInFormData(formData, name) {
            for (var i = 0, l = formData.length; i < l; i++) {
                var o = formData[i];
                if (o.name == name) {
                    return true;
                }
            }
            return false;
        }
    };

    //提示 验证错误信息
    LG.showInvalid = function (validator) {
        validator = validator || LG.validator;
        if (!validator) {
            return;
        }
        var message = '<div class="invalid">存在' + validator.errorList.length + '个字段验证不通过，请检查!</div>';
        //top.LG.tip(message);
        $.ligerDialog.error(message);
    };

    //表单验证
    LG.validate = function (form, options) {
        if (typeof (form) == "string") {
            form = $(form);
        }
        else if (typeof (form) == "object" && form.NodeType == 1) {
            form = $(form);
        }

        options = $.extend({
            errorPlacement: function (lable, element) {
                if (!element.attr("id")) {
                    element.attr("id", new Date().getTime());
                }
                if (element.hasClass("l-textarea")) {
                    element.addClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field")) {
                    element.parent().addClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
                $(element).attr("title", lable.html()).ligerTip({
                    distanceX: 5,
                    distanceY: -3,
                    auto: true
                });
            },
            success: function (lable) {
                if (!lable.attr("for")) {
                    return;
                }
                var element = $("#" + lable.attr("for"));

                if (element.hasClass("l-textarea")) {
                    element.removeClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field")) {
                    element.parent().removeClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
            }
        }, options || {});
        LG.validator = form.validate(options);
        return LG.validator;
    };

    LG.loadToolbar = function (grid, toolbarBtnItemClick) {
        var alias = LG.getPageAlias();

        $.ajax({
            url: adminPath + "buttons",
            data: {
                alias: alias
            },
            dataType: 'json',
            type: 'get',
            converters: {
                "text json": JSON2.parse
            },
            success: function (data) {
                if (!grid.toolbarManager) {
                    return;
                }
                if (!data || !data.length) {
                    return;
                }
                var items = [];
                for (var i = 0, l = data.length; i < l; ++i) {
                    var o = data[i];
                    items[items.length] = {
                        click: toolbarBtnItemClick,
                        text: o.name,
                        img: basePath + iconPath + o.icon,
                        id: o.alias
                    };
                    items[items.length] = {line: true};
                }
                grid.toolbarManager.set('items', items);
            },
            error: function () {
                console.log(arguments);
            }
        });
    };

    //关闭Tab项,如果tabid不指定，那么关闭当前显示的
    LG.closeCurrentTab = function (tabid) {
        if (!tabid) {
            tabid = $("#framecenter > .l-tab-content > .l-tab-content-item:visible").attr("tabid");
        }
        if (tab) {
            tab.removeTabItem(tabid);
        }
    };

    //关闭Tab项并且刷新父窗口
    LG.closeAndReloadParent = function (tabid, parentMenuNo) {
        LG.closeCurrentTab(tabid);
        var menuitem = $("#mainmenu ul.menulist li[menuno=" + parentMenuNo + "]");
        var parentTabid = menuitem.attr("tabid");
        var iframe = window.frames[parentTabid];
        if (tab) {
            tab.selectTabItem(parentTabid);
        }
        if (iframe && iframe.f_reload) {
            iframe.f_reload();
        }
        else if (tab) {
            tab.reload(parentTabid);
        }
    };

    //覆盖页面grid的loading效果
    LG.overrideGridLoading = function () {
        $.extend($.ligerDefaults.Grid, {
            onloading: function () {
                LG.showLoading('正在加载表格数据中...');
            },
            onloaded: function () {
                LG.hideLoading();
            }
        });
    };

    //根据字段权限调整 页面配置
    LG.adujestConfig = function (config, forbidFields) {
        if (config.Form && config.Form.fields) {
            for (var i = config.Form.fields.length - 1; i >= 0; i--) {
                var field = config.Form.fields[i];
                if ($.inArray(field.name, forbidFields) != -1) {
                    config.Form.fields.splice(i, 1);
                }
            }
        }
        if (config.Grid && config.Grid.columns) {
            for (var i = config.Grid.columns.length - 1; i >= 0; i--) {
                var column = config.Grid.columns[i];
                if ($.inArray(column.name, forbidFields) != -1) {
                    config.Grid.columns.splice(i, 1);
                }
            }
        }
        if (config.Search && config.Search.fields) {
            for (var i = config.Search.fields.length - 1; i >= 0; i--) {
                var field = config.Search.fields[i];
                if ($.inArray(field.name, forbidFields) != -1) {
                    config.Search.fields.splice(i, 1);
                }
            }
        }
    };

    //查找是否存在某一个按钮
    LG.findToolbarItem = function (grid, itemID) {
        if (!grid.toolbarManager) {
            return null;
        }
        if (!grid.toolbarManager.options.items) {
            return null;
        }
        var items = grid.toolbarManager.options.items;
        for (var i = 0, l = items.length; i < l; i++) {
            if (items[i].id == itemID) {
                return items[i];
            }
        }
        return null;
    };

    //设置grid的双击事件(带权限控制)
    LG.setGridDoubleClick = function (grid, btnID, btnItemClick) {
        btnItemClick = btnItemClick || toolbarBtnItemClick;
        if (!btnItemClick) {
            return;
        }
        grid.bind('dblClickRow', function (rowdata) {
            var item = LG.findToolbarItem(grid, btnID);
            if (!item) {
                return;
            }
            grid.select(rowdata);
            btnItemClick(item);
        });
    };

    LG.confirm = function (msg, func) {
        var args = [];
        for (var i = 2, len = arguments.length; i < len; i++) {
            args.push(arguments[i]);
        }

        $.ligerDialog.confirm(msg, function (confirm) {
            if (confirm) {
                func.apply(this, args);
            }
        });
    };

    //打开新标签 不成功 则打开新窗口
    LG.addTab = function (title, url) {
        if (typeof top.f_addTab != 'undefined') {
            top.f_addTab(null, title, url);
        }
        else {
            window.open(url);
        }
    };

    //填充表单数据
    LG.loadDataForm = function (mainform, data, options) {
        options = options || {};
        if (!mainform) {
            mainform = $("form:first");
        }

        var preID = options.preID || "";
        //根据返回的属性名，找到相应ID的表单元素，并赋值
        for (var p in data) {
            var ele = $("[name=" + (preID + p) + "]", mainform);
            //针对复选框和单选框 处理
            if (ele.is(":checkbox,:radio")) {
                ele[0].checked = data[p] ? true : false;
            }
            else {
                ele.val(data[p]);
            }
        }
        //下面是更新表单的样式
        var managers = $.ligerui.find($.ligerui.controls.Input);
        for (var i = 0, l = managers.length; i < l; ++i) {
            //改变了表单的值，需要调用这个方法来更新ligerui样式
            var o = managers[i];
            o.updateStyle();
            if (managers[i] instanceof $.ligerui.controls.TextBox) {
                o.checkValue();
            }
        }
    };

    LG.Defaults.submit = {
        append: ""
    };

    //带验证、带loading的提交
    LG.submit = function (options) {
        options = $.extend({}, options, LG.Defaults.submit);
        var form = options.form;
        if (!form) {
            form = $("form:first");
        }

        $.metadata.setType("attr", "validate");
        LG.validate(form, {debug: true});

        if (!form.valid()) {
            LG.showInvalid();
            return;
        }

        var data = {};
        var len = options.prefix ? options.prefix.length : 0;
        form.find("input,textarea").each(function (index, obj) {
            var prefixName = obj.name;
            if (!prefixName) {
                return;
            }
            var name = prefixName.substring(len);
            var value = obj.value;

            //将下划线替换为点
            if (name.indexOf("_") != -1) {
                name = name.replace("_", ".");
            }

            if ($.inArray(name, options.ignore) > -1) {
                return;
            }
            data[options.append + name] = value;
        });

        if (options.before) {
            options.before(data);
        }

        $.ajax({
            url: options.url,
            data: data,
            dataType: 'json',
            type: 'post',
            converters: {
                "text json": JSON2.parse
            },
            success: options.success,
            error: function () {
                console.log(arguments);
            }
        });

    };

    var dict = {};
    LG.getDict = function (type) {
        if (!type) {
            return [];
        }

        type = type.toLowerCase();

        var arr = dict[type];
        if (!arr) {
            arr = [];
            dict[type] = arr;
            map = window.dictMap[type];

            $.each(map, function (key, text) {
                arr.push({id: key, text: text});
            });
        }

        return arr;
    };

    //关闭当前窗口或当前TAB
    LG.closeTab = function () {
        if (!(window === window.parent)) {
            window.parent.LG.closeCurrentTab(null);
        }
        else {
            window.close();
        }
    };

    function getQueryStringByName(name) {
        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 1) {
            return "";
        }
        return result[1];
    }

})(jQuery);
