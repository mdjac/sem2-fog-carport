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
        <form action="${pageContext.request.contextPath}/fc/editanddeleteorderlinecommand" method="POST">
            <input type="hidden" name="orderid" value="${requestScope.BOM.firstEntry().value.ordersID}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Materiale (LxBxH)</th>
                    <th scope="col">Antal</th>
                    <th scope="col">Enhed</th>
                    <th scope="col">Beskrivelse</th>
                    <th scope="col">Marker for at slette</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderline" items="${requestScope.BOM}">
                    <tr>
                        <th scope="row">${orderline.key}</th>
                        <td>
                            <select class="form-select" name="materialVariantId[]">
                                <option value="${orderline.value.material.variantId}"
                                        selected>${orderline.value.material.toString()}</option>
                                <c:forEach var="buildingmaterialvariantmap" items="${applicationScope.materialVariantMap}">
                                    <c:if test="${orderline.value.material.materialsId == buildingmaterialvariantmap.key}">
                                        <c:set var="variantsbymaterialId" value="${buildingmaterialvariantmap.value}"/>
                                    </c:if>
                                    <c:forEach var="variants" items="${variantsbymaterialId}">
                                        <option value="${variants.key}">${variants.value.toString()}</option>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <div class="form-group w-25">
                                <input type="number" class="form-control" name="quantity" min="0"
                                       value="${orderline.value.quantity}">
                            </div>
                        </td>
                        <td>${orderline.value.unit}</td>
                        <td>
                            <div class="form-group">
                                <input type="text" class="form-control" name="description"
                                       value="${orderline.value.description}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input class="form-check-input" type="checkbox" value="${orderline.key}"
                                       name="deleteIds[]">
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center mt-2">
                <input class="btn btn-danger mt-2" type="reset" value="Fortryd">
                <input class="btn btn-primary mt-2" type="submit" value="Gem">
            </div>
        </form>
    </jsp:body>
</t:genericpage>