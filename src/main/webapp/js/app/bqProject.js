/**
 * Created by Administrator on 2017/7/19.
 */

dataTableName = "#dataTableList";
docName = "查询统计-基础数据查询-项目信息";
mainObject = "bqProject";
authStatus = "已审核";
formId = "#projectForm";
appName = "项目信息";
selectArray = ["#id", "#area", "#city", "#district","#landUsingType","#engineeringType","#buildStatus"];//编辑界面弹出前需要清空的下拉列表以及输入框

$(function () {
    initMenus("topMenu");

    //初始化项目名称下拉列表
    var projectUrl = "project/findVByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var project = getMediaSelectedData(projectUrl, data, "projectName");
    initSelectData("#project", {projectsArray: project});
    $("#project").select2({'width': '180px'});

    //搜索条件中市县查询
    city_District();
    //新增或编辑界面中市县下拉列表
    cityAndDistrict();

    //根据市名称一个条件查询项目名称
    searchProjectNameBycityOrDistrict("#cityName",authStatus);
    //根据市县名称两个条件查询项目名称
    searchProjectNameBycityOrDistrict("#districtName",authStatus);
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
        url: "/bqProject/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>"
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                to: function (value) {
                    return value.substring(0, 7);
                }
            }
        }
    });


    //编辑界面中，用地类型下拉列表
    var url = "/landUsingType/findAll";
    var landUsingTypes = getSelectedData(url, "landUsingType");
    initSelectData(" #landUsingType", {landUsingTypeArray: landUsingTypes});

    //编辑界面,工程类型下拉列表
    var url = "/engineeringType/findAll";
    var engineeringTypes = getSelectedData(url, "engineeringType");
    initSelectData( " #engineeringType", {engineeringTypeArray: engineeringTypes});

    //编辑界面,所属区块下拉列表
    var areaUrl = "/area/findAll";
    var area = getSelectedArea(areaUrl, "areaNo", "areaName");
    initSelectData("#area", {areaArray: area});


    setFormReadStatus(formId, true);


});



//编辑记录
function edit(id) {
    currentId = id;
    $(".projectNo_error").html("");
    $(".projectName_error").html("");
    cleanData();
    var project = findById(id);
    console.log(project);
    for (var key in project) {
        if (project[key]) {
            $("#projectForm #" + key).val(project[key]);
            if (key == "area") {
                var areaId = project["area"].id;
                console.log(areaId);
                $("#projectForm #area").val(areaId);
                $("#projectForm #area").select2({'width': '61%'});
            }
            if (key == "city") {
                var cityId = project["city"].id;
                $("#projectForm #city").val(cityId);
                $("#projectForm #city").select2({'width': '61%'});
            }
            if (key == "district") {
                var parentId = $("#city").val();
                var url = "location/findLocsByParentId/" + parentId;
                var district = getSelectedData(url, "locName");
                for (var i = 0; i < district.length; i++) {
                    var option = new Option(district[i]["text"], district[i]["id"]);
                    $("#projectForm #district").append(option);
                }
                var districtId = project["district"].id;
                $("#projectForm #district").val(districtId);
                $("#projectForm #district").select2({'width': '61%'});
            }
            if (key == "beginYear" || key == "endYear") {
                var beginYear = project.beginYear.substring(0, 7);
                $("#projectForm #beginYear").val(beginYear);

                var endYear = project.endYear.substring(0, 7);
                $("#projectForm #endYear").val(endYear);
            }
            if (key == "landUsingType") {
                var landUsingType = project["landUsingType"]["id"];
                $("#projectForm #landUsingType").val(landUsingType);
                $("#projectForm #landUsingType").select2({'width': '61%'});
            }
            if (key == "engineeringType") {
                var engineeringType = project["engineeringType"]["id"];
                $("#projectForm #engineeringType").val(engineeringType);
                $("#projectForm #engineeringType").select2({'width': '61%'});
            }
        }
    }

}


/**
 *根据id查询返回对象
 * */
function findById(id) {
    $.ajaxSettings.async = false;
    var object = null;
    var url =  "project/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}