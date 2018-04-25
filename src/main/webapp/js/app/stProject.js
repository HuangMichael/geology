dataTableName = "#dataTableList";
docName = "查询统计-统计分析-项目信息统计分析";
mainObject = "stProject";
authStatus = "已审核";

$(function () {
    initMenus("topMenu");

    //搜索条件中市县查询
    city_District();

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

    //点击查看统计图按钮
    $("#submit").on("click", function () {
        var searchParams = getSearchParams();
        var planDesc = searchParams.replace(/,/g, "");
        var plan = planDesc.replace(/已审核/g, "");
        drawProjectBarChart(plan + "项目投资进度分析", "chart");

        $("#layer").css("display", "block");
        return false;
    })
    $(".div1 .off").on("click", function () {
        $("#layer").css("display", "none");
    });


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
        url: "/stProject/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看统计图' onclick='check(" + row.id + ")'><i class='icon-zoom-in'></i></button>";
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                // to: function (value) {return moment(value).format('YYYY-MM');}
                to: function (value) {
                    return value.substring(0, 7);
                }
            }
        }
    }).on("selected.rs.jquery.bootgrid", function (e, rows) {
        var rowIds = [];
        for (var i = 0; i < rows.length; i++) {
            rowIds.push(rows[i].id);
        }
    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        var rowIds = [];
        for (var i = 0; i < rows.length; i++) {
            rowIds.push(rows[i].id);
        }
    });
});


/**
 *
 * @param title
 * @param container
 */
function drawProjectBarChart(title, container) {
    var searchPhrase = getSearchParams();
    var url = "stProject/getProjectFundByIds?searchPhrase=" + searchPhrase;
    var projects = getProjects(url);
    var xArray = [];
    var budgetList = [];
    var investedList = [];
    for (var i = 0; i < projects.length; i++) {
        xArray.push(projects[i][2]);
        budgetList.push(projects[i][3] == null ? 0 : projects[i][3].toFixed(2));
        investedList.push(projects[i][4] == null ? 0 : projects[i][4].toFixed(2));
    }
    var myChart = echarts.init(document.getElementById(container));

    var option = {
        title: {
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['预算投资金额', '已投资金额'],
            top: 30,
            orient: 'horizontal'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        dataZoom: {
            show: true,
            realtime: true,
            start: 40,
            end: 60
        },
        xAxis: [
            {
                type: 'category',
                data: xArray,
                axisLabel: {
                    interval: 0,
                    rotate: -20,//倾斜度 -90 至 90 默认为0
                    margin: 2,
                    textStyle: {
                        fontWeight: "bolder",
                        color: "#000000"
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '金额',
                axisLabel: {
                    formatter: '{value}亿元'
                }
            }
        ],
        series: [
            {
                name: '预算投资金额',
                type: 'bar',
                data: budgetList,
                itemStyle: {normal: {label: {show: true, position: 'top'}}},
            },
            {
                name: '已投资金额',
                type: 'bar',
                data: investedList,
                itemStyle: {normal: {label: {show: true, position: 'top'}}},
            }
        ]
    };
    myChart.setOption(option);
    window.onresize = function () {
        myChart.resize();
    };
}


/**
 * 获取项目信息
 */
function getProjects(url) {
    var projects = [];
    $.ajaxSettings.async = false;
    $.get(url, function (data) {
        projects = data;
    });
    return projects;

}


/**
 *
 * @param container 容器
 * @param projectName 项目名称
 */
function drawProjectPieChart(container, projectName, type, investedSum, unInvestedSum) {
    var legends = [];
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: projectName + type + "单位(亿元)",
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top: 25,
            data: ['已投资金额', '未到位金额']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '项目投资进度',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: investedSum, name: '已投资金额'},
                    {value: unInvestedSum, name: '未到位金额'},
                ],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: '{b} : {c} 亿元({d}%)'
                        },
                        labelLine: {show: true}
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}


// 点击查看统计图时生成图表信息
function check(id) {

    $("#layer").css("display", "block");
    var url = "/stProject/findById/" + id;
    var AreaProject = getProjects(url);

    var projectName = AreaProject.projectName + "项目";
    var budget = AreaProject.budget == null ? 0 : AreaProject.budget;
    var investedSum = AreaProject.investedSum == null ? 0 : AreaProject.investedSum;

    console.log("AreaProject========budget=====" + budget + "====investedSum====" + investedSum);
    drawProjectPieChart("chart", projectName, "投资进度分析", investedSum.toFixed(2), (budget - investedSum).toFixed(2));
}
