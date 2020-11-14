var fH = [];
var level = 0,
  prevLevel = 0;
var thisItem = true;

$(function () {
  window.onload = function () {
    // TODO 加载时...
  };

  window.onbeforeunload = function (event) {
    var top = $(window).scrollTop();
    sessionStorage.setItem("offsetTop", top);
  };

  $(window).scroll(function () {
    if (thisItem && $(window).scrollTop() != sessionStorage.getItem("offsetTop")) {
      thisItem = false;
      $(document).scrollTop(sessionStorage.getItem("offsetTop"));
      $("#catalogList a").each(function (index, element) {
        if ($(window).scrollTop() > $($(this).attr("href")).offset().top - 21) {
          $("#catalogList a").removeClass("catalog-this");
          $(this).addClass("catalog-this");
          return;
        }
      });
    }
    $("#catalogList a").each(function (index, element) {
      if ($(window).scrollTop() > $($(this).attr("href")).offset().top - 21) {
        $("#catalogList a").removeClass("catalog-this");
        $(this).addClass("catalog-this");
        return;
      }
    });
  });
});

// function setThisItem(item) {
//   thisItem = item;
// }

function getLevel(str = "") {
  return Number(str.slice(-1));
}

function selectTitle(tagName, hLabel) {
  if (tagName == "UL" || tagName == "OL") {
    hLabel.children().each(function (index, element) {
      selectTitle(tagName, $(this));
    });
  }
  addEntry(hLabel);
}

function addEntry(hLabel) {
  var tagName = hLabel.get(0).tagName;
  var hId = hLabel.attr('id');
  if (tagName.substr(0, 1).toUpperCase() != "H" || $.isEmptyObject(hId)) {
    return;
  }
  level = getLevel(tagName);
  if (fH.length > 0 &&
    (prevLevel >= level) &&
    (Number(fH[0].substr(1, 1)) >= level)) {
    fH = [];
  }
  var li = '<li><a href="#' + hId + '" title="' + hLabel.text() + '">' + hLabel.text() + '</a><ul id="' + tagName + hId + '"></ul></li>';
  var nKey = fH[0];
  for (var i = fH.length - 1; i >= 0; i--) {
    if (fH[i] != null && Number(fH[i].substr(1, 1)) < level) {
      nKey = fH[i];
      break;
    }
  }
  if (nKey == null) {
    $("#catalogList").append(li);
  } else {
    $("#" + nKey).append(li);
  }
  fH.push(tagName + hId);
  prevLevel = getLevel(tagName);
}

/**
 * 创建目录
 */
function createCatalog() {
  $("#wrapper").children().each(function (index, element) {
    selectTitle($(this).get(0).tagName, $(this));
  });
  if ($("#catalogList a").length <= 0) {
    return;
  }
  $($("#catalogList a")[0]).addClass("catalog-this");
}