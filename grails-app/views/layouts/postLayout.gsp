<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
    <title>${grailsApplication.config.my.project.domain}</title>

    <!-- meta -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- css -->
    <link rel="stylesheet" href="${basePath}static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}static/css/front/index/css/ionicons.min.css">
    <link rel="stylesheet" href="${basePath}static/css/front/index/css/pace.css">
    <link rel="stylesheet" href="${basePath}static/css/front/index/css/custom.css">

    <!-- js -->
    <script src="${basePath}static/jQuery/jquery-2.1.3.min.js"></script>
    <script src="${basePath}static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${basePath}static/js/front/index/pace.min.js"></script>
    <script src="${basePath}static/js/front/index/modernizr.custom.js"></script>

</head>


<body>
<div class="container">
    <header id="site-header">
        <div class="row">
            <div class="col-md-4 col-sm-5 col-xs-8">
                <div class="logo">
                    <h1><a href="${basePath}"><b>${grailsApplication.config.my.project.domain}</b>
                    </a></h1>
                </div>
            </div>
            <!-- col-md-4 -->
            <div class="col-md-8 col-sm-7 col-xs-4">
                <nav class="main-nav" role="navigation">
                    <div class="navbar-header">
                        <button type="button" id="trigger-overlay" class="navbar-toggle">
                            <span class="ion-navicon"></span>
                        </button>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="cl-effect-11"><a href="${basePath}">首页</a></li>
                            <li class="cl-effect-11"><a href="${basePath}">文章</a></li>
                            <li class="cl-effect-11"><a href="${basePath}about">关于</a></li>
                            <li class="cl-effect-11"><a href="${basePath}contact">联系</a></li>
                        </ul>
                    </div>
                    <!-- /.navbar-collapse -->
                </nav>

                <div id="header-search-box">
                    <a id="search-menu" href="#"><span id="search-icon"
                                                       class="ion-ios-search-strong"></span></a>

                    <div id="search-form" class="search-form">
                        <form role="search" method="get" id="searchform" action="#">
                            <input type="search" placeholder="Search" required>
                            <button type="submit"><span class="ion-ios-search-strong"></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <!-- col-md-8 -->
        </div>
    </header>
</div>


<div class="content-body">
    <g:layoutBody/>
</div>

<footer id="site-footer">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <p class="copyright">&copy; 2016 ${grailsApplication.config.my.project.domain}
                &nbsp;本站由<a href="http://www.vultr.com/?ref=6831370">vultr</a>提供
                    <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
                    document.write(unescape("%3Cspan id='cnzz_stat_icon_1256197951'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1256197951%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script>
                </p>
            </div>
        </div>
    </div>
</footer>

<!-- Mobile Menu -->
<div class="overlay overlay-hugeinc">
    <button type="button" class="overlay-close"><span class="ion-ios-close-empty"></span></button>
    <nav>
        <ul>
            <li><a href="${basePath}">首页</a></li>
            <li><a href="${basePath}">文章</a></li>
            <li><a href="${basePath}about">关于</a></li>
            <li><a href="${basePath}contact">联系</a></li>
        </ul>
    </nav>
</div>

<script src="${basePath}static/js/front/index/script.js"></script>
<script src="${basePath}static/syntaxhighlighter/scripts/brush.js" type="text/javascript"></script>
<link href="${basePath}static/syntaxhighlighter/styles/shCore.css" type="text/css"
      rel="stylesheet"/>
<link href="${basePath}static/syntaxhighlighter/styles/shThemeDefault.css" type="text/css"
      rel="stylesheet"/>
<script type="text/javascript">
    $(document).ready(function () {
        SyntaxHighlighter.config.clipboardSwf = "${basePath}static/syntaxhighlighter/scripts/clipboard.swf";
        SyntaxHighlighter.all();
    });
</script>
</body>
</html>
