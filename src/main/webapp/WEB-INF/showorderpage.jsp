<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="header">
         Order ID: ${requestScope.order.id}
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>

        <div class="container align-items-center justify-content-center">
            <div>
                <p>Ordre pris: ${requestScope.order.totalPrice} DKK.</p>
                <p>Carport højde: ${requestScope.order.carport.carportHeight} cm.</p>
                <p>Carport bredde: ${requestScope.order.carport.carportWidth} cm.</p>
                <p>Carport længde: ${requestScope.order.carport.carportLength} cm.</p>
                <p>Carport beklædning: ${requestScope.order.carport.carportMaterial.materialName} cm.</p>
                <p>Carport tagtype: ${requestScope.order.carport.roofType}</p>
                <p>Carport tag materiale: ${requestScope.order.carport.roofMaterial.materialName}</p>
                <c:if test="${requestScope.order.carport.roofType.equals('Tag med rejsning')}">
                    <p>Carport tag hældning: ${requestScope.order.carport.roofTilt}</p>
                </c:if>
                <c:if test="${requestScope.order.carport.shedLength > 0}">
                    <p>Carport skur længde: ${requestScope.order.carport.shedLength}</p>
                    <p>Carport skur bredde: ${requestScope.order.carport.shedWidth}</p>
                    <p>Carport skur beklædning: ${requestScope.order.carport.shedMaterial.materialName}</p>
                </c:if>

                <!-- skal slettes !!!!!!!!!!!!!!!!!!!!!!!!!!!-->
                <a href="${pageContext.request.contextPath}/fc/showsvg">SVG TEGNING</a>


                <c:if test="${requestScope.order.status.equals(applicationScope.status.get(1))}">
                    <div class="mt-4">
                        <form action="${pageContext.request.contextPath}/fc/changeorderstatuscommand" method="POST">
                            <input type="hidden" name="orderid" value="${requestScope.order.id}">
                            <button type="submit" class="btn btn-success changed"
                                    value="${applicationScope.status.get(2).toString()}" name="status">Accepter tilbud
                            </button>
                        </form>
                    </div>
                </c:if>

            </div>

        </div>
    </jsp:body>
</t:genericpage>