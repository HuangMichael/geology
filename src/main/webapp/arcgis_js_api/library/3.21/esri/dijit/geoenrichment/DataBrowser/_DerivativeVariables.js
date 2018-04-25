// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/dijit/geoenrichment/DataBrowser/_DerivativeVariables",["dojo/_base/declare","dojo/_base/lang","dojo/_base/array","dojo/i18n!../../../nls/jsapi"],function(h,k,e,f){f=f.geoenrichment.dijit.VariableStore;var d=function(){function a(a,b){var c=f[a];return void 0!==c?c:b}var b=a("number","#"),c=a("percent","%"),d=a("average","Avg"),g=a("index","Index"),e=a("reliability","Reliability");return{_N_P:{labels:[b,c],names:["_N","_P"]},_N_A:{labels:[b,d],names:["_N","_A"]},_N_I:{labels:[b,g],names:["_N",
"_I"]},_N_R:{labels:[b,e],names:["_N","_R"]},_N_P_I:{labels:[b,c,g],names:["_N","_P","_I"]},_N_A_I:{labels:[b,d,g],names:["_N","_A","_I"]},_N_P_R:{labels:[b,c,e],names:["_N","_P","_R"]}}},d=d();return h(null,{_statesHash:null,_getDataCollections:function(a){var b=this.variableFields;b&&(b=b.slice(),b.push("derivative"));this._statesHash={};return this._getGeoenrichmentTask().getDataCollections(a,null,b,["all"])},_processDataCollections:function(a){this.inherited(arguments);e.forEach(this._data,this._processDerivativeVariable,
this)},_processDerivativeVariable:function(a){a.derivative=!!a.derivative;var b=this._getDerivingState(a);if(b){var c=this._getBaseVariable(a,b);c&&(c._base=!0,c[b]=!0,a.derivative=b,this._registerTogether(a,c))}},_getDerivingState:function(a){var b=a.id;return a.derivative?b.substr(b.length-2):"US"==this.getCurrentCountryID()&&"REL"==b.substr(0,3)?"_R":null},_getBaseVariable:function(a,b){var c=a.id,c="_R"===b?"ACS"+c.substr(3):c.substr(0,c.length-2);return this.get(this._composeIdentity(a,c))},
_registerTogether:function(a,b){var c=this._composeIdentity(b,a.id);this._variables[c]=a},_composeIdentity:function(a,b){var c=a[this.idProperty],c=c.substr(0,c.length-a.id.length-1);return c+"."+b},queryFilter:function(a){return"string"!=typeof a.derivative},getStates:function(a){(a=this.get(a))&&"string"==typeof a.derivative&&(a=this._getBaseVariable(a,a.derivative));if(!a||!a._base)return null;var b=this._statesHash[a[this.idProperty]];void 0===b&&(b=this._createStates(a),this._statesHash[a[this.idProperty]]=
b);return b},_createStates:function(a){var b="_N";a._P&&(b+="_P");a._A&&(b+="_A");a._I&&(b+="_I");a._R&&(b+="_R");if(b=d[b])b=k.mixin({},b),b.ids=e.map(b.names,function(b){return this._getRelatedVariable(a,b)[this.idProperty]},this);return b||null},_getRelatedVariable:function(a,b){if("_N"==b)return a;var c=a.id,c="_R"===b?"REL"+c.substr(3):c+b;return this.get(this._composeIdentity(a,c))}})});