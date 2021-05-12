<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Tilføj standard carport
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <form action="${pageContext.request.contextPath}/fc/addstandardcarportcommand" method="post">
            <div class="row">
                <div class="col-lg-2"></div>
                <div class="col-lg-8">
                    <div class="row">
                        <div class="col-lg-6 mt-2">
                            <label for="rooftype">Vælg fladt tag eller tag med rejsning</label>
                            <select class="form-select mt-2" aria-label="rooftype" id="rooftype" name="rooftype"
                                    required>
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
                        <div class="col-lg-6 mt-2">
                            <label for="roofmaterial">Tag materiale</label>
                            <select class="form-select mt-2" aria-label="roofmaterial" id="roofmaterial"
                                    name="roofmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(2).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </div>
                        <div class="col-lg-6 mt-3">
                            <label for="rooftilt">Tag hældning (maks. xx)</label>
                            <input type="number" class="form-control" id="rooftilt" aria-describedby="rooftilt"
                                   name="rooftilt" required>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <label for="carportmaterial">Carport beklædning</label>
                            <select class="form-select mt-2" aria-label="carportmaterial" id="carportmaterial"
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
                                <label for="carportlength">Carport længde (maks. xx)</label>
                                <input type="number" class="form-control" id="carportlength"
                                       aria-describedby="carportlength"
                                       name="carportlength" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="carportwidth">Carport bredde (maks. xx)</label>
                                <input type="number" class="form-control" id="carportwidth"
                                       aria-describedby="carportwidth"
                                       name="carportwidth" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="carportheight">Carport højde (maks. xx)</label>
                                <input type="number" class="form-control" id="carportheight"
                                       aria-describedby="carportheight"
                                       name="carportheight" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <label for="shedmaterial">Redskabsskur beklædning</label>
                            <select class="form-select mt-2" aria-label="shedmaterial" id="shedmaterial"
                                    name="shedmaterial" required>
                                <option value="" selected disabled>Vælg her</option>
                                <c:forEach var="option" items="${applicationScope.categoryFormOptions[(3).intValue()]}">
                                    <option value="${option.value.materialsId}">${option.value.materialName}</option>
                                </c:forEach>
                            </select>
                            </select>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="shedlength">Redskabsskur længde (maks. xx)</label>
                                <input type="number" class="form-control" id="shedlength" aria-describedby="shedlength"
                                       name="shedlength" required>
                            </div>
                        </div>
                        <div class="col-lg-6 mt-2">
                            <div class="form-group mt-2">
                                <label for="shedwidth">Redskabsskur bredde (maks. xx)</label>
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
    </jsp:body>
</t:genericpage>