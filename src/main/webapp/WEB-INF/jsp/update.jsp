<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.com.javarush.quest.kossatyy.questdelta.entity.Role" %>


<!DOCTYPE html>
<html>
<head>
    <title>Edit</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/signin.css"/>
</head>
<body>
<c:import url="/WEB-INF/jsp/parts/header.jsp"/>

<main class="form-register w-100 m-auto">
    <form class="form-horizontal" action="update?id=${requestScope.userUpdate.id}" method="post">

        <fieldset>
            <img class="mb-3 mx-auto d-block" src="${pageContext.request.contextPath}/images/site/eagle-black.svg" alt="logo" width="100" height="100">
            <legend class="text-center">${requestScope.userUpdate.login}</legend>

            <div class="form-group">
                <label class="col control-label" for="input_username_new"><strong>New username</strong></label>
                <div class="col d-flex flex-column mb-2">
                    <input id="input_username_new" name="loginNew" type="text"
                           class="form-control input-md"
                           placeholder="enter username"
                           minlength="1" maxlength="20" pattern="[A-Za-z0-9]+">
                    <span class="form-text text-center"><small>Latin letters or digits</small></span>
                </div>
            </div>

            <div class="form-group">
                <label class="col control-label" for="input_password"><strong>New password</strong></label>
                <div class="col d-flex flex-column mb-2">
                    <input id="input_password" name="password" type="password" placeholder="enter password"
                           class="form-control input-md"
                           minlength="1" maxlength="8" pattern="[A-Za-z0-9]+">
                    <span class="form-text text-center"><small>Latin letters or digits</small></span>
                </div>
            </div>

            <div class="form-group">
                <label class="col control-label" for="select_roles"><strong>Role</strong></label>
                <div class="col">
                    <select id="select_roles" name="role" class="form-control">
                        <c:forEach var="role" items="${Role.values()}">
                            <c:choose>
                                <c:when test="${role == requestScope.userUpdate.role}">
                                    <option value="${role}" selected><c:out value="${role}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${role}"><c:out value="${role}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col control-label" for="singlebutton"></label>
                <div class="col d-flex">
                    <button id="singlebutton" name="singlebutton" class="btn btn-primary w-50 mx-auto">Update</button>
                </div>
            </div>
            <c:if test="${not empty requestScope.error}">
                <div class="form-floating text-danger my-3 p-2">
                    <p class="text-center m-0">${requestScope.error}</p>
                </div>
            </c:if>
        </fieldset>
    </form>
</main>

<c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
<c:import url="/WEB-INF/jsp/parts/scripts.jsp"/>
</body>

</html>
