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
        <form action="${pageContext.request.contextPath}/fc/bmiresult" method="post">
            <div class="row">
                <div class="col-lg-2"></div>
                <div class="col-lg-4">
                    <label for="rooftype">Vælg fladt tag eller tag med rejsning</label>
                    <select class="form-select mt-2" aria-label="rooftype" id="rooftype" name="rooftype" required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-4">
                    <img src="${pageContext.request.contextPath}/images/carport-flatroof.PNG" class="img-fluid"
                         alt="Responsive image">
                    <img src="${pageContext.request.contextPath}/images/carport-nonflatroot.PNG" class="img-fluid"
                         alt="Responsive image">
                </div>
                <div class="col-lg-2"></div>


                <div class="col-lg-2 mt-2"></div>
                <div class="col-lg-4 mt-2">
                    <label for="carportwidth">Carport bredde</label>
                    <select class="form-select mt-2" aria-label="rooftype" id="carportwidth" name="carportwidth"
                            required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-4 mt-2">
                    <label for="carportlength">Carport længde</label>
                    <select class="form-select mt-2" aria-label="rooftype" id="carportlength" name="carportlength"
                            required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-2 mt-2"></div>


                <div class="col-lg-2 mt-2"></div>
                <div class="col-lg-4 mt-2">
                    <label for="roof">Tag</label>
                    <select class="form-select mt-2" aria-label="roof" id="roof" name="roof" required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-4 mt-2">
                    <label for="rooftilt">Taghældning</label>
                    <select class="form-select mt-2" aria-label="rooftilt" id="rooftilt" name="rooftilt" required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-2 mt-2"></div>

                <div class="col-lg-2 mt-2"></div>
                <div class="col-lg-4 mt-2">
                    <label for="toolroomwidth">Redskabsrum bredde</label>
                    <select class="form-select mt-2" aria-label="toolroomwidth" id="toolroomwidth" name="toolroomwidth"
                            required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-4 mt-2">
                    <label for="toolroomlength">Redskabsrum længde</label>
                    <select class="form-select mt-2" aria-label="toolroomlength" id="toolroomlength"
                            name="toolroomlength" required>
                        <option value="" selected disabled>Vælg her</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                    </select>
                </div>
                <div class="col-lg-2 mt-2"></div>



                <div class="col-lg-2"></div>
                <div class="col-lg-8 mt-4">
                    <hr class="solid">
                    <div class="form-group">
                        <label for="name">Navn</label>
                        <input type="text" class="form-control" id="name" aria-describedby="name" name="name" required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="address">Adresse</label>
                        <input type="text" class="form-control" id="address" aria-describedby="address" name="address" required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="zip">Postnummer</label>
                        <input type="number" class="form-control" id="zip" aria-describedby="zip" name="zip" required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="telephone">Telefon</label>
                        <input type="number" class="form-control" id="telephone" aria-describedby="telephone" name="telephone" required>
                    </div>
                    <div class="form-group">
                        <label for="email">email</label>
                        <input type="email" class="form-control" id="email" aria-describedby="email" name="email" required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="comments">Evt. bemærkninger</label>
                        <input type="text" class="form-control" id="comments" aria-describedby="comments" name="comments" required>
                    </div>
                </div>
                <div class="col-lg-2"></div>
                <div class="col-lg-2"></div>
                <div class="col-lg-2">
                <button type="submit" class="btn btn-primary mt-2">Send forespørgsel</button>
                </div>
            </div>
        </form>
    </jsp:body>
</t:genericpage>