<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div class="container">
    <div class="row">
        <main class="col-md-8">

            <g:each in="${posts}" var="post" status="st">
                <article class="post post-${st.intValue()}">
                    <header class="entry-header">
                        <h1 class="entry-title">
                            <a href="${basePath}posts/${post.id}">${post.title}</a>
                        </h1>

                        <div class="entry-meta">
                            <span class="post-category">
                                <a href="${basePath}categorys/${post.category.alias}">${post.category.name}</a>
                            </span>

                            <span class="post-date">
                                <a href="${basePath}post/date/<g:formatDate
                                        date="${post.dateCreated}"
                                        format="yyyy"/>/<g:formatDate date="${post.dateCreated}"
                                                                      format="MM"/>">
                                    <time class="entry-date"
                                          datetime="${post.dateCreated}">
                                        <g:formatDate date="${post.dateCreated}"
                                                      format="yyyy年MM月dd日"/>
                                    </time>
                                </a>
                            </span>

                            <span class="post-author">
                                <a href="${basePath}post/user/">admin</a>
                            </span>

                        </div>
                    </header>

                    <div class="entry-content clearfix">
                        <p>${post.excerpt}</p>

                        <div class="read-more cl-effect-14">
                            <a href="${basePath}posts/${post.id}" class="more-link">阅读全文 <span
                                    class="meta-nav">→</span></a>
                        </div>
                    </div>
                </article>
            </g:each>
        </main>

        <aside class="col-md-4">
            <div class="widget widget-recent-posts">
                <h3 class="widget-title">最新文章</h3>
                <ul>
                    <g:each in="${recentPosts}" var="post">
                        <li>
                            <a href="${basePath}posts/${post.id}">${post.title}</a>
                        </li>
                    </g:each>
                </ul>
            </div>

            <div class="widget widget-archives">
                <h3 class="widget-title">归档</h3>
                <ul>
                    <g:each in="${archives}" var="archive">
                        <li>
                            <g:set var="year"><g:formatDate date="${archive[0]}"
                                                            format="yyyy"/></g:set>

                            <g:set var="month"><g:formatDate date="${archive[0]}"
                                                             format="MM"/></g:set>
                            <a href="${basePath}posts/date/${year}/${month}">${year}年${month}月(${archive[1]})</a>
                        </li>
                    </g:each>
                </ul>
            </div>

            <div class="widget widget-category">
                <h3 class="widget-title">分类目录</h3>
                <ul>
                    <g:each in="${categorys}" var="category">
                        <li>
                            <a href="${basePath}categorys/${category.alias}">${category.name}</a>
                        </li>
                    </g:each>
                </ul>
            </div>
        </aside>

    </div>

    <div class="row">
        <div class="col-lg-8 entry">
            <nav class="text-center">
                <ul class="pagination">
                    <g:if test="${pageCount}">
                        <li>
                            <a href="?page=1" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>


                        <g:each in="${pageCount}" var="i">
                            <li>
                                <a href="?page=${i}">${i}</a>
                            </li>
                        </g:each>

                        <li>
                            <a href="?page=${pageCount}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </g:if>
                </ul>
            </nav>
        </div>
    </div>
</div>