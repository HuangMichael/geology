var tileUrl = getGisTileServerIp() + "/arcgis/rest/services/china_streets/MapServer";
console.log("tileUrl", tileUrl);

function commonGisMap(basemap, main, main2, main3, path, landusePolygonLayer, attrName, obj, formId) {
    var map;

    require([
        "esri/map",
        "esri/layers/ArcGISTiledMapServiceLayer",
        "esri/SpatialReference",
        "esri/toolbars/draw",
        "esri/toolbars/edit",
        "esri/graphic",
        "esri/config",
        "esri/layers/FeatureLayer",
        "esri/tasks/query",
        "esri/symbols/SimpleMarkerSymbol",
        "esri/symbols/SimpleLineSymbol",
        "esri/symbols/SimpleFillSymbol",
        "esri/dijit/editing/TemplatePicker",
        "dojo/_base/array",
        "dojo/_base/event",
        "dojo/_base/lang",
        "dojo/parser",
        "dijit/registry",
        "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
        "dijit/form/Button", "dojo/domReady!"
    ], function (Map, ArcGISTiledMapServiceLayer, SpatialReference, Draw, Edit, Graphic, esriConfig,
                 FeatureLayer, Query,
                 SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol,
                 TemplatePicker,
                 arrayUtils, event, lang, parser, registry) {
        parser.parse();
        esriConfig.defaults.io.proxyUrl = "/proxy/";
        esriConfig.defaults.geometryService = new esri.tasks.GeometryService(path + "/arcgis/rest/services/Utilities/Geometry/GeometryServer");
        map = new Map("viewDiv", {
            basemap: "streets",
            center: [121, 32],
            zoom: 7
        });

        map.on("layers-add-result", initEditing);

        landusePolygonLayer = new FeatureLayer(path + "/arcgis/rest/services/" + main2 + "/FeatureServer/0", {
            mode: FeatureLayer.MODE_SNAPSHOT,
            outFields: ["*"]
        });
        map.addLayers([landusePolygonLayer]);

        loadFeatures2(main3);


        function initEditing(evt) {
            console.log("initEditing", evt);
            // var map = this;
            var currentLayer = null;
            var layers = arrayUtils.map(evt.layers, function (result) {
                return result.layer;
            });
            console.log("layers", layers);
            var editToolbar = new Edit(map);
            editToolbar.on("deactivate", function (evt) {
                currentLayer.applyEdits(null, [evt.graphic], null);
            });

            arrayUtils.forEach(layers, function (layer) {
                var editingEnabled = false;
                layer.on("dbl-click", function (evt) {
                    event.stop(evt);
                    if (editingEnabled === false) {
                        editingEnabled = true;
                        editToolbar.activate(Edit.EDIT_VERTICES, evt.graphic);
                    } else {
                        currentLayer = this;
                        editToolbar.deactivate();
                        editingEnabled = false;
                    }
                });

                layer.on("click", function (evt) {
                    event.stop(evt);
                    var oid = evt.graphic.attributes["id"];
                    console.log("oid--------------" + oid);
                    $("#li" + oid).addClass("active").siblings().removeClass("active");
                });
            });

            var templatePicker = new TemplatePicker({
                featureLayers: layers,
                rows: "auto",
                columns: 1,
                grouping: false,
                style: "height: auto; overflow: auto;border:none;align:center"
            }, "templatePickerDiv");

            templatePicker.startup();

            var drawToolbar = new Draw(map);

            var selectedTemplate;
            templatePicker.on("selection-change", function () {
                if (templatePicker.getSelected()) {
                    selectedTemplate = templatePicker.getSelected();
                }
                switch (selectedTemplate.featureLayer.geometryType) {
                    case "esriGeometryPoint":
                        drawToolbar.activate(Draw.POINT);
                        break;
                    case "esriGeometryPolyline":
                        drawToolbar.activate(Draw.POLYLINE);
                        break;
                    case "esriGeometryPolygon":
                        drawToolbar.activate(Draw.POLYGON);
                        break;
                }
            });

            drawToolbar.on("draw-end", function (evt) {
                drawToolbar.deactivate();
                editToolbar.deactivate();
                var newAttributes = lang.mixin({}, selectedTemplate.template.prototype.attributes);


                console.log("newAttributes---------------" + JSON.stringify(newAttributes));


                //attrNameDB 是地理对象的英文名称
                var maxId = getMaxId(main);
                obj["id"] = maxId;
                obj["area_id"] = "1";
                obj["auth_key"] = getAuthkey();
                var oLi = attrName + maxId;
                obj["status"] = "0";
                var newGraphic = new Graphic(evt.geometry, null, obj);
                selectedTemplate.featureLayer.applyEdits([newGraphic], null, null);

                var li = "<li id='li" + obj["id"] + "'><i>" + oLi + "</i><span class='icon-search pull-right' title='搜索' data-id='" + obj["id"] + "'></span><span class='icon-remove pull-right' title='删除' data-id='" + obj["id"] + "' onclick='dell(" + obj["id"] + ")'></span><span class='icon-edit pull-right' title='编辑' data-id='" + obj["id"] + "' onclick='edit(" + obj["id"] + ")'></span></li>";
                $("#nyc_graphics").append(li);
                goPage(1, 10);
                console.log("------------------新增之后加载");
                // setTimeout(function () {
                //     loadFeatures2(main3);
                // },5000);
                //弹出编辑框之前先清空所有的数据
                cleanDataForCreateFeature();
                //将obj数据绑定到表单
                $(formId + " #id").val(obj["id"]);
                $(formId + " #area").val(obj["area_id"]);
                $(formId + " #status").val(obj["status"]);
               $("#confirm_modal").modal("show");
            });
        }

        $("span[class='icon-remove pull-right']").on("click", function () {
            console.log("删除");
            var id = $(this).data("id");
            var Lid = $(this).parent("li").attr("id");
            console.log(id, Lid);
            if (id) {
                var url = main3 + "/delete/" + id;
                bootbox.confirm({
                    message: "确定要删除该记录么?",
                    buttons: {
                        confirm: {
                            label: '是',
                            className: 'btn-success'
                        },
                        cancel: {
                            label: '否',
                            className: 'btn-danger'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            $.ajax({
                                type: "GET",
                                url: url,
                                success: function (msg) {
                                    if (msg["result"]) {
                                        showMessageBox("info", "信息删除成功!");
                                        var id = "#" + Lid;
                                        $("#nyc_graphics").find(id).remove();
                                        goPage(1, 10);
                                        landusePolygonLayer.refresh();
                                    } else {
                                        showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
                                    }
                                },
                                error: function (msg) {
                                    showMessageBox("danger", "信息删除失败，请联系管理员");
                                }
                            });
                        }
                    }
                });
            }
        });

        $("span[class='icon-search pull-right']").on("click", function () {
            var id = $(this).data("id");
            //通过oid查询对应的要素  设置居中  高光
            var query = new Query();
            query.where = "id=" + id;
            landusePolygonLayer.queryFeatures(query).then(
                function (result) {
                    var feature = result.features[0].geometry;
                    console.log(JSON.stringify(feature));
                    var x = "";
                    var y = "";
                    if (feature.type == "point") {
                        x = feature.x;
                        y = feature.y;
                    } else if (feature.type == "polygon") {
                        var ring0 = feature.rings[0];
                        x = ring0[0][0];
                        y = ring0[0][1];
                    } else if (feature.type == "polyline") {
                        var ring0 = feature.paths[0];
                        x = ring0[0][0];
                        y = ring0[0][1];
                    }

                    var m = {
                        X: x,
                        Y: y
                    }


                    // console.log("m before------------", JSON.stringify(m));
                    var center = Mercator2lonLat(m);
                    // console.log("m after------------", JSON.stringify(m));
                    map.centerAndZoom([center["x"], center["y"]], 11);
                }
            );
        });
    })
}


