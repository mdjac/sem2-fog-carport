<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Standard Carports
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <c:if test="${sessionScope.role == 'customer' }">
            <c:set var="link" scope="session" value="/fc/submitorder"/>
        </c:if>
        <c:if test="${empty sessionScope.role}">
            <c:set var="link" scope="session" value="/fc/loginpage"/>
        </c:if>

                <div class="row justify-content-center">
                    <c:forEach var="standardcarport" items="${applicationScope.standardCarports}">
                        <div class="col-sm-auto card">
                            <form class="d-flex align-items-end flex-column bd-highlight mb-3" style="height: 100%" action="${pageContext.request.contextPath}${link}" method="post">
                                <div class="text-center p-2 bd-highlight" style="width: 18rem;">
                                    <c:set var="picture" value="/images/carport-std-fladtTag.png"></c:set>
                                    <c:if test="${not empty standardcarport.value.roofTilt}">
                                        <c:set var="picture" value="/images/carport-std-rejsning.png"></c:set>
                                    </c:if>
                                    <img class="card-img-top"
                                         src="${pageContext.request.contextPath}${picture}"
                                         alt="Card image cap">
                                    <div class="card-body p-2 bd-highlight">
                                        <h5 class="card-title">Standard carport
                                            #${standardcarport.value.standardCarportId} </h5>
                                        <div class="card-text">
                                            <div>
                                                <b>Carport:</b><br>
                                                Beklædning: ${standardcarport.value.carportMaterial.materialName}<br>
                                                B${standardcarport.value.carportWidth}/
                                                L${standardcarport.value.carportLength}/
                                                H${standardcarport.value.carportHeight}
                                            </div>
                                            <c:if test="${standardcarport.value.shedMaterial != null }">
                                                <div class="mt-1">
                                                    <b>Redskabsskur:</b><br>
                                                    Beklædning: ${standardcarport.value.shedMaterial.materialName}<br>
                                                    B${standardcarport.value.shedWidth}/
                                                    L${standardcarport.value.shedLength}
                                                </div>
                                            </c:if>
                                            <div class="mt-1">
                                                <b>Tag:</b><br>
                                                Type: ${standardcarport.value.roofType.toString()}<br>
                                                Materiale: ${standardcarport.value.roofMaterial.materialName}<br>
                                                <c:if test="${standardcarport.value.roofTilt != null }">
                                                    Tag hældning: ${standardcarport.value.roofTilt}
                                                </c:if>
                                            </div>
                                        </div>
                                        <input type="hidden" id="standardCarportId" name="standardCarportId"
                                               value="${standardcarport.value.standardCarportId}">


                                    </div>
                                </div>
                                <c:if test="${!sessionScope.user.role.equals('employee')}">
                                    <div class="mt-auto p-2 bd-highlight align-items-end" style="width: 100%;">
                                        <input class="button" type="submit" value="Send forespørgsel">
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </c:forEach>
                </div>

    </jsp:body>
</t:genericpage>