<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width,user-scalable=no">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <title>项目信息编辑</title>
    <link rel="stylesheet" href="https://js.arcgis.com/3.21/dijit/themes/nihilo/nihilo.css">
    <link rel="stylesheet" href="https://js.arcgis.com/3.21/esri/css/esri.css">
    <style>
        html, body, #mainWindow {
            font-family: sans-serif;
            height: 100%;
            width: 100%;
        }

        html, body {
            margin: 0;
            padding: 0;
        }

        #header {
            height: 80px;
            width: 200px;
            overflow: auto;
            padding: 0.5em;
        }
    </style>

    <script src="https://js.arcgis.com/3.21/"></script>
    <script src="/js/jquery-1.9.1.min.js"></script>
    <script src="/js/gis/gis_project_edit.js"></script>
</head>
<body class="nihilo">
<div id="mainWindow" data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'headline'">
    <div id="header" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'right'">
        <span>绘制<br/></span>
        <button data-dojo-type="dijit/form/Button" name="POINT">项目信息</button>
        <button data-dojo-type="dijit/form/Button" name="POLYGON">围垦区块</button>
    </div>
    <div id="map" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"></div>
</div>

</body>
</html>


