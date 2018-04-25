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
    <div class="modal-body">围垦区块</div>
    <form class="form-horizontal subfield" id="areaForm">

        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="areaNo" class="col-md-2 control-label">区块编号：</label>
                    <div class="col-md-3">
                        <input type="hidden" id="id" name="id"/>
                        <input type="text" class="form-control" id="areaNo" name="areaNo" placeholder="区块编号"/>
                        <div class="areaNo_error"></div>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="areaName" class="col-md-2 control-label">区块名称：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="areaName" name="areaName" placeholder="区块名称">
                        <div class="areaName_error"></div>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
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
                <div class="pull-left">
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
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="buildSize" class="col-md-2 control-label">规划面积(万亩)：</label>
                    <div class="col-md-3">
                        <input type="number" step="0.0001" class="form-control" id="buildSize" name="buildSize"
                               placeholder="规划面积">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="inningSize" class="col-md-2 control-label">已围面积(万亩)：</label>
                    <div class="col-md-3">
                        <input type="number" step="0.0001" class="form-control" id="inningSize" name="inningSize"
                               placeholder="已围面积">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="functionType" class="col-md-2 control-label">围垦区块功能类型：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="functionType" name="functionTypeId"
                                placeholder="围垦区块功能类型">
                            <option value="">--请选择围垦区块功能类型--</option>
                            <template v-for="l in functionTypesArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="inningStatus" class="col-md-2 control-label">围垦状态：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="inningStatus" name="inningStatus"
                                placeholder="围垦状态">
                            <option value="1" selected="true">已围</option>
                            <option value="2">未围</option>
                            <option value="3">在围</option>
                        </select>
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="mainPurpose" class="col-md-2 control-label">主要用途：</label>
                    <div class="col-md-3">
                        <textarea type="text" class="form-control" id="mainPurpose" rows="2" name="mainPurpose"
                                  style="width:58%;resize:none;" placeholder="主要用途"></textarea>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="locDesc" class="col-md-2 control-label">位置描述：</label>
                    <div class="col-md-3">
                        <textarea type="text" class="form-control" id="locDesc" name="locDesc" rows="2"
                                  style="width:58%;resize:none;" placeholder="位置描述"></textarea>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="areaType" class="col-md-2 control-label">区块类型：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="areaType" name="areaTypeId" placeholder="区块类型">
                            <option value="">--请选择区块类型--</option>
                            <template v-for="l in areaTypesArray">
                                <option :value="l.id">{{l.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="avgHeight" class="col-md-2 control-label">平均起围高程(米)：</label>
                    <div class="col-md-3">
                        <input type="number" step="0.1" class="form-control" id="avgHeight" name="avgHeight"
                               placeholder="平均起围高程">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="areaDesc" class="col-md-2 control-label">区块描述：</label>
                    <div class="col-md-3">
                    <textarea type="text" class="form-control" id="areaDesc" rows="5" name="areaDesc"
                              style="width:58%;resize:none;" placeholder="区块描述"></textarea>
                    </div>
                </div>
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
    <img style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;" src="img/title-bg.jpg"/>
    <div class="container-fluid">
        <div class="divider-vertical" style="height: 20px;"></div>
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="mySpan10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <h2>查询统计-基础数据查询-围垦区块</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%@include file="../common/common-searchBox.jsp" %>
                                <button type="button" class="btn-button" onclick="complexSearch()"><i
                                        class="icon-search"></i></button>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table table-hover table-bordered" style="margin-top:10px;columnHeaderAnchor:column-header-anchor;">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="areaNo" data-width="5%">区块编号</th>
                                    <th data-column-id="areaName" data-width="12%">区块名称</th>
                                    <th data-column-id="cityName" data-width="8%">所在市</th>
                                    <th data-column-id="districtName" data-width="8%">所在区县</th>
                                    <th data-column-id="buildSize" data-width="8%">规划面积(万亩)</th>
                                    <th data-column-id="inningSize" data-width="8%">已围面积(万亩)</th>
                                    <th data-column-id="functionType" data-width="8%">区块功能类型</th>
                                    <th data-column-id="locDesc" data-width="10%">位置描述</th>
                                    <th data-column-id="mainPurpose" data-width="15%">主要用途</th>
                                    <th data-column-id="inningStatus" data-width="5%">围垦状态</th>
                                    <th data-column-id="areaType" data-width="8%" data-visible="false">区块类型</th>
                                    <th data-column-id="avgHeight" data-width="8%" data-visible="false">平均起围高程(米)</th>
                                    <th data-column-id="areaDesc" data-width="10%" data-visible="false">区块描述</th>
                                    <th data-column-id="commands" data-formatter="commands" data-width="6%">查看详细</th>
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
<script src="/js/app/area.js"></script>
<script src="/js/app/bqArea.js"></script>
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
        url: "/bqArea/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>"
            }
        }
    })
</script>
</body>

</html>