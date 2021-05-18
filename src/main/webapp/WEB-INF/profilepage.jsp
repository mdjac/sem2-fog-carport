<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Profil
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="about-section">
            <h1>Profile Page</h1>
        </div>


        <div class="row mt-5">
            <div class="col-md-4"></div>

            <div class="col-md-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/images/person.png" alt="Mike" style="width:100%">
                    <div class="container">
                        <h2>User ID: ${sessionScope.user.id}</h2>
                        <p class="title">${sessionScope.user.role}</p>
                        <p>${sessionScope.user.name}</p>
                        <p>${sessionScope.user.email}</p>
                        <!-- Button trigger modal -->
                        <p><button class="button" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Rediger profil</button></p>
                    </div>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>

        <!-- Modal -->
        <div class="modal fade " id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Rediger profil</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="${pageContext.request.contextPath}/fc/editprofile" method="post">
                        <div class="modal-body">
                            <div class="row g-3">
                                <div class="col-auto">
                                    <label for="staticEmail" class="visually-hidden">Email</label>
                                    <input type="email" readonly class="form-control-plaintext" id="staticEmail" value="${sessionScope.user.email}">
                                </div>
                                <div class="col-auto">
                                    <label for="inputemail" class="visually-hidden">Email</label>
                                    <input type="email" name="newemail" class="form-control" id="inputemail" placeholder="Ny email">
                                </div>
                            </div>

                            <div class="row g-3 mt-2">
                                <div class="col-auto">
                                    <label for="staticpassword" class="visually-hidden">Password</label>
                                    <input type="text" readonly class="form-control-plaintext" id="staticpassword" value="Password">
                                </div>
                                <div class="col-auto">
                                    <label for="inputpassword" class="visually-hidden">Password</label>
                                    <input type="password" name="newpassword" class="form-control" id="inputpassword" placeholder="Nyt kodeord">
                                </div>
                            </div>
                            <div class="row g-3 mt-2">
                                <div class="col-auto">
                                    <label for="staticTelephone" class="visually-hidden">Telephone</label>
                                    <input type="text" readonly class="form-control-plaintext" id="staticTelephone" value="${sessionScope.user.telephone}">
                                </div>
                                <div class="col-auto">
                                    <label for="inputpassword" class="visually-hidden">Email</label>
                                    <input type="text" name="newtelephonenumber" class="form-control" id="inputTelephone" placeholder="Nyt telefonnummer">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="reset" class="btn btn-secondary" data-bs-dismiss="modal">Annuller</button>
                            <button type="submit" class="btn btn-primary">Gem ændringer</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:genericpage>