<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户登录</title>
    <g:include view="/admin/base.gsp"/>

    <asset:stylesheet src="static/css/admin/login/login.css"/>
    <asset:javascript src="js/admin/user/login.js"/>
    <asset:javascript src="js/common/security.js"/>

    <script type="text/javascript">
        var modulus = '${application.modulus}';
        var exponent = '${application.exponent}';
    </script>
</head>

<body style="padding:10px">
<div id="login">
    <div id="loginlogo"></div>

    <div id="loginpanel">
        <div class="panel-h"></div>

        <div class="panel-c">
            <div class="panel-c-l">
                %{--<form action="${webRequest.baseUrl}/admin/user/j_spring_security_check"
                      method="post">--}%
                <table cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td align="left" colspan="2">
                            <h3>登录</h3>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">账号：</td>
                        <td align="left">
                            <input type="text" name="username" id="username"
                                   class="login-text"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">密码：</td>
                        <td align="left">
                            <input type="password" name="password" id="password"
                                   class="login-text"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">记住我：</td>
                        <td align="left">
                            <input type="checkbox" name="${rememberMeParameter}"
                                   id="${rememberMeParameter}"
                                   class="login-text"
                                   <g:if test='${hasCookie}'>checked="checked"</g:if>/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input type="submit" id="btnLogin" value="登陆" class="login-btn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                %{--</form>--}%

            </div>

            <div class="panel-c-r">
                <p>${grailsApplication.config.my.project.name}</p>

            </div>
        </div>

        <div class="panel-f"></div>
    </div>

    <div id="logincopyright">Copyright © 2012 LigerUI</div>
</div>
</body>
</html>

