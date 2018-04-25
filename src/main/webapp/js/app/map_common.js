//初始化地图
var map;
var areaZoomSize = 6;
var projectZoomSize = 8;


//给地图加入右键菜单
function addMapContextMenu() {
    var contextMenu = new AMap.ContextMenu(); //创建右键菜单

    contextMenu.addItem("放大一级", function (e) {
        map.zoomIn();
    }, 0);

    contextMenu.addItem("缩小一级", function (e) {
        map.zoomOut();
    }, 1);

    contextMenu.addItem("查看天气", function (e) {
        showWeather();
    }, 2);
    return contextMenu;

}


// //在区块旁边添加该区块的标签
// function addAreaLabel(lnglat, content) {
//     var marker = new AMap.Marker({
//         map: map,
//         position: lnglat,
//         icon: new AMap.Icon({
//             size: new AMap.Size(0, 0), //图标大小
//         })
//     });
//     marker.setLabel({
//         offset: new AMap.Pixel(20, 20), //修改label相对于maker的位置
//         content: content
//     });
// }


/**
 *
 * @param projects
 * 在地图中载入项目
 */
function addProject(projects) {

    for (var x in projects) {
        var marker = new AMap.Marker({
            icon: projects[x]["icon"] ? projects[x]["icon"] : "img/cons.png",
            position: projects[x]["position"]
        });

        marker.setLabel({
            offset: new AMap.Pixel(20, 20), //修改label相对于maker的位置
            content: projects[x]["projectName"]
        });
        marker.setMap(map);


        marker.addEventListener("click", createClickEvent(marker));


        function createClickEvent(overlay) {
            return function (e) {
                var p = e.target;
                alert(overlay.getPosition().lng)
            }
        }


        //地图绑定鼠标右击事件——弹出右键菜单
        marker.on('rightclick', function (e) {
            var contextMenu = addProjectContextMenu();
            contextMenu.open(map, e.lnglat);
            contextMenuPositon = e.lnglat;
        });
    }

}

/**
 *
 * @param markerArr:原始项目的标注
 * @param cluster:生成的聚合点
 * 在地图中载入项目时生成聚合点
 */
var cluster;

function addClusterToMap(markerArr) {
    if (cluster) {
        cluster.setMap(null);
    }
    map.plugin(["AMap.MarkerClusterer"], function () {
        cluster = new AMap.MarkerClusterer(map, markerArr);
    });
    map.setCenter(projectLnglat);
    map.setZoom(projectZoomSize);
}

/**
 *
 * @param cluster:生成的聚合点
 * 清除地图中的聚合点
 */
function clearClusterInMap() {
    if (cluster) {
        cluster.setMap(null);
    }
}

var projectLnglat;

/**
 *
 * @param projects
 * 在地图中载入项目
 */
function drawProjects(projects) {
    //画完项目后的地图中心点的经纬度
    projectLnglat = projects[parseInt(projects.length / 2)]["position"];//必须加上parseInt，才能把浮点数转换为整数。
    var cMarkers = [];
    for (var x in projects) {
        if (projects[x]["projectName"]) {
            var project = projects[x];
            var marker = new AMap.Marker({
                icon: project["icon"] ? project["icon"] : "img/cons.png",
                position: project["position"],
                projectId: project["id"]
            });
            marker.setLabel({
                offset: new AMap.Pixel(20, 20), //修改label相对于maker的位置
                content: project["projectName"],
                projectId: project["id"]
            });
            //marker.setMap(map);
            marker.on("click", createClickEvent());

            /**
             * @returns {Function}
             */
            function createClickEvent() {
                return function (e) {
                    var p = e.target;
                    var projectID = p.getLabel()["projectId"];
                    var projectDesc = getProjectMsgById(projectID, "consContent", projects);
                    var projectName = getProjectMsgById(projectID, "projectName", projects);
                    $("#xmDesc").html(projectDesc);

                    if ($(".row-fluid .span3").css("left", "500px")) {
                        $(".row-fluid .span3").css("left", "0px")
                        $("#btn_m").css("display", "block");
                        $("#btn_b").css("display", "none");
                        $(".row-fluid .span9").css("width", "74.4681%");
                    }
                    showProjectPicture(projectID);
                    //点击项目时，右侧统计投资金额饼状图
                    drawEChartsOfPie("chartContainer", projectName + "项目投资进度", ["已投资金额", "未投资金额"], [
                        {
                            name: '投资比例',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                {value: (Math.random() * 2000).toFixed(2), name: '已投资金额'},
                                {value: (Math.random() * 2000).toFixed(2), name: '未投资金额'}
                            ]
                        }
                    ]);
                    //点击项目时，右侧同时显示此工程类型的统计表格
                    //var result = getProjectById(getAllProjects(), projectID);
                    // Vue.set(projectDataVue.$data, 'projectDataList', result);

                }
            }
        }


        //项目图标绑定鼠标右击事件——弹出右键菜单
        marker.on('rightclick', function (e) {
            var contextMenu = addProjectContextMenu();
            contextMenu.open(map, e.lnglat);
            contextMenuPositon = e.lnglat;
        });
        cMarkers.push(marker);
    }
    addClusterToMap(cMarkers);

}


