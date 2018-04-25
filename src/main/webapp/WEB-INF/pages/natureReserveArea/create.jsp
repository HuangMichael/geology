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


<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">自然核心保护区信息</div>
    <form class="form-horizontal" id="natureReserveAreaForm">

        <div class="row-fluid">

            <div class="form-group ">
                <label for="areaNo" class="col-md-2 control-label">保护区编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="area.id"/>
                    <input type="text" class="form-control" id="areaNo" name="areaNo" v-model="area.areaNo"
                           placeholder="保护区编号">
                </div>
            </div>

            <div class="form-group ">
                <label for="areaName" class="col-md-2 control-label">保护区名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="areaName" name="areaName" v-model="area.areaName"
                           placeholder="保护区名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="areaDesc" class="col-md-2 control-label">保护区描述：</label>
                <div class="col-md-3">
                    <textarea type="text" class="form-control" id="areaDesc" name="areaDesc" v-model="area.areaDesc"
                              placeholder="保护区描述" rows="5" style="width:58%;resize: none;"></textarea>
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status" v-model="area.status">
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
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
        <h2>系统管理-地理信息录入-自然核心区创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h4>自然核心保护区图层</h4>
            <div id="templatePickerDiv"><i class="icon-pencil"></i></div>
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
<script type="text/javascript" src="/js/app/natureReserveArea.js"> </script>
<script type="text/javascript">
    var path = getGisServerIp();
    var main = "natureReserveArea";
    var main2 = "natureReserveAreaAll";
    var colName = "areaName";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrNameDB = "area";
    var formId = "#natureReserveAreaForm";
    var attrName = "自然保护核心区";
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