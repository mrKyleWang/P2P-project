$(function(){
	
	initAccountInfo();
	$("#sendMailButton").click(sendMail);
	$("#checkMailButton").click(checkMail);
});

function initAccountInfo(){
	//读取账户信息
	$.post("/p2p_home/account?method=findCustomerAccount",function(data){
		var json = eval(data);
		if(json.type==0){
			//未登录
			alert(json.msg);
			location.href = "/p2p_home/login.html";
		}else{
			$(".totalInfo").html(json.total);
			$(".balanceInfo").html(json.balance);
			$(".interestInfo").html(json.interest);
			$("#customerNameSpan").html(json.c.c_name);
			
			if(json.c.email_status==0){
				$("#emailStatus").html("未认证");
				$("#emailStatus").attr("class","no");
				$("#emailStatusSign").attr("class","glyphicon glyphicon-info-sign");
			}else{
				$("#emailStatus").html("已认证");
				$("#emailStatus").attr("class","yes");
				$("#emailStatusSign").attr("class","glyphicon glyphicon-ok-circle");
			}
			$("#checkEmail").val(json.c.email);
		}
	},"json");
	
	//读取投资列表
	/*<td id="pa_num">投资编号</td>
	<td id="proName">产品名称</td>
	<td id="proLimit">期限</td>
	<td id="annualized">年化利率</td>
	<td id="pa_money">本金</td>
	<td id="pa_interest">预期收益</td>
	<td id="pa_date">投资时间</td>
	<td id="isMature">是否到期</td>*/
	
	/*<td>投资编号</td>
	<td>产品名称</td>
	<td>期限</td>
	<td>年化利率</td>
	<td>本金</td>
	<td>预期收益</td>
	<td>投资时间</td>
	<td>是否到期</td>*/
	
	
	$.post("/p2p_home/productAccount?method=findAllInfo",function(data){
		var json = eval(data);
		var content ="";
		var totalInvest = 0;
		var readyGet = 0;
		
		for(var i=0;i<json.length;i++){
			
			var pa_status ="";
			if(json[i].status == "0"){
				pa_status="否";
				readyGet += parseInt(json[i].interest);
			}else{
				pa_status="是";
			}
			
			content += ("<tr><td>"+json[i].pa_num+"</td><td>"+json[i].p.proName+"</td><td>"+json[i].p.proLimit+"</td><td>"+json[i].p.annualized+"</td><td>"+json[i].money+"</td><td>"+json[i].interest+"</td><td>"+(new Date(json[i].pa_date).toLocaleDateString())+"</td><td>"+pa_status+"</td></tr>");
			totalInvest += parseInt(json[i].money);
		}
		
		console.info(json);
		$("#tContent").html(content)
		$("#totalInvest").html(totalInvest);
		$("#readyGet").html(readyGet);
		
	},"json");
	
}

function sendMail(){
	var emailAddress = $("#checkEmail").val();
	$.post("/p2p_home/account?method=sendMail",{"emailAddress":emailAddress},function(data){
		var json = eval(data);
		if(json.type==0){
			//发送失败
			tip($("#emailAddressDiv"),json.msg,"red");
		}else{
			//发送成功
			tip($("#emailAddressDiv"),json.msg,"green");
		}
	},"json");
}

function checkMail(){
	var emailCode = $("#emailCode").val();
	$.post("/p2p_home/account?method=checkMail",{"emailCode":emailCode},function(data){
		var json = eval(data);
		if(json.type==0){
			//验证失败
			tip($("#emailCodeDiv"),json.msg,"red");
		}else{
			//验证成功
			tip($("#emailCodeDiv"),json.msg,"green");
			initAccountInfo();
		}
	},"json");
}

//显示提示
function tip(target,msg,color){
	var msgSpan = target.next("span");
	var msgContent = "<font color='"+color+"'>"+msg+"</font>";
	if(msgSpan.length==0){
		target.after("<span>"+msgContent+"</span>");
	}
	msgSpan.html(msgContent);
}

