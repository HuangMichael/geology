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
    <div class="modal-body">围垦进度信息</div>
    <%--<form class="form-horizontal" id="areaBuildingForm">--%>
        <form class="form-horizontal" id="areaForm">
        <div class="row-fluid">
            <div class="form-group ">
                <label for="area" class="col-md-2 control-label">所属区块：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <select type="text" class="form-control" id="area" name="areaId" placeholder="所属区块">
                        <option value="">---请选择所属区块---</option>
                        <template v-for="a in areaArray">
                            <option :value="a.id">{{a.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="buildDesc" class="col-md-2 control-label">区块围垦描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="buildDesc" name="buildDesc" placeholder="区块围垦描述"/>
                </div>
            </div>

            <%--<div class="form-group ">--%>
                <%--<label for="city" class="col-md-2 control-label">所在市：</label>--%>
                <%--<div class="col-md-3">--%>
                    <%--<select class="form-control" id="city" name="cityId">--%>
                        <%--<option value="">---请选择所在市---</option>--%>
                        <%--<template v-for="c in citiesArray">--%>
                            <%--<option :value="c.id">{{c.text}}</option>--%>
                        <%--</template>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group ">--%>
                <%--<label for="district" class="col-md-2 control-label">所在区县：</label>--%>
                <%--<div class="col-md-3">--%>
                    <%--<select class="form-control" id="district" name="districtId" >--%>
                        <%--<option value="">---请选择所在区县---</option>--%>
                        <%--<template v-for="d in districtArray">--%>
                            <%--<option :value="d.id">{{d.text}}</option>--%>
                        <%--</template>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>

            <div class="form-group ">
                <label for="beginYear" class="col-md-2 control-label">开始年份：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="beginYear" name="beginYear" placeholder="开始年份" onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>

            <div class="form-group ">
                <label for="endYear" class="col-md-2 control-label">结束年份：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="endYear" name="endYear" placeholder="结束年份" onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>
            <div class="form-group ">
                <label for="buildSize" class="col-md-2 control-label">总围垦面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="number" class="form-control buildSize" id="buildSize" name="buildSize"
                           placeholder="总围垦面积" step="0.01">
                    <div class="buildSize_error"></div>
                </div>
            </div>
            <div class="form-group ">
                <label for="nyBuildSize" class="col-md-2 control-label">农业用地面积：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="nyBuildSize" name="nyBuildSize" placeholder="农业用地面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="stBuildSize" class="col-md-2 control-label">生态用地面积：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="stBuildSize" name="stBuildSize" placeholder="生态用地面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="jsBuildSize" class="col-md-2 control-label">建设用地面积：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="jsBuildSize" name="jsBuildSize" placeholder="建设用地面积">
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
        <h2>系统管理-地理信息录入-围垦进度创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h3>围垦进度信息</h3>
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
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/app/sysMngAreaBuilding.js"></script>
<%--<script type="text/javascript" src="/js/app/bqAreaBuilding.js"></script>--%>

<script type="text/javascript">
    var path = getGisServerIp();
    var main = "areaBuilding";
    var main2 = "areaBuilding";
    var main3 = "areaBuilding";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrName = "围垦进度";
    var obj = {};
    var formId = "#areaForm";
    $(function () {
        initMenus("topMenu");
        $("#saveBtn").on("click", function () {

            loadFeatures2(main3);
        });
    })
</script>
<script>
    commonGisMap(basemap, main, main2, main3, path, landusePolygonLayer,attrName,obj,formId)

</script>

</body>

</html>