<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String basePath = "/";
%>
<html>

<head>
    <title>${post.title}</title>
    <meta name="description" content="${post.excerpt}">
</head>


<body>
<div class="container">
    <div class="row">
        <main class="col-md-12">
            <article class="post post-1">
                <header class="entry-header">
                    <h1 class="entry-title">${post.title}</h1>

                    <div class="entry-meta">
                        <span class="post-category">
                            <a href="${basePath}categorys/${post?.category?.alias}">${post?.category?.name}</a>
                        </span>

                        <span class="post-date">
                            <g:set var="year"><g:formatDate date="${post.dateCreated}"
                                                            format="yyyy"/></g:set>

                            <g:set var="month"><g:formatDate date="${post.dateCreated}"
                                                             format="MM"/></g:set>
                            <g:set var="dateStr"><g:formatDate date="${post.dateCreated}"
                                                               format="yyyy年MM月dd日"/></g:set>
                            <a href="${basePath}post/date/${year}/${month}">
                                <time class="entry-date"
                                      datetime="${dateStr}">${dateStr}</time>
                            </a>
                        </span>

                        %{--<span class="post-author">--}%
                            %{--<a href="${basePath}post/user/${post.category.id}">${post.category.name}</a>--}%
                        %{--</span>--}%

                        <span class="comments-link">
                            <a href="#ds-reset" class="ds-thread-count" data-thread-key="${post.id}"
                               data-count-type="comments"></a>
                        </span>
                    </div>
                </header>

                <div class="entry-content clearfix">
                    ${raw(post.content)}
                </div>
            </article>
        </main>

    </div>
</div>
</body>
</html>