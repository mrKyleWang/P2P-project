$(function() {
	// 读取商品信息
	$
			.getJSON(
					"/p2p_management/product?method=getAll&callback=?",
					function(data) {
						var json = eval(data);
						var rollContent = "";

						for (var i = 0; i < json.length; i++) {
							rollContent += "<li><div class='col-sm-6 col-md-4'><div class='thumbnail'><div class='caption pro1'><h4>"
									+ json[i].proName
									+ "</h4></div><img src='img/help/m-ico_01.png'/><div class='caption'><h4>期限:"
									+ json[i].proLimit
									+ "月</h4><h4>年化利率:"
									+ json[i].annualized
									+ "%</h4><a href='#shopArea1' onclick='showProductInfo("
									+ json[i].id
									+ ")' data-toggle='tab' class='btn btn-info'>我要购买</a></div></div><span class='ico'></span></div></li>";
						}
						$("#rollWindow").html(rollContent);
						$(function() {
							$('.dowebok').liMarquee({
								direction : 'up', // 设置滚动方向
								scrollamount : '40', // 设置滚动速度
								runshort : false
							// 内容未超过容器宽度（高度），不滚动。
							});
						});
					});
});

// 在弹出框内显示商品信息
function showProductInfo(productId) {
	//清除数据
	$("#moneyInput").val("");
	$("#money").html("0");
	$("#interest > i").html("0");
	$("#buyButton").unbind();	
	// 读取商品信息
	$.getJSON("/p2p_management/product?method=findById&callback=?", {
		'productId' : productId
	}, function(data) {
		var json = eval(data);
		//显示商品信息
		$("#proLimit").html(json.proLimit+"月");
		$("#annualized > i").html(json.annualized);
		$("#annualized2").html(json.annualized+"%");
		$("#proName").html(json.proName);
		//校验输入金额,计算预期收益
		//预期收益 = 投资金额*期限/12*预期年化收益/100
		$("#moneyInput").keyup(function() {
			var nu = $(".num").val();
			if (null == nu || "" == nu) {
				$(".word").html("");
			} else {
				if (nu % 100 == 0) {
					$(".word").html("");
					//计算预期收益
					var money = $("#moneyInput").val();
					var interest = money*json.proLimit*json.annualized/100/12;
					//保留两位小数
					var interestStr = interest+"";
					var index = interestStr.indexOf("\.");
					if(index!=-1){
						interest = parseFloat(interestStr.substring(0,index+3));
					}
					$("#money").html(money);
					$("#interest > i").html(money*json.proLimit*json.annualized/100/12);
					$("#buyButton").unbind();
					//点击确认购买,向服务器发出请求
					$("#buyButton").bind("click",function(){
						$.post("/p2p_home/productAccount?method=purchase",{"productId":json.id,"money":money},function(data){
							var json = eval(data);
							if(json.type==-1){
								//未登录
								alert(json.msg)
								location.href="/p2p_home/login.html";
							}else if(json.type==0){
								//购买失败
								alert(json.msg)
							}else{
								//购买成功
								alert(json.msg)
							}
						},"json");
					});
				} else {
					$(".word").html("请输入100的整数倍");
				}
			}

		});
	});
}


