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
    var icons = [
        'Time Machine.gif', 'clock.gif', 'edit.gif', 'lightbulb.gif', 'pencil.gif', 'searchtool.gif', 'add.gif', 'coffee.gif', 'email.gif', 'limited_edition.gif', 'phone.gif', 'settings.gif', 'address.gif', 'collaboration.gif', 'featured.gif', 'link.gif', 'photograph.gif', 'setup.gif', 'administrative_docs.gif', 'comment.gif', 'feed(1).gif', 'lock.gif', 'photography.gif', 'shipping.gif', 'advertising.gif', 'communication.gif', 'feed.gif', 'login.gif', 'plus.gif', 'showreel.gif', 'archives.gif', 'config.gif', 'finished_work.gif', 'logout.gif', 'premium.gif', 'sign_in.gif', 'attibutes.gif', 'consulting.gif', 'flag.gif', 'mailbox.gif', 'prev.gif', 'sign_out.gif', 'back.gif', 'contact.gif', 'folder.gif', 'member.gif', 'print.gif', 'sign_up.gif', 'bank.gif', 'cost.gif', 'free_for_job.gif', 'memeber.gif', 'process.gif', 'sitemap.gif', 'basket.gif', 'credit.gif', 'freelance.gif', 'milestone.gif', 'product.gif', 'special_offer.gif', 'bestseller.gif', 'credit_card.gif', 'full_time.gif', 'modify.gif', 'product_169.gif', 'star.gif', 'billing.gif', 'current_work.gif', 'future_projects.gif', 'msn.gif', 'product_193.gif', 'statistics.gif', 'bluebook.gif', 'customers.gif', 'graphic_design.gif', 'my_account.gif', 'product_design.gif', 'suppliers.gif', 'book.gif', 'cut.gif', 'graywarn.gif', 'myaccount.gif', 'project.gif', 'tag.gif', 'book2.gif', 'cv.gif', 'greenwarn.gif', 'networking.gif', 'publish.gif', 'ticket.gif', 'bookmark.gif', 'database.gif', 'heart.gif', 'ok.gif', 'qq.gif', 'total.gif', 'bookpen.gif', 'date.gif', 'help.gif', 'order.gif', 'refresh.gif', 'true.gif', 'brainstorming.gif', 'delete.gif', 'hire_me.gif', 'order_159.gif', 'report.gif', 'twitter(1).gif', 'business_contact.gif', 'delicious (2).gif', 'home.gif', 'order_192.gif', 'right.gif', 'up.gif', 'busy.gif', 'discuss.gif', 'illustration.gif', 'outbox.gif', 'role.gif', 'upcoming_work.gif', 'calendar.gif', 'document_library.gif', 'import.png', 'pager.gif', 'save-disabled.gif', 'user.gif', 'candle.gif', 'donate.gif', 'information.gif', 'payment_card.gif', 'save.gif', 'view.gif', 'category.gif', 'down.gif', 'invoice.gif', 'paypal.gif', 'search.gif', 'world.gif', 'check.gif', 'drawings.gif', 'issue.gif', 'pen.gif', 'search2.gif', 'zoom.gif'
    ];

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
            $.each(icons, function (index, icon) {
                var src = assetPath + 'icons/32X32/' + icon;
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

        }
    }

    window.f_openIconsWin = f_openIconsWin;
});


