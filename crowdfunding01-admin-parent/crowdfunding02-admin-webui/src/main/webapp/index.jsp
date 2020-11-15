<%--
  Created by IntelliJ IDEA.
  User: hual
  Date: 2020/11/9
  Time: 3:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hual</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript " src="jquery/jquery-2.1.1.min.js"></script>
     <script type="text/javascript " src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {

            $("#btn2").click(function () {
                layer.msg("layer..............");
            });
        });
    </script>
 <%--   <script type="text/javascript">
        $(function () {
            $("#btn2").click(function () {

                layer.msg("lay....................");
            });

            $("#btn1").click(function () {


                //准备好要发送到服务器端的数组
                var array = [1,3,5];
                console.log(array.length);


                //将JSON数组转换为JSON字符串
                var requestBody = JSON.stringify(array);
                // "['5','8','12']"
                console.log(requestBody.length);
                $.ajax({
                    "url":"send/array.json", //请求目标资源的地址
                    "type":"post",           //请求方式
                    "data": requestBody,
                    "contentType":"application/json;charset=UTF-8",//设置请求体的内容类型，告诉服务器本次请求的请求体是JSON数据
                    "dataType": "json",                         //如何对待服务器端返回的数据
                    "success":function (response) {
                        console.log(response.data);
                    },                       //服务器端成功处理请求后调用的回调函数
                    "error":function (response) {
                        alert(response);
                    }                        //服务器端处理请求失败后调用的回调函数
                });
            });


        });

    </script>--%>
</head>
<body>
<a href="test/ssm.html">测试SSM整合环境</a>

<br/><br/>

<button id="btn1">Send [1,3,5]</button>

<br/><br/>

<button id="btn2">点我弹框</button>
</body>
</html>
