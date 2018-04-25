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
<div class="modal fade confirm_modal subfield project_modal" id="confirm_modal">
    <div class="modal-body">项目信息</div>
    <form class="form-horizontal subfield" id="projectForm">
        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="projectNo" class="col-md-2 control-label">项目编号：</label>
                    <div class="col-md-3">
                        <input type="hidden" id="id" name="id"/>
                        <input type="text" class="form-control" id="projectNo" name="projectNo"
                               placeholder="项目编号(仅支持字母和数字)">
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
                        <div class="errorTips error area_error"></div>
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
                        <textarea type="text" class="form-control" id="mainPurpose" name="mainPurpose"
                                  placeholder="主要用途" style="width: 57%;resize: none"></textarea>

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
                        <input type="text" class="form-control" id="monitorUnit" name="monitorUnit" placeholder="监理单位">
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
                        <select class="form-control" id="buildStatus" name="buildStatus"
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
                    <label for="budget" class="col-md-2 control-label">预算金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="budget" name="budget"
                               placeholder="预算金额">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="investedSum" class="col-md-2 control-label">已投资金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSum" name="investedSum"
                               placeholder="已投资金额">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="taskProgress" class="col-md-2 control-label">投资进度：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="taskProgress" name="taskProgress"
                               placeholder="投资进度">
                    </div>
                </div>
            </div>

            <div class="form-group ">
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

            <%--项目的可行性研究报告 填写以下专用表单--%>
            <div class="form-group ">
                <label class="col-md-3 control-label" style="color:#20b470">------------可行性研究报告------------</label>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="beginDateForReport" class="col-md-2 control-label">实施开始时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="beginDateForReport" name="beginDateForReport"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="实施开始时间">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="endDateForReport" class="col-md-2 control-label">实施结束时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="endDateForReport" name="endDateForReport"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="实施结束时间">
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
                    <label for="investedSumForReport" class="col-md-2 control-label">投资额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSumForReport" name="investedSumForReport"
                               placeholder="投资额">
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

            </div>

            <%--项目建设任务信息 填写以下专用表单--%>
            <div class="form-group ">
                <label class="col-md-3 control-label" style="color:#20b470">------------项目建设任务信息------------</label>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="beginDateForTask" class="col-md-2 control-label">实施开始时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="beginDateForTask" name="beginDateForTask"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="实施开始时间">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="endDateForTask" class="col-md-2 control-label">实施结束时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="endDateForTask" name="endDateForTask"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="实施结束时间">
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="consTask" class="col-md-2 control-label">建设任务：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="consTask" name="consTask" placeholder="建设任务">
                    </div>
                </div>

            </div>

            <%--项目验收基本信息 填写以下专用表单--%>
            <div class="form-group ">
                <label class="col-md-3 control-label" style="color:#20b470">------------项目验收基本信息------------</label>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="expert" class="col-md-2 control-label">专家组长：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="expert" name="expert" placeholder="专家组长">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="conclusion" class="col-md-2 control-label">验收结论：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="conclusion" name="conclusion" placeholder="验收结论">
                    </div>
                </div>
            </div>

            <%--共有字段：状态--%>
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
    </form>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="saveBtn">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>


<%--点击编辑按钮后弹出的编辑界面，字段较少--%>
<div class="modal fade confirm_modal subfield project_modal" id="confirm_modal_edit">
    <div class="modal-body">项目信息</div>
    <form class="form-horizontal subfield" id="projectFormForEdit">
        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="projectNo" class="col-md-2 control-label">项目编号：</label>
                    <div class="col-md-3">
                        <input type="hidden" id="id" name="id"/>
                        <input type="text" class="form-control" id="projectNo" name="projectNo"
                               placeholder="项目编号(仅支持字母和数字)">
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
                        <textarea type="text" class="form-control" id="mainPurpose" name="mainPurpose"
                                  placeholder="主要用途" style="width: 57%;resize: none"></textarea>

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
                        <input type="text" class="form-control" id="monitorUnit" name="monitorUnit" placeholder="监理单位">
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
                        <select class="form-control" id="buildStatus" name="buildStatus"
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
                    <label for="budget" class="col-md-2 control-label">预算金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="budget" name="budget"
                               placeholder="预算金额">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="investedSum" class="col-md-2 control-label">已投资金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSum" name="investedSum"
                               placeholder="已投资金额">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="taskProgress" class="col-md-2 control-label">投资进度：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="taskProgress" name="taskProgress"
                               placeholder="投资进度">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="important" class="col-md-2 control-label">是否重大项目</label>
                    <div class="col-md-3">
                        <select class="form-control" id="important" name="important">
                            <option value="0" selected="true">普通项目</option>
                            <option value="1">重大项目</option>
                        </select>
                    </div>
                </div>
                <%--共有字段：状态--%>
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
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="saveBtnForEdit">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>
<div class="content" style=" position: relative;">
    <div style="height:50px"></div>
    <div class="containerHeadline tableHeadline">
        <i class="icon-list"></i>
        <h2>系统管理-地理信息录入-项目信息创建</h2>
    </div>
    <div class="panel-container">
        <div class="panel-side">
            <h3>项目信息</h3>
            <div id="templatePickerDiv"></div>
            <ul id="nyc_graphics">
                <li>加载中...</li>
            </ul>
            <div id="barcon" name="barcon"></div>
        </div>
        <div id="viewDiv"></div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer_map.jsp" %>
<%@include file="../common/common-arcgis-resource321.jsp" %>
<script type="text/javascript" src="/js/app/projectCreate.js"></script>
</body>

</html>