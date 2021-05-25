<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Kvittering
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>

        <div class="text-center">
            <p>Kære ${sessionScope.user.name}</p>

            <p>Tusind tak for din forespørgsel. Dit ordre nummer er: ${requestScope.order.id}</p>
            <p>Vi sender dig en email så snart en af vores medarbejdere har kigget din forspørgsel igennem.</p>

            <p>Her kan du se en genereret tegning af din forespørgsel.</p>
        </div>
    </jsp:body>
</t:genericpage>