/*
 画出所有的区块信息
 * */
function drawFinishedAreas(areas) {
    for (var x in areas) {
        if (areas[x].finishedPolygon) {
            for (var i = 0, len = areas[x].finishedPolygon.length; i < len; i++) {
                var polygon = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    // childIndex: i,
                    path: areas[x].finishedPolygon[i], //设置多边形边界路径
                    strokeColor: "green", //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: "#000000", //填充色
                    fillOpacity: 0 //填充透明度
                });
                polygon.setMap(map);

                $(polygon).attr("areaNo", areas[x].areaNo);

                polygon.on('rightclick', function (e) {
                    var contextMenu = addAreaContextMenu();
                    contextMenu.open(map, e.lnglat);
                    contextMenuPositon = e.lnglat;
                });
                var pos = [areas[x]["longitude"], areas[x]["latitude"]];
                addAreaLabel(pos, areas[x]["areaNo"] + areas[x]["areaName"]);

                function createClickEvent(overlay) {
                    return function (e) {
                        var p = e.target;
                        var areaNo = $(p).attr("areaNo");
                        var desc = getAreaMsgByAreaNo(areaNo, "situationDesc");
                        $("#wkDesc").html(desc);
                    }
                }

                polygon.on('click', createClickEvent(polygon));
            }
        }

    }
    map.setCenter([areas[0]["longitude"], areas[0]["latitude"]]);
    map.setZoom(9);

}

//
// /**
//  *画出符合条件的区块信息
//  * */
// function drawYbBoldAreas(areas, color) {
//
//     map.clearMap();
//     for (var x in areas) {
//         if (areas[x].finishedPolygon) {
//             for (var i = 0, len = areas[x].finishedPolygon.length; i < len; i++) {
//                 var polygon = new AMap.Polygon({
//                     areaNo: areas[x].areaNo,
//                     // childIndex: i,
//                     path: areas[x].finishedPolygon[i], //设置多边形边界路径
//                     strokeColor: color, //线颜色
//                     strokeOpacity: 1, //线透明度
//                     strokeWeight: 1, //线宽
//                     fillColor: color, //填充色
//                     fillOpacity: 0.6 //填充透明度
//                 });
//                 polygon.setMap(map);
//
//                 $(polygon).attr("areaNo", areas[x].areaNo);
//                 // var pos = [areas[x]["longitude"], areas[x]["latitude"]];
//                 // addAreaLabel(pos, areas[x]["areaNo"] + areas[x]["areaName"]);
//                 function createClickEvent(overlay) {
//                     return function (e) {
//                         var p = e.target;
//                         var areaNo = $(p).attr("areaNo");
//                         showAreaDetail(areaNo);
//
//                     }
//                 }
//
//                 polygon.on('click', createClickEvent(polygon));
//             }
//         }
//
//     }
//     map.setCenter([areas[parseInt(areas.length / 2)]["longitude"], areas[parseInt(areas.length / 2)]["latitude"]]);//必须加上parseInt，才能把浮点数转换为整数。
//     map.setZoom(9);
//
// }

/**
 * 围垦演变——画出符合条件的区块围垦情况，每个区块显示三种功能类型的区域。 路丽民20170524
 * @param areas 符合条件的区块
 * @param timeScale 三个时间段：2010-2012 ， 2013-2015 ， 2016-2020.
 * */
