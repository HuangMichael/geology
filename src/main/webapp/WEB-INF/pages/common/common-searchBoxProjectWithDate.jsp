<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/21 0021
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<select type="text" class="form-control" id="cityName">
    <option value="">江苏省</option>
    <template v-for="c in citiesArray">
        <option :value="c.text">{{c.text}}</option>
    </template>
</select>
<select type="text" class="form-control" id="districtName">
    <option value="">--请选择区县--</option>
    <template v-for="d in districtArray">
        <option :value="d.text">{{d.text}}</option>
    </template>
</select>
<input type="text" class="form-control" id="projectNo" placeholder="项目编号">
<select type="text" class="form-control" id="project" placeholder="项目名称">
    <option value="">--请选择项目名称--</option>
    <template v-for="c in projectsArray">
        <option :value="c.text">{{c.text}}</option>
    </template>
</select>
<input type="text" class="form-control" id="beginDate" onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="开始年份" >
<input type="text" class="form-control" id="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="结束年份">
<%--<button type="button" class="btn-button" onclick="complexSearch()"><i--%>
        <%--class="icon-search"></i></button>--%>
