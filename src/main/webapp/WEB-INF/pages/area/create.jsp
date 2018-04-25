<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <title>围垦区块信息创建</title>
    <style>
        html, body {
            height: 100%;
            width: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        #header {
            border: solid 2px #462d44;
            background: #fff;
            color: #444;
            -moz-border-radius: 4px;
            border-radius: 4px;
            font-family: sans-serif;
            font-size: 1.1em
            padding-left: 20px;
        }

        #map {
            padding: 1px;
            border: solid 2px #444;
            -moz-border-radius: 4px;
            border-radius: 4px;
        }

        #rightPane {
            border: none;
            padding: 0;
            width: 228px;
        }

        .templatePicker {
            border: solid 2px #444;
        }
    </style>
    <script src="/js/jquery-1.9.1.min.js"></script>
    <script src="https://js.arcgis.com/3.21/"></script>
    <script>
        var map;
        require([
            "esri/map",
            "esri/toolbars/draw",
            "esri/toolbars/edit",
            "esri/graphic",
            "esri/config",
            "esri/layers/FeatureLayer",
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
        ], function (Map, Draw, Edit, Graphic, esriConfig,
                     FeatureLayer,
                     SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol,
                     TemplatePicker,
                     arrayUtils, event, lang, parser, registry) {
            parser.parse();

            // refer to "Using the Proxy Page" for more information:  https://developers.arcgis.com/javascript/3/jshelp/ags_proxy.html
            esriConfig.defaults.io.proxyUrl = "/proxy/";

            // This service is for development and testing purposes only. We recommend that you create your own geometry service for use within your applications.
            esriConfig.defaults.geometryService = new esri.tasks.GeometryService("http://192.168.0.108:6080/arcgis/rest/services/Utilities/Geometry/GeometryServer");
            map = new Map("map", {
                basemap: "hybrid",
                center: [121, 31],
                zoom: 12
            });

            map.on("layers-add-result", initEditing);

//            var landusePointLayer = new FeatureLayer("http://192.168.0.108:6080/arcgis/rest/services/final/projects_all/FeatureServer/0", {
//                mode: FeatureLayer.MODE_SNAPSHOT,
//                outFields: ["*"]
//            });
            var landusePolygonLayer = new FeatureLayer("http://192.168.0.108:6080/arcgis/rest/services/final/area/FeatureServer/0", {
                mode: FeatureLayer.MODE_SNAPSHOT,
                outFields: ["*"]
            });

            map.addLayers([landusePolygonLayer]);

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
                        if (evt.ctrlKey === true || evt.metaKey === true) {  //delete feature if ctrl key is depressed
                            layer.applyEdits(null, null, [evt.graphic]);
                            currentLayer = this;
                            editToolbar.deactivate();
                            editingEnabled = false;
                        }
                    });
                });

                var templatePicker = new TemplatePicker({
                    featureLayers: layers,
                    rows: "auto",
                    columns: 2,
                    grouping: true,
                    style: "height: auto; overflow: auto;"
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


                    var attr = {
                        "id": getMaxId(),
                        "beach_name": "沿海滩涂",
                        "sea_level_type_id": 1,
                        "location_id": 15,
                        "measure_date": null,
                        "inning_status": '1'
                    }
                    console.log("newAttributes-------------------------" + JSON.stringify(attr));
                    var newGraphic = new Graphic(evt.geometry, null, attr);
                    selectedTemplate.featureLayer.applyEdits([newGraphic], null, null);
                });
            }
        });


        /**
         *获取最大的id
         */
//        function getMaxId() {
//            var newId = 1;
//            var url = "/beach/findMaxId";
//            $.ajaxSettings.async = false;
//            $.getJSON(url, function (data) {
//                console.log("newId----------------" + JSON.stringify(data));
//                newId = data;
//            });
//            return newId;
//        }
    </script>
</head>
<body class="claro">
<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="gutters:true, design:'headline'"
     style="width:100%;height:100%;">
    <div id="map" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"></div>
    <div id="rightPane" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'right'">
        <div id="templatePickerDiv"></div>
    </div>
</div>
</body>
</html>