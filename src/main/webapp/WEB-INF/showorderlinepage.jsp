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
            <input type="hidden" name="orderid" value="${requestScope.order.BOM.firstEntry().value.ordersID}">
            <input type="hidden" name="ordrestatus" value="${requestScope.order.status.toString()}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Materiale (LxBxH)</th>
                    <th scope="col">Antal</th>
                    <th scope="col">Akk. pris ,-</th>
                    <th scope="col">Enhed</th>
                    <th scope="col">Beskrivelse</th>
                    <th scope="col">Marker for at slette</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderline" items="${requestScope.order.BOM}">
                <form>
                    <input type="hidden" id="${orderline.key}" value="${orderline.key}" name="orderlineid[]">
                    <tr>
                        <th scope="row">${orderline.key}</th>
                        <td>
                            <select class="form-select" id="materialVariantId_${orderline.key}"
                                    name="materialVariantId[]">
                                <option value="${orderline.value.material.variantId}"
                                        selected>${orderline.value.material.toString()}</option>
                                <c:set var="materialid" value="${orderline.value.material.materialsId}"></c:set>
                                <c:set var="variantid" value="${orderline.value.material.variantId}"></c:set>
                                <c:forEach var="buildingmaterialvariantmap"
                                           items="${applicationScope.materialVariantMap.get(materialid)}">
                                    <c:if test="${buildingmaterialvariantmap.key != variantid}">
                                        <option value="${buildingmaterialvariantmap.key}">${buildingmaterialvariantmap.value.toString()}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <div class="form-group w-25">
                                <input type="number" id="quantity_${orderline.key}" class="form-control"
                                       name="quantity[]" min="0"
                                       value="${orderline.value.quantity}">
                            </div>
                        </td>
                        <td>
                            <div class="w-25">
                                    ${orderline.value.accumulatedPrice}
                            </div>
                        </td>
                        <td>
                                ${orderline.value.unit}
                            <input type="hidden" name="unit[]" value="${orderline.value.unit}"
                                   id="unit_${orderline.key}">
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="text" id="description_${orderline.key}" class="form-control"
                                       name="description[]"
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
                <input class="btn btn-primary mt-2" name="myButton" type="submit" value="Gem">

            </div>
        </form>
        <div class="text-center mt-5">
            <form action="${pageContext.request.contextPath}/fc/calculateorderpricecommand" method="POST">
                <label for="costPrice" class="col-sm-2 col-form-label">Total indkøbspris for ordren</label>
                <input class="form-control" id="costPrice" type="number"
                       placeholder="${requestScope.order.costPrice}" readonly>

                <label for="totalPrice" class="col-sm-2 col-form-label">Total pris for ordren</label>
                <input class="form-control" min="0" id="totalPrice" type="number" step=".01" name="totalPrice"
                       value="${requestScope.order.totalPrice}">
                <label for="avance">Avance i %</label>
                <input type="number" step=".01" class="form-control" id="avance" name="avance"
                       value="${requestScope.avance}">
                <c:if test="${requestScope.error != null }">
                    <p class="text-center text-danger mb-4 mt-4">${requestScope.error}</p>
                </c:if>
                <input class="btn btn-primary mt-2" name="action" type="submit" value="Beregn ny pris">
                <input type="hidden" name="orderid" value="${requestScope.order.id}">
            </form>
        </div>
        <div class="text-center mt-5">
            <form action="${pageContext.request.contextPath}/fc/changeorderstatuscommand" method="POST">
                <input type="hidden" name="orderid" value="${requestScope.order.BOM.firstEntry().value.ordersID}">
                <button type="submit" class="btn btn-success changed" value="${applicationScope.status.get(1).toString()}" name="status">Afsend tilbud</button>
            </form>
        </div>
        <script>
            $(document).ready(function () {
                var status = $("input[name=ordrestatus]").val();
                console.log("status "+status);
                if (status === "Afhentet"){
                    $('input:not(.changed), textarea:not(.changed), select:not(.changed)').prop('disabled', true);
                    $('button[name=status]').prop('disabled', true);
                }
                $('input, select, textarea').on('change', function () {

                    $(this).addClass('changed');

                    var id = $(this).attr('id');
                    var idSplitted = id.slice(id.indexOf("_") + 1)

                    var id = '#' + idSplitted;
                    $(id).addClass("changed");

                    var m = "#materialVariantId_" + idSplitted;
                    $(m).addClass("changed");

                    var q = "#quantity_" + idSplitted;
                    $(q).addClass("changed");

                    var u = "#unit_" + idSplitted;
                    $(u).addClass("changed");

                    var d = "#description_" + idSplitted;
                    $(d).addClass("changed");
                });
                $('form').on('submit', function () {
                    var numItems = $('.changed').length
                    if (numItems > 0) {
                        $('input:not(.changed), textarea:not(.changed), select:not(.changed)').prop('disabled', true);
                        $('input[name="orderid"]').prop('disabled', false);
                        return true;
                    }
                    return false;
                });
            });
        </script>
    </jsp:body>
</t:genericpage>