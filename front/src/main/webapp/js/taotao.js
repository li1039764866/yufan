var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("token");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.yufan.com/sso/login/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == "success"){
					var _data = data.data;
					var html =_data.username+"，欢迎来到羽帆！<a href=\"http://www.yufan.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});