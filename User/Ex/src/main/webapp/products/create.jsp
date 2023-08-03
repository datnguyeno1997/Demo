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
  <div id="formBody" class="row">

  </div>
  <button class="btn btn-primary" type="submit">
    <c:if test = "${product.id != null}"> Edit Product </c:if>
    <c:if test = "${product.id == null}"> Create Product </c:if>
  </button>
  <a href="/products" class="btn btn-secondary" onclick="console.log(${product.toString()})">Back</a>
</form>
<script src="../base.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<script>

  var product = ${productJSON};
  const inputs = [
    {
      label: "Author",
      name: "author",
      type: "select",
      message: "Please choose author",
      options: [
        {value: "TOKUDA", name: "TOKUDA"},
        {value: "KENTO", name: "KENTO"}
      ],
      require: true,
      value: product.author || '',
      classDiv: 'col-6'
    },
    {
      name:'id',
      value: product.id,
      type: 'hidden',
      classDiv: 'd-none'
    },
    {
      label: "Category",
      name: "category",
      type: "select",
      message: "Please choose category",
      options: [
        {value: "ACTION", name: "ACTION"},
        {value: "HORROR", name: "HORROR"},
        {value: "COMEDY", name: "COMEDY"},
        {value: "DRAMA", name: "DRAMA"}
      ],
      require: true,
      value: product.category || '',
      classDiv: 'col-6'
    },
    {
      label: "Title",
      name: "title",
      pattern: "^[A-Za-z ]{10,50}",
      message: "title must have minimun is 10 charaters and maximun is 50 charaters",
      require: true,
      classDiv: 'col-6',
      value: product.title || ''
    },
    {
      label: "Date Publish",
      name: "datePublish",
      pattern: "[0-9]{4}+[-]+[0-9]{1,2}+[-][0-9]{1,2}",
      type: "date",
      message: "Date Publish invalid",
      require: true,
      value:  product.datePublish || '',
      classDiv: 'col-6'
    },
    {
      label: "Description",
      name: "description",
      pattern: "^[A-Za-z0-9 ]{10,100}",
      message: "title must have minimun is 10 charaters and maximun is 100 charaters",
      require: true,
      classDiv: 'col-6',
      value: product.description || ''
    }
  ];

  const formBody = document.getElementById('formBody');

  inputs.forEach((props, index) => {
    formBody.innerHTML += formInput(props, index);
  })
</script>

</body>
</html>