function drawYbBoldAreas(areas, timeScale) {
    for (var x in areas) {
        if (areas[x].finishedPolygon) {
            for (var i = 0, len = areas[x].finishedPolygon.length; i < len; i++) {
                var polygon = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedPolygon[i], //设置多边形边界路径
                    strokeColor: "green", //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: "#068A38", //填充色 - 墨绿色
                    fillOpacity: 1//填充透明度
                });
                polygon.setMap(map);
                $(polygon).attr("areaNo", areas[x].areaNo);

                function createClickEvent() {
                    return function (e) {
                        var p = e.target;
                        var areaNo = $(p).attr("areaNo");
                        showAreaDetail(areaNo);
                    }
                }

                polygon.on('click', createClickEvent());
            }
        }
        if (timeScale == "2010-2012") {
            if (areas[x].finishedPolygon2010_2012ny) {
                for (var i = 0, len = areas[x].finishedPolygon2010_2012ny.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2010_2012ny[i], //设置多边形边界路径
                        strokeColor: strokeColorNy, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorNy, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2010_2012st) {
                for (var i = 0, len = areas[x].finishedPolygon2010_2012st.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2010_2012st[i], //设置多边形边界路径
                        strokeColor: strokeColorSt, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorSt, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2010_2012js) {
                for (var i = 0, len = areas[x].finishedPolygon2010_2012js.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2010_2012js[i], //设置多边形边界路径
                        strokeColor: strokeColorJs, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorJs, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
        } else if (timeScale == "2013-2015") {
            if (areas[x].finishedPolygon2013_2015ny) {
                for (var i = 0, len = areas[x].finishedPolygon2013_2015ny.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2013_2015ny[i], //设置多边形边界路径
                        strokeColor: strokeColorNy, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorNy, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2013_2015st) {
                for (var i = 0, len = areas[x].finishedPolygon2013_2015st.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2013_2015st[i], //设置多边形边界路径
                        strokeColor: strokeColorSt, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorSt, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2013_2015js) {
                for (var i = 0, len = areas[x].finishedPolygon2013_2015js.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2013_2015js[i], //设置多边形边界路径
                        strokeColor: strokeColorJs, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorJs, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
        } else if (timeScale == "2016-2020") {
            if (areas[x].finishedPolygon2016_2020ny) {
                for (var i = 0, len = areas[x].finishedPolygon2016_2020ny.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2016_2020ny[i], //设置多边形边界路径
                        strokeColor: strokeColorNy, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorNy, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2016_2020st) {
                for (var i = 0, len = areas[x].finishedPolygon2016_2020st.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2016_2020st[i], //设置多边形边界路径
                        strokeColor: strokeColorSt, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorSt, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
            if (areas[x].finishedPolygon2016_2020js) {
                for (var i = 0, len = areas[x].finishedPolygon2016_2020js.length; i < len; i++) {
                    var polygon = new AMap.Polygon({
                        areaNo: areas[x].areaNo,
                        path: areas[x].finishedPolygon2016_2020js[i], //设置多边形边界路径
                        strokeColor: strokeColorJs, //线颜色
                        strokeOpacity: 1, //线透明度
                        strokeWeight: 1, //线宽
                        fillColor: fillColorJs, //填充色
                        fillOpacity: 1 //填充透明度
                    });
                    polygon.setMap(map);

                    $(polygon).attr("areaNo", areas[x].areaNo);

                    function createClickEvent(overlay) {
                        return function (e) {
                            var p = e.target;
                            var areaNo = $(p).attr("areaNo");
                            showAreaDetail(areaNo);

                        }
                    }

                    polygon.on('click', createClickEvent(polygon));
                }
            }
        }
    }
    // map.setCenter([areas[parseInt(areas.length / 2)]["longitude"], areas[parseInt(areas.length / 2)]["latitude"]]);//必须加上parseInt，才能把浮点数转换为整数。
    // map.setZoom(9);

}

/**
 * 画出海岸线最新现状
 * @param areas
 */
function drawAreasCoast(areas) {
    for (var x in areas) {
        if (areas[x].finishedCoast2016_2020) {
            for (var i = 0, len = areas[x].finishedCoast2016_2020.length; i < len; i++) {
                var polyline = new AMap.Polyline({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedCoast2016_2020[i], //设置多边形边界路径
                    strokeColor: "#8B4500", //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 3//线宽
                });
                polyline.setMap(map);
            }
        }
    }
}

/**
 *
 * @param natureArray 自然保护区的数据
 * @param natureType 自然保护区的类型（核心区or缓冲区），取值：'CORE' 和 'BUFFER'二选一
 * "#ff7f7e"----自然保护核心区         "#ffebb0"----自然保护缓冲区
 */
function drawNatureRegionByType(natureArray, natureType) {
    if (natureArray) {
        for (var i = 0, len = natureArray.length; i < len; i++) {
            var polygon = new AMap.Polygon({
                path: natureArray[i], //设置多边形边界路径
                strokeColor: (natureType == "CORE" ? "#ff7f7e" : "#ffebb0"), //线颜色
                strokeOpacity: 1, //线透明度
                strokeWeight: 1,//线宽
                fillColor: (natureType == "CORE" ? "#ff7f7e" : "#ffebb0"), //填充色 - 墨绿色
                fillOpacity: 1//填充透明度
            });
            polygon.setMap(map);
        }
    }
}

/**
 *画出指定的区块数组的已围垦信息，另外每个区块内画出三种不同功能类型的区域 路丽民20170524 修改此函数
 *
 **/
//strokeColorNy = 表示功能类型区域的线颜色，  fillColorNy 表示功能类型区域的填充色
var strokeColorNy = "#ffff33", strokeColorSt = "#50ea69", strokeColorJs = "#00a2e8";
var fillColorNy = "#ffff33", fillColorSt = "#50ea69", fillColorJs = "#00a2e8";

function drawBoldAreas(areas) {
    map.clearMap();

    function createClickEvent() {
        return function (e) {
            var p = e.target;
            var areaNo = $(p).attr("areaNo");
            showAreaDetail(areaNo);
        }
    }

    for (var x in areas) {
        if (areas[x].finishedPolygon) {
            for (var i = 0, len = areas[x].finishedPolygon.length; i < len; i++) {
                var polygon = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedPolygon[i], //设置多边形边界路径
                    strokeColor: "green", //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: "#068A38", //填充色 - 墨绿色
                    fillOpacity: 1//填充透明度
                });
                polygon.setMap(map);
                $(polygon).attr("areaNo", areas[x].areaNo);
                $(polygon).attr("longitude", areas[x].longitude);
                $(polygon).attr("latitude", areas[x].latitude);

                polygon.on('click', createClickEvent());
                //鼠标放在区块上就显示该区块的基本信息，移出就不显示
                polygon.on('mouseover', createMouseOverEvent()).on('mouseout', function () {
                    infoWindow.close();
                });
            }
        }
        // 路丽民20170524 添加以下程序
        var myFillOpacity = 0.9;
        //农业区域
        if (areas[x].finishedPolygon2016_2020ny) {
            for (var i = 0, len = areas[x].finishedPolygon2016_2020ny.length; i < len; i++) {
                var polygonNy = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedPolygon2016_2020ny[i], //设置多边形边界路径
                    strokeColor: strokeColorNy, //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: fillColorNy, //填充色
                    fillOpacity: myFillOpacity //填充透明度
                });
                polygonNy.setMap(map);
                $(polygonNy).attr("areaNo", areas[x].areaNo);
                $(polygonNy).attr("longitude", areas[x].longitude);
                $(polygonNy).attr("latitude", areas[x].latitude);

                polygonNy.on('click', createClickEvent());
                //鼠标放在区块上就显示该区块的基本信息，移出就不显示
                polygonNy.on('mouseover', createMouseOverEvent()).on('mouseout', function () {
                    infoWindow.close();
                });
            }
        }
        //生态区域
        if (areas[x].finishedPolygon2016_2020st) {
            for (var i = 0, len = areas[x].finishedPolygon2016_2020st.length; i < len; i++) {
                var polygonSt = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedPolygon2016_2020st[i], //设置多边形边界路径
                    strokeColor: strokeColorSt, //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: fillColorSt, //填充色
                    fillOpacity: myFillOpacity //填充透明度
                });
                polygonSt.setMap(map);
                $(polygonSt).attr("areaNo", areas[x].areaNo);
                $(polygonSt).attr("longitude", areas[x].longitude);
                $(polygonSt).attr("latitude", areas[x].latitude);

                polygonSt.on('click', createClickEvent());
                //鼠标放在区块上就显示该区块的基本信息，移出就不显示
                polygonSt.on('mouseover', createMouseOverEvent()).on('mouseout', function () {
                    infoWindow.close();
                });
            }
        }

        //建设区域
        if (areas[x].finishedPolygon2016_2020js) {
            for (var i = 0, len = areas[x].finishedPolygon2016_2020js.length; i < len; i++) {
                var polygonJs = new AMap.Polygon({
                    areaNo: areas[x].areaNo,
                    path: areas[x].finishedPolygon2016_2020js[i], //设置多边形边界路径
                    strokeColor: strokeColorJs, //线颜色
                    strokeOpacity: 1, //线透明度
                    strokeWeight: 1, //线宽
                    fillColor: fillColorJs, //填充色
                    fillOpacity: myFillOpacity//填充透明度
                });
                polygonJs.setMap(map);
                $(polygonJs).attr("areaNo", areas[x].areaNo);
                $(polygonJs).attr("longitude", areas[x].longitude);
                $(polygonJs).attr("latitude", areas[x].latitude);

                polygonJs.on('click', createClickEvent());
                //鼠标放在区块上就显示该区块的基本信息，移出就不显示
                polygonJs.on('mouseover', createMouseOverEvent()).on('mouseout', function () {
                    infoWindow.close();
                });
            }
        }
    }
    map.setCenter([areas[parseInt(areas.length / 2)]["longitude"], areas[parseInt(areas.length / 2)]["latitude"]]);//必须加上parseInt，才能把浮点数转换为整数。
    map.setZoom(9);
}


/**
 *画出指定的区块数组的已围垦信息
 *
 **/
// function drawBoldAreas(areas) {
//     map.clearMap();
//     for (var x in areas) {
//         if (areas[x].finishedPolygon) {
//             for (var i = 0, len = areas[x].finishedPolygon.length; i < len; i++) {
//                 var polygon = new AMap.Polygon({
//                     areaNo: areas[x].areaNo,
//                     path: areas[x].finishedPolygon[i], //设置多边形边界路径
//                     strokeColor: "green", //线颜色
//                     strokeOpacity: 1, //线透明度
//                     strokeWeight: 1, //线宽
//                     fillColor: "#068A38", //填充色 - 墨绿色
//                     fillOpacity: 1//填充透明度
//                 });
//                 polygon.setMap(map);
//                 $(polygon).attr("areaNo", areas[x].areaNo);
//                 // var pos = [areas[x]["longitude"], areas[x]["latitude"]];
//                 // addAreaLabel(pos, areas[x]["areaNo"] + areas[x]["areaName"]);
//                 function createClickEvent() {
//                     return function (e) {
//                         var p = e.target;
//                         var areaNo = $(p).attr("areaNo");
//                         showAreaDetail(areaNo);
//                     }
//                 }
//
//                 polygon.on('click', createClickEvent());
//             }
//         }
//     }
//     map.setCenter([areas[parseInt(areas.length / 2)]["longitude"], areas[parseInt(areas.length / 2)]["latitude"]]);//必须加上parseInt，才能把浮点数转换为整数。
//     map.setZoom(9);
// }


/**
 *画出指定的区块数组的规划信息
 * @param areas 指定的区块数组
 * @param planType 规划类型，取值：总体规划"ztplanDesc" ,  十三五规划 "plan135Desc"
 **/
function drawPlanedAreas(areas, planType) {
    map.clearMap();//首先清除地图上的覆盖物
    for (var x in areas) {
        if (areas[x].polygon) {
            var polygon = new AMap.Polygon({
                path: areas[x].polygon, //设置多边形边界路径
                strokeColor: "red", //线颜色
                strokeOpacity: 1, //线透明度
                strokeWeight: 1, //线宽
                fillColor: "#FF0000", //填充色
                fillOpacity: 0.7, //填充透明度
                areaNo: areas[x].areaNo
            });
            polygon.setMap(map);

            $(polygon).attr("areaNo", areas[x].areaNo);
            $(polygon).attr("longitude", areas[x].longitude);
            $(polygon).attr("latitude", areas[x].latitude);

            /**
             *
             * @returns {Function}
             */

            function createClickEvent() {
                return function (e) {
                    var p = e.target;
                    var areaNo = $(p).attr("areaNo");
                    console.log("规划类型" + planType);
                    var desc = getAreaMsgByAreaNo(areaNo, planType);
                    $("#wkDesc").html(desc);
                    var str = $("#wkDesc").text();
                    if (str.length > 300) {
                        str2 = str.substring(0, 300);
                        console.log(str2)
                        $("#wkDesc").html = str2;//container是你的容器，我假设是div
                    } else {
                        $("#wkDesc").html = str;
                    }
                    // 判断右边区块信息是否显示
                    if ($(".row-fluid .span3").css("left", "500px")) {
                        $(".row-fluid .span3").css("left", "0px")
                        $("#btn_m").css("display", "block");
                        $("#btn_b").css("display", "none");
                        $(".row-fluid .span9").css("width", "74.4681%");
                    }
                    drawPieChart("chartContainer", areaNo, "规划");
                    createTableOfLandUsingType("dataListTable", "规划面积(万亩)", areaNo);
                }
            }

            // function createMouseOverEvent() {
            //     return function (e) {
            //         var p = e.target;
            //         var areaNo = $(p).attr("areaNo");
            //         var longitude = $(p).attr("longitude");
            //         var latitude = $(p).attr("latitude");
            //         openInfo(map,[longitude,latitude],areaNo);
            //     }
            // }
            polygon.on('click', createClickEvent());
            //鼠标放在区块上就显示该区块的基本信息，移出就不显示
            polygon.on('mouseover', createMouseOverEvent()).on('mouseout', function () {
                infoWindow.close();
            });


        }
        map.setCenter([areas[parseInt(areas.length / 2)]["longitude"], areas[parseInt(areas.length / 2)]["latitude"]]);//必须加上parseInt，才能把浮点数转换为整数。
        map.setZoom(9);
    }
}

function createMouseOverEvent() {
    return function (e) {
        var p = e.target;
        var areaNo = $(p).attr("areaNo");
        var longitude = $(p).attr("longitude");
        var latitude = $(p).attr("latitude");
        openInfo(map, [longitude, latitude], areaNo);
    }
}


var infoWindow;

//在指定位置打开信息窗体
function openInfo(map, center, areaNo) {

    var areas = getAllAreas();
    var area = getAreaByAreaNo(areaNo, areas);
    console.log("area---------------" + JSON.stringify(area));
    //构建信息窗体中显示的内容
    var info = [];
    info.push("<div><div style=\"padding:0px 0px 0px 4px;\"><b>区块编号:" + areaNo + "</b>");
    info.push("区块名称：" + area.areaName);
    info.push("区块已围面积：" + area.finishedAreaSize + "万亩");
    info.push("区块规划面积：" + area.areaSize + "万亩</div></div>");
    infoWindow = new AMap.InfoWindow({
        content: info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容
    });
    infoWindow.open(map, center);
}


// //在区块旁边添加该区块的标签
// function addAreaLabel(lnglat, content) {
//     var marker = new AMap.Marker({
//         map: map,
//         position: lnglat,
//         icon: new AMap.Icon({
//             size: new AMap.Size(0, 0), //图标大小
//         })
//     });
//     marker.setLabel({
//         offset: new AMap.Pixel(20, 20), //修改label相对于maker的位置
//         content: content
//     });
// }

function measureDistance() {

    var ruler1, ruler2;
    //地图初始化
    map.plugin(["AMap.RangingTool"], function () {
        ruler1 = new AMap.RangingTool(map);
        AMap.event.addListener(ruler1, "end", function (e) {
            ruler1.turnOff();
        });
        var sMarker = {
            icon: new AMap.Icon({
                size: new AMap.Size(19, 31), //图标大小
                image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b1.png"
            })
        };
        var eMarker = {
            icon: new AMap.Icon({
                size: new AMap.Size(19, 31), //图标大小
                image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b2.png"
            }),
            offset: new AMap.Pixel(-9, -31)
        };
        var lOptions = {
            strokeStyle: "solid",
            strokeColor: "#FF33FF",
            strokeOpacity: 1,
            strokeWeight: 2
        };
        var rulerOptions = {
            startMarkerOptions: sMarker,
            endMarkerOptions: eMarker,
            lineOptions: lOptions
        };
        ruler2 = new AMap.RangingTool(map, rulerOptions);
    });

    //启用默认样式测距
    function startRuler1() {
        ruler2.turnOff();
        ruler1.turnOn();
    }
}

function showAnalysis() {

    $("#contentDesc").html("多维分析");

}


/*
 *初始化图例
 * */
function initLegend(legendDiv, legendArray) {
    var html = "";
    for (var x in legendArray) {

        if (legendArray[x]["marker"] && legendArray[x]["description"]) {
            html += "<tr>";
            html += '<td><img src=' + legendArray[x]['marker'] + '></td>';
            html += '<td>' + legendArray[x]['description'] + '</td>';
            html += "</tr>";
        }

    }
    $(legendDiv).html(html);

}


/**
 * 共用函数——绘制ECharts柱状图
 * @param echartsDiv:要在哪个div元素显示图表
 * @param title: 图表题目
 * @param legendData： 图表的图例，为数组
 * @param xAxisName: X轴名称
 * @param xAxisData：ECharts图x轴上的数据
 * @param yAxisName: Y轴名称
 * @param seriesData：ECharts图上的数据
 * @param colorValue：ECharts图bar的颜色值
 */
function drawEChartsOfBar(echartsDiv, title, legendData, xAxisName, xAxisData, yAxisName, seriesData, colorValue) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById(echartsDiv));
    // 指定图表的配置项和数据
    var myOption = {
        title: {
            text: title,
            x: "center"
        },
        color: colorValue,
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        toolbox: {
            top: 25,
            show: true,
            feature: {
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        grid: {
            left: '10%',
            right: '15%',
            containLabel: true
        },
        legend: {
            data: legendData,
            top: 25,
            orient: 'horizontal'
        },
        xAxis: {
            name: xAxisName,
            data: xAxisData
        },
        yAxis: {
            name: yAxisName,
            // data:yAxisData
        },
        series: seriesData
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(myOption);
}

//三种用地类型的假数据
// var nyData, stData, jsData;
/**
 *
 * @param container 容器
 * @param areaNo
 */
function drawPieChart(container, areaNo, type, nyData, stData, jsData) {

    var legends = [];
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: areaNo + type + '(单位：万亩)',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}万亩 ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top: 25,
            data: legends
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
                name: '用地类型',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: nyData, name: '农业用地'},
                    {value: stData, name: '生态用地'},
                    {value: jsData, name: '建设用地'},
                ],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: '{b} : {c} ({d}%)'
                        },
                        labelLine: {show: true}
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}


