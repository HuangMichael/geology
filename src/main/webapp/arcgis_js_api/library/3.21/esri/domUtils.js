// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/domUtils",["./kernel","dojo/_base/connect","dojo/_base/lang","dojo/dom-style","dojo/has"],function(q,n,r,t,p){var b={show:function(a){if(a=b.getNode(a))a.style.display="block"},getNode:function(a){return a&&a.domNode||a},hide:function(a){if(a=b.getNode(a))a.style.display="none"},toggle:function(a){if(a=b.getNode(a))a.style.display="none"===a.style.display?"block":"none"},documentBox:8>=p("ie")?{w:document.documentElement.clientWidth,h:document.documentElement.clientHeight}:{w:window.innerWidth,
h:window.innerHeight},setScrollable:function(a){if(a=this.getNode(a)){var b=0,g=0,h=0,k=0,l=0,m=0;return[n.connect(a,"ontouchstart",function(d){b=d.touches[0].screenX;g=d.touches[0].screenY;h=a.scrollWidth;k=a.scrollHeight;l=a.clientWidth;m=a.clientHeight}),n.connect(a,"ontouchmove",function(d){d.preventDefault();var f=a.firstChild;f instanceof Text&&(f=a.childNodes[1]);var c=f._currentX||0,e=f._currentY||0,c=c+(d.touches[0].screenX-b);0<c?c=0:0>c&&Math.abs(c)+l>h&&(c=-1*(h-l));f._currentX=c;e+=d.touches[0].screenY-
g;0<e?e=0:0>e&&Math.abs(e)+m>k&&(e=-1*(k-m));f._currentY=e;t.set(f,{"-webkit-transition-property":"-webkit-transform","-webkit-transform":"translate("+c+"px, "+e+"px)"});b=d.touches[0].screenX;g=d.touches[0].screenY})]}}};p("extend-esri")&&r.mixin(q,b);return b});