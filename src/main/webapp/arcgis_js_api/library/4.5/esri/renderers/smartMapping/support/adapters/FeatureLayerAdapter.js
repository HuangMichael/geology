// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../../../../core/tsSupport/declareExtendsHelper ../../../../core/tsSupport/decorateHelper ../../../../core/promiseUtils ../../../../core/Error ../../../../tasks/GenerateRendererTask ./LayerAdapter ../../../../tasks/support/StatisticDefinition ../../../../core/accessorSupport/decorators ../../../../layers/support/arcgisLayerUrl ../../statistics/support/utils ../utils ../../../../layers/support/fieldUtils".split(" "),function(y,z,r,t,f,h,u,v,m,p,w,g,k,n){var x=/(https?:)?\/\/services.*\.arcgis\.com/i;
return function(q){function d(a){var b=q.call(this)||this;b._layer=a.layer;return b}r(d,q);d.prototype._statisticsFromQuery=function(a){var b=this._layer;if(!b.get("capabilities.query.supportsStatistics")||"multipatch"===this.geometryType&&!w.isHostedAgolService(b.url)&&10.5>b.version)return f.reject(new h("feature-layer-adapter:not-supported","Layer does not support statistics query"));var c=b.createQuery();c.outStatistics=a.outStatistics;c.groupByFieldsForStatistics=a.groupByFieldsForStatistics;
c.orderByFields=a.orderByFields;c.where=g.mergeWhereClauses(c.where,a.where);c.sqlFormat=a.sqlFormat;return b.queryFeatures(c)};d.prototype._calcBinsSQL=function(a,b){var c=[],e=b.length;b.forEach(function(b,l){b=g.mergeWhereClauses(a+" \x3e\x3d "+b[0],a+(l===e-1?" \x3c\x3d ":" \x3c ")+b[1]);c.push("WHEN ("+b+") THEN "+(l+1))});return["CASE",c.join(" "),"ELSE 0 END"].join(" ")};d.prototype._getNormalizationTotal=function(a,b){return a&&"percent-of-total"===b?this.summaryStatistics({field:a}).then(function(a){return a.sum}):
f.resolve()};d.prototype._getQueryParamsForExpr=function(a,b){if(!a.valueExpression&&!a.sqlExpression){var c=a.field,e=c?this.getField(c):null,e=n.isDateField(e);return{sqlExpression:e?g.msSinceUnixEpochSQL(this,c):g.getFieldExpr(a,b),sqlWhere:e?null:g.getSQLFilterForNormalization(a)}}return{valueExpression:a.valueExpression,sqlExpression:a.sqlExpression,sqlWhere:a.sqlWhere}};d.prototype._getDataRange=function(a,b,c){return null!=b&&null!=c?f.resolve({min:b,max:c}):this.summaryStatistics(a).then(function(a){return{min:a.min,
max:a.max}})};d.prototype._histogramForExpr=function(a){var b=this;return this._getNormalizationTotal(a.field,a.normalizationType).then(function(c){return b._getQueryParamsForExpr(a,c)}).then(function(c){return b._getDataRange(c,a.minValue,a.maxValue).then(function(e){var d=e.min,l=e.max,f=a.numBins||10;e=k.getEqualIntervalBins(d,l,f);e=b._calcBinsSQL(c.sqlExpression,e);e={outStatistics:[new m({statisticType:"count",outStatisticFieldName:"countOFExpr",onStatisticField:"*"})],groupByFieldsForStatistics:[e],
orderByFields:[e],where:c.sqlWhere,sqlFormat:"standard"};return b._statisticsFromQuery(e).then(function(a){return k.getHistogramFromFeatureSet(a,d,l,f)})})})};d.prototype._histogramForField=function(a){var b=this,c=null,c=null!=a.minValue&&null!=a.maxValue?f.resolve({min:a.minValue,max:a.maxValue}):this.summaryStatistics(a).then(function(a){return a.count?{min:a.min,max:a.max}:f.reject(new h("feature-layer-adapter:insufficient-data","Either the layer has no features or none of the features have data for the field"))});
return c.then(function(c){return k.getBins({layer:b,field:a.field,numBins:a.numBins},{min:c.min,max:c.max})})};d.prototype.getField=function(a){void 0===a&&(a="");return this._layer.getField(a)};d.prototype.getFieldUsageInfo=function(a){return this.getField(a)?{supportsLabelingInfo:!0,supportsRenderer:!0,supportsPopupTemplate:!0,supportsLayerQuery:!0,supportsStatistics:!0}:null};d.prototype.getFieldDomain=function(a,b){return this._layer.getFieldDomain(a,b)};d.prototype.summaryStatistics=function(a){var b=
a.field,c=b?this.getField(b):null,e=n.isDateField(c),c=a.valueExpression||a.sqlExpression;if(this._hasLocalSource||a.features||"function"===typeof b)return f.reject(new h("feature-layer-adapter:not-implemented","Client-side calculation is not implemented yet"));if(!this.supportsSQLExpression&&(e||c))return f.reject(new h("feature-layer-adapter:not-supported","Layer does not support standardized SQL expression for queries"));var d=(this.supportsSQLExpression&&e?g.msSinceUnixEpochSQL(this,b):a.sqlExpression)||
b,b=d?g.getRangeExpr(d,a.minValue,a.maxValue):null;a={sqlFormat:a.sqlExpression?"standard":null,where:g.mergeWhereClauses(a.sqlWhere,b),outStatistics:g.statisticTypes.map(function(a){var b=new m;b.statisticType="variance"===a?"var":a;b.onStatisticField=d;b.outStatisticFieldName=a+"_value";return b})};return this._statisticsFromQuery(a).then(k.getSummaryStatisticsFromFeatureSet).then(k.processSummaryStatisticsResult).then(function(a){e&&("min max avg stddev sum variance".split(" ").forEach(function(b){null!=
a[b]&&(a[b]=Math.ceil(a[b]))}),a.min===a.max&&null!=a.min&&(a.avg=a.min,a.stddev=a.variance=0));return a})};d.prototype.uniqueValues=function(a){var b=this,c=a.field,e=a.sqlExpression,d=a.valueExpression&&(!e||!this.supportsSQLExpression);if(this._hasLocalSource||a.features||d)return f.reject(new h("feature-layer-adapter:not-implemented","Client-side calculation is not implemented yet"));var d="countOF"+(c||"Expr"),g=new m;g.statisticType="count";g.onStatisticField=e?"*":c;g.outStatisticFieldName=
d;return this._statisticsFromQuery({sqlFormat:e?"standard":null,where:a.sqlWhere,outStatistics:[g],groupByFieldsForStatistics:[c||e]}).then(function(a){return k.getUniqueValuesFromFeatureSet(a,b,c)})};d.prototype.histogram=function(a){var b=a.field,b=b?this.getField(b):null,b=n.isDateField(b),c=a.valueExpression||a.sqlExpression,d=this.supportsSQLExpression;if(this._hasLocalSource)return f.reject(new h("feature-layer-adapter:not-implemented","Client-side calculation is not implemented yet"));if(c){if(!d)return f.reject(new h("feature-layer-adapter:not-supported",
"Layer does not support standardized SQL expression for queries"));if(a.normalizationType)return f.reject(new h("feature-layer-adapter:not-supported","Normalization is not allowed when 'valueExpression' or 'sqlExpression' is specified"))}return c||d?this._histogramForExpr(a):b||a.normalizationType?f.reject(new h("feature-layer-adapter:not-supported","Normalization and date field are not allowed when layer does not support standardized SQL expression for queries")):this._histogramForField(a)};d.prototype.queryFeatureCount=
function(a){if(this._hasLocalSource)return f.reject(new h("feature-layer-adapter:not-supported","Layer does not support count query"));var b=this._layer,c=b.createQuery();c.where=g.mergeWhereClauses(c.where,a);return b.queryFeatureCount(c)};d.prototype.generateRenderer=function(a){var b=this._layer;if(this._hasLocalSource||10.1>b.version)return f.reject(new h("feature-layer-adapter:not-supported","Layer does not support generateRenderer operation (requires ArcGIS Server version 10.1+)"));var c=new u({url:b.parsedUrl.path,
gdbVersion:b.gdbVersion}),b=b.createQuery();a.where=g.mergeWhereClauses(a.where,b.where);return c.execute(a)};d.prototype.getAllFeatures=function(a){return this._hasLocalSource||a?a?a.then(function(){return a.graphics}):this._hasLocalSource?f.resolve(this._layer.source):null:f.reject(new h("feature-layer-adapter:missing-parameters","View is required for service based layers"))};d.prototype.load=function(){var a=this,b=this._layer,c=b.load().then(function(){a.geometryType=b.geometryType;a.objectIdField=
b.objectIdField;a.supportsSQLExpression=x.test(b.url);a._hasLocalSource=!b.url&&!!b.source;if(a._hasLocalSource)return f.reject(new h("feature-layer-adapter:not-supported","Feature collection is not supported"))});this.addResolvingPromise(c);return this};return d=t([p.subclass()],d)}(p.declared(v))});