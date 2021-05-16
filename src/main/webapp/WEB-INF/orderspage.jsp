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
                                <div class="col-sm-4">Timestamp: ${orders.value.timestamp}</div>
                                <div class="col-sm-2">Status: ${orders.value.status}</div>
                                <div class="col-sm-2">User Id: ${orders.value.userId}</div>
                                <div class="col-sm-2">Total Price: ${orders.value.totalPrice}</div>
                            </div>

                        </div>
                    <form class="col text-center"  action="${pageContext.request.contextPath}/fc/removefromorders" method="post">
                        <button type="submit" class="btn btn-danger btn-sm" name="delete" value="${orders.key}">
                            Delete
                        </button>
                    </form>
                </h2>

                </div>
                <div id="collapse${orders.key}" class="accordion-collapse collapse" aria-labelledby="heading${orders.key}" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <div class="list align-items-center">
                            <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                <div class="col-sm-4"><b>Name</b></div>
                                <div class="col-sm-6"><b>Quantity</b></div>
                                <div class="col-sm-2"><b>Total price</b></div>
                            </div>
                            <c:forEach var="products" items="${orders.value.products}">
                                <div class="row align-items-center" style="height: 50px; margin-top: 2px; border-radius: 7px; background-color: #F4F3EE;">
                                    <div class="col-sm-4">${products.name}</div>
                                    <div class="col-sm-6">${products.quantity}</div>
                                    <div class="col-sm-2">${products.totalPrice}</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    </jsp:body>
</t:genericpage>