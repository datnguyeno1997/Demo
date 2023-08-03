
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha512-3gJwYpMe3QewGELv8k/BX9vcqhryRdzRMxVfq6ngyWXwo03GFEzjsUm8Q7RZcHPHksttq7/GFoxjCVUjkjvPdw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" integrity="sha512-VEd+nq25CkR676O+pLBnDW09R7VQX9Mdiij052gVCp5yVH3jGtH70Ho/UUv4mJDsEdTvqRCFZg0NKGiojGnUCw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css" integrity="sha512-3pIirOrwegjM6erE5gPSwkUzO+3cTjpnV9lexlNZqvupR64iZBnOOTiiLPb9M36zpMScbmUNIcHUqKD47M719g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<div class="container card" style="height: 100vh">
    <div class="toast-body d-none"  id="message">
        ${message}
    </div>

    <h1>Product</h1>
    <div class="col-2">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/products?action=create"> Create</a>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>
                No.
            </th>
            <th>
                Name
            </th>
            <th>
                Price
            </th>
            <th>
                Quantily
            </th>
            <th>
                Category
            </th>
            <th>
                Avatar
            </th>
            <th>
                Description
            </th>
            <th>
                Create_at
            </th>
            <th>
                Action
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product" varStatus="loop">
            <tr>
                <td>
                        ${loop.index + 1}
                </td>
                <td>
                        ${product.name}
                </td>
                <td>
                        ${product.price}
                </td>
                <td>
                        ${product.quantity}
                </td>
                <td>
                        ${product.name}
                </td>
                <td>
                        ${product.avatar}
                </td>
                <td>
                        ${product.description}
                </td>
                <td>
                        ${product.createAt}
                </td>
                <td>
                    <a class="btn btn-secondary" href="/products?action=edit&id=${product.id}"> Edit</a>
                    <a class="btn btn-danger" href="/products?action=delete&id=${product.id}"
                       onclick="return confirm('Do you wanna delete this ${product.name}')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>




<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

<script>
    const message = document.getElementById('message');
    const btnToast = document.getElementById('liveToastBtn');
    window.onload = () => {
        if(message.innerHTML.trim() !== ''){
            toastr.success(message.innerHTML);
        }
    }

</script>
</body>
</html>