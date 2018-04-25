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
<div id="contentWindow" class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>围垦概况-围垦现状</h2>
        <%@include file="../common/common-full-screen.jsp" %>
    </div>
    <div id="viewDiv">
        <div class="selectBox">
            <div id="selectLocationBox">
                <select name="selectLocation" id="selectLocation">
                </select>
            </div>
            <div id="selectAreaBox">
                <select name="selectArea" id="selectArea">
                    <option value="">请选择区块信息</option>
                </select>
            </div>
            <div id="selectReset" onclick="selectReset()">
                <img src="img/rese.png" alt="">
            </div>
        </div>
        <div id="map_info_panel">
            <a id="closed"><img src="img/closed.png" alt=""></a>
            <div id="stat_desc" class="text-indent">
            </div>
            <div id="stat_datalist" style=" min-height: 30%; overflow: auto;">
                <table id="stat_tablelist">
                    <tr>
                        <td width='20%'>序号</td>
                        <td width='30%'>行政区划</td>
                        <td width='50%'>已围面积(单位：万亩)</td>
                    </tr>
                    <tbody id="stat_tablelistBody">

                    </tbody>
                </table>
            </div>
            <div id="stat_charts" style="max-height:30%;">
                <div id="stat_chart">

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
    <%--<script type="text/javascript" src="/js/app/common_crud.js"></script>--%>
    <script type="text/javascript" src="/js/app/gis_config.js"></script>
    <script type="text/javascript" src="/js/common/common-utils.js"></script>
    <script type="text/javascript" src="/js/echarts.js"></script>
    <%@include file="../common/common-arcgis-resource.jsp" %>
    <script type="text/javascript" src="/js/gis/gis_wkxz.js"></script>
</body>
</html>