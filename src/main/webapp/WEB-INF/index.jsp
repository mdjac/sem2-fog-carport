<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Forside
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-lg-3"></div>
            <div class="col-lg-6">
                <div class="text-center">
                    <h2>Køb din nye carport hos Fog! </h2>
                    <p>
                        Vælg mellem vores standard modeller eller byg-selv model og lad os give dig et konkret tilbud på en carport i nøjagtig de mål du ønsker!
                    </p>
                </div>
                <div class="row mt-5">
                    <div class="col-lg-6">
                        <form class="" action="${pageContext.request.contextPath}/fc/standardcarportorderpage"
                              method="post">
                            <div class="card text-center" style="width: 18rem;">
                                <img class="card-img-top"
                                     src="${pageContext.request.contextPath}/images/carport-custom.png"
                                     alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title">Standard carport</h5>
                                    <p class="card-text">
                                        Leveres som byg-selv sæt<br>
                                        Byggevejledning medfølger<br>
                                        Levering inden for 10 hverdage<br>
                                    </p>
                                    <input class="btn btn-primary" type="submit" value="Vælg">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-6">
                        <form class="" action="${pageContext.request.contextPath}/fc/customizedcarportorderpage"
                              method="post">
                            <div class="card text-center" style="width: 18rem;">
                                <img class="card-img-top"
                                     src="${pageContext.request.contextPath}/images/carport-custom.png"
                                     alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title">Special bygget carport</h5>
                                    <p class="card-text">
                                        Leveres som byg-selv sæt<br>
                                        Byggevejledning medfølger<br>
                                        Tilbud fremsendes indenfor 10 hverdage<br>
                                    </p>
                                    <input class="btn btn-primary" type="submit" value="Vælg">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-3"></div>

        </div>
    </jsp:body>
</t:genericpage>