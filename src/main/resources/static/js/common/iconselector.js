/*
 文件说明：
 icon选取

 接口方法：
 1，打开窗口方法：f_openIconsWin
 2，保存下拉框ligerui对象：currentComboBox

 例子：
 可以这样使用(选择ICON完了以后，会把icon src保存到下拉框的inputText和valueField)：
 onBeforeOpen: function ()
 {
 currentComboBox = this;
 f_openIconsWin();
 return false;
 }

 */

//图标
$(function () {
    var jiconlist, winicons;

    jiconlist = $("body > .iconlist:first");
    if (!jiconlist.length) jiconlist = $('<ul class="iconlist"></ul>').appendTo('body');

    function f_openIconsWin(callback) {
        if (winicons) {
            winicons.show();
            return;
        }
        winicons = $.ligerDialog.open({
            title: '选取图标',
            target: jiconlist,
            width: 470, height: 280, modal: true
        });

        if (!jiconlist.attr("loaded")) {
            $.get(adminPath + 'menus/icons', {}, function (icons) {
                $.each(icons, function (index, icon) {
                    var src = basePath + 'static/icons/32X32/' + icon;
                    jiconlist.append("<li><img src='" + src + "' raw='" + icon + "'/></li>");
                });
                jiconlist.attr("loaded", true);

                $(".iconlist li").live('mouseover', function () {
                    $(this).addClass("over");
                }).live('mouseout', function () {
                    $(this).removeClass("over");
                }).live('click', function () {
                    if (!winicons) return;
                    var src = $("img", this).attr("raw");
                    callback.call(this, src);
                    winicons.hide();
                });
            }, 'json');

        }
    }

    window.f_openIconsWin = f_openIconsWin;
});


