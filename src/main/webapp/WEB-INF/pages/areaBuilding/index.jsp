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
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<link rel="stylesheet" href="/css/gis_map.css">

<style>

    #bottomPanel {

        position: absolute;
        bottom: 0.5em;
        margin: auto;
        width: 100%;
    }

    #timeInfo {
        height: 60px;
        border-radius: 4px;
        border: solid 2px #ccc;
        background-color: #0f0f0f;
        opacity: 0.7;
        display: block;
        /*padding: 5px;*/
        /*bottom: 90px;*/
        position: relative;
        text-align: center;
        width: 100%;
        z-index: 99;
        color: white;
    }
</style>


<div id="contentWindow" class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>围垦概况-围垦演变</h2>
        <%@include file="../common/common-full-screen.jsp" %>
    </div>

    <div class="panel-container">
        <div class="panel-side">
            <div id="buildChart" style="height: 200px; width: 280px;align-content:center"></div>
            <div id="dataTable" style="height: 120px; width: 280px"></div>
            <div id="buildDetailChart" style="height: 200px; width: 280px;align-content:center"></div>
        </div>
        <div id="viewDiv">
            <div id="bottomPanel">
                <div id="timeInfo">
                    <div>江苏省沿海滩涂围垦<span id="daterange">1900年到2030年</span>演变</div>
                    <div id="timeSliderDiv"></div>
                </div>
            </div>
        </div>
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
<script src="js/app/map_common.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="js/common/common-title.js"></script>
<script type="text/javascript">
    $(function () {
        initMenus("topMenu");
    })
</script>
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app/gis_config.js"></script>
<script type="text/javascript" src="/js/echarts.js"></script>
<script src="/js/common/common-gis.js"></script>
<script src="/js/common/common-crud.js"></script>
<script src="/js/common/common-utils.js"></script>
<script src="/js/app/gis_config.js"></script>
<script type="text/javascript" src="/js/echarts.js"></script>
<%@include file="../common/common-arcgis-resource321.jsp" %>
<script type="text/javascript" src="/js/gis/gis_wkyb.js"></script>
</body>
</html>