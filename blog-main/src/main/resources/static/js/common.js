$(function () {
  into();
  //窗体缩放事件监听
  $(window).resize(function () {
    setMainSize();
    $('.button-collapse').sideNav('hide'); // 关闭侧边栏
    setSdebarSwitch();
    setHeaderCSS();
  });

  //滚动条事件监听
  $(window).scroll(function () {
    // $('.button-collapse').sideNav('hide'); // 关闭侧边栏
    setHeaderCSS();
  });

  // 侧边栏开关点击监听
  $("#sidebarSwitch").click(function () {
    $('.button-collapse').sideNav('show'); // 开启侧边栏
  });
  $("#mMenu").click(function () {
    $('.button-collapse').sideNav('show'); // 开启侧边栏
  });

  // 回到顶部按钮监听
  $("#arrowUp").click(function () {
    pageScroll();
  });

});

/**
 * 初始化
 */
function into() {
  setMainSize();
  setSdebarSwitch();
  setHeaderCSS();
  //侧边栏初始化
  $(".button-collapse").sideNav();

  layui.use('element', function () {
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    //监听导航点击
    element.on('nav(demo)', function (elem) {
      layer.msg(elem.text());
    });
  });

  //动画开关
  try {
    AOS.init();
  } catch (error) {}

  try {
    // 全屏轮播开关
    $('.slider').slider({
      full_width: true
    });
  } catch (error) {}

  try {
    //卡片流排列开关
    $('.article-main').masonry({
      // https://segmentfault.com/a/1190000013675077
      // 排列时间
      transitionDuration: '0.8s',
      // 是否横向排列
      horizontalOrder: false,
      // 参与排列的子组件(class属性)
      itemSelector: '.article-item',
      //一个组件发生改变其他组件排列的时间(毫秒)
      stagger: 5
    });
  } catch (error) {}
}

/**
 * 是否显示侧边栏开关
 */
function setSdebarSwitch() {
  if (window.innerWidth < 993) {
    $("#sidebarSwitch").removeClass("hide");
    return;
  }
  $("#sidebarSwitch").addClass("hide");
}

/**
 * 回到顶部
 */
function pageScroll() {
  $("html, body").animate({
    scrollTop: 0 + "px"
  }, 500);
}

/**
 * 调节底部栏的位置
 */
function setMainSize() {
  var contentHeight = $(window).height();
  var winHeight = $(document).height();
  if (winHeight > contentHeight) {
    $("#mFooter").removeClass("footer-button");
    return;
  }
  $("#mFooter").addClass("footer-button");
};

/**
 * 菜单透明度调节  
 */
function setHeaderCSS() {
  var scrollTop = formatZero($(document).scrollTop());

  if (scrollTop > 10) {
    $("#mMenu").removeClass("hide");
    $("#arrowUp").removeClass("hide");
    $("#mHeader").addClass("hide");
    return;
  }
  $("#mMenu").addClass("hide");
  $("#arrowUp").addClass("hide");
  $("#mHeader").removeClass("hide");
}
/**
 * value小于10补0
 * @param {*} value 值
 */
function formatZero(value) {
  if (value < 10) {
    return "0" + value;
  }
  return value;
}

/**
 * 提示栏
 * @param {*} tip 
 * @param {*} type 
 * @param {*} time 
 */
function ShowTip(tip, type, time) {
  var $tip = $('#tip');
  if ($tip.length == 0) {
    $tip = $('<span id="tip" style="font-weight:bold;position:absolute;top:50px;left: 50%;z-index:9999"></span>');
    $('body').append($tip);
  }
  $tip.stop(true).attr('class', 'alert alert-' + type).text(tip).css('margin-left', -$tip.outerWidth() / 2).fadeIn(
    500).delay(time).fadeOut(500);
}