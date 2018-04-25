// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/dijit/Legend/utils",["dojo/_base/lang","esri/lang","dojo/i18n!../../nls/jsapi"],function(k,p,q){var r=q.widgets.legend;return{getVisualVariableTitle:function(a,b){var f,c=a.field,d=a.normalizationField,g=!1,l=!1,m=!1,h,c=k.isFunction(c)?null:c,d=k.isFunction(d)?null:d;if(a.legendOptions&&a.legendOptions.title)f=a.legendOptions.title;else if(a.valueExpressionTitle)f=a.valueExpressionTitle;else{if(b.renderer&&b.renderer.authoringInfo&&b.renderer.authoringInfo.visualVariables){var n=b.renderer.authoringInfo.visualVariables;
for(h=0;h<n.length;h++){var e=n[h];if("colorInfo"===e.type&&"ratio"===e.style){g=!0;break}else if("colorInfo"===e.type&&"percent"===e.style){l=!0;break}else if("colorInfo"===e.type&&"percentTotal"===e.style){m=!0;break}}}(g=m&&"showRatioPercentTotal"||l&&"showRatioPercent"||g&&"showRatio"||d&&"showNormField"||c&&"showField"||null)&&(f=p.substitute({field:c&&b.getFieldLabel(c),normField:d&&b.getFieldLabel(d)},r[g]))}return f},getRendererTitle:function(a,b){}}});