var currentPage = 1;
var pageSize = 10;
var totalPage = 0;
var totalCount = 0;

$(function() {
	pageQuery(currentPage);
	$("#addButton").click(addProduct);

});

// 分页查询
function pageQuery(pageNum) {
	currentPage = pageNum;
	$.post("/p2p_management/product?method=pageQuery", {
		"currentPage" : currentPage,
		"pageSize" : pageSize
	}, function(data) {
		var json = eval(data);
		totalCount = json.total;
		var list = json.rows;
		var tContent = "<tr><td>序号</td><td>产品编号</td><td>产品名称</td><td>期限</td><td>年化利率</td><td>操作</td></tr>";
		for (var i = 0; i < list.length; i++) {
			tContent += "<tr><td>" + (i + 1) + "</td><td>" + list[i].proNum
					+ "</td><td>" + list[i].proName + "</td><td>"
					+ list[i].proLimit + "</td><td>" + list[i].annualized
					+ "</td><td><a href='javascript:void(0)' onclick='editProduct("+list[i].id+")'>编辑</a></td></tr>";
		}
		//添加导航栏
		var paginationContent="";
		//向前一页
		if(currentPage==1){
			paginationContent+="<a class='icon item disabled'><i class='left chevron icon'></i></a>";
		}else{
			paginationContent+="<a class='icon item' href='javascript:void(0)' onclick=pageQuery("+(currentPage-1)+")><i class='left chevron icon'></i></a>";
		}
		//数字页
		totalPage = Math.ceil(totalCount/pageSize);
		for(var i=0;i<totalPage;i++){
			paginationContent+="<a class='item' href='javascript:void(0)' onclick=pageQuery("+(i+1)+")>"+(i+1)+"</a>"
		}
		//向后一页
		if(currentPage==totalPage){
			paginationContent+="<a class='icon item disabled'><i class='right chevron icon'></i></a>";
		}else{
			paginationContent+="<a class='icon item' href='javascript:void(0)' onclick=pageQuery("+(currentPage+1)+")><i class='right chevron icon'></i></a>";
		}
		
		// 更改页面显示
		$("#tBody").html(tContent);
		$("#totalInfo").html(totalCount);
		$("#pageInfo").html(currentPage);
		$("#paginationDiv").html(paginationContent);
	}, "json");
}

// 新增商品
function addProduct() {
	$("#modalHeader").html("添加商品");
	$('.ui.modal').modal('show');
	//添加事件前先解绑
	$("#submitButton").unbind("click")
	//清除表单数据
	$("#prodForm :input").each(function(){
		$(this).val("");
	});
	//重新绑定
	$("#submitButton").bind("click",function() {
		var json = $("#prodForm").serialize();
		$.post("/p2p_management/product?method=add",json,function(data){
			pageQuery(currentPage);
			alert("添加成功!");
		});
		$('.ui.modal').modal('hide');
	});
}

//修改商品
function editProduct(productId) {
	$("#modalHeader").html("修改商品");
	$('.ui.modal').modal('show');
	
	//根据id查询商品
	$.post("/p2p_management/product?method=findById",{"productId":productId},function(data){
		var productJson = eval(data);
		$("input[name='proNum']").val(productJson.proNum);
		$("input[name='proName']").val(productJson.proName);
		$("input[name='proLimit']").val(productJson.proLimit);
		$("input[name='annualized']").val(productJson.annualized);
	},"json");
	//添加事件前先解绑
	$("#submitButton").unbind("click")
	//重新绑定
	$("#submitButton").bind("click",function() {
		var json = $("#prodForm").serialize();
		json+=("&id="+productId);
		$.post("/p2p_management/product?method=edit",json,function(data){
			pageQuery(currentPage);
			alert("修改成功!");
		});
		$('.ui.modal').modal('hide');
	});
}


// 注销
function logout() {
	var flag = window.confirm("确认退出么?");
	if (flag) {
		$.post("/p2p_management/user?method=logout", function() {
		});
		location.href = "/p2p_management/login.jsp";
	}
}

