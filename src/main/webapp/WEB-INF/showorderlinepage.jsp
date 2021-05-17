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
                    <form>
                    <input type="hidden" value="${orderline.key}" name="orderlineid[]">
                    <tr>
                        <th scope="row">${orderline.key}</th>
                        <td>
                            <select class="form-select" id="${orderline.key}" name="materialVariantId[]">
                                <option value="${orderline.value.material.variantId}"
                                        selected>${orderline.value.material.toString()}</option>
                                <c:set var="materialid" value="${orderline.value.material.materialsId}"></c:set>
                                <c:set var="variantid" value="${orderline.value.material.variantId}"></c:set>
                                <c:forEach var="buildingmaterialvariantmap"
                                           items="${applicationScope.materialVariantMap.get(materialid)}">
                                    <c:if test="${buildingmaterialvariantmap.key != variantid}">
                                        <option value="${orderline.key}#${buildingmaterialvariantmap.key}">${buildingmaterialvariantmap.value.toString()}</option>
                                    </c:if>
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
                <input class="btn btn-primary mt-2"  name="myButton" type="submit" value="Gem">

            </div>
        </form>
        <script>
            $(document).ready(function() {
                $('input, select, textarea').on('change', function() {
                    console.log( $(this).attr('id'));
                    $(this).addClass('changed');
                });
                $("#gem").click(function(){
                    $('input:not(.changed), textarea:not(.changed), select:not(.changed)').prop('disabled', true);

                });
                $('form').on('submit', function() {
                    $('input:not(.changed), textarea:not(.changed), select:not(.changed)').prop('disabled', true);
                    $('input[name="orderid"]').prop('disabled', false);


                    // alert and return just for showing
                    alert($(this).serialize().replace('%5B', '[').replace('%5D', ']'));
                    return true;
                });
            });
        </script>
    </jsp:body>
</t:genericpage>