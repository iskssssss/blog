var testEditor;
var getBlogInfo;
var blog, blogLabelInfoList, blogSortInfo;

function setBlog (_setBlogInfo) {
  getBlogInfo = _setBlogInfo;
}
/**
 * editormd初始化
 */
function editormdInit () {
  testEditor = editormd("mEditormd", {
    width: "100%",
    height: 800,
    emoji: true,
    toolbarAutoFixed: false,
    syncScrolling: "single",
    path: "../../../static/mod/editormd/lib/",
    imageUpload: true,
    imageFormats: ["jpg", "jpeg", "gif", "png", "ico"],
    imageUploadURL: "/common/uploadEditormd",
    saveHTMLToTextarea: true,
    htmlDecode: true,
    htmlDecode: "style,script,sub,sup|on*",
    onload: function (obj) {
      console.log(obj);
    },
    onfullscreen: function () {
      $("#hHeader").css("z-index", "997");
    },
    onfullscreenExit: function () {
      $("#hHeader").css("z-index", "999");
    }
  });
}

/**
 * 标签列表初始化（弃用）
 */
function selectLabelInit () {
  var selectLabel = $("#selectLabel");
  selectLabel.empty();
  selectLabel.append(new Option("请输入或选择标签", ""));
  $.ajax({
    type: "get",
    url: "/label/getLabelNameList",
    success: function (responseData) {
      if (responseData.code == 200) {
        data = responseData.data;
        data.forEach(element => {
          selectLabel.append(new Option(element.lName, element.lLid));
        });
        return;
      }
    },
    error: function () { }
  });
}

/**
 * 分类列表初始化（弃用）
 */
function selectSortInit () {
  var selectLabel = $("#selectSort");
  selectLabel.empty();
  selectLabel.append(new Option("请选择文章分类", ""));
  $.ajax({
    type: "get",
    url: "/sort/getSortNameList",
    success: function (responseData) {
      if (responseData.code == 200) {
        data = responseData.data;
        data.forEach(element => {
          selectLabel.append(new Option(element.sName, element.sSid));
        });
        return;
      }
    },
    error: function () { }
  });
}

