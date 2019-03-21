<%--
  Created by IntelliJ IDEA.
  User: gelos
  Date: 2/25/2019
  Time: 8:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Customers</title>
    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css"/>

    <!-- Reference Bootstrap files -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

    <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>CRM - Customer Relationship Manager</h2>
        </div>
    </div>
    <div id="container">
        <div id="content">
            <security:authorize access="hasAnyRole('MANAGER','ADMIN')" >
                <!--  put new button "Add Customer" -->
                <input type="button" value="Add Customer"
                       onclick="window.location.href='showFormForAdd'; return false"
                       class="add-button" />
            </security:authorize>
            <!-- Put new search field here -->
            <form:form action="search" method="get">
                Search customer:
                    <input type="text" name="theSearchName"/>
                <input type="submit" value="Search" class="add-button"/>
            </form:form>

            <!-- Add html table here -->
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <!-- Loop over and print customers -->
                <c:forEach var="tempCustomer" items="${customers}">
                    <!-- construct an "update" link with customer id -->
                    <c:url var="updateLink" value="/customer/showFormForUpdate">
                        <c:param name="customerId" value="${tempCustomer.id}"/>
                    </c:url>
                    <!-- construct a "delete" link with customer id -->
                    <c:url var="deleteLink" value="/customer/delete">
                        <c:param name="customerId" value="${tempCustomer.id}"/>
                    </c:url>
                    <tr>
                        <td>${tempCustomer.firstName}</td>
                        <td>${tempCustomer.lastName}</td>
                        <td>${tempCustomer.email}</td>
                        <!-- display the update link -->
                        <td>
                            <security:authorize access="hasAnyRole('MANAGER','ADMIN')" >
                            <a href="${updateLink}">Update</a>
                            </security:authorize>
                            <security:authorize access="hasAnyRole('ADMIN')" >
                                |
                                <a href="${deleteLink}"
                                   onclick="return confirm('Are you sure you want to delete this customer?');">
                                    Delete</a>
                            </security:authorize>

                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <br>
    <%-- Add Logout button--%>
    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout" class="add-button"/>
    </form:form>

</body>
</html>
