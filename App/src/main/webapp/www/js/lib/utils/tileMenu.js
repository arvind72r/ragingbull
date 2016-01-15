function tileMenu(s) {
  var tm = this;
  var m = $(s);
  var pointer = null;
  var clickable = true;

  function slidePointer(n, speed) {
    if (isNaN(speed)) { speed = 400; }
    if (speed > 0) {
      pointer.animate({ left: n + 'px' }, speed);
    } else {
      pointer.css({ left: n + 'px' });
    }
  }

  function initPanel(p, index) {
    var pn = this;
    var t = null;
    if (p.data('tile-id')) { t = $('#' + p.data('tile-id')); }

    pn.isVisible = function () {
      return p.is(':visible');
    }

    pn.elementID = function () { return p.attr('id') ? p.attr('id') : null; }

    var _tile = null;
    pn.tile = function () {
      if (_tile) { return _tile; }
      else {
        if (t) { _tile = tm.tile(t.attr('id')); }
        return _tile;
      }
    }

    var _onShow = null;
    pn.onShow = function (f) {
      if (typeof f === 'function') { _onShow = f; }
      else if (typeof f === 'undefined' && typeof _onShow === 'function') { _onShow(); }
    }

    pn.show = function (f) {
      isInitSet = true;
      if (!pn.isVisible()) {
        var origClickable = clickable ? true : false;

        if (pn.tile() && !pn.tile().isSelected()) { pn.tile().select(false); }

        clickable = false;
        if (typeof f == 'function') {
          p.fadeIn('fast', function () { clickable = origClickable; f(); pn.onShow(); });
        }
        else {
          p.fadeIn('fast', function () { clickable = origClickable; pn.onShow(); });
        }
      }
      else {
        if (typeof f == 'function') { f(); }
      }
    }

    var _onHide = null;
    pn.onHide = function (f) {
      if (typeof f === 'function') { _onHide = f; }
      else if (typeof f === 'undefined' && typeof _onHide === 'function') { _onHide(); }
    }

    pn.hide = function (f) {
      isInitSet = true;
      if (pn.isVisible()) {
        if (typeof f == 'function') {
          p.fadeOut('fast', function () { f(); pn.onHide(); });
        }
        else {
          p.fadeOut('fast', function () { pn.onHide(); });
        }
      }
      else {
        if (typeof f == 'function') { f(); }
      }
    }
  }

  function initTile(t, index) {
    var tl = this;
    var p = null;
    var pID = null;

    if (t.data('panel-id')) { p = $('#' + t.data('panel-id')); }
    if (!p) { p = null; } else { pID = p.attr('id') ? p.attr('id') : null; }

    tl.element = t;

    tl.elementID = function () { return t.attr('id') ? t.attr('id') : null; }

    tl.isSelected = function () {
      return tl.element.hasClass('selected');
    }

    var _onSelect = null;
    tl.onSelect = function (f) {
      if (typeof f === 'function') { _onSelect = f; }
      else if (typeof f === 'undefined' && typeof _onSelect === 'function') { f(); }
    };

    var _pointerPosition = 70 + 185 * index;
    tl.pointerPosition = function () { return _pointerPosition }

    tl.select = function (pnl) {
      isInitSet = true;
      var showCalled = false;
      if (!tl.element.hasClass('selected')) {
        for (var i = 0; i < tm.tiles.length; i++) {
          if (tm.tiles[i].isSelected()) {
            tm.tiles[i].element.removeClass('selected');
            break;
          }
        }
        tl.element.addClass('selected');
        showCalled = true;
        if (pointer) { slidePointer(_pointerPosition); }
      }
      if ((pnl || (p && pID)) && (pnl !== false)) {
        var selP = tm.visiblePanel();
        var thisP = pnl ? pnl : tm.panel(pID);
        if (thisP && selP != thisP) {
          if (selP) {
            clickable = false;
            selP.hide(function () { thisP.show(function () { clickable = true; }) });
          }
          else {
            thisP.show(function () { clickable = true; });
          }
        }
      }
      if (showCalled && typeof tl.onSelect == 'function') { tl.onSelect(); }
    }

    t.click(function (e) { e.preventDefault(); if (clickable) { tl.select(); } });

  }

  if (m) {
    tm.panels = [];
    var i = 0;
    $('.tile-panel').each(function () {
      tm.panels[i] = new initPanel($(this), i);
      i++;
    });

    tm.tiles = [];
    i = 0;
    m.find('.tile').each(function () {
      tm.tiles[i] = new initTile($(this), i);
      i++;
    });

    pointer = $('.pointer');
  }

  tm.panel = function (id) {
    for (var i = 0; i < tm.panels.length; i++) {
      if (tm.panels[i].elementID() == id) { return tm.panels[i]; }
    }
    return null;
  }

  tm.tile = function (id) {
    for (var i = 0; i < tm.tiles.length; i++) {
      if (tm.tiles[i].elementID() == id) { return tm.tiles[i]; }
    }
    return null;
  }

  tm.visiblePanel = function () {
    for (var i = 0; i < tm.panels.length; i++) {
      if (tm.panels[i].isVisible()) { return tm.panels[i] }
    }
    return null;
  }

  var isInitSet = false;
  tm.setInitialState = function (panel_id) {
    if (!isInitSet) {
      isInitSet = true;
      var p = tm.panel(panel_id);
      if (p) {
        var t = p.tile();
        if (t) {
          slidePointer(t.pointerPosition(), 0);
        }
        p.show();
      }
    }
  }
}