layui.use(['form'], function () {
  var form = layui.form,
    layer = layui.layer;

  //自定义验证规则
  form.verify({
    title: [/[\S]+/, "文章标题不可为空!"],
    synopsis: [/[\S]+/, "文章简介不可为空!"],
    content: [/[\S]+/, "正文不可为空!"],
    type: [/[\S]+/, "请选择文章类型!"],
    sort: [/[\S]+/, "请选择文章分类!"]
  });

  // 保存
  form.on('submit(btnSave)', function (data) {
    $("#isShowTS").val("false");
    saveInfo(data.field, false);
    return false;
  });

  // 发布
  form.on('submit(btnRelease)', function (data) {
    if ($("#abPublish").val() == "1") {
      ShowMessage("该博文已发布,请不要重复发布.", "error");
      return;
    }
    $("#isShowTS").val("false");
    saveInfo(data.field, true);
    return false;
  });

  function releaseBlog (requestData, type) {
    console.info(type);
    // 标题
    if (type || blog.abTitle != requestData["blog.abTitle"]) {
      requestData["blog"]["abTitle"] = requestData["blog.abTitle"];
    }
    delete requestData["blog.abTitle"];
    // 简介
    if (type || blog.abSynopsis != requestData["blog.abSynopsis"]) {
      requestData["blog"]["abSynopsis"] = requestData["blog.abSynopsis"];
    }
    delete requestData["blog.abSynopsis"];
    // 封面图
    if (type || blog.abCoverImage != requestData["blog.abCoverImage"]) {
      requestData["blog"]["abCoverImage"] = requestData["blog.abCoverImage"];
    }
    delete requestData["blog.abCoverImage"];
    // 正文
    if (type || blog.detailedInfo.adContent != requestData["blog.detailedInfo.adContent"]) {
      requestData["blog"]["detailedInfo"]["adContent"] = requestData["blog.detailedInfo.adContent"];
    }
    delete requestData["blog.detailedInfo.adContent"];
    // 类型 
    if (type || blog.detailedInfo.adType != requestData["blog.detailedInfo.adType"]) {
      requestData["blog"]["detailedInfo"]["adType"] = requestData["blog.detailedInfo.adType"];
    }
    delete requestData["detailedInfo.adType"];
    // 开启评论
    requestData['blog.detailedInfo.adOpenComment'] = requestData['blog.detailedInfo.adOpenComment'] == null ? 0 : 1;
    if (type || blog.detailedInfo.adOpenComment != requestData["blog.detailedInfo.adOpenComment"]) {
      requestData["blog"]["detailedInfo"]["adOpenComment"] = requestData["blog.detailedInfo.adOpenComment"];
    }
    delete requestData["blog.detailedInfo.adOpenComment"];
    // 开启推荐
    requestData['blog.detailedInfo.adOpenRecommend'] = requestData['blog.detailedInfo.adOpenRecommend'] == null ? 0 : 1;
    if (type || blog.detailedInfo.adOpenRecommend != requestData["blog.detailedInfo.adOpenRecommend"]) {
      requestData["blog"]["detailedInfo"]["adOpenRecommend"] = requestData["blog.detailedInfo.adOpenRecommend"];
    }
    delete requestData["blog.detailedInfo.adOpenRecommend"];
    return requestData;
  }

  // requestData 请求数据 labelAndsort 标签、分类、发布信息 type操作类型(true 发布操作 false 保存操作)
  function saveInfo (requestData, type) {
    var labelAndsort = blogInfoVerification(type);
    if ($.isEmptyObject(labelAndsort)) {
      return;
    }
    requestData["blog"] = {};
    requestData["blog"]["detailedInfo"] = {};
    // 发布博文 或者 保存博文
    if ((type && $("#abPublish").val() == "") || $("#abPublish").val() == "") {
      requestData = releaseBlog(requestData, true);
      // 保存-不发布 
      requestData["blog"]["abPublish"] = type ? 1 : 0;

      requestData["insertSorts"] = labelAndsort['sortList'];
      delete requestData["sorts"];
      requestData["insertLabels"] = labelAndsort['labelList'];
      releaseAjax(requestData);
      return;
    }
    if (type && $("#abPublish").val() == "0") {
      requestData["blog"]["abPublish"] = 1;
    }

    //获取博文信息
    getBlogInfo();
    requestData = releaseBlog(requestData, false);

    // 分类
    if (blogSortInfo.sSid == requestData["sSid"]) {
      delete requestData["sSid"];
    } else {
      var insertSorts = [];
      var deleteSorts = [];
      insertSorts.push({
        "sSid": requestData["sSid"],
      });
      deleteSorts.push({
        "sSid": blogSortInfo.sSid + "",
      });
      requestData["insertSorts"] = insertSorts;
      requestData["deleteSorts"] = deleteSorts;
      delete requestData["sSid"];
    }

    // 标签
    var deleteLabel = [];
    for (var labelKey in blogLabelInfoList) {
      var isDelete = true;
      for (var i = 0; i < labelAndsort["labelList"].length; i++) {
        if (labelAndsort["labelList"][i].lLid == blogLabelInfoList[labelKey].lLid) {
          delete blogLabelInfoList[labelKey];
          labelAndsort["labelList"].splice(i, 1);
          isDelete = false;
          break;
        }
      }
      if (isDelete) {
        deleteLabel.push({
          "lLid": blogLabelInfoList[labelKey].lLid,
        });
      }
    }
    if (deleteLabel.length != 0) {
      requestData["deleteLabels"] = deleteLabel;
    }
    if (labelAndsort["labelList"].length != 0) {
      requestData["insertLabels"] = labelAndsort['labelList'];
    }
    if (
      len(requestData) <= 1 &&
      len(requestData["blog"]) <= 1 &&
      len(requestData["blog"]["detailedInfo"]) <= 0) {

      ShowMessage('未改变.', "none");
      return false;
    }
    requestData["blog"]["abAid"] = $("#abAid").val();

    saveAjax(requestData);
  }

  // 验证信息
  function blogInfoVerification (isRelease) {
    var labelList = [];
    var sortList = [];
    $("#labelList li").each(function (index, value) {
      labelList.push({
        "lLid": $(value).children(".lLid").val()
      });
    });
    if (labelList.length < 1) {
      ShowMessage("请添加文章标签(至少一个).", "error");
      return null;
    }
    sortList.push({
      "sSid": $("#selectSort").val()
    });
    labelAndsort = {
      "labelList": labelList,
      "sortList": sortList
    };
    return labelAndsort;
  }

  function saveAjax (requestData) {
    $.ajax({
      type: "post",
      url: "/blog/modifyBlog",
      // traditional: true,
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify(requestData),
      success: function (responseData) {
        if (responseData.code == 200) {
          ShowMessage(responseData.message, "success");
          window.location.href = '/system/blogs';
          return;
        }
        ShowMessage("保存失败,请重试.", "error");
      },
      error: function () {
        ShowMessage("保存失败,请重试.", "error");
      }
    });
  }

  function releaseAjax (requestData) {
    $.ajax({
      type: "post",
      url: "/blog/insertBlog",
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify(requestData),
      success: function (responseData) {
        if (responseData.code == 200) {
          ShowMessage(responseData.message, "success");
          window.location.href = '/system/blogs';
          return;
        }
        ShowMessage("发布失败,请重试.", "error");
      },
      error: function () {
        ShowMessage("发布失败,请重试.", "error");
      }
    });
  }

  /**
   * 添加标签
   */
  $("#btnAddLabel").click(function () {
    var lName = $("#selectLabel").find("option:selected").text();
    var lLid = $("#selectLabel").val();
    if (lName == "" || lLid == "") {
      ShowMessage('请输入或选择标签.', 'error');
      return;
    }
    var isRepeat = false;
    $("#labelList li").each(function (index, value) {
      if ($(value).children(".lLid").val() == lLid) {
        isRepeat = true;
        return;
      }
    });
    if (isRepeat) {
      ShowMessage('该标签已存在.', 'error');
      return;
    }
    var label = `<li class="chip">
                  <input type="hidden" class="lLid" value="${lLid}"></input>
                  <span class="lName">${lName}</span>
                  <i class="close fa fa-close"></i>
                </li>`;
    $("#labelList").append(label);
  });

  /**
   * 返回
   */
  $("#btnBack").click(function () {
    window.history.back(-1);
  });

  /**
   * 消息框
   */
  function ShowMessage (msg, strIcon) {
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

  function len (requestData) {
    var size = 0;
    for (var key in requestData) {
      size++;
    };
    return size;
  }
});

// 封面图选择
$("#abCoverImage").click(function () {
  layer.confirm('请选择网络图片还是上传图片。', {
    title: "选择设置方式",
    closeBtn: 0,
    shadeClose: true,
    anim: 2,
    size: 60,
    scrollbar: false,
    btn: ['网络图片', '上传图片']
  }, function (index, layero) {
    console.info(layero);
    layer.close(index);
    layer.prompt({
      title: "网络图片",
      shadeClose: true,
      scrollbar: false,
    }, function (val, index) {
      $('.large-header').css("background-image", "url(" + val + ")");
      $("#large-img-input").val(val);
      layer.close(index);
    });
    return true;
  }, function (index, layero) {
    $("#avatar").trigger("click");
  });
});

// 选择本地图片
$('#avatar').on('change', function () {
  var file = this.files[0];
  var reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = function (e) {
    $('.large-header').css("background-image", "url(" + e.target.result + ")");
  }
  var formdata = new FormData();
  formdata.append("avatarfile", this.files[0]);
  $.ajax({
    url: '/common/upload',
    type: 'POST',
    data: formdata,
    contentType: false,
    processData: false,
    success: function (args) {
      console.log(args);  /*服务器端的图片地址*/
      $("#large-img-input").val(args.data);
    }
  })
});