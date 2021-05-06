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
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <form class="" action="${pageContext.request.contextPath}${link}" method="post">
                    <div class="card text-center" style="width: 18rem;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carport-std-enkelt.png"
                             alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Standard carport</h5>
                            <p class="card-text">CARPORT ENKELT 3,60X9,10M CRXL1HR MED REDSKABSRUM 3,20X3,55M</p>
                            <input type="hidden" id="standardCarportId" name="standardCarportId" value="1">
                            <input class="btn btn-primary" type="submit" value="Send forespørgsel">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-lg-3">
                <form class="" action="${pageContext.request.contextPath}/fc/customizedcarportorderpage" method="post">
                    <div class="card text-center" style="width: 18rem;">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carport-custom.png"
                             alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Special bygget carport</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk
                                of
                                the card's content.</p>
                            <input class="btn btn-primary" type="submit" value="Byg selv">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-lg-3"></div>
        </div>
    </jsp:body>
</t:genericpage>