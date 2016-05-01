LG.changepassword = function () {
    $(document).bind('keydown.changepassword', function (e) {
        if (e.keyCode == 13) {
            doChangePassword();
        }
    });

    if (!window.changePasswordWin) {
        var changePasswordPanle = $("<form></form>");
        changePasswordPanle.ligerForm({
            prefixID: "",
            fields: [
                {
                    display: '旧密码',
                    name: 'oldPassword',
                    type: 'password',
                    validate: {maxlength: 50, required: true, messages: {required: '请输入密码'}}
                },
                {
                    display: '新密码',
                    name: 'newPassword',
                    type: 'password',
                    validate: {maxlength: 50, required: true, messages: {required: '请输入密码'}}
                },
                {
                    display: '确认密码',
                    name: 'newPassword2',
                    type: 'password',
                    validate: {
                        maxlength: 50,
                        required: true,
                        equalTo: 'input[name=newPassword]',
                        messages: {
                            required: '请输入密码',
                            equalTo: '两次密码输入不一致'
                        }
                    }
                }
            ]
        });

        //验证
        jQuery.metadata.setType("attr", "validate");
        LG.validate(changePasswordPanle);

        window.changePasswordWin = $.ligerDialog.open({
            width: 400,
            height: 190, top: 200,
            isResize: true,
            title: '用户修改密码',
            target: changePasswordPanle,
            buttons: [
                {
                    text: '确定', onclick: function () {
                    doChangePassword();
                }
                },
                {
                    text: '取消', onclick: function () {
                    window.changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                }
                }
            ]
        });
    }
    else {
        window.changePasswordWin.show();
    }

    function doChangePassword() {
        var oldPassword = $("input[name=oldPassword]").val();
        var LoginPassword = $("input[name=newPassword]").val();
        if (changePasswordPanle.valid()) {

            var key = RSAUtils.getKeyPair(exponent, '', modulus);
            var enOldPassword = RSAUtils.encryptedString(key, oldPassword);
            var enNewPassword = RSAUtils.encryptedString(key, LoginPassword);

            $.ajax({
                url: adminPath + "password/reset",
                data: {
                    oldPassword: enOldPassword,
                    newPassword: enNewPassword
                },
                dataType: 'json',
                type: 'post',
                success: function (result) {
                    var msg = result.message;
                    if (result.isError) {
                        LG.showError(msg);
                    }
                    else {
                        LG.showSuccess(msg, function () {
                            grid.loadData();
                        });
                    }

                },
                error: function (result) {
                    LG.showError(result.responseJSON.errorMessage);
                }
            });

        }
    }

};