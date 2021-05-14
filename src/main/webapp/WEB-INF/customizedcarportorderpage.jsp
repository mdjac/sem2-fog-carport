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
        <form action="${pageContext.request.contextPath}${link}" method="post">
            <div class="row">
                <div class="col-lg-2"></div>
                <div class="col-lg-8">
                    <div class="row">
                        <div class="col-lg-6 mt-2">
                            <label for="rooftype">Vælg fladt tag eller tag med rejsning</label>
                            <select class="form-select" aria-label="rooftype" id="rooftype" name="rooftype"
                                    onchange="updateRoofVisibility('rooftype')" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="type" items="${applicationScope.roofTypes}">
                                    <option value="${type}">${type.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <img src="${pageContext.request.contextPath}/images/carport-flatroof.PNG" class="img-fluid"
                                 alt="Responsive image">
                            <img src="${pageContext.request.contextPath}/images/carport-nonflatroot.PNG"
                                 class="img-fluid"
                                 alt="Responsive image">
                        </div>
                        <div id="roofmaterialdiv" style="display: none" class="col-lg-6 mt-2">
                            <label for="roofmaterial">Tag materiale</label>
                            <select class="form-select" aria-label="roofmaterial" id="roofmaterial"
                                    name="roofmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(4).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </div>
                        <div id="roofmaterialplanediv" style="display: none" class="col-lg-6 mt-2">
                            <label for="roofmaterialplane">Tag materiale</label>
                            <select class="form-select" aria-label="roofmaterialplane" id="roofmaterialplane"
                                    name="roofmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(2).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </div>
                        <div id="rooftiltdiv" style="display: none" class="col-lg-6 mt-2">
                            <label for="rooftilt">Tag
                                hældning ${applicationScope.allowedMeasurements["roofTilt"].toString()}</label>
                            <input type="number" class="form-control" id="rooftilt" aria-describedby="rooftilt"
                                   name="rooftilt" required>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <label for="carportmaterial">Carport beklædning</label>
                            <select class="form-select" aria-label="carportmaterial" id="carportmaterial"
                                    name="carportmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(1).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="carportlength">Carport
                                    længde ${applicationScope.allowedMeasurements["carportLength"].toString()}</label>
                                <input type="number" class="form-control" id="carportlength"
                                       aria-describedby="carportlength"
                                       name="carportlength" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="carportwidth">Carport
                                    bredde ${applicationScope.allowedMeasurements["carportWidth"].toString()}</label>
                                <input type="number" class="form-control" id="carportwidth"
                                       aria-describedby="carportwidth"
                                       name="carportwidth" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="carportheight">Carport
                                    højde ${applicationScope.allowedMeasurements["carportHeight"].toString()}</label>
                                <input type="number" class="form-control" id="carportheight"
                                       aria-describedby="carportheight"
                                       name="carportheight" required>
                            </div>
                        </div>
                        <div id="choosesheddiv" class="col-lg-6 mt-2">
                            <label for="chooseshed">Ønskes redskabsskur</label>
                            <select class="form-select" aria-label="chooseshed" id="chooseshed"
                                    name="chooseshed" onchange="updateShedVisibility('chooseshed')" required>
                                <option value="" selected disabled>Vælg her</option>
                                <option value="ja">Ja</option>
                                <option value="nej">Nej</option>
                            </select>
                        </div>
                        <div id="shedmaterialdiv" style="display: none" class="col-lg-6 mt-2">
                            <label for="shedmaterial">Redskabsskur beklædning</label>
                            <select class="form-select" aria-label="shedmaterial" id="shedmaterial"
                                    name="shedmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(3).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div id="shedlengthdiv" style="display: none" class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="shedlength">Redskabsskur
                                    længde ${applicationScope.allowedMeasurements["shedLength"].toString()}</label>
                                <input type="number" class="form-control" id="shedlength" aria-describedby="shedlength"
                                       name="shedlength" required>
                            </div>
                        </div>
                        <div id="shedwidthdiv" style="display: none" class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="shedwidth">Redskabsskur
                                    bredde ${applicationScope.allowedMeasurements["shedWidth"].toString()}</label>
                                <input type="number" class="form-control" id="shedwidth" aria-describedby="shedwidth"
                                       name="shedwidth" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-4"></div>
                        <div class="col-lg-4 text-center mt-2">
                            <input class="btn btn-primary mt-2" type="submit" value="Tilføj carport">
                        </div>
                        <div class="col-lg-4"></div>
                    </div>
                </div>
                <div class="col-lg-2"></div>
            </div>
        </form>

        <script type="text/javascript">
            function updateRoofVisibility(input) {
                var value = document.getElementById(input).value;
                if (value === "Tag_Med_Rejsning") {
                    document.getElementById('roofmaterialplanediv').style.display = 'none';
                    document.getElementById('roofmaterialplane').required = false;
                    document.getElementById('roofmaterialdiv').style.display = 'block';
                    document.getElementById('rooftiltdiv').style.display = 'block';
                } else if (value === "Fladt_Tag") {
                    document.getElementById('roofmaterialplanediv').style.display = 'block';
                    document.getElementById('roofmaterialdiv').style.display = 'none';
                    document.getElementById('roofmaterial').required = false;
                    document.getElementById('rooftiltdiv').style.display = 'none';
                    document.getElementById('rooftilt').required = false;
                }
            }

            function updateShedVisibility(input) {
                var value = document.getElementById(input).value;
                if (value === "ja") {
                    document.getElementById('shedmaterialdiv').style.display = 'block';
                    document.getElementById('shedlengthdiv').style.display = 'block';
                    document.getElementById('shedwidthdiv').style.display = 'block';
                } else if (value === "nej") {
                    document.getElementById('shedmaterialdiv').style.display = 'none';
                    document.getElementById('shedmaterial').required = false;
                    document.getElementById('shedlengthdiv').style.display = 'none';
                    document.getElementById('shedlength').required = false;
                    document.getElementById('shedwidthdiv').style.display = 'none';
                    document.getElementById('shedwidth').required = false;
                }
            }
        </script>
    </jsp:body>
</t:genericpage>