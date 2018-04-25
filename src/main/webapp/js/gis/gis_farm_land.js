var userPosition = getLoginUserPosition();

require([
        "esri/Map",
        "esri/views/SceneView",
        "esri/layers/Layer",
        "esri/Graphic",
        "esri/widgets/Expand",
        "esri/widgets/Home",
        "esri/geometry/Extent",
        "esri/Viewpoint",
        "esri/symbols/SimpleMarkerSymbol",
        "esri/core/watchUtils",
        "dojo/on",
        "dojo/dom",
        "dojo/domReady!"
    ],
    function (Map, SceneView, Layer, Graphic, Expand,
              Home, Extent, Viewpoint, SimpleMarkerSymbol, watchUtils,
              on, dom) {

        var featureLayer, editExpand;

        // feature edit area domNodes
        var editArea, attributeEditing, inputDescription,
            inputUserInfo, updateInstructionDiv;

        var map = new Map({
            basemap: "satellite"
        });

        var initialExtent = new Extent({
            xmin: -13045631,
            xmax: -13042853,
            ymin: 4034880,
            ymax: 4034880,
            spatialReference: 32679
        });

        var view = new SceneView({
            container: "viewDiv",
            map: map
//                extent: initialExtent
        });

        // get an ArcGIS server url from a custom function
        var arcgisUrl = "http://192.168.0.108:6080/arcgis/rest/services/projects_all/MapServer/0";
        Layer.fromArcGISServerUrl({
            url: arcgisUrl
        }).then(function (layer) {
            map.add(layer);
        });


        setupEditing();
        setupView();

        function applyEdits(params) {
            unselectFeature();
            var promise = featureLayer.applyEdits(params);
            editResultsHandler(promise);
        }

        // *****************************************************
        // applyEdits promise resolved successfully
        // query the newly created feature from the featurelayer
        // set the editFeature object so that it can be used
        // to update its features.
        // *****************************************************
        function editResultsHandler(promise) {
            promise
                .then(function (editsResult) {
                    var extractObjectId = function (result) {
                        return result.objectId;
                    };
                    // get the objectId of the newly added feature
                    if (editsResult.addFeatureResults.length > 0) {
                        var adds = editsResult.addFeatureResults.map(
                            extractObjectId);
                        newIncidentId = adds[0];
                        selectFeature(newIncidentId);
                    }
                })
                .otherwise(function (error) {
                    console.log("===============================================");
                    console.error("[ applyEdits ] FAILURE: ", error.code, error.name,
                        error.message);
                    console.log("error = ", error);
                });
        }

        // *****************************************************
        // listen to click event on the view
        // 1. select if there is an intersecting feature
        // 2. set the instance of editFeature
        // 3. editFeature is the feature to update or delete
        // *****************************************************
        view.on("click", function (evt) {
            unselectFeature();
            view.hitTest(evt.evt).then(function (response) {
                if (response.results.length > 0 && response.results[0].graphic) {

                    var feature = response.results[0].graphic;
                    selectFeature(feature.attributes[featureLayer.objectIdField]);

                    inputDescription.value = feature.attributes[
                        "Incident_Desc"];
                    inputUserInfo.value = feature.attributes[
                        "Incident_Address"];
                    attributeEditing.style.display = "block";
                    updateInstructionDiv.style.display = "none";
                }
            });
        });

        // *****************************************************
        // select Feature function
        // 1. Select the newly created feature on the view
        // 2. or select an existing feature when user click on it
        // 3. Symbolize the feature with cyan rectangle
        // *****************************************************
        function selectFeature(objectId) {
            // symbol for the selected feature on the view
            var selectionSymbol = SimpleMarkerSymbol({
                color: [0, 0, 0, 0],
                style: "square",
                size: "40px",
                outline: {
                    color: [0, 255, 255, 1],
                    width: "3px"
                }
            });
            var query = featureLayer.createQuery();
            query.where = featureLayer.objectIdField + " = " + objectId;

            featureLayer.queryFeatures(query).then(function (results) {
                if (results.features.length > 0) {
                    editFeature = results.features[0];
                    editFeature.symbol = selectionSymbol;
                    view.graphics.add(editFeature);
                }
            });
        }

        // *****************************************************
        // hide attributes update and delete part when necessary
        // *****************************************************
        function unselectFeature() {

            console.log("---------------------------------更新框")
            attributeEditing.style.display = "none";
            updateInstructionDiv.style.display = "block";
            inputDescription.value = null;
            inputUserInfo.value = null;
            view.graphics.removeAll();
        }

        // *****************************************************
        // add homeButton and expand widgets to UI
        // *****************************************************
        function setupView() {
            // set home buttone view point to initial extent
            var homeButton = new Home({
                view: view,
                viewpoint: new Viewpoint({
                    targetGeometry: initialExtent
                })
            });
            view.ui.add(homeButton, "top-left");

            //expand widget
            editExpand = new Expand({
                expandIconClass: "esri-icon-edit",
                expandTooltip: "Expand Edit",
                expanded: true,
                view: view,
                content: editArea
            });
            view.ui.add(editExpand, "top-right");
        }

        // *****************************************************
        // set up for editing
        // *****************************************************
        function setupEditing() {
            // input boxes for the attribute editing
            editArea = dom.byId("editArea");
            updateInstructionDiv = dom.byId("updateInstructionDiv");
            attributeEditing = dom.byId("featureUpdateDiv");
            inputDescription = dom.byId("inputDescription");
            inputUserInfo = dom.byId("inputUserInfo");

            // *****************************************************
            // btnUpdate click event
            // update attributes of selected feature
            // *****************************************************
            on(dom.byId("btnUpdate"), "click", function (evt) {
                if (editFeature) {
                    editFeature.attributes["Incident_Desc"] = inputDescription.value;
                    editFeature.attributes["Incident_Address"] = inputUserInfo.value;

                    var edits = {
                        updateFeatures: [editFeature]
                    };

                    applyEdits(edits);
                }
            });

            // *****************************************************
            // btnAddFeature click event
            // create a new feature at the click location
            // *****************************************************
            on(dom.byId("btnAddFeature"), "click", function () {
                console.log("--------------------新增要素");
                unselectFeature();
                on.once(view, "click", function (event) {
                    event.stopPropagation();

                    if (event.mapPoint) {
                        point = event.mapPoint.clone();
                        point.z = undefined;
                        point.hasZ = false;

                        newIncident = new Graphic({
                            geometry: point,
                            attributes: {}
                        });

                        var edits = {
                            addFeatures: [newIncident]
                        };


                        console.log("edits-----------------" + JSON.stringify(edits));

                        applyEdits(edits);

                        // ui changes in response to creating a new feature
                        // display feature update and delete portion of the edit area
                        attributeEditing.style.display = "block";
                        updateInstructionDiv.style.display = "none";
                        dom.byId("viewDiv").style.cursor = "auto";
                    }
                    else {
                        console.error("event.mapPoint is not defined");
                    }
                });

                // change the view's mouse cursor once user selects
                // a new incident type to create
                dom.byId("viewDiv").style.cursor = "crosshair";
                editArea.style.cursor = "auto";
            });

            // *****************************************************
            // delete button click event. ApplyEdits is called
            // with the selected feature to be deleted
            // *****************************************************
            on(dom.byId("btnDelete"), "click", function () {
                var edits = {
                    deleteFeatures: [editFeature]
                };
                applyEdits(edits);
            });

            // *****************************************************
            // watch for view LOD change. Display Feature editing
            // area when view.zoom level is 14 or higher
            // otherwise hide the feature editing area
            // *****************************************************
            view.then(function () {

                view.goTo({
                    center: userPosition,
                    tilt: 70,
                    duration: 1000,
                    easing: "in-out-expo",
                    zoom: 10
                });
                // watchUtils.whenTrue(view, "stationary", function () {
                //     if (editExpand) {
                //         if (view.zoom <= 14) {
                //             editExpand.domNode.style.display = "none";
                //         }
                //         else {
                //             editExpand.domNode.style.display = "block";
                //         }
                //     }
                // });
            });
        }

        function handleLayerLoadError(err) {
            console.log("Layer failed to load: ", err);
        }
    });
