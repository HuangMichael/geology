/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "项目管理-项目建设-项目建设基本信息";
mainObject = "vProjectCons";
// mainObject = "sysMngProjectCons";
formId = "#projectConsForm";
authStatus = "已审核";
selectArray = ["#id", "#area",  "#city","#district","#important"];
appName = "项目建设信息";
$(function () {
    initMenus("topMenu");

    //搜索条件中市县查询
    city_District();
    //新增或编辑界面中市县下拉列表
    cityAndDistrict();
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
        url: "/vProjectCons/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看详情' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" ;
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
    //初始化项目名称下拉列表
    var projectUrl = "project/findVByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var project = getMediaSelectedData(projectUrl, data, "projectName");

    initSelectData("#project", {projectsArray: project});
    $("#project").select2({'width': '180px'});


    //根据市名称一个条件查询项目名称
    searchProjectNameBycityOrDistrict("#cityName",authStatus);
    //根据市县名称两个条件查询项目名称
    searchProjectNameBycityOrDistrict("#districtName",authStatus);


    //初始化所属区块下拉列表
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
    var project = findByIdAndObject(id);
    console.log(project);
    for (var key in project["project"]) {
        $(formId + " #" + key).val(project["project"][key]);
        if(key == "beginYear" || key == "endYear"){
            var beginDate = project["project"].beginYear.substring(0, 7);
            $(formId + " #beginDate").val(beginDate);

            var endDate = project["project"].endYear.substring(0, 7);
            $(formId + " #endDate").val(endDate);
        }
        if(key == "city" || key == "district"){
            var cityId = project["project"]["city"].id;
            $(formId + " #city").val(cityId);
            $(formId + " #city").select2({'width': '61%'});


            var parentId = $("#city").val();
            var url = "location/findLocsByParentId/" + parentId;
            var district = getSelectedData(url, "locName");
            for (var i = 0; i < district.length; i++) {
                var option = new Option(district[i]["text"], district[i]["id"]);
                $(formId + " #district").append(option);
            }
            var districtId = project["project"]["district"].id;
            $(formId + " #district").val(districtId);
            $(formId + " #district").select2({'width': '61%'});
        }
        if(key == "area"){
            var areaId = project["project"]["area"].id;
            $(formId + " #area").val(areaId);
            $(formId + " #area").select2({'width': '61%'});
        }
    }
}

/**
 *根据id查询返回对象
 * */
function findByIdAndObject(id) {
    $.ajaxSettings.async = false;
    var object = null;
    var url = "/sysMngProjectCons/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}
