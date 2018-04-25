<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/13
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>

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
    <div class="modal-body">围垦进度</div>
    <form class="form-horizontal" id="areaForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="area" class="col-md-2 control-label">所属区块：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="area.id"/>
                    <select type="text" class="form-control" id="area" name="areaId" placeholder="所属区块">
                        <option value="">---请选择所属区块---</option>
                        <template v-for="a in areaArray">
                            <option :value="a.id">{{a.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="buildDesc" class="col-md-2 control-label">围垦描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="buildDesc" name="buildDesc" placeholder="围垦描述">
                </div>
            </div>

            <div class="form-group ">
                <label for="beginYear" class="col-md-2 control-label">开始年份：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="beginYear" name="beginYear" placeholder="开始年份"
                           onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>

            <div class="form-group ">
                <label for="endYear" class="col-md-2 control-label">结束年份：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="endYear" name="endYear" placeholder="结束年份"
                           onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>

            <div class="form-group ">
                <label for="city" class="col-md-2 control-label">所在市：</label>
                <div class="col-md-3">
                    <select class="form-control" id="city" name="cityId">
                        <option value="">---请选择所在市---</option>
                        <template v-for="c in citiesArray">
                            <option :value="c.id">{{c.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="district" class="col-md-2 control-label">所在区县：</label>
                <div class="col-md-3">
                    <select class="form-control" id="district" name="districtId">
                        <option value="">---请选择所在区县---</option>
                        <template v-for="d in districtArray">
                            <option :value="d.id">{{d.text}}</option>
                        </template>
                    </select>
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
                <label for="nyBuildSize" class="col-md-2 control-label">农业用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="number" class="form-control buildSize" id="nyBuildSize" name="nyBuildSize"
                           placeholder="农业用地面积" step="0.01">
                    <div class="buildSize_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="stBuildSize" class="col-md-2 control-label">生态用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="number" class="form-control buildSize" id="stBuildSize" name="stBuildSize"
                           placeholder="生态用地面积" step="0.01">
                    <div class="buildSize_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="jsBuildSize" class="col-md-2 control-label">建设用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="number" class="form-control buildSize" id="jsBuildSize" name="jsBuildSize"
                           placeholder="建设用地面积" step="0.01">
                    <div class="buildSize_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="manager" class="col-md-2 control-label">区块负责人：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="manager" name="manager" placeholder="区块负责人">
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status" placeholder="状态">
                        <option value="0" selected="true">未审核</option>
                        <option value="1">已审核</option>
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
<div class="content">
    <div class="container-fluid">
        <div class="divider-vertical" style="height: 20px;"></div>
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="mySpan10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <!-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div> -->
                    <h2>系统管理-基础数据管理-围垦进度</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox" style="display: inline">
                                <%@include file="../common/common-searchBoxWithDate.jsp" %>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table table-hover table-bordered" id="dataTableList" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="areaNo" data-width="5%">区块编号</th>
                                    <th data-column-id="areaName" data-width="14%">区块名称</th>
                                    <th data-column-id="buildDesc" data-width="6%">围垦描述</th>
                                    <th data-column-id="cityName" data-width="5%">市</th>
                                    <th data-column-id="districtName" data-width="5%">县</th>
                                    <th data-column-id="beginYear" data-converter="datetime" data-width="5%">开始年份</th>
                                    <th data-column-id="endYear" data-converter="datetime" data-width="5%">结束年份</th>
                                    <th data-column-id="buildSize" data-width="6%">总用地面积(万亩)</th>
                                    <th data-column-id="nyBuildSize" data-width="6%">农业用地面积(万亩)</th>
                                    <th data-column-id="jsBuildSize" data-width="6%">建设用地面积(万亩)</th>
                                    <th data-column-id="stBuildSize" data-width="6%">生态用地面积(万亩)</th>
                                    <th data-column-id="manager" data-width="5%">区块负责人</th>
                                    <th data-column-id="status" data-width="8%">状态</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="6%">编辑 删除
                                    </th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        <!-- ==================== END OF HOVER TABLE FLOATING BOX ==================== -->

                    </div>
                </div>
            </div>
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
        </div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/app/sysMngAreaBuilding.js"></script>
<script>
    //初始化搜索条件市区下拉列表
     city_District();
</script>
</body>
</html>