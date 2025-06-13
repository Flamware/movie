<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Videostore Inventory (Simple)</title>
    <style>
        body { font-family: sans-serif; margin: 20px; }
        table { width: 80%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .no-movies { color: #888; text-align: center; padding: 15px; }
    </style>
</head>
<body>

<h1>Videostore Inventory Management</h1>

<h2>Current Movie List</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Copies</th>
        <th>Type</th>
        <th>Main Actor</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="movie" items="${movies}"> <%-- CHANGE THIS LINE: from films to movies --%>
        <tr>
            <td>${movie.id}</td>
            <td>${movie.title}</td>
            <td>${movie.copies}</td>
            <td>${movie.movietype}</td>
            <td>
                <c:if test="${not empty movie.mainActor}">
                    ${movie.mainActor.firstName} ${movie.mainActor.lastName}
                </c:if>
                <c:if test="${empty movie.mainActor}">
                    N/A
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty movies}"> <%-- CHANGE THIS LINE: from films to movies --%>
        <tr>
            <td colspan="5" class="no-movies">No movies found in the inventory.</td>
        </tr>
    </c:if>
    </tbody>
</table>

<p>Page generated at: <%= new java.util.Date() %></p>

</body>
</html>