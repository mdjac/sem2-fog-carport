<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="header">
         Admin Orders page
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="accordion" id="accordionExample">
        <c:forEach var="orders" items="${requestScope.orders}">


            <div class="accordion-item align-items-center">
                <div class="align-items-center">
                <h2 class="row accordion-button collapsed accordionhover" style="margin-left: 0; margin-bottom: 0;" data-bs-toggle="collapse" data-bs-target="#collapse${orders.key}" aria-expanded="false" aria-controls="collapse${orders.key}" id="heading${orders.key}">
                        <div class="col-lg-10 collapsed align-items-center text-decoration-none">
                            <div class="row">
                                <div class="col-sm-2">Order ID: ${orders.value.id}</div>
                                <div class="col-sm-4">Timestamp: ${orders.value.time}</div>
                                <div class="col-sm-2">Status: ${orders.value.status}</div>
                                <div class="col-sm-2">User Id: ${orders.value.userId}</div>
                                <div class="col-sm-2">Total Price: ${orders.value.totalPrice}</div>
                            </div>

                        </div>
                    <form class="col text-center"  action="${pageContext.request.contextPath}/fc/showorderlinecommand" method="post">
                        <input type="hidden" name="carportid" value="${orders.value.carport.id}">
                        <button type="submit" class="btn btn-primary btn-sm" name="orderid" value="${orders.key}">
                            Vis stykliste
                        </button>
                    </form>
                </h2>

                </div>
                <div id="collapse${orders.key}" class="accordion-collapse collapse" aria-labelledby="heading${orders.key}" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <div class="list align-items-center">
                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4"><b>Carport</b></div>
                                <div class="col-sm-4"><b>Tag</b></div>
                                <div class="col-sm-4"><b>Redskabsskur</b></div>
                            </div>

                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4">Carport materiale: ${orders.value.carport.carportMaterial.materialName}</div>
                                <div class="col-sm-4">Tag Materiale: ${orders.value.carport.roofMaterial.materialName}</div>
                                <div class="col-sm-4">Redskabsskur Materiale: ${orders.value.carport.shedMaterial.materialName}</div>
                            </div>
                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4">Carport Bredde: ${orders.value.carport.carportWidth}</div>
                                <div class="col-sm-4">Tag Type: ${orders.value.carport.roofType}</div>
                                <div class="col-sm-4">Redskabsskur bredde: ${orders.value.carport.shedWidth}</div>
                            </div>
                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4">Carport Længde: ${orders.value.carport.carportLength}</div>
                                <div class="col-sm-4">Tag Hældning: ${orders.value.carport.roofTilt}</div>
                                <div class="col-sm-4">Redskabsskur længde: ${orders.value.carport.shedLength}</div>
                            </div>
                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4">Carport Højde: ${orders.value.carport.carportHeight}</div>
                                <div class="col-sm-4"></div>
                                <div class="col-sm-4"></div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
        <script>
            $(document).ready(function(){
                $("#myInput").on("keyup", function() {
                    var value = $(this).val().toLowerCase();
                    $("#myTable tr").filter(function() {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script>
    </jsp:body>
</t:genericpage>