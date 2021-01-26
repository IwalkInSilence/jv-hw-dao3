<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1> Login page </h1>

<h4 style="color:red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/login">
    Login <input type="text" name="login" required>
    Password <input type="text" name="pwd" required>
    <button type="submit">Login</button>
</form>
</body>
</html>
