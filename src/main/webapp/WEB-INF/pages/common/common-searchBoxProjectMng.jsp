<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/12
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<select type="text" class="form-control" id="projectName" placeholder="项目名称">
    <option value="">--请选择项目名称--</option>
    <template v-for="c in projectsArray">
        <option :value="c.text">{{c.text}}</option>
    </template>
</select>
<input type="text" class="form-control" id="beginDate"
       onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="开始年份">
<input type="text" class="form-control" id="endDate"
       onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})" placeholder="结束年份">
<button type="button" class="btn-button" onclick="complexSearch()"><i
        class="icon-search"></i></button>
