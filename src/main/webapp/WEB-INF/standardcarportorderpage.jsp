<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Home
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
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">
                <div class="row">
                    <c:forEach var="standardcarport" items="${applicationScope.standardCarports}">
                        <div class="col-lg-4 mt-2">
                            <form class="" action="${pageContext.request.contextPath}${link}" method="post">
                                <div class="card text-center" style="width: 18rem;">
                                    <img class="card-img-top"
                                         src="${pageContext.request.contextPath}/images/carport-std-enkelt.png"
                                         alt="Card image cap">
                                    <div class="card-body">
                                        <h5 class="card-title">Standard carport #${standardcarport.value.id} </h5>
                                        <div class="card-text">
                                            <div>
                                                <b>Carport:</b><br>
                                                Beklædning: ${standardcarport.value.carportBeklædning}<br>
                                                B${standardcarport.value.carportBredde}/
                                                L${standardcarport.value.carportLængde}/
                                                H${standardcarport.value.carportHøjde}
                                            </div>
                                            <c:if test="${standardcarport.value.redskabsskurBeklædning != null }">
                                                <div class="mt-1">
                                                    <b>Redskabsskur:</b><br>
                                                    Beklædning: ${standardcarport.value.redskabsskurBeklædning}<br>
                                                    B${standardcarport.value.redskabsskurBredde}/
                                                    L${standardcarport.value.redskabsskurLængde}
                                                </div>
                                            </c:if>
                                            <div class="mt-1">
                                                <b>Tag:</b><br>
                                                Type: ${standardcarport.value.roofType.toString()}<br>
                                                Materiale: ${standardcarport.value.tagMateriale}<br>
                                                <c:if test="${standardcarport.value.tagHældning != null }">
                                                    Tag hældning: ${standardcarport.value.tagHældning}
                                                </c:if>
                                            </div>
                                        </div>
                                        <input type="hidden" id="standardCarportId" name="standardCarportId"
                                               value="${standardcarport.value.id}">
                                        <input class="btn btn-primary mt-2" type="submit" value="Send forespørgsel">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-lg-2"></div>
        </div>
    </jsp:body>
</t:genericpage>