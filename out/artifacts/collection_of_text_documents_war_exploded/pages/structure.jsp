<%--
  Created by IntelliJ IDEA.
  User: Алина
  Date: 12.06.2024
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сollection structure</title>
</head>
<body>
<div>
<h1>Структура коллекции!</h1>
</div>
<div>
    <%
        String str = (String) request.getAttribute("structureCollection");
        out.println(str);
    %>
</div>
</body>
</html>
