<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <jsp:invoke fragment="header"/>
    </title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
    <meta name="theme-color" content="#7952b3">
</head>
<body>
<!--
    This header is inspired by this bootstrap
    example: https://getbootstrap.com/docs/5.0/examples/pricing/
-->

<div>
    <img class="img-fluid" src="${pageContext.request.contextPath}/images/forside-carport.jpg">
</div>
    <div class="alignMiddleOnPage container container-fluid py-0 px-0">
        <div class="navBackGround row ">
            <nav class="my-2 my-md-0 me-md-3">
                <img class="img-fluid image-responsive" src="${pageContext.request.contextPath}/images/logo-2.png">
                <div class="row">
                <div class="topNavigation col-xs-12">

                    <c:if test="${sessionScope.user != null }">
                        ${sessionScope.user.email}
                    </c:if>

                    <c:set var="thisPage" value="${pageContext.request.servletPath}"/>
                    <c:set var="isNotLoginPage" value="${!fn:endsWith(thisPage,'loginpage.jsp')}"/>
                    <c:set var="isNotRegisterPage" value="${!fn:endsWith(thisPage,'registerpage.jsp')}"/>

                    <c:if test="${isNotLoginPage && isNotRegisterPage}">
                        <c:if test="${sessionScope.user != null }">
                            <a
                               href="${pageContext.request.contextPath}/fc/logoutcommand">Logout</a>
                        </c:if>
                        <c:if test="${sessionScope.user == null }">
                            <a
                               href="${pageContext.request.contextPath}/fc/loginpage">Login</a>
                        </c:if>
                        <c:if test="${sessionScope.user == null }">
                            <a
                                    href="${pageContext.request.contextPath}/fc/registerpage">Sign up</a>
                        </c:if>
                    </c:if>
                </div>
                </div>
                <div class="row">
                <div class="bottomNavigation col-md-6">
                    <c:if test="${addHomeLink == null }">
                        <a class="three p-2 text-light" href="<%=request.getContextPath()%>">Home</a>
                    </c:if>
                    
                </div>
                </div>
            </nav>
        </div>
    </div>



<header class="d-flex flex-column flex-md-row align-items-center p-3 pb-0 px-md-4 mb-4 bg-white border-bottom shadow-sm">
    <div class="h5 mb-0 mt-3 me-md-auto fw-normal">
        <p style="font-size: larger; margin-left: 250px">
            <jsp:invoke fragment="header"/>
        </p>
    </div>


</header>

<div id="body" class="container" style="min-height: 20vh;">
    <jsp:doBody/>
</div>

<!-- Footer -->
<div class="container">
    <br>
    <hr>
    <br>
    <jsp:invoke fragment="footer"/>
</div>

</body>
</html>