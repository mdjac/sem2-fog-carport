<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Admin page
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-3"></div>
            <div class="col-lg-6">
            <div class="text-center">
                <h1>Hello ${sessionScope.email} </h1>
                You are now logged in as admin.
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
                    </tbody>
                </table>
            </div>
            <div class="col-lg-3"></div>
        </div>
    </jsp:body>
</t:genericpage>
