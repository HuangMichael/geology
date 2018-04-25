<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->

<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
    <%@include file="../common/common-header.jsp" %>
    <style>
        #optionsDiv {
            background-color: dimgray;
            color: white;
            z-index: 23;
            position: absolute;
            top: 100px;
            right: 160px;
            border-radius: 5px;
            padding: 10px;
            /*max-width: 350px;*/

            min-width: 300px;
        }

        .esri-popup .esri-popup-header .esri-title {
            font-size: 18px;
            font-weight: bolder;
        }

        .esri-popup .esri-popup-body .esri-popup-content {
            font-size: 14px;
        }

        #doBtn {
            height: 28px;
            line-height: 18px;
            padding: 5px;
            margin-bottom: 10px;
            border-radius: 4px;
        }
    </style>
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>空间分析-区块空间分析</h2>
    </div>
    <div id="viewDiv"></div>
    <div id="optionsDiv">
        <select id="attSelect" style="width:auto">
            <option value="city_id">城市</option>
        </select>
        <select id="signSelect" style="width:auto">
            <option value="=">=</option>
        </select>
        <select id="valSelect" style="width:auto">
            <option value="165">连云港市</option>
            <option value="173">盐城市</option>
            <option value="167">南通市</option>
        </select>
        <input id="doBtn" type="button" class="btn btn-sm" value="查询"/>
        <div id="printResults"></div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<script src="js/vendor/jquery-1.9.1.min.js"></script>
<script src="js/vue-2.2.6.js"></script>
<script src="js/vendor/bootstrap-slider.js"></script>
<!-- bootstrap slider plugin -->
<script src="js/vendor/jquery.sparkline.min.js"></script>
<!-- small charts plugin -->
<!-- editable fields plugin -->
<script src="js/thekamarel.min.js"></script>
<script src="js/app/menu.js"></script>
<%--<script src="js/app/map_common.js"></script>--%>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="js/common/common-title.js"></script>
<script type="text/javascript">
    $(function () {
        initMenus("topMenu");
    })
</script>
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/common/common-gis.js"></script>
<script type="text/javascript" src="/js/common/common-gis.js"></script>
<script type="text/javascript" src="/js/app/gis_config.js"></script>
<script type="text/javascript" src="/js/common/common-utils.js"></script>

<script type="text/javascript" src="/js/echarts.js"></script>
<%@include file="../common/common-arcgis-resource.jsp" %>
<script type="text/javascript" src="/js/gis/gis_query_area.js"></script>
</body>
</html>