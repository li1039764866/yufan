/*
(function($) { $.extend($.browser, { client: function() { return { width: document.documentElement.clientWidth, height: document.documentElement.clientHeight, bodyWidth: document.body.clientWidth, bodyHeight: document.body.clientHeight }; }, scroll: function() { return { width: document.documentElement.scrollWidth, height: document.documentElement.scrollHeight, bodyWidth: document.body.scrollWidth, bodyHeight: document.body.scrollHeight, left: document.documentElement.scrollLeft, top: document.documentElement.scrollTop }; }, screen: function() { return { width: window.screen.width, height: window.screen.height }; }, isIE6: $.browser.msie && $.browser.version == 6, isMinW: function(val) { return Math.min($.browser.client().bodyWidth, $.browser.client().width) <= val; }, isMinH: function(val) { return $.browser.client().height <= val; } }) })(jQuery); (function($) { $.widthForIE6 = function(option) { var s = $.extend({ max: null, min: null, padding: 0 }, option || {}); var init = function() { var w = $(document.body); if ($.browser.client().width >= s.max + s.padding) { w.width(s.max + "px"); } else if ($.browser.client().width <= s.min + s.padding) { w.width(s.min + "px"); } else { w.width("auto"); } }; init(); $(window).resize(init); } })(jQuery); (function($) { $.fn.hoverForIE6 = function(option) { var s = $.extend({ current: "hover" }, option || {}); $.each(this, function() { $(this).bind("mouseover", function() { $(this).addClass(s.current); }).bind("mouseleave", function() { $(this).removeClass(s.current); }) }) } })(jQuery); (function($) { $.extend({ _jsonp: { scripts: {}, counter: 1, head: document.getElementsByTagName("head")[0], name: function(callback) { var name = '_jsonp_' + (new Date).getTime() + '_' + this.counter; this.counter++; var cb = function(json) { eval('delete ' + name); callback(json); $._jsonp.head.removeChild($._jsonp.scripts[name]); delete $._jsonp.scripts[name]; }; eval(name + ' = cb'); return name; }, load: function(url, name) { var script = document.createElement('script'); script.type = 'text/javascript'; script.charset = this.charset; script.src = url; this.head.appendChild(script); this.scripts[name] = script; } }, getJSONP: function(url, callback) { var name = $._jsonp.name(callback); var url = url.replace(/{callback};/, name); $._jsonp.load(url, name); return this; } }); })(jQuery); (function($) { $.fn.jdTab = function(option, callback) { if (typeof option == "function") { callback = option; option = {}; }; var s = $.extend({ type: "static", auto: false, source: "data", event: "mouseover", currClass: "curr", tab: ".tab", content: ".tabcon", itemTag: "li", stay: 5000, delay: 100, mainTimer: null, subTimer: null, index: 0 }, option || {}); var tabItem = $(this).find(s.tab).eq(0).find(s.itemTag), contentItem = $(this).find(s.content); if (tabItem.length != contentItem.length) return false; var reg = s.source.toLowerCase().match(/http:\/\/|\d|\.aspx|\.ascx|\.asp|\.php|\.html\.htm|.shtml|.js|\W/g); var init = function(n, tag) { s.subTimer = setTimeout(function() { hide(); if (tag) { s.index++; if (s.index == tabItem.length) s.index = 0; } else { s.index = n; }; s.type = (tabItem.eq(s.index).attr(s.source) != null) ? "dynamic" : "static"; show(); }, s.delay); }; var autoSwitch = function() { s.mainTimer = setInterval(function() { init(s.index, true); }, s.stay); }; var show = function() { tabItem.eq(s.index).addClass(s.currClass); switch (s.type) { default: case "static": var source = ""; break; case "dynamic": var source = (reg == null) ? tabItem.eq(s.index).attr(s.source) : s.source; tabItem.eq(s.index).removeAttr(s.source); break; }; if (callback) { callback(source, contentItem.eq(s.index), s.index); }; contentItem.eq(s.index).show(); }; var hide = function() { tabItem.eq(s.index).removeClass(s.currClass); contentItem.eq(s.index).hide(); }; tabItem.each(function(n) { $(this).bind(s.event, function() { clearTimeout(s.subTimer); clearInterval(s.mainTimer); init(n, false); return false; }).bind("mouseleave", function() { if (s.auto) { autoSwitch(); } else { return; } }) }); if (s.type == "dynamic") { init(s.index, false); }; if (s.auto) { autoSwitch(); } } })(jQuery); (function($) { $.fn.jdMarquee = function(option, callback) { if (typeof option == "function") { callback = option; option = {}; }; var s = $.extend({ deriction: "up", speed: 10, auto: false, width: null, height: null, step: 1, control: false, _front: null, _back: null, _stop: null, _continue: null, wrapstyle: "", stay: 5000, delay: 20, dom: "div>ul>li".split(">"), mainTimer: null, subTimer: null, tag: false, convert: false, btn: null, disabled: "disabled", pos: { ojbect: null, clone: null} }, option || {}); var object = this.find(s.dom[1]); var subObject = this.find(s.dom[2]); var clone; if (s.deriction == "up" || s.deriction == "down") { var height = object.eq(0).outerHeight(); var step = s.step * subObject.eq(0).outerHeight(); }; if (s.deriction == "left" || s.deriction == "right") { var width = subObject.length * subObject.eq(0).outerWidth(); object.css({ width: width + "px" }); var step = s.step * subObject.eq(0).outerWidth(); }; var init = function() { var wrap = "<div style='position:relative;overflow:hidden;z-index:1;width:" + s.width + "px;height:" + s.height + "px;" + s.wrapstyle + "'></div>"; object.css({ position: "absolute", left: 0, top: 0 }).wrap(wrap); s.pos.object = 0; clone = object.clone(); object.after(clone); switch (s.deriction) { default: case "up": object.css({ marginLeft: 0, marginTop: 0 }); clone.css({ marginLeft: 0, marginTop: height + "px" }); s.pos.clone = height; break; case "down": object.css({ marginLeft: 0, marginTop: 0 }); clone.css({ marginLeft: 0, marginTop: -height + "px" }); s.pos.clone = -height; break; case "left": object.css({ marginTop: 0, marginLeft: 0 }); clone.css({ marginTop: 0, marginLeft: width + "px" }); s.pos.clone = width; break; case "right": object.css({ marginTop: 0, marginLeft: 0 }); clone.css({ marginTop: 0, marginLeft: -width + "px" }); s.pos.clone = -width; break; }; if (s.auto) { initMainTimer(); object.hover(function() { clear(s.mainTimer); }, function() { initMainTimer(); }); clone.hover(function() { clear(s.mainTimer); }, function() { initMainTimer(); }); }; if (callback) { callback(); }; if (s.control) { initControls(); } }; var initMainTimer = function(delay) { clear(s.mainTimer); s.stay = delay ? delay : s.stay; s.mainTimer = setInterval(function() { initSubTimer() }, s.stay); }; var initSubTimer = function() { clear(s.subTimer); s.subTimer = setInterval(function() { roll() }, s.delay); }; var clear = function(timer) { if (timer != null) { clearInterval(timer); } }; var disControl = function(A) { if (A) { $(s._front).unbind("click"); $(s._back).unbind("click"); $(s._stop).unbind("click"); $(s._continue).unbind("click"); } else { initControls(); } }; var initControls = function() { if (s._front != null) { $(s._front).click(function() { $(s._front).addClass(s.disabled); disControl(true); clear(s.mainTimer); s.convert = true; s.btn = "front"; if (!s.auto) { s.tag = true; }; convert(); }); }; if (s._back != null) { $(s._back).click(function() { $(s._back).addClass(s.disabled); disControl(true); clear(s.mainTimer); s.convert = true; s.btn = "back"; if (!s.auto) { s.tag = true; }; convert(); }); }; if (s._stop != null) { $(s._stop).click(function() { clear(s.mainTimer); }); }; if (s._continue != null) { $(s._continue).click(function() { initMainTimer(); }); } }; var convert = function() { if (s.tag && s.convert) { s.convert = false; if (s.btn == "front") { if (s.deriction == "down") { s.deriction = "up"; }; if (s.deriction == "right") { s.deriction = "left"; } }; if (s.btn == "back") { if (s.deriction == "up") { s.deriction = "down"; }; if (s.deriction == "left") { s.deriction = "right"; } }; if (s.auto) { initMainTimer(); } else { initMainTimer(4 * s.delay); } } }; var setPos = function(y1, y2, x) { if (x) { clear(s.subTimer); s.pos.object = y1; s.pos.clone = y2; s.tag = true; } else { s.tag = false; }; if (s.tag) { if (s.convert) { convert(); } else { if (!s.auto) { clear(s.mainTimer); } } }; if (s.deriction == "up" || s.deriction == "down") { object.css({ marginTop: y1 + "px" }); clone.css({ marginTop: y2 + "px" }); }; if (s.deriction == "left" || s.deriction == "right") { object.css({ marginLeft: y1 + "px" }); clone.css({ marginLeft: y2 + "px" }); } }; var roll = function() { var y_object = (s.deriction == "up" || s.deriction == "down") ? parseInt(object.get(0).style.marginTop) : parseInt(object.get(0).style.marginLeft); var y_clone = (s.deriction == "up" || s.deriction == "down") ? parseInt(clone.get(0).style.marginTop) : parseInt(clone.get(0).style.marginLeft); var y_add = Math.max(Math.abs(y_object - s.pos.object), Math.abs(y_clone - s.pos.clone)); var y_ceil = Math.ceil((step - y_add) / s.speed); switch (s.deriction) { case "up": if (y_add == step) { setPos(y_object, y_clone, true); $(s._front).removeClass(s.disabled); disControl(false); } else { if (y_object <= -height) { y_object = y_clone + height; s.pos.object = y_object; }; if (y_clone <= -height) { y_clone = y_object + height; s.pos.clone = y_clone; }; setPos((y_object - y_ceil), (y_clone - y_ceil)); }; break; case "down": if (y_add == step) { setPos(y_object, y_clone, true); $(s._back).removeClass(s.disabled); disControl(false); } else { if (y_object >= height) { y_object = y_clone - height; s.pos.object = y_object; }; if (y_clone >= height) { y_clone = y_object - height; s.pos.clone = y_clone; }; setPos((y_object + y_ceil), (y_clone + y_ceil)); }; break; case "left": if (y_add == step) { setPos(y_object, y_clone, true); $(s._front).removeClass(s.disabled); disControl(false); } else { if (y_object <= -width) { y_object = y_clone + width; s.pos.object = y_object; }; if (y_clone <= -width) { y_clone = y_object + width; s.pos.clone = y_clone; }; setPos((y_object - y_ceil), (y_clone - y_ceil)); }; break; case "right": if (y_add == step) { setPos(y_object, y_clone, true); $(s._back).removeClass(s.disabled); disControl(false); } else { if (y_object >= width) { y_object = y_clone - width; s.pos.object = y_object; }; if (y_clone >= width) { y_clone = y_object - width; s.pos.clone = y_clone; }; setPos((y_object + y_ceil), (y_clone + y_ceil)); }; break; } }; if (s.deriction == "up" || s.deriction == "down") { if (height >= s.height && height >= s.step) { init(); } }; if (s.deriction == "left" || s.deriction == "right") { if (width >= s.width && width >= s.step) { init(); } } } })(jQuery); function ResumeError() { return true; } window.onerror = ResumeError; if ($.browser.isIE6) { try { document.execCommand("BackgroundImageCache", false, true); } catch (err) { } }; var calluri = "http://fairyservice.360buy.com/WebService.asmx/MarkEx?callback=?"; callback1 = function(data) { ; }; mark = function(sku, type) { $.getJSON(calluri, { sku: sku, type: type }, callback1); }; function search(id) { var selKey = document.getElementById(id).value; window.location = 'http://search.360buy.com/Search?keyword=' + selKey; } function login() { location.href = "/user/login.html"; return false; } function regist() { location.href = "../emReg/emailReg" + location.search; return false; } function setWebBILinkCount(sType) { try { if (sType.length > 0) { var js = document.createElement('script'); js.type = 'text/javascript'; js.src = 'http://counter.360buy.com/aclk.aspx?key=' + sType; document.getElementsByTagName('head')[0].appendChild(js); } } catch (e) { } }; function addToFavorite() { var a = "http://www.360buy.com/"; var b = "京东商城-网购上京东，省钱又放心"; if (document.all) { window.external.AddFavorite(a, b) } else if (window.sidebar) { window.sidebar.addPanel(b, a, "") } else { alert("对不起，您的浏览器不支持此操作!\n请您使用菜单栏或Ctrl+D收藏本站。") } }*/
