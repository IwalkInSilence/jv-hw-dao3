<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to Car</title>
</head>
<body>
<h1> Add driver to Car </h1>

<form method="post" action="${pageContext.request.contextPath}/car/add">
    Please provide Car ID <input type="text" name="car_id">
    Please provide Driver ID <input type="text" name="driver_id">
    <button type="submit">Add Driver to Car</button>
</form>
</body>
</html>
