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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">

    <meta name="theme-color" content="#7952b3">
</head>
<body>
<!--
    This header is inspired by this bootstrap
    example: https://getbootstrap.com/docs/5.0/examples/pricing/
-->
<header class="d-flex flex-column align-items-center  pb-0  mb-4 bg-white border-bottom shadow-sm">
    <%--<div class="headerPictureSizeOnScreen">
        <div>
            <img class="img-fluid " style="background-position: center center; background-repeat: no-repeat no-repeat;" src="${pageContext.request.contextPath}/images/forside-carport.jpg">
        </div>
    </div>--%>


    <div class="container container-fluid py-0 px-0" style="z-index: 20; background-color: #0B2069;height: 126px;">

        <div style="float: left;width: 126px; height: 100%">
            <img class="img-fluid image-responsive" src="${pageContext.request.contextPath}/images/logo-2.png">
        </div>

        <div style="margin-left: 130px; height:100%;">
            <nav>
                <div class="p-2 d-flex align-items-center"
                     style="height: 55px; color: white; border-bottom: 1px solid white;margin-right: 4px">


                    <c:set var="thisPage" value="${pageContext.request.servletPath}"/>
                    <c:set var="isNotLoginPage" value="${!fn:endsWith(thisPage,'loginpage.jsp')}"/>
                    <c:set var="isNotRegisterPage" value="${!fn:endsWith(thisPage,'registerpage.jsp')}"/>

                    <c:if test="${sessionScope.user != null }">
                        ${sessionScope.user.email}
                    </c:if>
                    <c:if test="${isNotLoginPage && isNotRegisterPage}">
                        <c:if test="${sessionScope.user != null }">
                            <a class="p-2"
                               href="${pageContext.request.contextPath}/fc/logoutcommand"
                               style="color: white; text-decoration: none;">Logout</a>
                        </c:if>
                        <c:if test="${sessionScope.user == null }">

                            <i class="fa fa-lock mr-3" style="font-size:20px;color:white"></i>
                            <a
                                    href="${pageContext.request.contextPath}/fc/loginpage"
                                    style="color: white; text-decoration: none; margin-left: 4px">Login
                            </a>


                        </c:if>
                    </c:if>

                </div>


                <div class="d-flex align-items-center" style="height: 71px;">


                    <c:if test="${addHomeLink == null }">
                        <a class="p-2 text-light text-decoration-none" href="<%=request.getContextPath()%>">Home</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null }">
                        <a class="p-2 text-light text-decoration-none"
                           href="${pageContext.request.contextPath}/fc/profilepage">Profile</a>
                        <a class="p-2 text-light text-decoration-none"
                           href="${pageContext.request.contextPath}/fc/showorders">Orders</a>


                        <c:if test="${sessionScope.user.role.equals('employee')}">
                            <a class="p-2 navtext" href="${pageContext.request.contextPath}/fc/employeepage">Admin</a>
                        </c:if>
                    </c:if>


                </div>

            </nav>
        </div>


    </div>


    <div class="container h5 mb-0 me-md-auto fw-normal" style="margin-top: 200px;">
        <p style="font-size: larger;">
            <jsp:invoke fragment="header"/>
        </p>
    </div>


</header>
<div class="show banner test" data-height="400"></div>
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