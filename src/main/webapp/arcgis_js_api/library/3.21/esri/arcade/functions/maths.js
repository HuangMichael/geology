// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/arcade/functions/maths",["require","exports","../languageUtils","dojo/number"],function(l,h,d,k){function g(d,e,a){if("undefined"===typeof a||0===+a)return Math[d](e);e=+e;a=+a;if(isNaN(e)||"number"!==typeof a||0!==a%1)return NaN;e=e.toString().split("e");e=Math[d](+(e[0]+"e"+(e[1]?+e[1]-a:-a)));e=e.toString().split("e");return+(e[0]+"e"+(e[1]?+e[1]+a:a))}Object.defineProperty(h,"__esModule",{value:!0});h.registerFunctions=function(f,e){f.number=function(a,c){return e(a,c,function(a,
c,b){d.pcCheck(b,1,2);a=b[0];return d.isNumber(a)?a:null===a?0:d.isDate(a)||d.isBoolean(a)?Number(a):d.isArray(a)?NaN:""===a||void 0===a?Number(a):d.isString(a)?void 0!==b[1]?(b=d.multiReplace(b[1],"\u2030",""),b=d.multiReplace(b,"\u00a4",""),k.parse(a,{pattern:b})):Number(a.trim()):Number(a)})};f.abs=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.abs(d.toNumber(b[0]))})};f.acos=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.acos(d.toNumber(b[0]))})};
f.asin=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.asin(d.toNumber(b[0]))})};f.atan=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.atan(d.toNumber(b[0]))})};f.atan2=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,2,2);return Math.atan2(d.toNumber(b[0]),d.toNumber(b[1]))})};f.ceil=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,2);return 2===b.length?(a=d.toNumber(b[1]),isNaN(a)&&(a=0),g("ceil",d.toNumber(b[0]),-1*a)):Math.ceil(d.toNumber(b[0]))})};
f.round=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,2);return 2===b.length?(a=d.toNumber(b[1]),isNaN(a)&&(a=0),g("round",d.toNumber(b[0]),-1*a)):Math.round(d.toNumber(b[0]))})};f.floor=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,2);return 2===b.length?(a=d.toNumber(b[1]),isNaN(a)&&(a=0),g("floor",d.toNumber(b[0]),-1*a)):Math.floor(d.toNumber(b[0]))})};f.cos=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.cos(d.toNumber(b[0]))})};f.isnan=function(a,
c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return"number"===typeof b[0]&&isNaN(b[0])})};f.exp=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.exp(d.toNumber(b[0]))})};f.log=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.log(d.toNumber(b[0]))})};f.pow=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,2,2);return Math.pow(d.toNumber(b[0]),d.toNumber(b[1]))})};f.random=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,0,0);return Math.random()})};
f.sin=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.sin(d.toNumber(b[0]))})};f.sqrt=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.sqrt(d.toNumber(b[0]))})};f.tan=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return Math.tan(d.toNumber(b[0]))})};f.defaultvalue=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,2,2);return null===b[0]||""===b[0]||void 0===b[0]?b[1]:b[0]})};f.isempty=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,
1,1);return null===b[0]||""===b[0]||void 0===b[0]?!0:!1})};f["boolean"]=function(a,c){return e(a,c,function(a,c,b){d.pcCheck(b,1,1);return d.toBoolean(b[0])})}}});