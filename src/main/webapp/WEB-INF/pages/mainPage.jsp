<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main page TestIsida2</title>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/scripts.js"></script>
    <link id="mainCss" href="${pageContext.request.contextPath}/resources/css/mainCss.css" rel="stylesheet">
</head>
<body onload="bodyLoad()">
    <div id="loadWaitDiv">
        <img class="Spinner" src="${pageContext.request.contextPath}/resources/icons/spinner2.gif" /><br>
        <label id="waitLabel" style="font-size: 20px;"></label>
    </div>
    <div id="MessageDiv"><div></div></div>
    <div id="mainDiv" class="MainDiv">
        <div id="analyze" class="AnalyzeDiv">
            <label>Анализируемая страница</label>
            <input type="text" class="InputClass">
            <input type="button" value="Анализировать" onclick="analyze()" class="button">
        </div>
        <div id="results" class="ResultsDiv">
            <label>Обнаруженные ссылки</label>
            <div id="table" class="TableDiv">
                <table>
                    <thead>
                        <tr>
                            <th width="5%">№ п/п</th>
                            <th width="40%">Имя ссылки</th>
                            <th width="55%">Адрес ссылки</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="FooterDiv">
                <input type="button" value="Очистить" onclick="clearResults()" class="Button">
            </div>
        </div>
    </div>
</body>
</html>
