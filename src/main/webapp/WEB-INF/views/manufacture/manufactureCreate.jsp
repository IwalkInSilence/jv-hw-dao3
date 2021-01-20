<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Manufacture</title>
</head>
<body>
<h1> Manufacture creator </h1>

<form method="post" action="${pageContext.request.contextPath}/mn/create">
    Please provide Manufacture 'name' <input type="text" name="name">
    Please provide Manufacture 'country' <input type="text" name="country">
    <button type="submit">Add Manufacture</button>
</form>
</body>
</html>