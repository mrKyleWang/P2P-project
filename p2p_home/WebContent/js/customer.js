var checkPass = 0;
$(function(){
	
	showRemenber();
	$("#submitButton").click(register);
	$("#imageCode").click(changeImage);
	$("#checkCode").keyup(check);
	$("#loginButton").click(login);
});

//注册
function register(){
	var json = $("#regform").serializeJson();
	
	if(!notNull(json.c_name)){
		tip($("input[name=c_name]"),"用户名不能为空!","red");
		return;
	}
	if(!notNull(json.email)){
		tip($("input[name=email]"),"邮箱不能为空!","red");
		return;
	}
	if(!notNull(json.password)){
		tip($("input[name=password]"),"密码不能为空!","red");
		return;
	}
	if(json.confirmPassword!=json.password){
		tip($("input[name=confirmPassword]"),"确认密码不一致!","red");
		return;
	}
	if(!$("#checkbox").prop("checked")){
		tip($("#checkbox"),"请确认协议!","red");
		return;
	}
	//用户名格式校验
	if(!regex($("input[name=c_name]"),/^[a-zA-Z]{1}[a-zA-Z0-9]{2,9}$/,"用户名必须是字母开头并且不能少于3位不能多余10位","red")){
		return;
	}
	//邮箱格式校验
	if(!regex($("input[name=email]"),/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,"邮箱地址不符合规范!","red")){
		return;
	}
	$("input+span").html("");
	
	$.post("/p2p_home/customer?method=register",json,function(data){
		var result = eval(data);
		if(result.type==0){
			//注册失败
			alert(result.msg);
		}else if(result.type==1){
			//注册成功
			alert(result.msg);
			//跳转登录页面
			location.href = "/p2p_home/login.html";
		}
	},"json");
}

//非空校验
function notNull(str){
	if(str==null||($.trim(str)=="")){
		return false;
	}else{
		return true;
	}
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

//正则校验
function regex(target,reg,msg){
	if(!reg.test(target.val())){
		tip(target,msg,"red");
		return false;
	}
	return true;
}

//换验证码图片
function changeImage(){
	$("#imageCode").attr("src","/p2p_home/imageCode?time="+new Date().getTime());
}

//校验验证码
function check(){
	checkCode = $("#checkCode").val();
	$.post("/p2p_home/customer?method=checkCode",{"checkCode":checkCode}, function(data){
		var result = eval(data);
		if(result.type==0){
			tip($("#checkgroup"),result.msg,"red");
			checkPass = 0;
		}else if(result.type==1){
			tip($("#checkgroup"),result.msg,"blue");
			checkPass = 1;
		}
	},"json");
	
}

//检查记住用户名是否勾选
function checkRemenber(){
	if($("#isRemenber").prop("checked")){
		return 1;
	}else{
		return 0;
	}
}

//登录
function login(){
	//检查验证码校验结果
	if(checkPass == 0){
		tip($("#checkgroup"),"请输入正确的验证码","red");
	}else if(checkPass ==1){
		//检查记住用户名是否勾选
		var nameOrEmail = $("input[name=nameOrEmail]").val();
		var password =  $("input[name=password]").val();
		var isRemenber = checkRemenber();
		$.post("/p2p_home/customer?method=login",{"nameOrEmail":nameOrEmail,"password":password,"isRemenber":isRemenber},function(data){
			var result = eval(data);
			if(result.type==0){
				//登录失败
				alert(result.msg);
			}else if(result.type==1){
				//登录成功
				alert(result.msg);
				//跳转登录页面
				location.href = "/p2p_home/space.html";
			}
		},"json");
	}
}

//显示上次登录用户名
function showRemenber(){
	$.post("/p2p_home/customer?method=showRemenber",function(data){
		$("input[name=nameOrEmail]").val(data);
		if(notNull(data)){
			$("#isRemenber").prop("checked",true);
		}
	});
}



//定义一个jquery的插件，完成表单转换成json操作-----{username:tom,password:123,hobby:[eat,drink,play]}
$.fn.extend({
    serializeJson : function() {
        var json = {}; //就是一个javascript的对象.
        //1.通过jquery提供的serializeArray方法得到不符合要求的json串
        var msg = this.serializeArray();
        //console.info(msg);  
        //[Object { name="username",  value="tom"}, Object { name="password",  value="123"}, Object { name="hobby",  value="eat"}, Object { name="hobby",  value="drink"}, Object { name="hobby",  value="play"}]
        $(msg).each(function() {
            if (json[this.name]) { //在json对象中没有this.name对应的值
                //有,需要考虑一个名称对应多个值，而这些值应该放入到数组中
                if (!json[this.name].push) { //如果为true,代表是数组,如果为false，代表不是数组
                    json[this.name] = [ json[this.name] ];
                }
                json[this.name].push(this.value || ''); //装入到数组
            } else {
                //没有
                json[this.name] = this.value || '';
            }
        });
        return json
    }
});