/**
 * 共用函数——绘制ECharts饼图
 * @param echartsDiv 要在哪个div元素显示图表
 * @param title 图表题目
 * @param legendData 图表的图例，为数组
 * @param seriesData ECharts图上的数据
 */
function drawEChartsOfPie(echartsDiv, title, legendData, seriesData) {
    var myChart = echarts.init(document.getElementById(echartsDiv));
    var option = {
        title: {
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top: 25,
            data: legendData
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
        series: seriesData
    };


    myChart.setOption(option);
}


/**
 *
 * @param tableId 表格的div id
 * @param valueTitle 第三列的题目（已围面积or规划面积）
 * @param areaNo 区块编号
 */
function createTableOfLandUsingType(tableId, valueTitle, areaNo) {
    var table = document.getElementById(tableId);
    var tableHtml = "<thead><th>区块编号</th><th>用地类型</th><th>" + valueTitle + "</th></thead>";
    tableHtml += "<tr><td>" + areaNo + "</td>" + "<td>农业用地</td>" + "<td>" + nyData + "</td></tr>";
    tableHtml += "<tr><td>" + areaNo + "</td>" + "<td>生态用地</td>" + "<td>" + stData + "</td></tr>";
    tableHtml += "<tr><td>" + areaNo + "</td>" + "<td>建设用地</td>" + "<td>" + jsData + "</td></tr>";
    table.innerHTML = tableHtml;
}


function showProjectPicture(id) {
    //控件显示围垦区块描述信息
    var pros = getAllProjects();
    var p = getProjectById(pros, id);
    var arr = p[0].img;
    var $ul = $('<ul style="width:3370px"></ul><div class="guess-btn"><a href="javascript:void(0);" class="prev"><img src="img/button/btn-left.png" alt=""></a> <a href="javascript:void(0);" class="next"><img src="img/button/btn-right.png" alt=""></a> </div>');
    $('#wkGallery').html($ul);
    for (var i = 0; i < arr.length; i++) {
        var $li = $('<li></li>');
        var $img = $("<img src='" + arr[i] + "' class='img-responsive img-thumbnail'/>");
        $img.width = 256;
        $li.append($img);
        $('#wkGallery ul').append($li);

    }


    //图片轮播
    var index = 0;
    var $aLi = $("#wkGallery ul").find('li');
    var $last = $aLi.clone();
    //console.log($last)
    var $ul = $('#wkGallery ul');
    $ul.append($last);
    $aLi = $("#wkGallery ul").find('li');
    var $aLiw = $aLi.width();
    $len = $aLi.size();

    //自动轮播
    function go() {
        index++;
        if (index == $len - 4) {
            index = 1;
            $ul.css('marginLeft', 0);
        }
        $ul.stop().animate({
            marginLeft: -$aLiw * index
        })

    }

    setInterval(go, 4000)


    //左按钮。index--
    $(".guess-btn a.prev").on('click', function () {
        clearInterval;
        index--;
        if (index < 1) {
            index = 5;
            $ul.css('marginLeft', 0);
        }
        $ul.stop().animate({
            marginLeft: -$aLiw * index
        })

    })
    //右按钮。index++
    $(".guess-btn a.next").on('click', function () {
        clearInterval;
        go()

    })
}


/**
 *
 * 统计分析报表全部数据生成的图标
 *
 */
// function drawPlanBarChart(planDesc, container, url) {
//     var searchPhrase = getSearchParams();
//     var myChart = echarts.init(document.getElementById(container));
//     myChart.setOption({
//         title: {
//             text: planDesc + "用地面积统计(单位：万亩)",
//             x: 'center'
//         },
//
//         tooltip: {
//             trigger: 'axis'
//         },
//         toolbox: {
//             show: true,
//             feature: {
//                 mark: {show: true},
//                 dataView: {show: true, readOnly: false},
//                 magicType: {show: true, type: ['line', 'bar']},
//                 restore: {show: true},
//                 saveAsImage: {show: true}
//             }
//         },
//         calculable: true,
//         grid: {
//             left: '10%',
//             right: '15%',
//             containLabel: true
//         },
//         legend: {},
//         xAxis: [
//             {
//                 type: 'category',
//                 data: []
//             }
//         ],
//         yAxis: [
//             {
//                 type: 'value',
//                 name: '面积',
//                 axisLabel: {
//                     formatter: '{value}万亩'
//                 }
//             }
//
//         ],
//         series: []
//     });
//
//     var names = [];    //类别数组（实际用来盛放X轴坐标值）
//     var nyData = [];//销量数组（实际用来盛放Y坐标值）
//     var stData = [];
//     var jsData = [];
//
//     $.ajax({
//         type: "post",
//         async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
//         url: url,    //请求发送到TestServlet处
//         data: {"searchPhrase":searchPhrase},
//         dataType: "json",        //返回数据形式为json
//         success: function (result) {
//             //请求成功时执行该函数内容，result即为服务器返回的json对象
//             console.log(result);
//             if (result) {
//                 // console.log(result);
//                 for (var i = 0; i < result.length; i++) {
//                     names.push(result[i][0]);
//                     nyData.push(result[i][1]);
//                     stData.push(result[i][3]);
//                     jsData.push(result[i][2]);
//                 }
//                 myChart.setOption({        //加载数据图表
//                     xAxis: {
//                         data: names
//                     },
//                     legend: {
//                         top: 25,
//                         data: ['农业', '生态', '建设']
//                     },
//                     series: [
//                         {
//                             // 根据名字对应到相应的系列
//                             name: '农业',
//                             type: 'bar',
//                             data: nyData,
//                             itemStyle: {normal: {label: {show: true, position: 'top'}}},
//                         },
//                         {
//                             // 根据名字对应到相应的系列
//                             name: '生态',
//                             type: 'bar',
//                             data: stData,
//                             itemStyle: {normal: {label: {show: true, position: 'top'}}},
//                         },
//                         {
//                             // 根据名字对应到相应的系列
//                             name: '建设',
//                             type: 'bar',
//                             data: jsData,
//                             itemStyle: {normal: {label: {show: true, position: 'top'}}},
//                         }
//                     ],
//                 });
//             }
//         },
//     });
// }

function drawPlanBarChart(planDesc, container, url) {
    var searchPhrase = getSearchParams();
    var myChart = echarts.init(document.getElementById(container));
    myChart.setOption({
        title: {
            text: planDesc + "用地面积统计(单位：万亩)",
            x: 'center'
        },

        tooltip: {
            trigger: 'axis'
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
        grid: {
            left: '10%',
            right: '15%',
            containLabel: true
        },
        legend: {},
        xAxis: [
            {
                type: 'category',
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '面积',
                axisLabel: {
                    formatter: '{value}万亩'
                }
            }

        ],
        series: []
    });

    var names = [];    //类别数组（实际用来盛放X轴坐标值）
    var nyData = [];//销量数组（实际用来盛放Y坐标值）
    var stData = [];
    var jsData = [];

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: url,    //请求发送到TestServlet处
        data: {"searchPhrase": searchPhrase},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            console.log(result);
            if (result) {
                // console.log(result);
                for (var i = 0; i < result.length; i++) {
                    names.push(result[i][0]);
                    nyData.push(result[i][1]);
                    stData.push(result[i][3]);
                    jsData.push(result[i][2]);
                }
                myChart.setOption({        //加载数据图表
                    title: {
                        text: planDesc + "用地面积统计(单位：万亩)",
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
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
                    grid: {
                        width: 800,
                        left: '5%',
                        right: '5%',
                        containLabel: true
                    },
                    yAxis: [
                        {
                            type: 'value',
                            name: '面积',
                            axisLabel: {
                                formatter: '{value}万亩'
                            }
                        }

                    ],
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 40
                    },
                    xAxis: {
                        type: 'category',
                        data: names
                    },
                    legend: {
                        top: 30,
                        data: ['农业', '生态', '建设']
                    },
                    series: [
                        {
                            // 根据名字对应到相应的系列
                            name: '农业',
                            type: 'bar',
                            data: nyData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        },
                        {
                            // 根据名字对应到相应的系列
                            name: '生态',
                            type: 'bar',
                            data: stData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        },
                        {
                            // 根据名字对应到相应的系列
                            name: '建设',
                            type: 'bar',
                            data: jsData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        }
                    ],
                });
            }
        },
    });
}


