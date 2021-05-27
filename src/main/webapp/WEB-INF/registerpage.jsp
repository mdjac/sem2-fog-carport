<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Registrer dig som ny bruger
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
        <div style="margin-top: 5em;">
            <form name="login" action="${pageContext.request.contextPath}/fc/registercommand" method="POST">
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="email">Email</label>
                    <div class="col-sm-4">
                        <input id="email" class="form-control" type="email" name="email" value="${param.email}" placeholder="Indtast din email" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="password1">Password</label>
                    <div class="col-sm-4">
                        <input id="password1" class="form-control" type="password" name="password1"  value="${param.password1}"  placeholder="Indtast dit password" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="password2">Password</label>
                    <div class="col-sm-4">
                        <input id="password2" class="form-control" type="password" name="password2" value="${param.password2}"  placeholder="Gentag dit password" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="name">Navn</label>
                    <div class="col-sm-4">
                        <input id="name" class="form-control" type="text" name="name" placeholder="Indtast dit fulde navn" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="address">Adresse</label>
                    <div class="col-sm-4">
                        <input id="address" class="form-control" type="text" name="address" placeholder="Indtast din adresse" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="zip">Postnummer</label>
                    <div class="col-sm-4">
                        <input id="zip" class="form-control" type="number" name="zip" placeholder="Indtast dit postnummer" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="city">By</label>
                    <div class="col-sm-4">
                        <input id="city" class="form-control" type="text" name="city" placeholder="Indtast din hjemby" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-1 col-form-label" for="telephone">Telefon</label>
                    <div class="col-sm-4">
                        <input id="telephone" class="form-control" type="text" name="telephone" placeholder="Indtast dit telefonnummer" required>
                    </div>
                </div>
                <input class="btn btn-primary" type="submit" type="submit" value="Opret bruger">
            </form>

            <c:if test="${requestScope.error != null }">
                <p style="color:red">
                        ${requestScope.error}
                </p>
            </c:if>
        </div>
    </jsp:body>
</t:genericpage>