/**
 *获取最大的id
 */
function getMaxId(main) {
    var newId = 1;
    var url = main + "/findMaxId";
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        console.log(main + "-------findMaxId---------" + JSON.stringify(data));
        newId = data;
    });
    return newId;
};


/**
 *载入要素信息
 */
function loadFeatures(main, colName) {
    console.log("加载loadFeatures");
    var features = findAllGeoFeatures(main);
    console.log(features);
    var content = "";
    for (var x = 0; x < features.length; x++) {
        var i = features[x]["id"];
        content += "<li id='li" + i + "'><i>" + features[x][colName] + "</i><span class='icon-search pull-right' title='搜索' data-id='" + features[x]["id"] + "'></span><span class='icon-remove pull-right' title='删除' data-id='" + features[x]["id"] + "'></span><span class='icon-edit pull-right' title='编辑' data-id='" + features[x]["id"] + "' onclick='edit(" + features[x]["id"] + ")'></span></li>";
    }
    $("#nyc_graphics").html(content);
    goPage(1, 10);
}

function loadFeatures2(main3) {
    console.log("加载loadFeatures2");
    var features = findAllGeoFeatures(main3);
    console.log(features);
    var content = "";
    for (var x = 0; x < features.length; x++) {
        var i = features[x]["id"];
        var areaNo = features[x]["area"]["areaNo"];
        var areaName = features[x]["area"]["areaName"];
        content += "<li id='li" + i + "'><i>" + areaNo + areaName + "</i><span class='icon-search pull-right' title='搜索' data-id='" + features[x]["id"] + "'></span><span class='icon-remove pull-right' title='删除' data-id='" + features[x]["id"] + "'></span><span class='icon-edit pull-right' title='编辑' data-id='" + features[x]["id"] + "' onclick='edit(" + features[x]["id"] + ")'></span></li>";
    }
    $("#nyc_graphics").html(content);
    goPage(1, 10);
}

