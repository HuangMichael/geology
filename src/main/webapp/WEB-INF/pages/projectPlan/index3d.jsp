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
<div id="contentWindow" class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>项目管理-项目规划</h2>
        <span class="pull-right" onclick="showList(view)"><i class="icon-eye-open "></i>显示列表</span>
        <%@include file="../common/common-full-screen.jsp" %>
    </div>


    <div class="panel-container">
        <div class="panel-side" id="panelSide" style="display: none">
            <h2>规划项目信息</h2>
            <ul id="nyc_graphics">
                <li>加载中&hellip;</li>
            </ul>
            <div id="barcon" name="barcon"></div>
        </div>
        <div id="viewDiv">
            <div class="selectBox">
                <div id="selectLocationBox">
                    <select name="selectLocation" id="selectLocation" width="100px">

                    </select>
                </div>

                <div id="selectAreaBox">
                    <select name="selectProject" id="selectProject">
                        <option value="">请选择项目信息</option>
                    </select>
                </div>

                <div id="selectReset" onclick="selectReset()">
                    <img src="img/rese.png" alt="">
                </div>
            </div>
            <div id="map_info_panel">
                <a id="closed"><img src="img/closed.png" alt=""></a>
                <div id="stat_desc"  class="text-indent">
                </div>
                <div id="stat_datalist" style="min-height: 30%">

                </div>
                <div id="stat_charts">
                    <div id="stat_chart">

                    </div>
                    <a id="toggleChart">
                        <%--<img src="img/turn.png" alt="" title="切换图表">--%>
                        <i class="icon-exchange" style="color: #fff"></i>
                    </a>
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
<script src="js/common/common-title.js"></script>
<script type="text/javascript">
    $(function () {
        initMenus("topMenu");
    })
</script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="js/app/gis_config.js"></script>
<script type="text/javascript" src="/js/echarts.js"></script>
<%@include file="../common/common-arcgis-resource.jsp" %>
<script type="text/javascript" src="/js/common/common-crud.js"></script>
<script type="text/javascript" src="/js/gis/gis_xmgh.js"></script>
<script type="text/javascript" src="/js/page.js"></script>

</body>

</html>