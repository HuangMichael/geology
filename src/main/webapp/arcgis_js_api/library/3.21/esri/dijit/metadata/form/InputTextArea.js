// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/form/templates/InputTextArea.html":'\x3cdiv class\x3d"gxeInput gxeInputTextArea"\x3e\r\n  \x3ctextarea class\x3d"gxeEditOnly" cols\x3d"${cols}" rows\x3d"${rows}"\r\n    data-dojo-attach-point\x3d"focusNode"\r\n    data-dojo-attach-event\x3d"onchange: _onChange, onkeyup: _onKeyup"\x3e\x3c/textarea\x3e\r\n  \x3cdiv class\x3d"gxeViewOnlyText gxeViewOnly" data-dojo-attach-point\x3d"viewOnlyNode"\x3e\x3c/div\x3e\r\n  \x3cdiv class\x3d"gxeContainer" data-dojo-attach-point\x3d"containerNode"\x3e\x3c/div\x3e\r\n  \x3cdiv class\x3d"gxeHint gxeEditOnly" data-dojo-attach-point\x3d"hintNode"\x3e\x3c/div\x3e\r\n\x3c/div\x3e\r\n\r\n'}});
define("esri/dijit/metadata/form/InputTextArea","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/has ../base/InputBase dojo/text!./templates/InputTextArea.html ../../../kernel".split(" "),function(c,e,f,g,h,k,l){c=c([h],{templateString:k,large:!1,cols:50,rows:4,postCreate:function(){this.inherited(arguments)},postMixInProperties:function(){this.inherited(arguments);this.large&&(this.cols=60,this.rows=8)},connectXNode:function(a,b){this.inherited(arguments);var d=a.value;b&&!a.fixed||"undefined"===
typeof d||null===d||this.setInputValue(d)},getDisplayValue:function(){if(!this.focusNode)return null;var a=this.focusNode.value,b=[];if(null===a)return null;a=a.replace(/(\r\n|\r|\n|\n\r)/g,"\x3cbr/\x3e");a=a.split("\x3cbr/\x3e");f.forEach(a,function(a){a=e.trim(a);0<a.length&&b.push(a)});return 1===b.length?b[0]:1<b.length?b:null},getInputValue:function(){return this.focusNode?this.focusNode.value:null},_onChange:function(a){this.emitInteractionOccurred()},_onKeyup:function(a){this.emitInteractionOccurred()},
setInputValue:function(a){"undefined"===typeof a&&(a=null);this.focusNode.value=a;this.emitInteractionOccurred();this.applyViewOnly()}});g("extend-esri")&&e.setObject("dijit.metadata.form.InputTextArea",c,l);return c});