// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/analysis/templates/CreateSpaceTimeCube.html":'\x3cdiv class\x3d"esriAnalysis"\x3e\r\n    \x3cdiv data-dojo-type\x3d"dijit/layout/ContentPane" style\x3d"margin-top:0.5em; margin-bottom: 0.5em;"\x3e\r\n      \x3cdiv data-dojo-attach-point\x3d"_aggregateToolContentTitle" class\x3d"analysisTitle"\x3e\r\n        \x3ctable class\x3d"esriFormTable" \x3e \r\n          \x3ctr\x3e\r\n            \x3ctd class\x3d"esriToolIconTd"\x3e\x3cdiv class\x3d"createSpaceTimeCubeIcon"\x3e\x3c/div\x3e\x3c/td\x3e\r\n            \x3ctd class\x3d"esriAlignLeading esriAnalysisTitle" data-dojo-attach-point\x3d"_toolTitle"\x3e\r\n                \x3clabel data-dojo-attach-point\x3d"_titleLbl"\x3e${i18n.createSpaceTimeCube}\x3c/label\x3e\r\n                \x3cnav class\x3d"breadcrumbs"  data-dojo-attach-point\x3d"_analysisModeLblNode" style\x3d"display:none;"\x3e\r\n                  \x3ca href\x3d"#" class\x3d"crumb" style\x3d"font-size:12px;" data-dojo-attach-event\x3d"onclick:_handleModeCrumbClick" data-dojo-attach-point\x3d"_analysisModeCrumb"\x3e\x3c/a\x3e\r\n                  \x3ca href\x3d"#" class\x3d"crumb is-active" data-dojo-attach-point\x3d"_analysisTitleCrumb" style\x3d"font-size:16px;"\x3e${i18n.createSpaceTimeCube}\x3c/a\x3e\r\n                \x3c/nav\x3e                \r\n            \x3c/td\x3e\r\n            \x3ctd\x3e\r\n              \x3cdiv class\x3d"esriFloatTrailing" style\x3d"padding:0;"\x3e\r\n                  \x3cdiv class\x3d"esriFloatLeading"\x3e\r\n                    \x3ca href\x3d"#" class\x3d\'esriFloatLeading helpIcon\' esriHelpTopic\x3d"toolDescription"\x3e\x3c/a\x3e\r\n                  \x3c/div\x3e\r\n                  \x3cdiv class\x3d"esriFloatTrailing"\x3e\r\n                    \x3ca href\x3d"#" data-dojo-attach-point\x3d"_closeBtn" title\x3d"${i18n.close}" class\x3d"esriAnalysisCloseIcon"\x3e\x3c/a\x3e\r\n                  \x3c/div\x3e              \r\n              \x3c/div\x3e                \r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n        \x3c/table\x3e\r\n      \x3c/div\x3e\r\n      \x3cdiv style\x3d"clear:both; border-bottom: #CCC thin solid; height:1px;width:100%;"\x3e\x3c/div\x3e\r\n    \x3c/div\x3e\r\n    \x3cdiv data-dojo-type\x3d"dijit/form/Form" data-dojo-attach-point\x3d"_form" readOnly\x3d"true"\x3e\r\n       \x3ctable class\x3d"esriFormTable"  data-dojo-attach-point\x3d"_aggregateTable"  style\x3d"border-collapse:collapse;border-spacing:5px;" cellpadding\x3d"5px" cellspacing\x3d"5px"\x3e \r\n         \x3ctbody\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_titleRow"\x3e\r\n            \x3ctd  colspan\x3d"3" class\x3d"sectionHeader" data-dojo-attach-point\x3d"_toolDescription" \x3e\x3c/td\x3e\r\n          \x3c/tr\x3e \r\n          \x3ctr data-dojo-attach-point\x3d"_analysisLabelRow" style\x3d"display:none;"\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading  esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.oneLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.inputLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput" \x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"pointLayer"\x3e\x3c/a\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_selectAnalysisRow" style\x3d"display:none;"\x3e\r\n            \x3ctd  colspan\x3d"3" class\x3d"padBottom1"\x3e\r\n              \x3cselect class\x3d"esriLeadingMargin1 longInput esriLongLabel"   data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_analysisSelect" data-dojo-attach-event\x3d"onChange:_handleAnalysisLayerChange"\x3e\x3c/select\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"2" \x3e\r\n              \x3clabel class\x3d"esriFloatLeading  esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.twoLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.binShapeLabel}\x3c/label\x3e\r\n           \x3c/td\x3e\r\n           \x3ctd class\x3d"shortTextInput" \x3e  \r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"binType"\x3e\x3c/a\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n            \x3ctd  colspan\x3d"3" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cselect class\x3d"esriLeadingMargin1 longInput esriLongLabel"   data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_binShapeSelect"\x3e\r\n                \x3coption value\x3d"Hexagon"\x3e${i18n.hexagon}\x3c/option\x3e\r\n                \x3coption value\x3d"Square"\x3e${i18n.square}\x3c/option\x3e\r\n              \x3c/select\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e \r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.threeLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.binSizeLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"distanceInterval"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e             \r\n          \x3c/tr\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_binsTypeRow"\x3e\r\n            \x3ctd style\x3d"padding-right:0;width:50%;" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cinput type\x3d"text" data-dojo-type\x3d"dijit/form/NumberTextBox" data-dojo-props\x3d"intermediateChanges:true,value:\'5\',required:true,missingMessage:\'${i18n.distanceMsg}\'" data-dojo-attach-point\x3d"_distanceIntervalValue" class\x3d"esriLeadingMargin1"  style\x3d"width:75%;"\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd colspan\x3d"2" style\x3d"padding-left:0.25em;width:50%;" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cselect class\x3d"mediumInput esriAnalysisSelect" data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_distanceIntervalUnitSelect" style\x3d"width:80%;table-layout:fixed;"\x3e\r\n                 \x3coption value\x3d"NauticalMiles"\x3e${i18n.nautMiles}\x3c/option\x3e\r\n                 \x3coption value\x3d"Miles"\x3e${i18n.miles}\x3c/option\x3e\r\n                 \x3coption value\x3d"Yards"\x3e${i18n.yards}\x3c/option\x3e\r\n                 \x3coption value\x3d"Feet"\x3e${i18n.feet}\x3c/option\x3e\r\n                 \x3coption type\x3d"separator"\x3e\x3c/option\x3e\r\n                 \x3coption value\x3d"Kilometers"\x3e${i18n.kilometers}\x3c/option\x3e\r\n                 \x3coption value\x3d"Meters"\x3e${i18n.meters}\x3c/option\x3e\r\n              \x3c/select\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e          \r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.fourLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.timeIntervalSizeLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"timeSizing"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e             \r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n            \x3ctd style\x3d"padding-right:0;width:50%;" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cinput type\x3d"text" data-dojo-type\x3d"dijit/form/NumberTextBox" data-dojo-props\x3d"intermediateChanges:true,required: true,missingMessage:\'${i18n.distanceMsg}\'" data-dojo-attach-point\x3d"_timeIntervalValue" class\x3d"esriLeadingMargin1"  style\x3d"width:75%;"/\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd colspan\x3d"2" style\x3d"padding-left:0.25em;width:50%;" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cselect class\x3d"mediumInput esriAnalysisSelect" data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_timeIntervalUnitsSelect" style\x3d"width:80%;table-layout:fixed;"\x3e\r\n              \x3c/select\x3e\r\n            \x3c/td\x3e\r\n        \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n          \x3ctd colspan\x3d"2"\x3e\r\n            \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.fiveLabel}\x3c/label\x3e\r\n            \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.timeAlignLabel}\x3c/label\x3e\r\n          \x3c/td\x3e\r\n          \x3ctd class\x3d"shortTextInput"\x3e\r\n            \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"timeIntervalAlignment"\x3e\x3c/a\x3e \r\n          \x3c/td\x3e             \r\n        \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n          \x3ctd  colspan\x3d"3" class\x3d"padBottom1"\x3e\r\n            \x3cselect class\x3d"esriLeadingMargin1 longInput esriLongLabel"   data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_timeAlignmentSelect" data-dojo-attach-event\x3d"onChange:_handleTimeAlignmentChange"\x3e\r\n              \x3coption value\x3d"EndTime"\x3e${i18n.endTime}\x3c/option\x3e\r\n              \x3coption value\x3d"ReferenceTime"\x3e${i18n.referenceTime}\x3c/option\x3e\r\n              \x3coption value\x3d"StartTime" selected\x3d"true"\x3e${i18n.startTime}\x3c/option\x3e\r\n            \x3c/select\x3e\r\n          \x3c/td\x3e\r\n        \x3c/tr\x3e            \r\n          \x3ctr data-dojo-attach-point\x3d"_timeRefLabelRow"\x3e\r\n          \x3ctd colspan\x3d"2"\x3e\r\n            \x3clabel class\x3d"esriLeadingMargin2"\x3e${i18n.referenceTimeLabel}\x3c/label\x3e\r\n          \x3c/td\x3e\r\n          \x3ctd class\x3d"shortTextInput"\x3e\r\n            \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"referenceTime"\x3e\x3c/a\x3e \r\n          \x3c/td\x3e             \r\n        \x3c/tr\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_timeRefRow"\x3e\r\n         \x3ctd style\x3d"padding-right:0;width:50%;" class\x3d"padTop0 padBottom1"\x3e\r\n            \x3cinput class\x3d"esriLeadingMargin2 esriAnalysisSelect" value\x3d"now" data-dojo-props\x3d"required:false" data-dojo-type\x3d"dijit/form/DateTextBox" data-dojo-attach-point\x3d"_timeRefDay" style\x3d"width:75%;"\x3e\r\n          \x3c/input\x3e\r\n         \x3c/td\x3e\r\n         \x3ctd colpsan\x3d"2" style\x3d"padding-left:0.25em;width:50%;" class\x3d"padTop0 padBottom1"\x3e   \r\n          \x3cinput  type\x3d"text" data-dojo-type\x3d"dijit/form/TimeTextBox" value\x3d"now" data-dojo-props\x3d"required:false,intermediateChanges:true,constraints:{formatLength:\'short\',selector:\'time\'}"  data-dojo-attach-point\x3d"_timeRefTime" data-dojo-attach-event\x3d"onChange:_handleRefTimeChange" style\x3d"margin-top:0;width:90%;"\x3e\r\n          \x3c/input\x3e\r\n        \x3c/td\x3e\r\n        \x3c/tr\x3e             \r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"2" class\x3d"padBottom0"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.sixLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.addStatsLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput padBottom0"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"summaryFields"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e             \r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n           \x3ctd colspan\x3d"3" class\x3d"padTop0"\x3e\r\n           \x3ctable class\x3d"esriFormTable" class\x3d"padTop0 padBottom1"\x3e\r\n             \x3ctbody\x3e\r\n               \x3ctr data-dojo-attach-point\x3d"_afterStatsRow"\x3e\r\n                 \x3ctd colspan\x3d"4" class\x3d"clear"\x3e\x3c/td\x3e\r\n               \x3c/tr\x3e\r\n              \x3c/tbody\x3e\r\n            \x3c/table\x3e\r\n          \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n         \x3c!-- \x3ctr data-dojo-attach-point\x3d"_srLabelRow"\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel" data-dojo-attach-point\x3d"_spatialRefLabel"\x3e${i18n.sevenLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.spatialReference}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"spatialReference"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e            \r\n          \x3c/tr\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_srInputRow"\x3e\r\n            \x3ctd colspan\x3d"3" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cinput type\x3d"text" data-dojo-type\x3d"dijit/form/ValidationTextBox" class\x3d"esriLeadingMargin1 longInput" data-dojo-props\x3d"trim:true" data-dojo-attach-point\x3d"_spatialRefInput" value\x3d""\x3e\x3c/input\x3e\r\n            \x3c/td\x3e                \r\n          \x3c/tr\x3e--\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_datastoreLabelRow"\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel" data-dojo-attach-point\x3d"_datastoreLabel"\x3e${i18n.eightLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.datastoreLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"dataStore"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e              \r\n          \x3c/tr\x3e\r\n          \x3ctr data-dojo-attach-point\x3d"_selectDataStore"\x3e\r\n            \x3ctd  colspan\x3d"3" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cselect class\x3d"esriLeadingMargin1 longInput esriLongLabel"   data-dojo-type\x3d"dijit/form/Select" data-dojo-attach-point\x3d"_datastoreSelect"\x3e\r\n                \x3coption value\x3d"relational"\x3e${i18n.relationalDS}\x3c/option\x3e\r\n                \x3coption value\x3d"spatialtemporal" selected\x3d"true"\x3e${i18n.spatialDS}\x3c/option\x3e\r\n              \x3c/select\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e \r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"2"\x3e\r\n              \x3clabel class\x3d"esriFloatLeading esriTrailingMargin025 esriAnalysisNumberLabel"\x3e${i18n.nineLabel}\x3c/label\x3e\r\n              \x3clabel class\x3d"esriAnalysisStepsLabel"\x3e${i18n.outputLayerLabel}\x3c/label\x3e\r\n            \x3c/td\x3e\r\n            \x3ctd class\x3d"shortTextInput"\x3e\r\n              \x3ca href\x3d"#" class\x3d\'esriFloatTrailing helpIcon\' esriHelpTopic\x3d"outputName"\x3e\x3c/a\x3e \r\n            \x3c/td\x3e             \r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"3" class\x3d"padTop0 padBottom1"\x3e\r\n              \x3cinput type\x3d"text" data-dojo-type\x3d"dijit/form/ValidationTextBox" class\x3d"esriLeadingMargin1 longInput" data-dojo-props\x3d"trim:true,required:true" data-dojo-attach-point\x3d"_outputLayerInput" value\x3d""\x3e\x3c/input\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n          \x3ctr\x3e\r\n            \x3ctd colspan\x3d"3"\x3e\r\n               \x3cdiv class\x3d"esriLeadingMargin1" data-dojo-attach-point\x3d"_chooseFolderRow"\x3e\r\n                 \x3clabel style\x3d"width:9px;font-size:smaller;"\x3e${i18n.saveResultIn}\x3c/label\x3e\r\n                 \x3cinput class\x3d"longInput esriFolderSelect" data-dojo-attach-point\x3d"_webMapFolderSelect" dojotype\x3d"dijit/form/FilteringSelect" trim\x3d"true"\x3e\x3c/input\x3e\r\n               \x3c/div\x3e\r\n            \x3c/td\x3e\r\n          \x3c/tr\x3e\r\n        \x3c/tbody\x3e\r\n       \x3c/table\x3e\r\n     \x3c/div\x3e\r\n    \x3cdiv data-dojo-attach-point\x3d"_aggregateToolContentButtons" style\x3d"padding:5px;margin-top:5px;border-top:solid 1px #BBB;"\x3e\r\n      \x3cdiv class\x3d"esriExtentCreditsCtr"\x3e\r\n        \x3ca class\x3d"esriFloatTrailing esriSmallFont"  href\x3d"#" data-dojo-attach-point\x3d"_showCreditsLink" data-dojo-attach-event\x3d"onclick:_handleShowCreditsClick"\x3e${i18n.showCredits}\x3c/a\x3e\r\n       \x3clabel data-dojo-attach-point\x3d"_chooseExtentDiv" class\x3d"esriSelectLabel esriExtentLabel"\x3e\r\n         \x3cinput type\x3d"radio" data-dojo-attach-point\x3d"_useExtentCheck" data-dojo-type\x3d"dijit/form/CheckBox" data-dojo-props\x3d"checked:true" name\x3d"extent" value\x3d"true"/\x3e\r\n           ${i18n.useMapExtent}\r\n       \x3c/label\x3e\r\n      \x3c/div\x3e\r\n      \x3cbutton data-dojo-type\x3d"dijit/form/Button" type\x3d"submit" data-dojo-attach-point\x3d"_saveBtn" class\x3d"esriLeadingMargin4 esriAnalysisSubmitButton" data-dojo-attach-event\x3d"onClick:_handleSaveBtnClick"\x3e\r\n          ${i18n.runAnalysis}\r\n      \x3c/button\x3e\r\n    \x3c/div\x3e\r\n    \x3cdiv data-dojo-type\x3d"dijit/Dialog" title\x3d"${i18n.creditTitle}" data-dojo-attach-point\x3d"_usageDialog" style\x3d"width:40em;"\x3e\r\n      \x3cdiv data-dojo-type\x3d"esri/dijit/analysis/CreditEstimator"  data-dojo-attach-point\x3d"_usageForm"\x3e\x3c/div\x3e\r\n    \x3c/div\x3e\r\n  \x3c/div\x3e\r\n'}});
define("esri/dijit/analysis/CreateSpaceTimeCube","require dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/_base/connect dojo/_base/json dojo/has dojo/json dojo/string dojo/dom-style dojo/dom-attr dojo/dom-construct dojo/query dojo/dom-class dijit/_WidgetBase dijit/_TemplatedMixin dijit/_WidgetsInTemplateMixin dijit/_OnDijitClickMixin dijit/_FocusMixin dijit/registry dijit/form/Button dijit/form/CheckBox dijit/form/Form dijit/form/Select dijit/form/TextBox dijit/form/ValidationTextBox dijit/layout/ContentPane dijit/form/FilteringSelect dijit/Dialog ../../kernel ../../lang ./AnalysisBase ./utils ./CreditEstimator ./_AnalysisOptions dojo/i18n!../../nls/jsapi dojo/text!./templates/CreateSpaceTimeCube.html".split(" "),
function(u,m,d,f,n,k,v,I,w,l,J,g,x,y,z,A,B,C,D,q,K,L,M,p,N,O,P,Q,R,E,r,F,e,S,G,t,H){m=m([z,A,B,C,D,G,F],{declaredClass:"esri.dijit.analysis.CreateSpaceTimeCube",templateString:H,widgetsInTemplate:!0,pointLayer:null,summaryFields:null,outputLayerName:null,i18n:null,toolName:"CreateSpaceTimeCube",helpFileName:"CreateSpaceTimeCube",resultParameter:"outputCube",distanceDefaultUnits:"Miles",constructor:function(a){this._pbConnects=[];this._statsRows=[];a.containerNode&&(this.container=a.containerNode)},
destroy:function(){this.inherited(arguments);f.forEach(this._pbConnects,n.disconnect);delete this._pbConnects},postMixInProperties:function(){this.inherited(arguments);d.mixin(this.i18n,t.aggregatePointsTool);d.mixin(this.i18n,t.createSpaceTimeCubeTool)},postCreate:function(){this.inherited(arguments);y.add(this._form.domNode,"esriSimpleForm");this._outputLayerInput.set("validator",d.hitch(this,this.validateServiceName));this._timeIntervalValue.set("isInRange",d.hitch(this._timeIntervalValue,e.isGreaterThanZero));
this._timeIntervalValue.set("rangeMessage",this.i18n.greaterThanZeroMsg);this._distanceIntervalValue.set("rangeMessage",this.i18n.greaterThanZeroMsg);this._distanceIntervalValue.set("isInRange",d.hitch(this._distanceIntervalValue,e.isGreaterThanZero));this._buildUI()},startup:function(){},_onClose:function(a){this._aspectHandle&&(this._aspectHandle.remove(),this._aspectHandle=null);a&&(this._save(),this.emit("save",{save:!0}));this.emit("close",{save:a})},_buildJobParams:function(){var a={},b=e.constructAnalysisInputLyrObj(this.pointLayer,
!0);a.pointLayer=b;a.binSize=this._distanceIntervalValue.get("value");a.binSizeUnit=this._distanceIntervalUnitSelect.get("value");a.timeStepInterval=this._timeIntervalValue.get("value");a.timeStepIntervalUnit=this._timeIntervalUnitsSelect.get("value");a.timeStepAlignment=this._timeAlignmentSelect.get("value");"ReferenceTime"===a.timeStepAlignment&&(a.timeStepReference=this.get("timeReference"));0<this.get("summaryFields").length&&(a.summaryFields=k.toJson(this.get("summaryFields")));this.showChooseExtent&&
this._useExtentCheck.get("checked")&&(a.context=k.toJson({extent:this.map.extent._normalize(!0)}));this.returnFeatureCollection&&(b={outSR:this.map.spatialReference},this.showChooseExtent&&this._useExtentCheck.get("checked")&&(b.extent=this.map.extent._normalize(!0)),a.context=k.toJson(b));this.showGeoAnalyticsParams&&(b={},this.showChooseExtent&&this._useExtentCheck.get("checked")&&(b.extent=this.map.extent._normalize(!0)),a.context=k.toJson(b));this.returnFeatureCollection||(a.OutputName=k.toJson({serviceProperties:{name:this._outputLayerInput.get("value")}}));
return a},_handleSaveBtnClick:function(){if(this._form.validate()){this._saveBtn.set("disabled",!0);var a={};a.jobParams=this._buildJobParams();this.execute(a)}},_handleShowCreditsClick:function(a){a.preventDefault();this._form.validate()&&this.getCreditsEstimate(this.toolName,this._buildJobParams()).then(d.hitch(this,function(a){this._usageForm.set("content",a);this._usageDialog.show()}))},_handleBrowseItemsSelect:function(a){a&&a.selection&&e.addAnalysisReadyLayer({item:a.selection,layers:this.pointLayers,
layersSelect:this._analysisSelect,browseDialog:a.dialog||this._browsedlg,widget:this}).always(d.hitch(this,function(){this._isAnalysisSelect&&this._handleAnalysisLayerChange(this._analysisSelect.get("value"))}))},_handleAttrSelectChange:function(a){var b,c;"0"!==a&&(b=this.get("statisticSelect"),c=this.get("fillSelect"),(a=this.getOptions(a))&&a.type&&(e.addStatisticsOptions({selectWidget:b,type:a.type,showGeoAnalyticsParams:!0}),e.addFillOptions({selectWidget:c,showGeoAnalyticsParams:!0})),"0"===
b.get("value")||b.get("isnewRowAdded")||(c=b.get("removeTd"),l.set(c,"display","block"),c=b.get("referenceWidget"),d.hitch(c,c._createStatsRow()),b.set("isnewRowAdded",!0)))},_handleStatsValueUpdate:function(a,b,c){this.get("attributeSelect")&&(a=this.get("attributeSelect"),a.get("value")&&"0"!==a.get("value")&&c&&"0"!==c&&!this.get("isnewRowAdded")&&(c=this.get("removeTd"),l.set(c,"display","block"),c=this.get("referenceWidget"),d.hitch(c,c._createStatsRow()),this.set("isnewRowAdded",!0)))},_handleTimeAlignmentChange:function(a){e.updateDisplay([this._timeRefLabelRow,
this._timeRefRow],"ReferenceTime"===a,"table-row")},_save:function(){},_buildUI:function(){var a=!0;e.initHelpLinks(this.domNode,this.showHelp);this.get("showSelectAnalysisLayer")&&(!this.get("pointLayer")&&this.get("pointLayers")&&this.set("pointLayer",this.pointLayers[0]),e.populateAnalysisLayers(this,"pointLayer","pointLayers"));e.addReadyToUseLayerOption(this,[this._analysisSelect]);this.outputLayerName&&(this._outputLayerInput.set("value",this.outputLayerName),a=!1);this._timeIntervalUnitsSelect.addOption([{value:"Seconds",
label:this.i18n.seconds},{value:"Minutes",label:this.i18n.minutes},{value:"Hours",label:this.i18n.hours},{value:"Days",label:this.i18n.days},{value:"Weeks",label:this.i18n.weeks},{value:"Months",label:this.i18n.months},{value:"Years",label:this.i18n.years}]);this.timeIntervalAlignment&&this._timeAlignmentSelect.set("value",this.timeIntervalAlignment);this._handleTimeAlignmentChange(this._timeAlignmentSelect.get("value"));f.forEach(this.summaryFields,function(a){this._currentAttrSelect.set("value",
a.onStatisticField);d.hitch(this._currentAttrSelect,this._handleAttrSelectChange,a.onStatisticField)();this._currentStatsSelect.set("value",a.statisticType);d.hitch(this._currentStatsSelect,this._handleStatsValueUpdate,"value","",a.statisticType)()},this);l.set(this._chooseFolderRow,"display",!0===this.showSelectFolder?"block":"none");this.distanceDefaultUnits&&this._distanceIntervalUnitSelect.set("value",this.distanceDefaultUnits);this.showSelectFolder&&this.getFolderStore().then(d.hitch(this,function(a){this.folderStore=
a;e.setupFoldersUI({folderStore:this.folderStore,folderId:this.folderId,folderName:this.folderName,folderSelect:this._webMapFolderSelect,username:this.portalUser?this.portalUser.username:""})}));l.set(this._chooseExtentDiv,"display",!0===this.showChooseExtent?"inline-block":"none");this.set("groupBySelect",this.groupByField);l.set(this._showCreditsLink,"display",!0===this.showCredits?"block":"none");this.outputLayerName&&(this._outputLayerInput.set("value",this.outputLayerName),a=!1);this._updateAnalysisLayerUI(a);
this._loadConnections()},_loadConnections:function(){this.on("start",d.hitch(this,"_onClose",!0));this._connect(this._closeBtn,"onclick",d.hitch(this,"_onClose",!1))},_createStatsRow:function(){var a,b,c,h,f;a=g.create("tr",null,this._afterStatsRow,"before");c=g.create("td",{style:{width:"30%",maxWidth:"100px"}},a);b=g.create("td",{style:{width:"30%",maxWidth:"100px"}},a);h=g.create("td",{style:{width:"30%",maxWidth:"100px"}},a);c=new p({maxHeight:200,"class":"esriLeadingMargin05 mediumInput esriTrailingMargin025 attrSelect",
style:{tableLayout:"fixed",overflowX:"hidden"}},g.create("select",null,c));e.addAttributeOptions({selectWidget:c,layer:this.pointLayer,allowStringType:!1});b=new p({"class":"mediumInput statsSelect",style:{tableLayout:"fixed",overflowX:"hidden"}},g.create("select",null,b));h=new p({"class":"mediumInput attrSelect",style:{tableLayout:"fixed",overflowX:"hidden"}},g.create("select",null,h));e.addStatisticsOptions({selectWidget:b,showGeoAnalyticsParams:!0});e.addFillOptions({selectWidget:h,showGeoAnalyticsParams:!0});
b.set("disabled",!this.pointLayer||0===this.pointLayer.fields.length);h.set("disabled",!this.pointLayer||0===this.pointLayer.fields.length);c.set("statisticSelect",b);c.set("fillSelect",h);c.set("showGeoAnalyticsParams",this.showGeoAnalyticsParams);n.connect(c,"onChange",this._handleAttrSelectChange);f=g.create("td",{"class":"shortTextInput removeTd",style:{display:"none",maxWidth:"12px"}},a);h=g.create("a",{title:this.i18n.removeAttrStats,"class":"closeIcon statsRemove",innerHTML:"\x3cimg src\x3d'"+
u.toUrl("./images/close.gif")+"' border\x3d'0''/\x3e"},f);n.connect(h,"onclick",d.hitch(this,this._removeStatsRow,a));this._statsRows.push(a);b.set("attributeSelect",c);b.set("removeTd",f);b.set("isnewRowAdded",!1);b.set("referenceWidget",this);b.watch("value",this._handleStatsValueUpdate);this._currentStatsSelect=b;this._currentAttrSelect=c;return!0},_removeStatsRow:function(a){f.forEach(q.findWidgets(a),function(a){a.destroyRecursive()});g.destroy(a)},_removeStatsRows:function(){f.forEach(this._statsRows,
this._removeStatsRow,this);this._statsRows=[]},_handleAnalysisLayerChange:function(a){"browse"===a?(this._analysisquery||(this._analysisquery=this._browsedlg.browseItems.get("query")),this._browsedlg.browseItems.set("query",this._analysisquery+' AND tags:"point"'),this._isAnalysisSelect=!0,this._browsedlg.show()):"browselayers"===a?(this.showGeoAnalyticsParams&&(a=this._browseLyrsdlg.browseItems.get("query"),a.types.push('type:"Big Data File Share"'),this._browseLyrsdlg.browseItems.set("query",a)),
this._browseLyrsdlg.browseItems.plugIn.geometryTypes=["esriGeometryPoint"],this._isAnalysisSelect=this._browseLyrsdlg.browseItems.plugIn.checkTimeFilter=!0,this._browseLyrsdlg.show()):this.pointLayer!==this.pointLayers[a]&&(this.pointLayer=this.pointLayers[a],this._updateAnalysisLayerUI(!0))},_updateAnalysisLayerUI:function(a){this._removeStatsRows();this._createStatsRow();this.pointLayer&&a&&(this.outputLayerName=w.substitute(this.i18n.outputLayerName,{pointLayername:this.pointLayer.name}),this._outputLayerInput.set("value",
this.outputLayerName))},_handleRefTimeChange:function(a){this._timeRefDay.set("required",!this._timeRefDay.get("value"))},_setAnalysisGpServerAttr:function(a){a&&(this.analysisGpServer=a,this.set("toolServiceUrl",this.analysisGpServer+"/"+this.toolName))},_setPointLayerAttr:function(a){!r.isDefined(a)||"esriGeometryPoint"!==a.geometryType&&"esriGeometryMultipoint"!==a.geometryType&&"esriGeometryPolygon"!==a.geometryType||(this.pointLayer=a)},_getSummaryFieldsAttr:function(){var a=[],b,c;x(".statsSelect",
this.domNode).forEach(function(d){b=q.byNode(d);c=b.get("attributeSelect");"0"!==c.get("value")&&"0"!==b.get("value")&&(d={},d.statisticType=b.get("value"),d.onStatisticField=c.get("value"),a.push(d))});return a},_setDisableRunAnalysisAttr:function(a){this._saveBtn.set("disabled",a)},validateServiceName:function(a){return e.validateServiceName(a,{textInput:this._outputLayerInput})},_setPointLayersAttr:function(a){r.isDefined(a)&&(this.pointLayers=a=f.filter(a,function(a){return-1!==f.indexOf(["esriGeometryPoint",
"esriGeometryMultipoint","esriGeometryPolygon"],a.geometryType)}))},_connect:function(a,b,c){this._pbConnects.push(n.connect(a,b,c))}});v("extend-esri")&&d.setObject("dijit.analysis.CreateSpaceTimeCube",m,E);return m});