// 删除记录
function dell(id) {
    console.log("-----------------想要删除的删除");
    var url = main + "/delete/" + id;
    bootbox.confirm({
        message: "确定要删除该记录么?",
        buttons: {
            confirm: {
                label: '是',
                className: 'btn-success'
            },
            cancel: {
                label: '否',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "GET",
                    url: url,
                    success: function (msg) {
                        if (msg) {
                            showMessageBox("info", "信息删除成功!");
                            var Lid = "#li" + id;

                            $("#nyc_graphics").find(Lid).remove();
                            goPage(1, 10);
                        }
                    },
                    error: function (msg) {
                        showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
                    }
                });
            }
        }
    });
}


function commonGisMap2(basemap, main, main2, path, colName, landusePolygonLayer, attrNameDB, attrName, obj, formId) {
    var map;
    require([
        "esri/map",
        "esri/layers/ArcGISTiledMapServiceLayer",
        "esri/SpatialReference",
        "esri/toolbars/draw",
        "esri/toolbars/edit",
        "esri/graphic",
        "esri/config",
        "esri/layers/FeatureLayer",
        "esri/tasks/query",
        "esri/symbols/SimpleMarkerSymbol",
        "esri/symbols/SimpleLineSymbol",
        "esri/symbols/SimpleFillSymbol",
        "esri/dijit/editing/TemplatePicker",
        "dojo/_base/array",
        "dojo/_base/event",
        "dojo/_base/lang",
        "dojo/parser",
        "dijit/registry",
        "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
        "dijit/form/Button", "dojo/domReady!"
    ], function (Map, ArcGISTiledMapServiceLayer, SpatialReference, Draw, Edit, Graphic, esriConfig,
                 FeatureLayer, Query,
                 SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol,
                 TemplatePicker,
                 arrayUtils, event, lang, parser, registry) {
        parser.parse();
        esriConfig.defaults.io.proxyUrl = "/proxy/";
        esriConfig.defaults.geometryService = new esri.tasks.GeometryService(path + "/arcgis/rest/services/Utilities/Geometry/GeometryServer");
        basemap = "osm";
        map = new Map("viewDiv", {
            // basemap: basemap,
            basemap: "streets",
            center: [121, 32],
            fadeOnZoom: true,
            zoom: 7
        });

        // var tiled = new ArcGISTiledMapServiceLayer(tileUrl);
        // map.addLayer(tiled);


        map.on("layers-add-result", initEditing);

        landusePolygonLayer = new FeatureLayer(path + "/arcgis/rest/services/" + main2 + "/FeatureServer/0", {
            mode: FeatureLayer.MODE_SNAPSHOT,
            outFields: ["*"]
        });
        map.addLayers([landusePolygonLayer]);
        loadFeatures(main, colName);

        function initEditing(evt) {
            console.log("initEditing", evt);
            // var map = this;
            var currentLayer = null;
            var layers = arrayUtils.map(evt.layers, function (result) {
                return result.layer;
            });
            console.log("layers===========llm", layers);

            var editToolbar = new Edit(map);
            editToolbar.on("deactivate", function (evt) {
                currentLayer.applyEdits(null, [evt.graphic], null);
            });

            arrayUtils.forEach(layers, function (layer) {
                var editingEnabled = false;
                layer.on("dbl-click", function (evt) {
                    event.stop(evt);
                    if (editingEnabled === false) {
                        editingEnabled = true;
                        editToolbar.activate(Edit.EDIT_VERTICES, evt.graphic);
                    } else {
                        currentLayer = this;
                        editToolbar.deactivate();
                        editingEnabled = false;
                    }
                });

                layer.on("click", function (evt) {
                    event.stop(evt);
                    var oid = evt.graphic.attributes["id"];
                    console.log("oid--------------" + oid);
                    $("#li" + oid).addClass("active").siblings().removeClass("active");
                });
            });

            var templatePicker = new TemplatePicker({
                featureLayers: layers,
                rows: "auto",
                columns: 1,
                grouping: false,
                style: "height: auto; overflow: auto;border:none;align:center"
            }, "templatePickerDiv");

            templatePicker.startup();

            var drawToolbar = new Draw(map);

            var selectedTemplate;
            templatePicker.on("selection-change", function () {
                if (templatePicker.getSelected()) {
                    selectedTemplate = templatePicker.getSelected();
                }
                switch (selectedTemplate.featureLayer.geometryType) {
                    case "esriGeometryPoint":
                        drawToolbar.activate(Draw.POINT);
                        break;
                    case "esriGeometryPolyline":
                        drawToolbar.activate(Draw.POLYLINE);
                        break;
                    case "esriGeometryPolygon":
                        drawToolbar.activate(Draw.POLYGON);
                        break;
                }
            });

            drawToolbar.on("draw-end", function (evt) {
                drawToolbar.deactivate();
                editToolbar.deactivate();
                var newattributes = lang.mixin({}, selectedTemplate.template.prototype.attributes);
                var maxId = getMaxId(main);

                //attrNameDB 是地理对象在数据库中的英文名称前缀
                obj["id"] = maxId;
                obj[attrNameDB + "_no"] = maxId;//地理对象的编号
                obj[attrNameDB + "_name"] = attrName + maxId;//地理对象的名称
                obj["status"] = "0";
                var newGraphic = new Graphic(evt.geometry, null, obj);
                selectedTemplate.featureLayer.applyEdits([newGraphic], null, null);


                var li = "<li id='li" + obj["id"] + "'><i>" + obj[attrNameDB + "_name"] + "</i><span class='icon-search pull-right' title='搜索' data-id='"
                    + obj["id"] + "'></span><span class='icon-remove pull-right' title='删除' data-id='" + obj["id"] + "' onclick='dell(" + obj["id"] + ")'></span><span class='icon-edit pull-right' title='编辑' data-id='"
                    + obj["id"] + "' onclick='edit(" + obj["id"] + ")'></span></li>";
                $("#nyc_graphics").append(li);

                goPage(1, 10);
                console.log("------------------新增之后加载");

                //弹出编辑框之前先清空所有的数据
                cleanDataForCreateFeature();
                //将obj数据绑定到表单
                $(formId + " #id").val(obj["id"]);
                $(formId + " #" + attrNameDB + "No").val(obj[attrNameDB + "_no"]);
                $(formId + " #" + attrNameDB + "Name").val(obj[attrNameDB + "_name"]);
                $(formId + " #status").val(obj["status"]);
                $("#confirm_modal").modal("show");
            });
        }

        $("span[class='icon-remove pull-right']").on("click", function () {
            console.log("删除");
            var id = $(this).data("id");
            console.log(id);
            if (id) {
                var url = main + "/delete/" + id;
                bootbox.confirm({
                    message: "确定要删除该记录么?",
                    buttons: {
                        confirm: {
                            label: '是',
                            className: 'btn-success'
                        },
                        cancel: {
                            label: '否',
                            className: 'btn-danger'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            $.ajax({
                                type: "GET",
                                url: url,
                                success: function (msg) {
                                    if (msg["result"]) {
                                        showMessageBox("info", "信息删除成功!");
                                        var Lid = "#li" + id;
                                        $("#nyc_graphics").find(Lid).remove();
                                        goPage(1, 10);
                                        landusePolygonLayer.refresh();
                                    } else {
                                        showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
                                    }
                                },
                                error: function (msg) {
                                    showMessageBox("danger", "信息删除失败，请联系管理员");
                                }
                            });
                        }
                    }
                });

            }
        })


        $("span[class='icon-search pull-right']").on("click", function () {
            var id = $(this).data("id");
            //通过oid查询对应的要素  设置居中  高光
            var query = new Query();
            query.where = "id=" + id;
            landusePolygonLayer.queryFeatures(query).then(
                function (result) {
                    var feature = result.features[0].geometry;
                    var x = "";
                    var y = "";
                    if (feature.type == "point") {
                        x = feature.x;
                        y = feature.y;
                    } else if (feature.type == "polygon") {
                        var ring0 = feature.rings[0];
                        x = ring0[0][0];
                        y = ring0[0][1];
                    } else if (feature.type == "polyline") {
                        var ring0 = feature.paths[0];
                        x = ring0[0][0];
                        y = ring0[0][1];
                    }
                    var m = {
                        X: x,
                        Y: y
                    };
                    var center = Mercator2lonLat(m);
                    map.centerAndZoom([center["x"], center["y"]], 11);
                }
            );
        });
    })
}

/**
 *获取auth_key
 */
function getAuthkey() {
    var auth_key;
    var user = getLoginUser();
    auth_key = user.location.locCode;
    return auth_key;
}

