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

    <style>

        #BasemapToggle {
            position: absolute;
            top: 20px;
            right: 20px;
            z-index: 50;
        }

    </style>
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>


<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">海岸线信息</div>
    <form class="form-horizontal" id="coastLineForm">

        <div class="row-fluid">
            <div class="form-group " >

                <label for="lineNo" class="col-md-2 control-label">海岸线编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="lineNo" name="lineNo" placeholder="海岸线编号">
                </div>
            </div>

            <div class="form-group " >
                <label for="lineName" class="col-md-2 control-label">海岸线名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="lineName" name="lineName" placeholder="海岸线名称">
                </div>
            </div>

            <div class="form-group " >
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

            <div class="form-group " >
                <label for="lineLength" class="col-md-2 control-label">海岸线长度：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="lineLength" name="lineLength" placeholder="海岸线长度">
                </div>
            </div>

            <div class="form-group " >
                <label for="typeNameSelect" class="col-md-2 control-label">海岸线类型：</label>
                <div class="col-md-3">
                    <select class="form-control" id="typeNameSelect" name="coastLineTypeId">
                        <option value="">--请选择海岸线类型--</option>
                        <template v-for="l in typeNameArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group " >
                <label for="startPoint" class="col-md-2 control-label">起点所在地：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="startPoint" name="startPoint" placeholder="起点所在地">
                </div>
            </div>

            <div class="form-group " >
                <label for="endPoint" class="col-md-2 control-label">终点所在地：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="endPoint" name="endPoint" placeholder="终点所在地" >
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status">
                        <option value="1">已审核</option>
                        <option value="0">未审核</option>
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
        <h2>系统管理-地理信息录入-海岸线创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h3>海岸线图层</h3>
            <div id="templatePickerDiv"></div>
            <ul id="nyc_graphics">
                <li>加载中&hellip;</li>
            </ul>
            <div id="barcon" name="barcon"></div>
        </div>
        <div id="viewDiv">



        </div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer_map.jsp" %>
<%@include file="../common/common-arcgis-resource321.jsp" %>
<script type="text/javascript" src="/js/app/coastLine.js"> </script>

<script type="text/javascript">
    var path = getGisServerIp();
    var main = "coastLine";
    var main2 = "coastLineAll";
    var colName = "lineName";
    var landusePolygonLayer = "";
    var basemap = "satellite";
    var attrName = "海岸线";
    var attrNameDB = "line";//数据库中地理信息的名称。如：海岸线为line
    var obj = {};
    var formId = "#coastLineForm";
    $(function () {
        initMenus("topMenu");
        $("#saveBtn").on("click", function () {
            loadFeatures(main, colName);
        });
    })
</script>

<script>
    commonGisMap2(basemap,main,main2,path,colName,landusePolygonLayer,attrNameDB,attrName,obj,formId);
</script>
</body>

</html>