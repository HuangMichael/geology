// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../core/tsSupport/declareExtendsHelper ../core/tsSupport/decorateHelper ../core/accessorSupport/decorators ./support/widget ./Search/SearchViewModel ./Search/SearchResultRenderer ./Widget ../core/lang ../core/watchUtils ../PopupTemplate dojo/regexp dojo/keys dojo/query dojo/i18n!./Search/nls/Search".split(" "), function (A, B, t, d, e, c, n, u, v, w, x, y, z, f, p, h) {
    return function (r) {
        function b(a) {
            a = r.call(this) || this;
            a._inputNode = null;
            a._sourceMenuButtonNode = null;
            a._sourceListNode = null;
            a._suggestionListNode = null;
            a._searchResultRenderer = new u({container: document.createElement("div")});
            a._suggestPromise = null;
            a._popupTemplate = new y({title: h.searchResult, content: a._renderSearchResultsContent.bind(a)});
            a._relatedTarget = null;
            a.activeMenu = "none";
            a.activeSource = null;
            a.activeSourceIndex = null;
            a.allPlaceholder = null;
            a.autoNavigate = null;
            a.autoSelect = null;
            a.defaultSource = null;
            a.locationToAddressDistance = null;
            a.maxResults = null;
            a.maxSuggestions = null;
            a.minSuggestCharacters = null;
            a.popupEnabled = null;
            a.popupOpenOnSelect = null;
            a.popupTemplate = null;
            a.resultGraphic = null;
            a.resultGraphicEnabled = null;
            a.results = null;
            a.searchAllEnabled = null;
            a.searching = !1;
            a.searchTerm = null;
            a.selectedResult = null;
            a.sources = null;
            a.suggestions = null;
            a.suggestionsEnabled = null;
            a.view = null;
            a.viewModel = new n;
            return a
        }

        t(b, r);
        b.prototype.postInitialize = function () {
            var a = this;
            this.viewModel.popupTemplate = this._popupTemplate;
            this.own(x.whenNot(this, "searchTerm", function () {
                a.activeMenu = "none"
            }))
        };
        b.prototype.destroy = function () {
            this._cancelSuggest();
            this._searchResultRenderer && (this._searchResultRenderer.viewModel = null, this._searchResultRenderer.destroy(), this._searchResultRenderer = null)
        };
        b.prototype.clear = function () {
        };
        b.prototype.focus = function () {
            this._inputNode && (this._inputNode.focus(), this.emit("search-focus"))
        };
        b.prototype.blur = function (a) {
            this._inputNode && (this._inputNode.blur(), this._inputBlur(a), this.emit("search-blur"))
        };
        b.prototype.search = function (a) {
            var g = this;
            this.activeMenu = "none";
            this._set("searching", !0);
            this._cancelSuggest();
            return this.viewModel.search(a).then(function (a) {
                g.activeMenu = a.numResults ? "none" : "warning";
                g._set("searching", !1);
                return a
            }).otherwise(function () {
                g._set("searching", !1);
                g.activeMenu = "none";
                return null
            })
        };
        b.prototype.suggest = function (a) {
            var g = this;
            this._cancelSuggest();
            return this._suggestPromise = a = this.viewModel.suggest(a).then(function (a) {
                a.numResults && (g.activeMenu = "suggestion");
                return a
            }).otherwise(function () {
                g.activeMenu = "none";
                return null
            })
        };
        b.prototype.render = function () {
            var a = this, g = this.viewModel,
                b = g.searchTerm, d = this._getSourceName(g.activeSourceIndex), e = b.trim(), f = this.id,
                k = c.tsx("input", {
                    bind: this,
                    placeholder: g.placeholder,
                    "aria-label": h.searchButtonTitle,
                    maxlength: g.maxInputLength,
                    autocomplete: "on",
                    type: "text",
                    tabindex: "0",
                    class: "esri-search__input",
                    value: b,
                    "aria-haspopup": "true",
                    id: f + "_input",
                    role: "textbox",
                    onkeydown: this._handleInputKeydown,
                    onkeyup: this._handleInputKeyup,
                    oninput: this._handleInputPaste,
                    onpaste: this._handleInputPaste,
                    afterCreate: this._storeInputNode,
                    afterUpdate: this._storeInputNode,
                    onfocusout: this._storeRelatedTarget,
                    onfocus: this.focus,
                    onblur: this.blur
                }), k = c.tsx("form", {
                    key: "esri-search__form",
                    bind: this,
                    class: "esri-search__form",
                    onsubmit: this._formSubmit,
                    role: "search"
                }, k), q = b ? c.tsx("div", {
                    key: "esri-search__clear-button",
                    bind: this,
                    role: "button",
                    class: c.join("esri-search__clear-button", "esri-widget-button"),
                    tabindex: "0",
                    title: h.clearButtonTitle,
                    onfocus: this._clearButtonFocus,
                    onclick: this._handleClearButtonClick,
                    onkeydown: this._handleClearButtonClick
                }, c.tsx("span", {
                    "aria-hidden": "true",
                    class: "esri-icon-close"
                })) : null, l = g.suggestions ? g.suggestions.map(function (b) {
                    var g = b.sourceIndex, d = b.results.length ? a._getSuggestionHeaderNode(g) : null;
                    b = b.results.map(function (b, c) {
                        return a._getSuggestionNode(b, c, g)
                    });
                    return c.tsx("div", {key: "esri-search__suggestion-container-" + g}, d, c.tsx("ul", {key: "esri-search__suggestion-list-" + g}, b))
                }) : null, l = c.tsx("div", {
                    key: "esri-search__suggestions-menu",
                    class: c.join("esri-menu", "esri-search__suggestions-menu"),
                    role: "menu",
                    bind: this,
                    afterCreate: this._storeSuggestionsListNode,
                    afterUpdate: this._storeSuggestionsListNode
                }, l),
                k = c.tsx("div", {key: "esri-search__input-container", class: "esri-search__input-container"}, k, l, q),
                q = c.tsx("div", {
                    key: "esri-search__submit-button",
                    bind: this,
                    role: "button",
                    title: h.searchButtonTitle,
                    class: c.join("esri-search__submit-button", "esri-widget-button"),
                    tabindex: "0",
                    onclick: this._handleSearchButtonClick,
                    onkeydown: this._handleSearchButtonClick
                }, c.tsx("span", {
                    "aria-hidden": "true",
                    role: "presentation",
                    class: "esri-icon-search"
                }), c.tsx("span", {class: "esri-icon-font-fallback-text"},
                    h.searchButtonTitle)), b = w.substitute({value: '"' + b + '"'}, h.noResultsFound),
                b = e ? c.tsx("div", {key: "esri-search__no_results"}, c.tsx("div", {class: "esri-search__warning-header"}, h.noResults), c.tsx("div", {class: "esri-search__warning-text"}, b)) : null,
                e = e ? null : c.tsx("div", {key: "esri-search__empty-search"}, c.tsx("span", {
                    "aria-hidden": "true",
                    class: "esri-icon-notice-triangle"
                }), c.tsx("span", {class: "esri-search__no-value-text"}, h.emptyValue)), e = c.tsx("div", {
                    key: "esri-search__error-menu", class: c.join("esri-menu",
                    "esri-search__warning-menu")
                }, c.tsx("div", {class: "esri-search__warning-body"}, b, e)), l = g.sources, b = 1 < l.length,
                l = l && l.toArray(), g = g.searchAllEnabled ? this._getSourceNode(n.ALL_INDEX) : null,
                d = b ? c.tsx("div", {
                    key: "esri-search__source-menu-button",
                    bind: this,
                    role: "button",
                    title: h.searchIn,
                    id: f + "_menu_button",
                    class: c.join("esri-search__sources-button", "esri-widget-button"),
                    tabindex: "0",
                    onkeydown: this._handleSourcesMenuToggleClick,
                    onclick: this._handleSourcesMenuToggleClick,
                    onkeyup: this._handleSourceMenuButtonKeyup,
                    onblur: this._sourcesButtonBlur,
                    afterCreate: this._storeSourceMenuButtonNode,
                    afterUpdate: this._storeSourceMenuButtonNode
                }, c.tsx("span", {
                    "aria-hidden": "true",
                    role: "presentation",
                    class: "esri-icon-down-arrow esri-search__sources-button--down"
                }), c.tsx("span", {
                    "aria-hidden": "true",
                    role: "presentation",
                    class: "esri-icon-up-arrow esri-search__sources-button--up"
                }), c.tsx("span", {class: "esri-search__source-name"}, d)) : null, f = b ? c.tsx("ul", {
                    bind: this,
                    afterCreate: this._storeSourcesListNode,
                    afterUpdate: this._storeSourcesListNode
                },
                g, l.map(function (b, g) {
                    return a._getSourceNode(g)
                })) : null, f = c.tsx("div", {
                    key: "esri-search__source-menu",
                    class: c.join("esri-menu", "esri-search__sources-menu"),
                    role: "menu"
                }, f), g = this.activeMenu,
                g = (m = {}, m["esri-search--multiple-sources"] = b, m["esri-search--loading"] = this.searching, m["esri-search--warning"] = "warning" === g, m["esri-search--sources"] = "source" === g, m["esri-search--show-suggestions"] = "suggestion" === g, m);
            return c.tsx("div", {class: "esri-search esri-widget"}, c.tsx("div", {
                role: "presentation", classes: g,
                class: "esri-search__container"
            }, d, f, k, q, e));
            var m
        };
        b.prototype._handleSourcesMenuToggleClick = function () {
            this.activeMenu = "source" === this.activeMenu ? "none" : "source"
        };
        b.prototype._handleClearButtonClick = function () {
            this.viewModel.clear();
            this.focus()
        };
        b.prototype._handleSearchButtonClick = function () {
            this.search()
        };
        b.prototype._handleSuggestionClick = function (a) {
            if (a = a.currentTarget["data-suggestion"]) this.search(a), this.focus()
        };
        b.prototype._handleSourceClick = function (a) {
            this.viewModel.activeSourceIndex =
                a.currentTarget["data-source-index"];
            this.activeMenu = "none";
            this.focus()
        };
        b.prototype._sourcesButtonBlur = function (a) {
            this._removeActiveMenu(a && a.relatedTarget, this._sourceListNode)
        };
        b.prototype._inputBlur = function (a) {
            a = a && a.relatedTarget;
            this._removeActiveMenu(a ? a : this._relatedTarget, this._suggestionListNode)
        };
        b.prototype._storeRelatedTarget = function (a) {
            this._relatedTarget = a.relatedTarget
        };
        b.prototype._clearButtonFocus = function () {
            this.activeMenu = "none"
        };
        b.prototype._removeActiveMenu = function (a, b) {
            a &&
            b && b.contains(a) || (this.activeMenu = "none")
        };
        b.prototype._cancelSuggest = function () {
            var a = this._suggestPromise;
            a && (a.cancel(), this._suggestPromise = null)
        };
        b.prototype._storeSuggestionsListNode = function (a) {
            this._suggestionListNode = a
        };
        b.prototype._storeSourcesListNode = function (a) {
            this._sourceListNode = a
        };
        b.prototype._storeInputNode = function (a) {
            this._inputNode = a
        };
        b.prototype._storeSourceMenuButtonNode = function (a) {
            this._sourceMenuButtonNode = a
        };
        b.prototype._handleInputKeydown = function (a) {
            var b = a.keyCode;
            (b ===
            f.TAB || b === f.ESCAPE || a.shiftKey && b === f.TAB) && this._cancelSuggest()
        };
        b.prototype._handleInputKeyup = function (a) {
            var b = a.keyCode,
                c = a.ctrlKey || a.metaKey || b === f.copyKey || b === f.LEFT_ARROW || b === f.RIGHT_ARROW || b === f.ENTER || b === f.SHIFT,
                d = this._suggestionListNode ? p("li", this._suggestionListNode) : null, e = b === f.UP_ARROW,
                h = b === f.DOWN_ARROW;
            c || (b === f.TAB || b === f.ESCAPE || a.shiftKey && b === f.TAB ? (this._cancelSuggest(), b === f.ESCAPE && (this.activeMenu = "none")) : (e || h) && d ? (this.activeMenu = "suggestion", a.stopPropagation(),
                a.preventDefault(), this._cancelSuggest(), (a = d[e ? d.length - 1 : 0]) && a.focus()) : this.viewModel.searchTerm && this.suggest())
        };
        b.prototype._handleInputPaste = function (a) {
            var b = this.viewModel;
            a = a.target;
            b.searchTerm !== a.value && (b.searchTerm = a.value);
            b.searchTerm && this.suggest()
        };
        b.prototype._handleSourceMenuButtonKeyup = function (a) {
            var b = a.keyCode, c = b === f.UP_ARROW, b = b === f.DOWN_ARROW;
            if (c || b) a.stopPropagation(), a.preventDefault(), (a = this._sourceListNode ? p("li", this._sourceListNode) : null) && (c = a[c ? a.length - 1 : 0]) &&
            c.focus()
        };
        b.prototype._handleSourceKeyup = function (a) {
            var b = a.target, c = this._sourceListNode ? p("li", this._sourceListNode) : null, d = a.keyCode;
            d === f.ESCAPE ? (this.activeMenu = "none", this.focus()) : c && (b = c.indexOf(b), d === f.UP_ARROW ? (a.stopPropagation(), a.preventDefault(), a = b - 1, (c = 0 > a ? this._sourceMenuButtonNode : c[a]) && c.focus()) : d === f.DOWN_ARROW && (a.stopPropagation(), a.preventDefault(), a = b + 1, (c = a >= c.length ? this._sourceMenuButtonNode : c[a]) && c.focus()))
        };
        b.prototype._handleSuggestionKeyup = function (a) {
            var b = a.target,
                c = this._suggestionListNode ? p("li", this._suggestionListNode) : null, b = c.indexOf(b),
                d = a.keyCode;
            this._cancelSuggest();
            d === f.BACKSPACE || d === f.DELETE ? this.focus() : d === f.ESCAPE ? (this.activeMenu = "none", this.focus()) : c && (d === f.UP_ARROW ? (a.stopPropagation(), a.preventDefault(), a = b - 1, 0 > a ? this.focus() : (c = c[a]) && c.focus()) : d === f.DOWN_ARROW && (a.stopPropagation(), a.preventDefault(), a = b + 1, a >= c.length ? this.focus() : (c = c[a]) && c.focus()))
        };
        b.prototype._formSubmit = function (a) {
            a.preventDefault();
            this.search()
        };
        b.prototype._getSourceName =
            function (a) {
                var b = this.viewModel.sources.getItemAt(a);
                return a === n.ALL_INDEX ? h.all : b ? b.name : h.untitledSource
            };
        b.prototype._getSuggestionHeaderNode = function (a) {
            var b = this.viewModel;
            return 1 < b.sources.length && b.activeSourceIndex === n.ALL_INDEX ? (a = this._getSourceName(a), c.tsx("div", {class: "esri-header"}, a)) : null
        };
        b.prototype._splitResult = function (a, b) {
            b = z.escapeString(b);
            return a.replace(new RegExp("(^|)(" + b + ")(|$)", "ig"), "$1|$2|$3").split("|")
        };
        b.prototype._getSuggestionNode = function (a, b, d) {
            var e = this.viewModel.searchTerm;
            if (e) {
                var g = this._splitResult(a.text || h.untitledResult, e), f = e.toLowerCase(), k = [];
                g.forEach(function (a, e) {
                    a && a.length && (e = d + "-" + b + "-" + e, a.toLowerCase() === f ? k.push(c.tsx("strong", {key: "esri-search__partial-match-" + e}, a)) : k.push(a))
                });
                return c.tsx("li", {
                    bind: this,
                    onclick: this._handleSuggestionClick,
                    onkeydown: this._handleSuggestionClick,
                    onkeyup: this._handleSuggestionKeyup,
                    key: "esri-search__suggestion$-{sourceIndex}_" + b,
                    "data-suggestion": a,
                    role: "menuitem",
                    tabindex: "0"
                }, k)
            }
        };
        b.prototype._getSourceNode =
            function (a) {
                var b = (d = {}, d["esri-search__source--active"] = a === this.viewModel.activeSourceIndex, d);
                return c.tsx("li", {
                    bind: this,
                    key: "esri-search__source-" + a,
                    onclick: this._handleSourceClick,
                    onkeydown: this._handleSourceClick,
                    onkeyup: this._handleSourceKeyup,
                    "data-source-index": a,
                    role: "menuitem",
                    class: "esri-search__source",
                    classes: b,
                    tabindex: "0"
                }, this._getSourceName(a));
                var d
            };
        b.prototype._renderSearchResultsContent = function () {
            this._searchResultRenderer.showMoreResultsOpen = !1;
            this._searchResultRenderer.viewModel =
                this.viewModel;
            return this._searchResultRenderer
        };
        d([e.property(), c.renderable()], b.prototype, "activeMenu", void 0);
        d([e.aliasOf("viewModel.activeSource"), c.renderable()], b.prototype, "activeSource", void 0);
        d([e.aliasOf("viewModel.activeSourceIndex"), c.renderable()], b.prototype, "activeSourceIndex", void 0);
        d([e.aliasOf("viewModel.allPlaceholder"), c.renderable()], b.prototype, "allPlaceholder", void 0);
        d([e.aliasOf("viewModel.autoNavigate")], b.prototype, "autoNavigate", void 0);
        d([e.aliasOf("viewModel.autoSelect")],
            b.prototype, "autoSelect", void 0);
        d([e.aliasOf("viewModel.defaultSource")], b.prototype, "defaultSource", void 0);
        d([e.aliasOf("viewModel.locationToAddressDistance")], b.prototype, "locationToAddressDistance", void 0);
        d([e.aliasOf("viewModel.maxResults")], b.prototype, "maxResults", void 0);
        d([e.aliasOf("viewModel.maxSuggestions")], b.prototype, "maxSuggestions", void 0);
        d([e.aliasOf("viewModel.minSuggestCharacters")], b.prototype, "minSuggestCharacters", void 0);
        d([e.aliasOf("viewModel.popupEnabled")], b.prototype,
            "popupEnabled", void 0);
        d([e.aliasOf("viewModel.popupOpenOnSelect")], b.prototype, "popupOpenOnSelect", void 0);
        d([e.aliasOf("viewModel.popupTemplate")], b.prototype, "popupTemplate", void 0);
        d([e.aliasOf("viewModel.resultGraphic")], b.prototype, "resultGraphic", void 0);
        d([e.aliasOf("viewModel.resultGraphicEnabled")], b.prototype, "resultGraphicEnabled", void 0);
        d([e.aliasOf("viewModel.results"), c.renderable()], b.prototype, "results", void 0);
        d([e.aliasOf("viewModel.searchAllEnabled"), c.renderable()], b.prototype,
            "searchAllEnabled", void 0);
        d([e.property({readOnly: !0}), c.renderable()], b.prototype, "searching", void 0);
        d([e.aliasOf("viewModel.searchTerm"), c.renderable()], b.prototype, "searchTerm", void 0);
        d([e.aliasOf("viewModel.selectedResult")], b.prototype, "selectedResult", void 0);
        d([e.aliasOf("viewModel.sources"), c.renderable()], b.prototype, "sources", void 0);
        d([e.aliasOf("viewModel.suggestions"), c.renderable()], b.prototype, "suggestions", void 0);
        d([e.aliasOf("viewModel.suggestionsEnabled")], b.prototype, "suggestionsEnabled",
            void 0);
        d([e.aliasOf("viewModel.view"), c.renderable()], b.prototype, "view", void 0);
        d([c.vmEvent("search-complete search-clear search-start select-result suggest-start suggest-complete".split(" ")), e.property({type: n}), c.renderable("viewModel")], b.prototype, "viewModel", void 0);
        d([e.aliasOf("viewModel.clear")], b.prototype, "clear", null);
        d([c.accessibleHandler()], b.prototype, "_handleSourcesMenuToggleClick", null);
        d([c.accessibleHandler()], b.prototype, "_handleClearButtonClick", null);
        d([c.accessibleHandler()],
            b.prototype, "_handleSearchButtonClick", null);
        d([c.accessibleHandler()], b.prototype, "_handleSuggestionClick", null);
        d([c.accessibleHandler()], b.prototype, "_handleSourceClick", null);
        return b = d([e.subclass("esri.widgets.Search")], b)
    }(e.declared(v))
});