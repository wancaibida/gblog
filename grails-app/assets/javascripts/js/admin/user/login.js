$(function () {
    $(".login-text").focus(function () {
        $(this).addClass("login-text-focus");
    }).blur(function () {
        $(this).removeClass("login-text-focus");
    });

    $(document).keydown(function (e) {
        if (e.keyCode == 13) {
            dologin();
        }
    });

    $("#btnLogin").click(function () {
        dologin();
    });

    function dologin() {
        var username = $("#username").val();
        var password = $("#password").val();
        if (username == "") {
            alert('账号不能为空!');
            $("#username").focus();
            return;
        }
        if (password == "") {
            alert('密码不能为空!');
            $("#password").focus();
            return;
        }

        var key = RSAUtils.getKeyPair(exponent, '', modulus);
        var enPassword = RSAUtils.encryptedString(key, password);
        $.ajax({
            type: 'post',
            cache: false,
            dataType: 'json',
            url: adminPath + "login/authenticate",
            data: {
                username: username,
                password: enPassword,
                'remember-me': $("#remember-me").is(":checked")
            },
            success: function (result) {
                //if (result.error) {
                //    LG.showError(result.error, function () {
                //        $("#username").focus();
                //    });
                //}
                //else {
                location.href = basePath + "admin";
                //LG.showSuccess('登陆成功!', function () {
                //});
                //}
            },
            error: function (result) {
                alert(result.responseJSON.errorMessage);
            },
            beforeSend: function () {
                $.ligerDialog.waitting("正在登陆中,请稍后...");
                $("#btnLogin").attr("disabled", true);
            },
            complete: function () {
                $.ligerDialog.closeWaitting();
                $("#btnLogin").attr("disabled", false);
            }
        });
    }
});