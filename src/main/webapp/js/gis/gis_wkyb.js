var map;
require([
        "esri/map",
        "esri/layers/FeatureLayer",
        "esri/tasks/query",
        "esri/TimeExtent",
        "esri/dijit/TimeSlider",
        "dojo/_base/array", "dojo/dom", "dojo/domReady!"
    ], function (Map, FeatureLayer, Query, TimeExtent, TimeSlider, arrayUtils, dom) {

        var timeSlider = new TimeSlider({style: "width: 100%;"}, dom.byId("timeSliderDiv"));
        var serverUrl = getGisServerIp();
        map = new Map("viewDiv", {
            basemap: "satellite",
            center: [125, 35],
            zoom: 7
        });


        drawChart();
        initSlider();
        function initSlider() {

            map.setTimeSlider(timeSlider);

            var timeExtent = new TimeExtent();
            timeExtent.startTime = new Date("1/1/1900 UTC");
            timeExtent.endTime = new Date("12/31/2020 UTC");
            timeSlider.setThumbCount(1);
            timeSlider.createTimeStopsByTimeInterval(timeExtent, 10, "esriTimeUnitsYears");
            timeSlider.setThumbIndexes([0, 1]);
            timeSlider.setThumbMovingRate(3000);
            timeSlider.startup();

            // drawChart(timeExtent.endTime.getFullYear());

            //add labels for every other time stop
            var labels = arrayUtils.map(timeSlider.timeStops, function (timeStop, i) {
                if (i % 2 === 0) {
                    return timeStop.getUTCFullYear();
                } else {
                    return "";
                }
            });

            timeSlider.setLabels(labels);
            var featureLayer;
            timeSlider.on("time-extent-change", function (evt) {
                // // map.removeAllLayers();
                // featureLayer.removeAll();
                var startValString = evt.startTime.getUTCFullYear();
                var endValString = evt.endTime.getUTCFullYear();
                var layerUrl = serverUrl + "/arcgis/rest/services/historyBuilding/FeatureServer/0";
                featureLayer = new FeatureLayer(layerUrl);
                featureLayer.outFields = ["*"];
                featureLayer.setDefinitionExpression("PERIOD = '" + endValString + "'");
                featureLayer.setOpacity(0.50);
                map.addLayer(featureLayer);
                dom.byId("daterange").innerHTML = "<i>" + startValString + " 到 " + endValString + "<\/i>";
                // drawChart(endValString);
                drawDataTable(endValString);

            });
        }


        /**
         * 江苏省围垦演变统计分析，每个bar表示按照每个年代三个市围垦的总面积
         */
        function drawChart() {
            var cities = new Array();
            var periods = new Array();
            var dataList = queryAllHistoryBuilding();//查询出三个市各自在不同年代的围垦面积，需要进一步求和，得出每个年代的总面积
            var dataValues = [];

            for (var x in dataList) {
                periods[x] = dataList[x][0];
                dataValues[x] = dataList[x][1];
                // periods[x] = dataList[x]["period"];
                // dataValues[x] = dataList[x]["buildSize"];
            }
            var myChart = echarts.init(document.getElementById('buildChart'));

            var option = {
                title: {
                    text: '江苏省围垦演变统计分析',
                    textStyle: {
                        color: "white"
                    }
                },
                tooltip: {
                    show: true
                },
                legend: {

                    show: false,
                    data: ['围垦面积']
                },
                xAxis: [
                    {
                        type: 'category',
                        data: periods,
                        axisLine: {
                            lineStyle: {
                                color: '#ffffff',
                                width: 1,//这里是为了突出显示加上的
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '单位: 万亩',
                        splitLine: {
                            lineStyle: {
                                // 使用深浅的间隔色
                                color: ['#ffffff']
                            }
                        },
                        nameTextStyle: {
                            color: ['#ffffff']
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#ffffff',
                                width: 1,//这里是为了突出显示加上的
                            }
                        }
                    }
                ],
                series: [
                    {
                        "name": "围垦面积",
                        "type": "bar",
                        "data": dataValues
                    }
                ]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);


        }

        /**
         *
         * @param dataList
         * @returns {string}
         *
         * 绘制数据表格信息
         */
        function drawDataTable(year) {
            //根据年份获取历史围垦信息
            var historyData = queryHistoryBuildingByYear(year);


            var table = "<table id='dataListTable' style='color: white;margin:0px auto;border: 1px solid yellow;width:280px'>";
            table += "<tr>";
            table += "<td style='border: 1px solid yellow '>年份</td>";
            table += "<td style='border: 1px solid yellow '>城市</td>";
            table += "<td style='border: 1px solid yellow '>面积(万亩)</td>";
            table += "<tr>";

            for (var i = 0; i < historyData.length; i++) {
                table += "<tr>";
                table += "<td width='20%' style='border: 1px solid yellow'>" + historyData[i]["period"] + "</td>";
                table += "<td width='30%' style='border: 1px solid yellow'>" + historyData[i]["locName"] + "</td>";
                table += "<td width='30%' style='border: 1px solid yellow'>" + historyData[i]["buildSize"] + "</td>";
                table += "<tr>";
            }
            table += "</table>";
            $("#dataTable").html(table);
            showBuildDetail(historyData);
            return table;
        }


    }
);

function showBuildDetail(historyData) {
    var dataValues = [];
    for (var x = 0; x < historyData.length; x++) {
        dataValues[x] = {
            value: historyData[x]["buildSize"],
            name: historyData[x]["locName"]
        }

    }

    var year = historyData[0]["period"];
    var myChart = echarts.init(document.getElementById('buildDetailChart'));
    var option = {
        color: pieColor,
        title: {
            text: '江苏省围垦历史统计(' + year + '年代)',
            x: 'center',
            textStyle: {
                color: "white"
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} (万亩)"
        },
        calculable: true,
        series: [
            {
                name: '围垦面积',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: dataValues
            }
        ]
    };

    myChart.setOption(option);
}


/**
 *
 * @returns {*}
 * 查询所有历史演变列表数据
 */
function queryHistoryBuilding(period) {
    var url = "/historyBuilding/findByPeriod/" + period;
    return findListByUrl(url);

}


/**
 *
 * @returns {*}
 * 查询所有历史演变列表数据
 */
function queryAllHistoryBuilding() {
    var url = "/historyBuilding/findAllGroupByPeriod";
    return findListByUrl(url);

}


/**
 *
 * @returns {*}
 * 查询所有历史演变列表数据
 */
function queryHistoryBuildingByYear(year) {
    var url = "/historyBuilding/findByPeriod/" + year;
    return findListByUrl(url);

}