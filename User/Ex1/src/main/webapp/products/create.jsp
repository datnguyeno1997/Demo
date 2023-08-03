<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="../style.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha512-3gJwYpMe3QewGELv8k/BX9vcqhryRdzRMxVfq6ngyWXwo03GFEzjsUm8Q7RZcHPHksttq7/GFoxjCVUjkjvPdw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" integrity="sha512-VEd+nq25CkR676O+pLBnDW09R7VQX9Mdiij052gVCp5yVH3jGtH70Ho/UUv4mJDsEdTvqRCFZg0NKGiojGnUCw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css" integrity="sha512-3pIirOrwegjM6erE5gPSwkUzO+3cTjpnV9lexlNZqvupR64iZBnOOTiiLPb9M36zpMScbmUNIcHUqKD47M719g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<h1>Create</h1>
<h3>${message}</h3>
<form method="post"
        <c:if test = "${product.id == null}"> action="/products?action=create" </c:if>
        <c:if test = "${product.id != null}"> action="/products?action=edit" </c:if>
>
    <%--        cần phải có thằng formBody thì xài được validation js--%>
    <div id="formBody" class="row">

    </div>
    <button class="btn btn-primary" type="submit">
        <c:if test = "${product.id != null}"> Edit User </c:if>
        <c:if test = "${product.id == null}"> Create User </c:if>
    </button>
    <a href="/products" class="btn btn-secondary" onclick="console.log(${product.toString()})">Back</a>
</form>
<script src="../base.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<%--    <% var user = (User) request.getAttribute("user"); %>--%>
<script>
    <%--const user =<%= user%>;--%>
    //lấy được userở đây mấy ae tra google để
    // lấy data user từ controller
    let product = ${productJSON};
    <%--const genders = ${genderJSON};--%>
    const inputs = [
        {
            label: "Name",
            name: "name",
            pattern: "^[A-Za-z ]{6,20}",
            message: "Name must have minimun is 6 charaters and maximun is 20 charaters",
            require: true,
            classDiv: 'col-6',
            value: product.name || ''
        },
        {
            name:'id',
            value: product.id,
            type: 'hidden',
            classDiv: 'd-none'
        },
        {
            label: "Price",
            name: "price",
            message: "Price invalid",
            require: true,
            value:  product.price || '',
            pattern: "[0-9]{1,9999}",
            classDiv: 'col-6'
        },
        {
            label: "Quantity",
            name: "quantity",
            message: "Quantity invalid",
            // disable: product.quantity,
            require: true,
            value:  product.quantity || '',
            classDiv: 'col-6'
        },
        {
            label: "Category",
            name: "idCategory",
            require: true,
            value:  product.idCategory || '',
            classDiv: 'col-6'
        },
        {
            label: "Avatar",
            name: "avatar",
            require: true,
            value:  product.avatar || '',
            classDiv: 'col-6'
        }, {
            label: "Description",
            name: "description",
            require: true,
            value:  product.description || '',
            classDiv: 'col-6'
        },
    ];
    // phải có những dòng dưới này

    const formBody = document.getElementById('formBody'); // DOM formBody theo id

    // loop qua inputs
    inputs.forEach((props, index) => {
        // vẽ từng ô input
        formBody.innerHTML += formInput(props, index);
    })
</script>

</body>
</html>