<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1> Car creator </h1>

<form method="post" action="${pageContext.request.contextPath}/car/create">
    Please provide Car model <input type="text" name="model" required>
    Please provide Car Manufacturer name <input type="text" name="name" required>
    Please provide Car Manufacturer country <input type="text" name="country" required>
    <button type="submit">Add Car</button>
</form>
</body>
</html>
