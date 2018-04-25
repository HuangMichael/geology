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
    <div class="modal-body">可行性研究报告</div>
    <form class="form-horizontal subfield" id="projectReportForm">

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
                    <label for="beginDate" class="col-md-2 control-label">实施开始时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="beginDate" name="beginDate" placeholder="实施开始时间"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="endDate" class="col-md-2 control-label">实施结束时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="endDate" name="endDate" placeholder="实施结束时间"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectSize" class="col-md-2 control-label">匡围面积(万亩)：</label>
                    <div class="col-md-3">
                        <input type="number" class="form-control buildSize" id="projectSize" name="projectSize"
                               placeholder="匡围面积" step="0.1">
                        <div class="buildSize_error"></div>
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="dykeLevel" class="col-md-2 control-label">围堤等级：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="dykeLevel" name="dykeLevel" placeholder="围堤等级">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="designStandard" class="col-md-2 control-label">设计标准：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="designStandard" name="designStandard"
                               placeholder="设计标准">
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="dykeLineSetting" class="col-md-2 control-label">堤线布置：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="dykeLineSetting" name="dykeLineSetting"
                               placeholder="堤线布置">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="dykeSectionalType" class="col-md-2 control-label">堤身断面类型：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="dykeSectionalType" name="dykeSectionalType"
                               placeholder="堤身断面类型">
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="dykeTopHeight" class="col-md-2 control-label">堤顶高程：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="dykeTopHeight" name="dykeTopHeight"
                               placeholder="堤顶高程">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="dykeTopWidth" class="col-md-2 control-label">堤顶宽度：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="dykeTopWidth" name="dykeTopWidth"
                               placeholder="堤顶宽度">
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="slopeRatio" class="col-md-2 control-label">坡比：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="slopeRatio" name="slopeRatio" placeholder="坡比">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="investedSum" class="col-md-2 control-label">投资额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSum" name="investedSum" placeholder="投资额">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="investedProvince" class="col-md-2 control-label">省级补助资金(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedProvince" name="investedProvince"
                               placeholder="省级补助资金">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="investedCity" class="col-md-2 control-label">市县补助资金(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedCity" name="investedCity"
                               placeholder="市县补助资金">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="investedSelf" class="col-md-2 control-label">自筹资金(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSelf" name="investedSelf"
                               placeholder="自筹资金">
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
                        <select type="text" class="form-control" id="status" name="status" placeholder="状态">
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
                    <h2>项目管理-项目建设-可行性研究报告</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%@include file="../common/common-searchBoxProjectWithDate.jsp" %>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                                <button type="button" class="btn-button" onclick="complexSearch()"><i
                                        class="icon-search"></i></button>
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
                                    <th data-column-id="beginDate" data-converter="datetime" data-width="5%">实施开始时间</th>
                                    <th data-column-id="endDate" data-converter="datetime" data-width="5%">实施结束时间</th>
                                    <th data-column-id="projectSize" data-width="5%" title="匡围面积(万亩)">匡围面积(万亩)</th>
                                    <th data-column-id="dykeLevel" data-width="5%">围堤等级</th>
                                    <th data-column-id="designStandard" data-width="6%">设计标准</th>
                                    <th data-column-id="dykeLineSetting" data-width="8%">堤线布置</th>
                                    <th data-column-id="dykeSectionalType" data-width="5%">堤身断面类型
                                    </th>
                                    <th data-column-id="dykeTopHeight" data-width="3%" data-visible="false">堤顶高程(米)</th>
                                    <th data-column-id="dykeTopWidth" data-width="3%" data-visible="false">堤顶宽度(米)</th>
                                    <th data-column-id="slopeRatio" data-width="5%" data-visible="false">坡比</th>
                                    <th data-column-id="investedSum" data-width="4%">投资额(亿元)</th>
                                    <th data-column-id="investedProvince" data-width="2%" data-visible="false">
                                        省级补助资金(亿元)
                                    </th>
                                    <th data-column-id="investedCity" data-width="2%" data-visible="false">市县补助资金(亿元)
                                    </th>
                                    <th data-column-id="investedSelf" data-width="2%" data-visible="false">自筹资金(亿元)</th>
                                    <th data-column-id="important" data-width="4%" data-visible="false">是否重大项目</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="4%">查看详情
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
<script src="/js/app/projectReport.js"></script>
<script>

</script>
</body>
</html>