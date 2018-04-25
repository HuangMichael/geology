<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->

<!--[if gt IE 8]><!-->
<html class="no-js" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<!--<![endif]-->

<head>
    <%@include file="../common/common-header.jsp" %>
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="content">
    <div class="container-fluid" style="padding:0px">
        <div class="row-fluid">
            <div class="containerHeadline tableHeadline">
                <i class="icon-table"></i>
                <h2>地理空间分析-叠加分析</h2>
            </div>
            <div class="span12">
                <div class="floatingBox" id="contentContainer">
                    <div id="container">
                    </div>
                    <div class="layer">
                        <img id="bigPic" src="" alt="">
                    </div>
                    <div class="button-group">
                        <input id="distanceBtn" type="button" class="button" value="测量距离"/>
                        <input id="areaBtn" type="button" class="button" value="测量面积"/>
                    </div>
                    <div id="tip">
                        <strong>查询</strong> 市：
                        <select id='city' style="width:100px" onchange='search(this)'>
                        </select>
                    </div>
                    <div id="legend">
                        <table id="legendTable">
                            <thead>
                            <tr>
                                <th width="50%" align="center">图例</th>
                                <th width="50%" align="center"></th>
                            </tr>
                            </thead>
                            <tbody id="legendBody">

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>

<%@include file="../common/common-footer_map.jsp" %>
<script type="text/javascript " src="/js/app/geoOverlay.js"></script>
</body>
</html>