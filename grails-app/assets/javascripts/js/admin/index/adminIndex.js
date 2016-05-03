//几个布局的对象
var layout, tab, accordion;
var tabidcounter = 0;
$(function () {

    //窗口改变时的处理函数
    function f_heightChanged(options) {
        if (tab) {
            tab.addHeight(options.diff);
        }
        if (accordion && options.middleHeight - 24 > 0) {
            accordion.setHeight(options.middleHeight - 24);
        }
    }

    //增加tab项的函数
    function f_addTab(tabid, text, url) {
        if (!tab) {
            return;
        }
        if (!tabid) {
            tabidcounter++;
            tabid = "tabid" + tabidcounter;
        }
        tab.addTabItem({tabid: tabid, text: text, url: url});
    }

    //登录
    function f_login() {
        LG.login();
    }

    //修改密码
    function f_changepassword() {
        LG.changepassword();
    }

    //布局初始化
    //layout
    layout = $("#mainbody").ligerLayout({
        height: '100%',
        heightDiff: -3,
        leftWidth: 140,
        onHeightChanged: f_heightChanged,
        minLeftWidth: 120
    });
    var bodyHeight = $(".l-layout-center:first").height();
    //Tab
    tab = $("#framecenter").ligerTab({height: bodyHeight, contextmenu: true});

    //预加载dialog的背景图片
    LG.prevDialogImage();

    var mainmenu = $("#mainmenu");

    $.get(adminPath + 'allMenus', {}, function (menus) {
        renderMenu(menus)
    }, 'json');

    function renderMenu(menus) {
        var menuTree = common.listToTree(menus, "id", "parentId");
        var sortedTree = [];
        $.each(menuTree, function (key, value) {
            sortedTree.push(value);
        });

        sortedTree.sort(function (a, b) {
            return a._sort > b._sort;
        });

        $.each(sortedTree, function (index, menu) {
            var item = $('<div title="' + menu.name + '"><ul class="menulist"></ul></div>');

            var children = menu.children;

            if (children && children.length > 0) {
                $.each(menu.children, function (j, submenu) {
                    var subitem = $('<li><img/><span></span><div class="menuitem-l"></div><div class="menuitem-r"></div></li>');
                    subitem.attr({
                        url: adminPath + submenu.url,
                        alias: submenu.alias
                    });
                    $("img", subitem).attr("src", assetPath + 'icons/32X32/' + submenu.icon);
                    $("span", subitem).html(submenu.name || submenu.text);

                    $("ul:first", item).append(subitem);
                });
            }
            mainmenu.append(item);
        });

        //Accordion
        accordion = $("#mainmenu").ligerAccordion({height: bodyHeight - 24, speed: null});

        $("#pageloading").hide();

        //菜单初始化
        $("ul.menulist li").live('click', function () {
            var jitem = $(this);
            var tabid = jitem.attr("tabid");
            var url = jitem.attr("url");
            if (!url) {
                return;
            }
            if (!tabid) {
                tabidcounter++;
                tabid = "tabid" + tabidcounter;
                jitem.attr("tabid", tabid);

                //给url附加menuno
                if (url.indexOf('?') > -1) {
                    url += "&";
                }
                else {
                    url += "?";
                }
                url += "alias=" + jitem.attr("alias");
                jitem.attr("url", url);
            }
            f_addTab(tabid, $("span:first", jitem).html(), url);
        }).live('mouseover', function () {
            var jitem = $(this);
            jitem.addClass("over");
        }).live('mouseout', function () {
            var jitem = $(this);
            jitem.removeClass("over");
        });

        window.f_addTab = f_addTab;
    }
});
