<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .red    {
            color: red;
        }
    </style>
    <title>YOU LOSE</title>

</head>
<body>
<body style='background-color: red;'>
<form class="form-horizontal" action="index.jsp" method="get">
    <div class="form-group">
        <div class="col-md-4">
        </div>
        <div>
            <h1><%= request.getAttribute("questionText") %> </h1>
        </div>
        <div>
            <% request.getSession().setAttribute("id" , null);
            %>
        </div>

    </div>

    <div class=" form-group">
        <label class="col-md-4 control-label" for="submit"></label>
        <div class="col-md-4">
            <button id="submit" class="btn btn-success">Начать заново</button>
        </div>
    </div>
</form>

</body>