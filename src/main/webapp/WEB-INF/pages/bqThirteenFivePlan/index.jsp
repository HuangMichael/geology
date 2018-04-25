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
<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">围垦区块</div>
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
                <label for="planDesc" class="col-md-2 control-label">区块规划描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="planDesc" name="planDesc" placeholder="区块规划描述">
                </div>
            </div>

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
                    <select class="form-control" id="district" name="districtId" >
                        <option value="">---请选择所在区县---</option>
                        <template v-for="d in districtArray">
                            <option :value="d.id">{{d.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="reportSize" class="col-md-2 control-label">上报面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="reportSize" name="reportSize" placeholder="上报面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="buildSize" class="col-md-2 control-label">规划面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="buildSize" name="buildSize" placeholder="规划面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="nyBuildSize" class="col-md-2 control-label">农业用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="nyBuildSize" name="nyBuildSize" placeholder="农业用地面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="stBuildSize" class="col-md-2 control-label">生态用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="stBuildSize" name="stBuildSize" placeholder="生态用地面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="jsBuildSize" class="col-md-2 control-label">建设用地面积(万亩)：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="jsBuildSize" name="jsBuildSize" placeholder="建设用地面积">
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
                    <h2>查询统计-基础数据查询-十三五规划</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%--<input type="text" class="form-control" id="beginYear" placeholder="开始年份">--%>
                                <%--<input type="text" class="form-control" id="endYear" placeholder="结束年份">--%>
                                <%@include file="../common/common-searchBox.jsp" %>
                                <button type="button" class="btn-button" onclick="complexSearch()"><i
                                        class="icon-search"></i></button>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table  table-bordered table-hover bootgrid-table" id="dataTableList"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="areaNo" data-width="5%">区块编号</th>
                                    <th data-column-id="areaName" data-width="10%">所属区块</th>
                                    <th data-column-id="planDesc" data-width="8%">区块规划描述</th>
                                    <th data-column-id="beginYear" data-converter="datetime" data-width="8%">开始年份</th>
                                    <th data-column-id="endYear" data-converter="datetime" data-width="8%">结束年份</th>
                                    <th data-column-id="cityName" data-width="6%">市</th>
                                    <th data-column-id="districtName" data-width="6%">县</th>
                                    <th data-column-id="reportSize" data-width="8%">上报面积(万亩)</th>
                                    <th data-column-id="buildSize" data-width="8%">规划面积(万亩)</th>
                                    <th data-column-id="nyBuildSize" data-width="8%">农业用地面积(万亩)</th>
                                    <th data-column-id="stBuildSize" data-width="8%">生态用地面积(万亩)</th>
                                    <th data-column-id="jsBuildSize" data-width="8%">建设用地面积(万亩)</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false" data-width="6%">查看详细</th>
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
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/app/sysMngThirteenFivePlan.js"></script>
<script type="text/javascript" src="/js/app/bqThirteenFivePlan.js"></script>
<script>
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                searchPhrase: getSearchParams()
            };
        },
        url: "/bqThirteenFivePlan/data",

        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>"
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                // to: function (value) {return moment(value).format('YYYY-MM');}
                to: function (value) {return value.substring(0,7);}
            }
        }
    })
</script>
</body>
</html>