$(function () {
  mRender();
  mRowMonitor();
  mHeadMonitor();
});
/**
 * 头工具栏事件
 */
function mHeadMonitor() {
  layui.use('table', function () {
    var table = layui.table;
    table.on('toolbar(mLabelTable)', function (obj) {
      switch (obj.event) {
        case 'addLabel':
          openInput('添加', '/label/addLabel', null);
          break;
        case 'mReload':
          reload();
          break;
      };
    });
  });
}

/**
 * 监听行工具事件
 */
function mRowMonitor() {
  layui.use('table', function () {
    var table = layui.table;
    var checkStatus = table.checkStatus('mLabelTable');
    table.on('tool(mLabelTable)', function (obj) {
      var data = obj.data;
      switch (obj.event) {
        case 'edit':
          openInput('修改', '/label/updateLabel', data);
          break;
        case 'del':
          layer.prompt({
            formType: 1,
            title: '删除',
            area: ['450px', '100px'],
            resize: false,
            btnAlign: 'c',
            content: "确定删除[" + data.lName + "]标签吗?",
            yes: function (index, layero) {
              $.ajax({
                type: "get",
                url: "/label/deleteLabel",
                data: {
                  lId: data.lLid,
                },
                success: function (data) {
                  layer.close(index);
                  if (data.code == 200) {
                    ShowMessage('删除成功.', "success");
                    obj.del();
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
      elem: '#mLabelTable',
      url: '/label/getLabelList',
      toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
      cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      title: '标签表',
      cols: [
        [{
          field: 'lLid',
          title: 'ID',
          width: 105,
          sort: true
        }, {
          field: 'lName',
          title: '名称',
          sort: true
        }, {
          field: 'lAlias',
          title: '别名',
        }, {
          field: 'lDescription',
          title: '描述',
        }, {
          field: 'lAddDate',
          title: '创建时间',
          sort: true
        }, {
          fixed: 'right',
          title: '操作',
          toolbar: '#barDemo',
          width: 112,
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
    table.reload('mLabelTable', {
      url: '/label/getLabelList',
      page: {
        curr: 1
      },
      where: {}
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
    title: title + '标签',
    area: ['450px', '390px'],
    resize: false,
    // skin: 'layui-layer-rim',
    content: '/system/label/add',
    success: function (layero, index) {
      if (data != null) {
        layero.find("iframe")[0].contentWindow.setLid(data.lLid);
        layero.find("iframe")[0].contentWindow.setInfo(data.lName, data.lAlias, data.lDescription);
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