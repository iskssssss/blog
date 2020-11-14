$(function () {
  $("#btnLogin").click(function () {
    login();
  });
  $("#btnRegister").click(function () {
    register();
  });
});

function login () {
  var user = $("input[name='inUser']").val();
  var password = $("input[name='inPassword']").val();
  var rememberMe = $("input[name='rememberme']").is(':checked');
  if (user == "" || password == "") {
    ShowTip("账号或密码不能为空", 'warning', 3000);
    return;
  }
  $.ajax({
    type: "post",
    url: "/login",
    data: {
      username: user,
      password: password,
      rememberMe: rememberMe
    },
    success: function (data) {
      if (data.code == 200) {
        ShowTip(data.message, 'success', 3000);
        location.href = "/system"
        return;
      }
      ShowTip(data.message, 'danger', 3000);
    }
  });
}

function register () {
  var phone = $("input[name='inPhone']").val();
  var nickname = $("input[name='inNickname']").val();
  var password1 = $("input[name='inPassword1']").val();
  var password2 = $("input[name='inPassword2']").val();

  if (phone == "") {
    ShowTip("手机号不可为空。", 'warning', 3000);
    return;
  }
  if (nickname == "") {
    ShowTip("昵称不可为空。", 'warning', 3000);
    return;
  }
  if (password1 == "") {
    ShowTip("密码不可为空。", 'warning', 3000);
    return;
  }
  if (password2 == "") {
    ShowTip("确认密码不可为空。", 'warning', 3000);
    return;
  }
  if (password1 != password2) {
    ShowTip("两次密码不一样，请重新输入。", 'warning', 3000);
    return;
  }

  $.ajax({
    type: "post",
    url: "/register",
    data: {
      phone: phone,
      nickname: nickname,
      password: password2
    },
    success: function (data) {
      if (data.code == 200) {
        ShowTip(data.message + "您的UID为：" + data.data, 'success', 3000);
        setTimeout('toLogin()', 3000);
        return;
      }
      ShowTip(data.message, 'danger', 3000);
    }
  });
}

function toLogin () {
  location.href = "/login";
}