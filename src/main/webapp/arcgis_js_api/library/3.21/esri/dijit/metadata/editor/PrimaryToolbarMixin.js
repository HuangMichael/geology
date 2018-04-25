// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/dijit/metadata/editor/PrimaryToolbarMixin","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/dom-class dojo/dom-style dojo/has dojo/i18n!../nls/i18nBase ../base/ValidationTracker ../base/xml/XmlInterrogator ../base/xml/xmlUtil ./util/DownloadXmlDialog ./util/LoadDocumentDialog ./util/MessageDialog ./util/SaveDocumentDialog ./util/TransformDialog ../base/transform/Iso2IsoTransformer ../../../kernel".split(" "),function(k,c,t,q,u,v,d,n,w,r,x,y,l,z,A,B,C){k=k(null,{constructor:function(a){c.mixin(this,
a)},_compareXmls:function(a,b){var e=function(a){if("undefined"===typeof a||null===a)return a;var b=a.indexOf("\x3cModTime\x3e"),c;return-1!==b&&(c=a.indexOf("\x3c/ModTime\x3e"),c>b)?(b=a.substring(0,b),a=a.substring(c+10),b+a):a},c=e(a),e=e(b);return c===e?!0:!1},_confirmAndDelete:function(){if(this.editor.gxeAdaptor.getAllowEditMetadata()&&this.editor.gxeAdaptor.getAllowDeleteMetadata()){var a=this.editor&&this.editor.dialogBroker;(new l({title:d.editor.del.dialogTitle,okLabel:d.editor.del.caption,
onOkClick:c.hitch(this,function(){var b=new l({title:d.editor.del.dialogTitle,showOkCancelBar:!1});b.show(d.editor.del.working).then(c.hitch(this,function(){this.editor.gxeAdaptor.deleteMetadata({}).then(c.hitch(this,function(){this.lastSavedXml=null;setTimeout(c.hitch(this,function(){b.hide();a&&this._close()}),1500)}),c.hitch(this,function(a){b.hide();this._handleError(d.editor.del.errorDeleting,a,!0)}))}))})})).show(d.editor.del.prompt)}},_executeSave:function(a,b,e,f){if(this.editor.gxeAdaptor.getAllowEditMetadata()){var g=
new l({title:d.editor.save.dialogTitle,showOkCancelBar:!1});g.show(d.editor.save.working).then(c.hitch(this,function(){this.editor.gxeAdaptor.saveXml(a,b,f).then(c.hitch(this,function(){this.lastSavedXml=b;setTimeout(c.hitch(this,function(){g.hide();e&&this._close()}),1500)}),c.hitch(this,function(a){g.hide();this._handleError(d.editor.save.errorSaving,a,!0)}))}))}},_download:function(a,b,c){null===b&&(b="metadata");var e;e=b+".xml";window.navigator&&window.navigator.msSaveOrOpenBlob?window.navigator.msSaveOrOpenBlob(new Blob([a],
{type:"text/xml"}),e):(e=d.editor.download.dialogTitle,c&&(e=d.editor.saveDraft.dialogTitle),c=new x({dialogTitle:e}),c.show(a,b))},_getTransformationTypes:function(a){var b=[];a=this.editor.getEditDocument();if(!a||!a.documentType.isIso)return b;t.forEach(this.editor.gxeContext.filterDocumentTypes(),function(c){c.key!==a.documentType.key&&c.isIso&&a.documentType.isIso&&b.push(c)});return b},_handleError:function(a,b,c){if(b)try{console.error(b)}catch(f){}c&&(new l({title:d.editor.errorDialog.dialogTitle,
showOk:!1,cancelLabel:d.general.close})).show(a,"gxeIconError",b)},_initializeView:function(){var a=this.editor.gxeAdaptor.getAllowEditMetadata(),b=this.editor.gxeContext.allowView,e=this.editor.gxeContext.allowViewXml,f=this.editor.gxeContext.startupTypeKey,g=null;"string"===typeof f&&0<f.length&&-1!==this.editor.gxeContext.allowedTypeKeys.indexOf(f)&&t.some(this.editor.gxeContext.documentTypes.list,function(a){if(a.key===f)return g=a,!0});var m=c.hitch(this,function(f,m){u.set(this.commonToolset,
"display","");m&&a?(this._setMode("edit"),g?this._fadeOut(d.editor.primaryToolbar.loadingDocument,c.hitch(this,function(){this.editor.loadEditor(g,null,!0,!1).then(c.hitch(this,function(){this._fadeIn()}),c.hitch(this,function(a){this._fadeIn();this._handleError(d.editor.primaryToolbar.errors.errorLoadingDocument,a,!0)}))})):this._fadeIn(c.hitch(this,function(){setTimeout(c.hitch(this,function(){this._showLoadDialog(d.editor.load.noMetadata)}),500)}))):(b||e||!a||(f="edit"),this._setMode(f),this._fadeIn())}),
p=this.editor.viewDocumentPane,l,k=null,n,h=this._parseXml(this.editor.gxeAdaptor.getOriginalXml());h.documentType?(l=h.documentType,k=h.xmlString,n=h.xmlDocument,a&&!b?(u.set(this.commonToolset,"display",""),this._setMode("edit"),this._loadEditor()):this._fadeOut(d.editor.primaryToolbar.initializing,c.hitch(this,function(){this.editor.loadView(l,n,!0).then(c.hitch(this,function(a){p.xmlString=k;this.editor.xmlPane.setXml(k,a.originalTitle);m("view")}),c.hitch(this,function(a){m("view");this._handleError(d.editor.primaryToolbar.errors.errorGeneratingView,
a,!0)}))}))):h.xmlDocument?(h=d.editor.xmlViewOnly,"string"===typeof this.editor.gxeContext.xmlViewOnlyText&&(h=this.editor.gxeContext.xmlViewOnlyText),!b&&a?this.editor.editDocumentPane.showMessage(h):p.showMessage(h),m("viewXml")):(p.showMessage(d.editor.noMetadata),m("view",!0))},_loadEditor:function(){if(this.editor.gxeAdaptor.getAllowEditMetadata()){var a,b=this._parseXml(this.editor.gxeAdaptor.getOriginalXml());b.documentType?this._fadeOut(d.editor.primaryToolbar.startingEditor,c.hitch(this,
function(){this.editor.loadEditor(b.documentType,b.xmlDocument,!1,!0).then(c.hitch(this,function(){this._fadeIn()}),c.hitch(this,function(a){this._fadeIn();this._handleError(d.editor.primaryToolbar.errors.errorLoadingDocument,a,!0)}))})):(a=d.editor.load.noMetadata,b.xmlDocument&&(a=d.editor.load.unrecognizedMetadata),this._showLoadDialog(a))}},_loadView:function(){var a=this.editor.getEditDocument();if(a){var b=this.editor.viewDocumentPane,e=new n({ignoreErrors:!0}),f=a.generateXml(e);this._compareXmls(f,
b.xmlString)?this._setMode("view"):(q.add(this.viewButton.domNode,"current"),q.remove(this.viewXmlButton.domNode,"current"),q.remove(this.editButton.domNode,"current"),this._fadeOut(d.editor.primaryToolbar.generatingView,c.hitch(this,function(){this._setMode("view");var e=a.documentType,m=r.parseFromString(f);this.editor.loadView(e,m,!1).then(c.hitch(this,function(a){b.xmlString=f;b.hideMessage();this.editor.xmlPane.setXml(f,a.originalTitle);this._fadeIn()}),c.hitch(this,function(a){b.hideMessage();
this._fadeIn();this._handleError(d.editor.primaryToolbar.errors.errorGeneratingView,a,!0)}))})))}else this._setMode("view")},_parseXml:function(a){var b={documentType:null,xmlString:a,xmlDocument:null};if(!a)return b;var c=null;try{c=r.parseFromString(a)}catch(g){return b}b.xmlDocument=c;a=this.editor.gxeContext.filterDocumentTypes();var d=new w;b.documentType=d.interrogate(c,a);return b},_save:function(a){if(this.editor.gxeAdaptor.getAllowEditMetadata()){var b=this.editor.getEditDocument();if(b){this.editor.validationPane.clearMessages();
var d=new n({isSaveAsDraft:!0,validationPane:this.editor.validationPane});this.editor.gxeContext.validateOnSave&&!a.isSaveAsDraft&&(d.isSaveAsDraft=!1);var f=b.generateXml(d),g=d.documentTitle;d.hadValidationErrors||null===g||0===g.length||(a.isSaveAsDraft?this._download(f,g,!0):a.showDialog?(a=new z({editor:this.editor,gxeDocument:b,onSave:c.hitch(this,function(a,c,d){a.hide();this._executeSave(b,f,c,d)})}),a.show()):this._executeSave(b,f,a.isSaveAndClose,a.bPushToItem))}}},_showLoadDialog:function(a){(new y({editor:this.editor,
prompt:a,onSelect:c.hitch(this,function(a,e,f,g){a.hide();this._fadeOut(d.editor.primaryToolbar.loadingDocument,c.hitch(this,function(){this.editor.loadEditor(e,f,g,!1).then(c.hitch(this,function(){this._fadeIn()}),c.hitch(this,function(a){this._fadeIn();this._handleError(d.editor.primaryToolbar.errors.errorLoadingDocument,a,!0)}))}))}),onSelectPullItem:c.hitch(this,function(a){a.hide();this.editor.gxeAdaptor.pullItem(this.editor.getEditDocument())})})).show()},_showTransformDialog:function(a,b){(new A({editor:this.editor,
documentTypes:b,prompt:null,onSelect:c.hitch(this,function(b,f){b.hide();this._fadeOut(d.editor.transform.working,c.hitch(this,function(){var b=new B({gxeDocument:a,toDocumentType:f}),e=new n({ignoreErrors:!0}),b=a.generateXml(e,b),e=null;if(b)try{e=r.parseFromString(b)}catch(p){e=null,console.error(p)}this.editor.loadEditor(f,e,!1,!1).then(c.hitch(this,function(b){b.documentType.afterTransform(b,a);this._fadeIn()}),c.hitch(this,function(a){this._fadeIn();this._handleError(d.editor.transform.errorTransforming,
a,!0)}))}))})})).show()},_validate:function(a){var b=this.editor.getEditDocument();b&&(this.editor.validationPane.clearMessages(),a=new n({isSaveAsDraft:a,validationPane:this.editor.validationPane}),b.generateXml(a),a.hadValidationErrors||(new l({title:d.editor.validate.dialogTitle,showCancel:!1})).show(d.editor.validate.docIsValid,null,null))}});v("extend-esri")&&c.setObject("dijit.metadata.editor.PrimaryToolbarMixin",k,C);return k});