<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Login page
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div style="margin-top: 5em; background: white;" class="container">
            <form name="login" action="${pageContext.request.contextPath}/fc/logincommand"  method="POST">
                <h2>Log Ind</h2>
                <div class="row mb-3 mt-3">
                    <label class="col-sm-1 col-form-label" for="email">Email</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="text" name="email" placeholder="Skriv din e-mail">
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="password">Password</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="password" name="password" placeholder="Skriv din adgangskode">
                    </div>
                </div>
                <c:if test="${requestScope.error != null }">
                    <p style="color:red">
                            ${requestScope.error}
                    </p>
                </c:if>

                <c:if test="${not empty param.msg}">
                    <p style="font-size: large">${param.msg}</p>
                </c:if>
                <button class="btn btn-primary float-md-right" type="submit" value="Login">Sign in</button>
            </form>


        </div>
    </jsp:body>
</t:genericpage>
