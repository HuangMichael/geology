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
    <div class="modal-body">自然保护试验区信息</div>
    <form class="form-horizontal" id="natureReserveExperimentForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="experimentNo" class="col-md-2 control-label">试验区编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="experiment.id"/>
                    <input type="text" class="form-control" id="experimentNo" name="experimentNo" placeholder="试验区编号"
                           v-model="experiment.experimentNo"/>
                </div>
            </div>

            <div class="form-group ">
                <label for="experimentName" class="col-md-2 control-label">试验区名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="experimentName" name="experimentName"
                           placeholder="试验区名称" v-model="experiment.experimentName"/>
                </div>
            </div>

            <div class="form-group ">
                <label for="experimentDesc" class="col-md-2 control-label">试验区描述：</label>
                <div class="col-md-3">
                    <textarea type="text" class="form-control" id="experimentDesc" name="experimentDesc" placeholder="试验区描述" rows="5" style="width:58%;resize: none;"></textarea>
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status">
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
        <h2>系统管理-地理信息录入-自然试验区创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h3>自然试验区</h3>
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
<script type="text/javascript" src="/js/app/natureReserveExperiment.js"> </script>
<script type="text/javascript">
    var path = getGisServerIp();
    var main = "natureReserveExperiment";
    var main2 = "natureReserveExperimentAll";
    var colName = "experimentName";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrName = "自然保护试验区";
    var attrNameDB = "experiment";//数据库中地理信息的名称。如：海岸线为line
    var obj = {};
    var formId = "#natureReserveExperimentForm";
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