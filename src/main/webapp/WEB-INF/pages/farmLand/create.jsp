\<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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


<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">耕地信息</div>
    <form class="form-horizontal" id="farmLandForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="landNo" class="col-md-2 control-label">耕地编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="farmLand.id"/>
                    <input type="text" class="form-control" id="landNo" name="landNo" placeholder="耕地编号"
                           v-model="farmLand.landNo">
                </div>
            </div>

            <div class="form-group ">
                <label for="landName" class="col-md-2 control-label">耕地名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="landName" name="landName" placeholder="耕地名称"
                           v-model="farmLand.landName">
                </div>
            </div>

            <div class="form-group ">
                <label for="landSize" class="col-md-2 control-label">耕地面积：</label>
                <div class="col-md-3">
                    <input type="number" class="form-control" id="landSize" name="landSize" placeholder="耕地面积"
                           v-model="farmLand.landSize" step="0.1">
                </div>
            </div>

            <div class="form-group ">
                <label for="landType" class="col-md-2 control-label">土壤类型：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="landType" name="landType" placeholder="土壤类型"
                           v-model="farmLand.landType">
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select class="form-control" id="status" name="status" v-model="farmLand.status">
                        <option value="0">禁用</option>
                        <option value="1" selected="true">启用</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="saveBtn">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>


<div class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>系统管理-地理信息录入-耕地信息创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h2>耕地图层信息</h2>
            <div id="templatePickerDiv"></div>
            <ul id="nyc_graphics">
                <li>加载中&hellip;</li>
            </ul>
            <div id="barcon" name="barcon"></div>
        </div>
        <div id="viewDiv"></div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer_map.jsp" %>
<%@include file="../common/common-arcgis-resource321.jsp" %>
<script type="text/javascript" src="/js/app/farmLand.js"> </script>
<script type="text/javascript">
    var path = getGisServerIp();
    var main = "farmLand";
    var main2 = "farmLand";
    var colName = "landName";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrNameDB = "land";
    var formId = "#farmLandForm";
    var attrName = "耕地";
    var obj = {};
    $(function () {
        initMenus("topMenu");

        $("#saveBtn").on("click", function () {

            loadFeatures(main, colName);
        });
    })
</script>
<script>
    commonGisMap2(basemap, main, main2, path, colName, landusePolygonLayer, attrNameDB, attrName, obj, formId);

</script>
</body>

</html>