<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Driver</title>
</head>
<body>
<h1> Driver creator </h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Please provide Driver name <input type="text" name="name" required>
    Please provide Driver License Number <input type="text" name="licenseNumber" required>
    Login <input type="text" name="login" required>
    Password <input type="text" name="pwd" required>
    <button type="submit">Registration</button>
</form>
</body>
</html>
