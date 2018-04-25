// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../core/tsSupport/declareExtendsHelper ../core/tsSupport/decorateHelper ./support/widget ../core/accessorSupport/decorators ../core/watchUtils ./Widget ./ScaleBar/ScaleBarViewModel ../geometry/ScreenPoint dojo/dom-geometry dojo/i18n!./ScaleBar/nls/ScaleBar".split(" "),function(t,u,l,e,c,d,m,n,p,q,r,h){return function(k){function b(a){a=k.call(this)||this;a.unit="non-metric";a.view=null;a.viewModel=new p;return a}l(b,k);b.prototype.postInitialize=function(){var a=this;this.own([m.whenTrue(this,
"view.stationary",function(){return a.scheduleRender()})])};Object.defineProperty(b.prototype,"style",{set:function(a){this._set("style","dual"===this.unit?"line":a)},enumerable:!0,configurable:!0});b.prototype.castStyle=function(a){return"line"===a?a:"ruler"};b.prototype.castUnit=function(a){return"metric"===a||"dual"===a?a:"non-metric"};b.prototype.render=function(){var a="disabled"===this.get("viewModel.state"),b=(e={},e["esri-disabled"]=a,e),g,d;if(!a){var f=this.unit,a=this.style,e="metric"===
f||"dual"===f;if("non-metric"===f||"dual"===f)d=this.viewModel.getScaleBarProperties(50,"non-metric"),d="ruler"===a?this._renderRuler(d):this._renderLine(d,"bottom");e&&(g=this.viewModel.getScaleBarProperties(50,"metric"),g="ruler"===a?this._renderRuler(g):this._renderLine(g,"top"))}return c.tsx("div",{afterCreate:this._handleRootCreation,bind:this,class:"esri-scale-bar esri-widget",classes:b},g,d);var e};b.prototype._renderRuler=function(a){var b=2*a.value+" "+(h[a.unit]||h.unknownUnit);return c.tsx("div",
{class:"esri-scale-bar__bar-container",styles:{width:2*Math.round(a.length)+"px"},key:"esri-scale-bar__ruler"},c.tsx("div",{class:"esri-scale-bar__ruler"},c.tsx("div",{class:"esri-scale-bar__ruler-block"}),c.tsx("div",{class:"esri-scale-bar__ruler-block"}),c.tsx("div",{class:"esri-scale-bar__ruler-block"}),c.tsx("div",{class:"esri-scale-bar__ruler-block"})),c.tsx("div",{class:"esri-scale-bar__label-container"},c.tsx("div",{class:"esri-scale-bar__label"},"0"),c.tsx("div",{class:"esri-scale-bar__label"},
a.value),c.tsx("div",{class:"esri-scale-bar__label"},b)))};b.prototype._renderLine=function(a,b){var d=2*a.value+" "+(h[a.unit]||h.unknownUnit),d=c.tsx("div",{class:c.join("esri-scale-bar__label-container","esri-scale-bar__label-container--line"),key:"esri-scale-bar__label"},c.tsx("div",{class:"esri-scale-bar__label"},d)),e=(f={},f["esri-scale-bar__line--top"]="top"===b,f["esri-scale-bar__line--bottom"]="bottom"===b,f),f=c.tsx("div",{class:"esri-scale-bar__line",classes:e,key:"esri-scale-bar__line"});
return c.tsx("div",{class:"esri-scale-bar__bar-container",styles:{width:2*Math.round(a.length)+"px"},key:"esri-scale-bar__line-container"},"top"===b?[d,f]:[f,d]);var f};b.prototype._handleRootCreation=function(a){var b=this.viewModel;b&&(a=r.position(a,!0),b.scaleComputedFrom=new q({x:a.x,y:a.y}))};e([d.property({dependsOn:["unit"]}),c.renderable()],b.prototype,"style",null);e([d.cast("style")],b.prototype,"castStyle",null);e([d.property(),c.renderable()],b.prototype,"unit",void 0);e([d.cast("unit")],
b.prototype,"castUnit",null);e([d.aliasOf("viewModel.view"),c.renderable()],b.prototype,"view",void 0);e([d.property(),c.renderable(["viewModel.state"])],b.prototype,"viewModel",void 0);return b=e([d.subclass("esri.widgets.ScaleBar")],b)}(d.declared(n))});