/**
 * 只为区块统计分析服务，路丽民20170926
 * @param planDesc
 * @param container
 * @param url
 */
function drawBarChartForAreaStat(planDesc, container, url) {
    var searchPhrase = getSearchParams();
    var myChart = echarts.init(document.getElementById(container));
    myChart.setOption({
        title: {
            text: planDesc + "用地面积统计(单位：万亩)",
            x: 'center'
        },

        tooltip: {
            trigger: 'axis'
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
        grid: {
            left: '10%',
            right: '15%',
            containLabel: true
        },
        legend: {},
        dataZoom: {
            show: true,
            realtime: true,
            start: 20,
            end: 60
        },
        xAxis: [
            {
                type: 'category',
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '面积',
                axisLabel: {
                    formatter: '{value}万亩'
                }
            }

        ],
        series: []
    });

    var names = [];    //类别数组（实际用来盛放X轴坐标值）
    var nyData = [];//销量数组（实际用来盛放Y坐标值）
    var stData = [];
    var jsData = [];

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: url,    //请求发送到TestServlet处
        data: {"searchPhrase": searchPhrase},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            console.log(result);
            if (result) {
                // console.log(result);
                for (var i = 0; i < result.length; i++) {
                    names.push(result[i]["areaNo"]);
                    nyData.push(result[i]["nyBuildSize"]);
                    stData.push(result[i]["stBuildSize"]);
                    jsData.push(result[i]["jsBuildSize"]);
                }
                myChart.setOption({        //加载数据图表
                    title: {
                        text: planDesc + "用地面积统计(单位：万亩)",
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
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
                    grid: {
                        left: '5%',
                        right: '5%',
                        containLabel: true
                    },
                    yAxis: [
                        {
                            type: 'value',
                            name: '面积',
                            axisLabel: {
                                formatter: '{value}万亩'
                            }
                        }

                    ],
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 20,
                        end: 60
                    },
                    xAxis: {
                        type: 'category',
                        data: names
                    },
                    legend: {
                        top: 30,
                        data: ['农业', '生态', '建设']
                    },
                    series: [
                        {
                            // 根据名字对应到相应的系列
                            name: '农业',
                            type: 'bar',
                            data: nyData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        },
                        {
                            name: '生态',
                            type: 'bar',
                            data: stData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        },
                        {
                            name: '建设',
                            type: 'bar',
                            data: jsData,
                            itemStyle: {normal: {label: {show: true, position: 'top'}}},
                        }
                    ],
                });
            }
        },
    });
}
