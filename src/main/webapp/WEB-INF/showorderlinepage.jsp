<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="header">
         Stykliste
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Materiale</th>
                <th scope="col">Længde</th>
                <th scope="col">Antal</th>
                <th scope="col">Enhed</th>
                <th scope="col">Beskrivelse</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orderline" items="${requestScope.BOM}">
                <tr>
                    <th scope="row">${orderline.key}</th>
                    <td>${orderline.value.material.toString()}</td>
                    <td>${orderline.value.material.length} cm</td>
                    <td>${orderline.value.quantity}</td>
                    <td>${orderline.value.unit}</td>
                    <td>${orderline.value.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:genericpage>