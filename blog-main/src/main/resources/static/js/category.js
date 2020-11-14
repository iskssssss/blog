$(function () {
  setHeaderCSS();
  setMainDivTop();
  $("#pmImages").css("height", getWindowsHeight());

  $(window).resize(function () {
    setHeaderCSS();
    setMainDivTop();
    $("#pmImages").css("height", getWindowsHeight());
  });

  $(window).scroll(function () {
    setHeaderCSS();
  });

  layui.use('carousel', function () {
    var carousel = layui.carousel;
    carousel.render({
      elem: '#pmImages',
      width: '100%',
      height: getWindowsHeight(),
      interval: 5000
    });
  });

  layui.use('element', function () {
    var element = layui.element;
    element.on('nav(demo)', function (elem) {
      layer.msg(elem.text());
    });
  });
});


function getWindowsHeight() {
  var height = window.innerHeight / 2;
  // height = height - (height / 4);
  return height;
}

// 设置主内容位置
function setMainDivTop() {
  $("#mainDiv").css("top", getWindowsHeight() - 200);
}