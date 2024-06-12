<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My collection documents</title>
</head>
<body>
      <div>
        <h1>Коллекция текстовых документов!</h1>
      </div>
       <div>
         <div>
           <img src="https://get.wallhere.com/photo/1920x1080-px-animal-cat-cute-Kitty-1661487.jpg"
                style="width:500px; height:400px;"/>
           <h3>Введи путь к каталогу, который нужно обойти, например,</h3>
           <h4>"catalog_for_the_test"</h4>

           <form method="post" action="/structure">  <!-- метод указывает какой будет тип запроса  -->
               <input type="text" name="rootDirectory">
               <button type="submit">Отправить</button>
           </form>

<%--             <button type="button" onclick="location.href='/structure'">Открыть структуру коллекции</button>--%>
         </div>
       </div>
</body>
</html>