<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20 0020
  Time: 15:53
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
<div class="modal fade confirm_modal subfield" id="confirm_modal">
    <div class="modal-body">项目信息</div>
    <form class="form-horizontal subfield" id="projectForm">
        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="projectNo" class="col-md-2 control-label">项目编号：</label>
                    <div class="col-md-3">
                        <input type="hidden" id="id" name="id"/>
                        <input type="text" class="form-control" id="projectNo" name="projectNo" placeholder="项目编号">
                        <div class="projectNo_error"></div>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectName" class="col-md-2 control-label">项目名称：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectName" name="projectName" placeholder="项目名称">
                        <div class="projectName_error"></div>
                    </div>
                </div>
            </div>


            <div class="form-group ">
                <div class="pull-left">
                    <label for="area" class="col-md-2 control-label">所属区块：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="area" name="areaId" placeholder="所属区块">
                            <option value="">---请选择所属区块---</option>
                            <template v-for="a in areaArray">
                                <option :value="a.id">{{a.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="city" class="col-md-2 control-label">所属市：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="city" name="cityId" placeholder="所属市">
                            <option value="">---请选择所属市---</option>
                            <template v-for="l in citiesArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="district" class="col-md-2 control-label">所属县区：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="district" name="districtId" placeholder="所属县区">
                            <option value="">---请选择所属县区---</option>
                            <template v-for="l in districtArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="beginYear" class="col-md-2 control-label">开始年份：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="beginYear" name="beginYear"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="开始年份">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="endYear" class="col-md-2 control-label">结束年份：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="endYear" name="endYear"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="结束年份">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectSize" class="col-md-2 control-label">项目占地面积(万亩)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectSize" name="projectSize"
                               placeholder="项目占地面积">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="mainPurpose" class="col-md-2 control-label">主要用途：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="mainPurpose" name="mainPurpose" placeholder="主要用途">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectLeader" class="col-md-2 control-label">项目负责人：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectLeader" name="projectLeader"
                               placeholder="项目负责人">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="consUnit" class="col-md-2 control-label">建设单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="consUnit" name="consUnit" placeholder="建设单位">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="monitorUnit" class="col-md-2 control-label">监理单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="monitorUnit" name="monitorUnit" placeholder="验收单位">
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="acceptUnit" class="col-md-2 control-label">验收单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="acceptUnit" name="acceptUnit" placeholder="验收单位">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="landUsingType" class="col-md-2 control-label">用地类型：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="landUsingType" name="landUsingTypeId"
                                placeholder="用地类型">
                            <option value="">---请选择用地类型---</option>
                            <template v-for="l in landUsingTypeArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="engineeringType" class="col-md-2 control-label">工程类型：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="engineeringType" name="engineeringTypeId"
                                placeholder="工程类型">
                            <option value="">---请选择工程类型---</option>
                            <template v-for="l in engineeringTypeArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="buildStatus" class="col-md-2 control-label">建设状态：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="buildStatus" name="buildStatus"
                                placeholder="项目状态">
                            <option value="">---请选择建设状态---</option>
                            <option value="0">规划</option>
                            <option value="1">在建</option>
                            <option value="2">已建</option>
                        </select>
                    </div>
                </div>
            </div>


            <div class="form-group ">
                <div class="pull-left">
                    <label for="leaderContact" class="col-md-2 control-label">项目负责人电话：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="leaderContact" name="leaderContact"
                               placeholder="项目负责人电话">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="important" class="col-md-2 control-label">是否重大项目</label>
                    <div class="col-md-3">
                        <select class="form-control" id="important" name="important">
                            <option value="0" selected="true">普通项目</option>
                            <option value="1">重大项目</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left" hidden>
                    <label for="status" class="col-md-2 control-label">状态：</label>
                    <div class="col-md-3">
                        <select class="form-control" id="status" name="status">
                            <option value="0" selected="true">未审核</option>
                            <option value="1">已审核</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </form>
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
                    <h2>查询统计-基础数据查询-项目信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%@include file="../common/common-searchBoxProjectWithDate.jsp" %>
                                <button type="button" class="btn-button" onclick="complexSearch()"><i
                                        class="icon-search"></i></button>
                                <%--<%@include file="../common/common-buttonCrudGroup.jsp" %>--%>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="3%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="projectNo" data-width="4%">项目编号</th>
                                    <th data-column-id="projectName" data-width="8%">项目名称</th>
                                    <th data-column-id="areaName" data-width="7%">所属区块</th>
                                    <th data-column-id="cityName" data-width="5%">所属市</th>
                                    <th data-column-id="districtName" data-width="5%">所属县区</th>
                                    <th data-column-id="beginYear" data-converter="datetime" data-width="5%">开始年份</th>
                                    <th data-column-id="endYear" data-converter="datetime" data-width="5%">结束年份</th>
                                    <th data-column-id="projectSize" data-width="5%">占地面积(万亩)</th>
                                    <th data-column-id="mainPurpose" data-width="8%" data-visible="false">主要用途</th>
                                    <th data-column-id="projectLeader" data-width="8%" data-visible="false">项目负责人</th>
                                    <th data-column-id="leaderContact" data-width="8%" data-visible="false">负责人电话</th>
                                    <th data-column-id="consUnit" data-width="8%" data-visible="false">施工单位</th>
                                    <th data-column-id="monitorUnit" data-width="8%" data-visible="false">监管单位</th>
                                    <th data-column-id="acceptUnit" data-width="8%" data-visible="false">验收单位</th>
                                    <th data-column-id="landUsingType" data-width="4%">用地类型</th>
                                    <th data-column-id="engineeringType" data-width="4%">工程类型</th>
                                    <th data-column-id="buildStatus" data-width="4%">建设状态</th>
                                    <th data-column-id="budget" data-width="4%" data-visible="false">预算金额(万元)</th>
                                    <th data-column-id="investedSum" data-width="4%" data-visible="false">已投资金额(万元)</th>
                                    <th data-column-id="taskProgress" data-width="4%" data-visible="false">投资进度</th>
                                    <th data-column-id="important" data-width="4%" data-visible="false">是否重大项目</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="4%">查看详细
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
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
<script type="text/javascript" src="/js/app/bqProject.js"></script>

</body>
</html>