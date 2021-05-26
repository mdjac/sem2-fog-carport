<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Administrator side
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-3"></div>
            <div class="col-lg-6">
            <div class="text-center">
                <h1>Velkommen ${sessionScope.user.name} </h1>
            </div>
                <div>
                    <c:if test="${requestScope.status != null }">
                        <h5 class="text-center text-success mb-4">${requestScope.status}</h5>
                    </c:if>
                    <c:if test="${requestScope.error != null }">
                        <h5 class="text-center text-danger mb-4">${requestScope.error}</h5>
                    </c:if>
                </div>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">
                            <h4>Admin menu</h4>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="align-middle">
                        <form class="" action="${pageContext.request.contextPath}/fc/addstandardcarportpage">
                        <td>
                            Tilføj standard carport
                        </td>
                        <td>
                            <button type="submit" class="btn btn-primary btn-sm">Gå til</button>
                        </td>
                        </form>
                    </tr>
                    <tr class="align-middle">
                        <form class="" action="${pageContext.request.contextPath}/fc/adminchangeorderstatuspage">
                            <td>
                                Ændre ordre status
                            </td>
                            <td>
                                <button type="submit" class="btn btn-primary btn-sm">Gå til</button>
                            </td>
                        </form>
                    </tr>
                    <tr class="align-middle">
                        <form class="" action="${pageContext.request.contextPath}/fc/reloadStaticValues">
                            <td>
                                Opdater globale værdier og materialer fra databasen
                            </td>
                            <td>
                                <button type="submit" class="btn btn-primary btn-sm">Opdater</button>
                            </td>
                        </form>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-lg-3"></div>
        </div>
    </jsp:body>
</t:genericpage>
