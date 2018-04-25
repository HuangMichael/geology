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
    <div class="modal-body">沿海沙洲信息</div>
    <form class="form-horizontal" id="sandLandForm">
        <div class="row-fluid">
            <div class="form-group ">
                <label for="sandNo" class="col-md-2 control-label">沙洲编号：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="sandNo" name="sandNo" placeholder="沙洲编号">
                </div>
            </div>
            <div class="form-group ">
                <label for="sandName" class="col-md-2 control-label">沙洲名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="sandName" name="sandName" placeholder="沙洲名称">
                </div>
            </div>
            <div class="form-group ">
                <label for="position" class="col-md-2 control-label">所在位置：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="position" name="position" placeholder="所在位置">
                </div>
            </div>

            <div class="form-group ">
                <label for="locationSelect" class="col-md-2 control-label">所属行政区划：</label>
                <div class="col-md-3">
                    <select class="form-control" id="locationSelect" name="locationId">
                        <option value="">--请选择行政区划--</option>
                        <template v-for="l in locationsArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                    <div class="locationSelect_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="length" class="col-md-2 control-label">南北长（公里）：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="length" name="length" placeholder="南北长（公里）">
                </div>
            </div>

            <div class="form-group ">
                <label for="width" class="col-md-2 control-label">东西宽（公里）：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="width" name="width" placeholder="东西宽（公里）">
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select class="form-control" id="status" name="status">
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
        <h2>系统管理-地理信息录入-沿海沙洲创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h3>沿海沙洲信息</h3>
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
<script type="text/javascript" src="/js/app/sandLand.js"></script>
<script type="text/javascript">
    var path = getGisServerIp();
    var main = "sandLand";
    var main2 = "sandLandAll";
    var colName = "sandName";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrNameDB = "sand";//数据库中沿海沙洲的前缀，如sandland_no和sandland_name
    var attrName = "沿海沙洲";
    var obj = {};//地理信息对象
    $(function () {
        initMenus("topMenu");
        $("#saveBtn").on("click", function () {

            loadFeatures(main, colName);
        });
    })
</script>

<script>
    commonGisMap2(basemap, main, main2, path, colName, landusePolygonLayer, attrNameDB, attrName, obj, formId)
</script>
</body>

</html>