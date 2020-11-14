$(function () {
  mRender();
  mRowMonitor();
  mHeadMonitor();
  // openInput1(data);
});
/**
 * 头工具栏事件
 */
function mHeadMonitor() {
  layui.use('table', function () {
    var table = layui.table;
    table.on('toolbar(mBlogTable)', function (obj) {
      switch (obj.event) {
        case 'mReload':
          reload();
          break;
      };
    });
  });
}

function openComment(data) {
  var index = layer.open({
    type: 2,
    title: '查看评论[' + data.abTitle + ']',
    area: ['800px', '600px'],
    maxmin: true,
    // resize: false,
    // skin: 'layui-layer-rim',
    content: '/system/comment',
    success: function (layero, index) {
      layero.find("iframe")[0].contentWindow.mRender(data.abAid);
    },
    cancel: function (index, layero) {
      // layer.msg('已取消', {
      //   time: 600
      // });
      return true;
    },
    end: function () {}
  });
  layer.full(index);
}

/**
 * 监听行工具事件
 */
function mRowMonitor() {
  layui.use('table', function () {
    var table = layui.table;
    table.on('tool(mBlogTable)', function (obj) {
      var data = obj.data;
      switch (obj.event) {
        case 'comment':
          openComment(data);
          break;
        case 'edit':
          // openInput('修改', '/sort/updateSort', data);
          window.location.href = '/system/blog/modify/' + data.abAid;
          break;
        case 'del':
          layer.prompt({
            formType: 1,
            title: '删除',
            area: ['450px', '100px'],
            resize: false,
            btnAlign: 'c',
            content: "确定删除[" + data.abTitle + "]博文吗?",
            yes: function (index, layero) {
              $.ajax({
                type: "get",
                url: "/blog/deleteBlog",
                data: {
                  abAid: data.abAid,
                },
                success: function (data) {
                  if (data.code == 200) {
                    ShowMessage('删除成功.', "success");
                    obj.del();
                    layer.close(index);
                    reload();
                    return;
                  }
                  ShowMessage(data.message, "error");
                },
                error: function () {
                  layer.close(index);
                  ShowMessage('删除失败,请重试.', "error");
                }
              });
            },
            btn2: function () {
              ShowMessage('已取消', "none");
              return true;
            },
            cancel: function (index, layero) {
              ShowMessage('已取消', "none");
              return true;
            }
          });
          break;
      }
    });
  });
}

/**
 * 初始化表格
 */
function mRender() {
  layui.use('table', function () {
    var table = layui.table;
    table.render({
      elem: '#mBlogTable',
      // url: '/templates/system/blog/demo3.json',
      url: '/blog/getBlogList',
      toolbar: '#toolbarDemo',
      cellMinWidth: 80,
      title: '分类表',
      cols: [
        [{
          field: 'abAid',
          title: '编号',
          width: 105,
          sort: true
        }, {
          field: 'abTitle',
          title: '标题',
          sort: true
        }, {
          field: 'abUid',
          title: '发布人',
        }, {
          field: 'adType',
          title: '类型',
          templet: function (d) {
            if (d.detailedInfo.adType == 1) {
              return '原创';
            } else if (d.detailedInfo.adType == 2) {
              return '转载';
            } else if (d.detailedInfo.adType == 3) {
              return '翻译';
            } else {
              return '未知';
            }
          }
        }, {
          field: 'abPublish',
          title: '是否发布',
          templet: function (d) {
            if (d.abPublish == 1) {
              return "是";
            }
            return "否";
          }
        }, {
          field: 'abUpdateDate',
          title: '更新时间',
          sort: true
        }, {
          field: 'abPublishDate',
          title: '发表时间',
          sort: true
        }, {
          fixed: 'right',
          title: '操作',
          toolbar: '#barDemo',
          width: 158,
          // unresize: true
        }]
      ],
      page: true,
      limit: 10,
      limits: [5, 10, 15]
    });
  });
}

/**
 * 重载表格
 */
function reload() {
  layui.use('table', function () {
    var table = layui.table;
    table.reload('mBlogTable', {
      url: '/blog/getBlogList',
      page: {
        curr: 1
      },
      where: {},
      error: {}
    });
  });
}

function ShowMessage(msg, strIcon) {
  var icon = 1;
  if (strIcon == "error") {
    icon = 2;
  } else if (strIcon == "none") {
    layer.msg(msg, {
      time: 1000
    });
    return;
  }
  layer.msg(msg, {
    icon: icon,
    time: 1000
  });
}

function openInput(title, url, data) {
  layer.open({
    type: 2,
    title: title + '分类',
    area: ['450px', '390px'],
    resize: false,
    // skin: 'layui-layer-rim',
    content: '/sort/add',
    success: function (layero, index) {
      if (data != null) {
        layero.find("iframe")[0].contentWindow.setSid(data.sSid);
        layero.find("iframe")[0].contentWindow.setInfo(data.sName, data.sAlias, data.sDescription);
      }
      layero.find("iframe")[0].contentWindow.setModify(title, url);
    },
    cancel: function (index, layero) {
      layer.msg('已取消', {
        time: 600
      });
      return true;
    },
    end: function () {}
  });
}