<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="box-body" style="padding: 5px 20px 5px 5px">
    <div class="btn-group">
        <button type="button" class="btn btn-sm blue">
            <i class="glyphicon glyphicon-plus"></i>
            新增记录
        </button>
        <button type="button" class="btn btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            编辑记录
        </button>
        <button type="button" class="btn btn-sm">
            <i class="glyphicon glyphicon-save"></i>
            保存记录
        </button>
        <button type="button" class="btn btn-sm">
            <i class="glyphicon glyphicon-remove"></i>
            删除记录
        </button>
        <button type="button" class="btn btn-sm">
            <i class="glyphicon glyphicon-arrow-left"></i>
            上一条
        </button>
        <button type="button" class="btn btn-sm">
            <i class="glyphicon glyphicon-arrow-right"></i>
            下一条
        </button>
        <button type="button" class="btn btn-sm" onclick="exportExcel()">
            导出excel
        </button>
    </div>
</div>