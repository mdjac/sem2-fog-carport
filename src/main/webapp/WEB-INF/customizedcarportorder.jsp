<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="header">
         Home
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <form action="${pageContext.request.contextPath}/fc/bmiresult" method="post">
            <div class="row">
                <div class="col-lg-2"></div>
                <div class="col-lg-8">
                    <div class="text-center">
                        <img src="${pageContext.request.contextPath}/images/carport-flatroof.PNG" class="img-fluid"
                             alt="Responsive image">
                        <img src="${pageContext.request.contextPath}/images/carport-nonflatroot.PNG" class="img-fluid"
                             alt="Responsive image">
                    </div>
                    <div class="row">
                        <c:forEach var="option" items="${applicationScope.options}">
                            <div class="col-lg-6">
                                <label for="${option.value.id}">${option.value.name}</label>
                                <select class="form-select mt-2" aria-label="${option.value.id}" id="${option.value.id}"
                                        name="${option.value.id}" required>
                                    <option value="" selected disabled>Vælg her</option>
                                        <%--Nested foreach--%>
                                    <c:forEach var="optionValue" items="${option.value.values}">
                                        <option value="${optionValue.key}">${optionValue.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary mt-3">Send forespørgsel</button>
                    </div>
                </div>
                <div class="col-lg-2"></div>
            </div>
        </form>
    </jsp:body>
</t:genericpage>