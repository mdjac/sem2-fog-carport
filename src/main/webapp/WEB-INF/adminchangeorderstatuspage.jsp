<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Ændre ordre status side
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
        <div class="container align-items-center justify-content-center">
            <div>
                <form action="${pageContext.request.contextPath}/fc/changeorderstatuscommand" method="POST">
                    <input type="number" class="form-control" id="orderId"
                           placeholder="Indtast ordre ID" min="0" name="orderid" required>

                    <select class="form-select mt-2" id="status"
                            name="status" required>
                        <option value="" selected disabled>Vælg her</option>
                        <c:forEach var="status" items="${applicationScope.status}">
                            <option value="${status.toString()}">${status.toString()}</option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-primary btn-sm mt-2">Gem</button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
