<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.goochou.p2b.constant.client.ClientConstants" %>
<%@ page import="com.goochou.p2b.constant.Constants" %>
<%@ page import="com.goochou.p2b.constant.TestEnum" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String httpScheme="https";
    if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
    	httpScheme = "http";
    }
    String basePath2 = httpScheme + "://" + ClientConstants.APP_ROOT;
    request.setAttribute("basePath", basePath2);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no,upgrade-insecure-requests">

<title>奔富牧业用户注册</title>
<!-- <script src="./js/axios.js"></script> -->
<script src="${basePath}/js/jquery-1.10.2.min.js"></script>
<script src="${basePath}/js/vue.js"></script>
<style media="screen">
body{
	  margin: 0 auto;
	  padding: 0;
	  max-width:650px;
}
ul{
  list-style: none;
}
.time{
  color:#999999;
  font-size: 14px;
	float: right;
}


#app{
	background-size: 100% 100% !important;
	position: absolute;
    width: 100%;
    height: 13.5rem;
	max-width: 650px;
}
.iptTxt{
	 width: 100%;
	 height: 44px;
	 border:1px solid #EEEEEE;
	 border-radius: 10px;
	 text-indent: 1em;
	 width: 100%;
	 font-size: 16px;
	 box-sizing: border-box;
}
.register{
		margin: 0 20px;
	    background: #FFFFFF;
	    padding: 20px;
		padding-top: 0;
		border-radius: 10px;
		position: absolute;
		width: 6.6rem;
		box-sizing: border-box;
		bottom: 0.95rem;
}
.icode2{
	background: gray;
    height: 44px;
    background-size: contain;
    background-repeat: no-repeat;
    width: 108.5px;
    border: 0;
    color: #FFFFFF;
    font-size: 16px;
    outline: none;
    position: absolute;
		right: 0;
}
.icode{
	background: #F4C05F;
    height: 44px;
    background-size: contain;
    background-repeat: no-repeat;
    width: 108.5px;
    border: 0;
    color: #FFFFFF;
    font-size: 16px;
    outline: none;
    position: absolute;
	border-radius: .16rem;
	right: 0;
}
.btn1{
	background: #F4C05F;
	background-size: 100%;
	height: .8rem;
	line-height: .8rem;
	text-align: center;
	font-size: .36rem;
	color: #FFFF;
	border-radius:.4rem;
}

.iframeTitle{
	position: fixed;
	top:0;
	z-index: 3;
	height: 44px;
	line-height: 44px;
	text-align: center;
	width: 100%;
	background: #FFFFFF;
}
#iframe{
	position: absolute;
    top: 44px;
    background: white;
    z-index: 2;
    height: 200%;
	border:0;
}
.back{
	width: 20px;
    position: absolute;
    left: 10px;
    top: 0;
    bottom: 0;
    margin: auto;
}
#mt {
    z-index: 1;
    position: fixed;
    width: 100%;
    height: 100%;
    background: #000000;
    opacity: 0.5;
    display: none;
}
#close {
    display: none;
    position: fixed;
    top: 30%;
    z-index: 1;
    margin: 0 27px;
    padding: 14px 18px 25px 18px;
    color: #333333;
    background-size: cover;
    font-size: 12px;
    border-radius: 16px;
    line-height: 20px;
    background: #FFFFFF;
    background-repeat: no-repeat;
    max-width: 600px;
    min-width: 88%;
    box-sizing: border-box;
}
#registerSuccess {
    display: none;
    position: fixed;
    top: 30%;
    z-index: 1;
    margin: 0 27px;
    padding: 14px 18px 25px 18px;
    color: #333333 !important;
    background-size: cover;
    font-size: 12px;
    border-radius: 16px;
    line-height: 20px;
    background: #FFFFFF;
    background-repeat: no-repeat;
    max-width: 600px;
	width:6rem;
	text-align: center;
}
#errorShow {
    display: none;
    position: fixed;
    top: 30%;
    z-index: 1;
    margin: 0 27px;
    padding: 14px 18px 25px 18px;
    color: #333333 !important;
    background-size: cover;
    font-size: 12px;
    border-radius: 16px;
    line-height: 20px;
    background: #FFFFFF;
    background-repeat: no-repeat;
    max-width: 600px;
	width:6rem;
	text-align: center;
}
.rul{
	background: #F4C05F;
	color: #FFFFFF;
	position: absolute;
	top: .3rem;
	font-size: .26rem;
	padding: .08rem .2rem;
	/* line-height: .4rem; */
	text-align: center;
    border-radius: .2rem 0px 0px .2rem;
    right: 0;
}
</style>
</head>
<body>
  <div id="app" :style="{background: 'url('+backgroundUrl+') no-repeat'}">
	  <div class="rul" onclick="javascipt:document.getElementById('close').style.display='block';document.getElementById('mt').style.display='block'" >活动规则</div>

		<div id="mt"> </div>
		<div id="close">
				<div class="" style="font-size:.29rem;text-align: center;">
				  活动规则
				  <img onclick="javascipt:document.getElementById('close').style.display='none';document.getElementById('mt').style.display='none'" style="width:17px;float: right;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAEqElEQVRYR82YXYgbVRTH/2cm2WzFSoJfmPWhittqu4tfWEGwj4L162mhUMHsNrnZtI26IFVchAEt4uIHNN1mc5OyK1go5MmvCj7WF1FUtLvVsqAF2YBfZBRXdzuZOXLLTJgdJ5tJ3RbzEBLmzDm/c+455557CT18xsbGNuu6PgxgKxGlmflaAP2uihUi+g3AEjMv9vf3z5dKpT+iqqcogvl8fpCZH2LmISLSmNkB8DOAn5h5VekgogSAGwHc4MkQ0TwRfVypVBa72VkXRAhxHYA9AIaZuUVEXxHR56urq+fm5uZWwpRnMpn+RCKxzXGcnQDuIqIYgDMATkopf+0E1BFkfHx8p+M4ewEkmPmTWCx2qlwuN7t55n9eKBRSrVZrNxE9CGBV07QTMzMzn4XpCAXJ5XKPEtFjAExd1yvlcvn7XgCCsoVC4VbbtvMAksz8frVa/SAo8y+QbDa7W9O0J5j5vGVZ5bm5OfO/QHjvZjKZZDweLxDRFsdx3q3Vaqf8eteAuMuxT2W9aZpH6vX6hY2A8HSMjIz0pVKpZwDcpmnacf8ytUHcxHwJwJ8ADksp/9pICE+XEOIqAJMArgbwspfAfpCDAIZ0XZ8K5oRhGJphGKpke/qo9xYWFmLByLo5cwjAvJTy6MXyV19un3iOmU9Xq9UTfmsjIyN6KpV6VeXMwMCANAyjFYXGXYYigJvS6fQLwfdyudxeItpFRK+rPnMRRAhxgJm3W5Y1GZacQohnAdzBzPMDAwPlbjAKIplMHiSibaqHeF6HlPYrRHRWSjlNbtueIqIvpJS1MG993m3tBuPKHgBwezdZIUSWme+1bfsQ5XK5B4joKSI6VqlUvu4U9igGXJn9UaOXz+fvZOb9zPy2AskAuN+yrIlObTtQfqGGhBBxpZSItneLhK+39Mfj8beI6FMSQrwIoE9KaURNwmQyqRpT22Cj0VC5VgCwIyqEr5wNtXGqiLwB4Hy1Wi1FAXGTO+43TEQKpGcIpSuXy6nK2qJAppn5y1qtdjwqSBBG/e81Ep6tbDa7j4juuWQQwzBiS0tLaomGNgRECPEmM//Qy9IEIBZc73YAUL/LUkoranTbS9NrsoZAlNPpNDcaDVVNPcMIIdrJGrl8/RDMfFb1Hs979cyDUc9M0yx3273VNNcuX6+hOY4zXavVvukU0kAkvm02m8eChvwwAEJl/PrXNLRisXjNysrKa+u1+ADEd81mc7qTtwGYdWXXtPiIm54aEdQAfc40zaPdQh6AWZBSHglG2p3YDrc3vW5jgKt0CsCP60UiaMh9b5yZbzZNc7Jer9t+mdAxwI1Kx8GoWCwmSqWSGhs5all6cmFDVTabvUXTtOeZecFrG1d8VJyYmNi0vLysRsXNoaOi8uBKDM/JZPJpIhrsODz7dsNHADx+OY8TAN6TUn7oX+b/7wHLo1TLZNv2k0TUB+C0rusfXcqR07bthwHsYuYLuq6/09OR04MZHR29PhaL7VE7bK+HcGa+j5nvVodwNSK0Wq2Ts7Ozv3Squst2LeGW+pkNuZYI0qvtwLKsYdu2B72LGiLa5M4jf6uLGmZu6Lq+6DiOOjz9HrXv/AMqEVb6mUdcWQAAAABJRU5ErkJggg==">
				</div>
				<div v-html="vhtml"></div>
		 </div>
		 <div id="registerSuccess">
				<div class="" style="font-size:.36rem;text-align: center;">
				  
				</div>
				<div style="color:#333333;" v-html="vhtmlSuccess"></div>
					<div style="margin-top:19px;" onclick="javascript:window.location.href=VueObj.redirectUrl+'?token='+ VueObj.userToken+'&qd=1';" class="btn1">
					确认
				</div>
				<input type="hidden" v-model="userToken" />
		 </div>
		 <div id="errorShow">

		 	<div style="text-align:right;">
		 		<img @click="closeToApp()" style="height:.5rem;width:.5rem;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAiCAYAAAA6RwvCAAAElklEQVRYR82YX2hbZRTAz/nuTYjFPwlTX1qEYZs5p4Lg+iAiefDJP3sLCHswTe69devAP+DGUCEgOjZRka7/vpukAa0KeZu6F32I+NYJwuasa4eK0D7oRuKErsSb78iJufX2Lre52RowbyEn5/zOn++c830IPXyy2ewdiPiIEGIEEQeJaBcA3NZWcR0RrxLRqlJqZWNj48LCwsK1sOoxjGA2m92j6/pTALAPADQAaALA7wBwRSl1nXUIIRjobgC4l2WISBHRD0qpr0ul0qVudrYFGRsbu0fX9ecR8SEichDxe6XUouM4y+VyeaOT8kwmE9N1PSmEGCWiRxFRZyBE/FRKeSUIKBAkl8uNapp2kIiiiPhto9E4Wy6X69088/6eyWTikUjkGQB4AhEbRPSxbdvnOunoCGKa5gFEfBoAriqlCoVC4ZdeAPyyhmHsFkIYALCLiM7atn3GL3MDiGVZ7MEBIlpBxGkp5fqtQLj/tSxrQCk1IYQYBoAzUsovvXq3gJimuR8Rc0R0GRE/lFL+vRMQHpiIUuplIcT9RFT0pmkTxLIsrvg3lVJ/CSHe2alI+B3hyADA6wBwOwC85RawF+QIEe0jolP+mkin01qlUuEj2+tHZDKZqP+Ecc0g4lFEvCilPM1KWyC5XG5Y07TXAOAbKeUnXmsMkUgkTgDAb8vLy7PVatUJQ5NOp6PxePwIAAzW6/WjfkdM0zyIiE82m813i8Xi5RaIYRhcRHs5ZFLKPzuE8xUAeID7wcrKykw3GBcCEfcAwHkp5VQHnXcBwNtKqaVCoTCF3LZ1XT9JRN/Ztl3q5K1XcTcYy7IiADABAHu7yZqmmQWA/evr68fQNM3HEfEFpdRUoVA4HxR2hkkkEmwgMDIM0T6iXSHameC5NeE4zkcMkkHE0Uaj8WpQ2/YevyBvGYKIDiPig90i4erjcRCNRt8nokUGeYPngZQyH6YIOxlMpVKwtrZ2mIdiWAiPc/nWHDNN8z0i+pkLJgwIy7Tr4JBruHX8/h2MoYrZa8eyLE73bgZhgHO2bZfDgrBcPp/XV1dXDzEAf78ZiHadjCHiYzcNkkql9JGRkS0gg4ODM/l8PlSfcZ02DGMTpOfUeCE4Em5qlFIXhRAzvcwob2p6KlYvBBseGhqarlar4EaHiH5sT+1QA9OyrM1iDX18fZHYYtAHuCSEmOoWGf/xDdXQtoNw8+2rm6VarTZdqVQaQYfAMIz/GlqYFu83AACB3vqAL9Xr9dNBMG6Lj8Vix8IOPZ6iDxPRtooDIvOTlPKDrkMvzBoQj8dPAcDqdt75DXFkksnkiwBwX61WOx5qDWh3y9ZipGnaydnZ2V+9irmoyuUy51r10vRYttNSFbgYtUFaqyIRXUPEE/1cFYnoOCLe2XFVbKeI7zLZfi7PRPQSIg43m81SsVhcdCN8w3VifHz8OSJ6tp/XCUT8Ym5u7nNvmv+/FyyXsh9XzmazueBNR9eIuAI7eQl3HOez+fn5P4JOXV+fJfje4jjOV7f8LOGndx9qEDHJDzUAkACAWFuOnylq/FBDRMsDAwMXJicnQz/U/ANsxFAATYS5agAAAABJRU5ErkJggg==" />
			</div>
				<div class="" style="font-size:.36rem;text-align: center;">
				  <div>您已是奔富牧业用户</div>
				  <div>可直接前往下载APP</div>
				</div>
				<div style="color:#FFF0C4;"></div>
					<div style="margin-top:19px;" onclick="javascript:window.location.href='https://wap.bfmuchang.com/downLoad.html'" class="btn1">
					立即下载
				</div>
				<input type="hidden" v-model="userToken" />
		 </div>
		 
	
				<div id="top2" class="register">
						<div class="">
							<input @blur="getCaptImg()" v-model="form.phone" class="iptTxt" type="text" placeholder="请输入手机号" maxlength="11" />
						</div>
						<div class="mt48" style="position:relative;margin-top:10px;display: grid;">
		          <input v-model="form.vCode" style="width:60%" maxlength="6" class="iptTxt" placeholder="请输入验证码" type="text"  />
		          <input v-model="icodeTxt" v-on:click="getIcode()" :class="[timeFlag?'icode':'icode2']" type="button" name="">
		        </div>
						
						<div style="margin-top:19px;" @click="goRegister()"   class="btn1">
							{{buttonText}}
							<!-- <img src="./images/reg5.png" width="100%" /> -->
						</div>
					</div>
					<div style="text-align: center;position: absolute;left: 0;right: 0;bottom: 0;">
						<img style="height:.36rem;width:4.56rem;" src="data:image/png;base64,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" />
  					</div>
  </div>

  <c:if test="${channel.tongjiType==1}">
	  <script>
          var _hmt = _hmt || [];
          (function() {
              var hm = document.createElement("script");
              hm.src = "https://hm.baidu.com/hm.js?${channel.tongjiKey}";
              var s = document.getElementsByTagName("script")[0];
              s.parentNode.insertBefore(hm, s);
          })();
	  </script>
  </c:if>
</body>
<script>
    var VueObj = new Vue({
      el: '#app',
      data: {
			form: {
					phone: "",
					vCode: "",
					inviteCode:"",
					appVersion: '1.0.0',
					client: "WAP",
					channelNo: "",
					reqStep: 1,
				},
				userToken: "",
				rule: "",
				isShowiFrame: false,
				time: 60,
				icodeTxt: "获取验证码",
				timeFlag: true,
				isDisabled:true,
				isAgree: false,
				buttonText:"",
				backgroundUrl:"",
				vhtmlSuccess: "",
				vhtml: "",
				redirectUrl: ""
				// CapyImg_i: 0,
				// CaptImg: "https://app.bfmuchang.com/captcha4j/getCaptImg?phone=&appVersion=1.0.0&client=WAP", //验证码
      },
      created:function(){

		  		this.backgroundUrl = "${channel.topImagePath}";
		  		this.buttonText="${channel.buttonText}";
				this.vhtml ="${channel.guizeText}";
				this.form.channelNo="${channel.channelNo}";
				this.vhtmlSuccess="${channel.successText}";
				var inviteCode = GetQueryString("inviteCode");

				this.redirectUrl="${channel.redirectUrl}";

				var _this = this;
				// token="b506fef1e9f40b66e3ccc648629883ffb925fa5923483560773582d60c5e265e";
				/*$.get("https://app.bfmuchang.com/user/getInviteRecordTotalAmount",{inviteCode:inviteCode,appVersion:1,client:"WAP"},function(res){
					console.log(res);
					if(res.code==="1"){
						if(res.data!=0){
							// _this.num = "您的小伙伴已赚"+res.data+"元，赶快跟上他的步伐";
						}
						 // res.data
					}else{
						console.log(res.msg);
					}
				})*/
				// document.getElementById("top1").style.paddingTop = document.documentElement.clientHeight/9.2+'px'
				// document.getElementById("top2").style.marginTop = document.documentElement.clientHeight/2.887+'px'


				// document.getElementById("app").style.height = document.documentElement.clientHeight+'px'
        // this.getInviteList();
      },
      methods:{
				goRegister:function(i){
					var _this = this;
						console.log(_this.form);
						_this.form.reqStep = 2;
						// return false;
						$.ajax({
				             url: "${basePath}/user/channelRegister",
				             type: "POST",
				             xhrFields: {
				                 withCredentials: true
				             },
							 data: _this.form,
				             crossDomain: true,
				             success: function (res) {
								if(res.code == 1){
									_this.userToken = res.data.token;
									if(_this.vhtmlSuccess){
										console.log(_this.vhtmlSuccess);
										document.getElementById('mt').style.display='block';
										document.getElementById('registerSuccess').style.display='block';
									}else{
										window.location.href = _this.redirectUrl+'?token='+ _this.userToken +'&qd=1';
									}

								}else{
									$toast(res.msg);
								}
							}
					 })
				},
				closeToApp:function(){
			    	document.getElementById('mt').style.display='none';
					document.getElementById('errorShow').style.display='none';
			    },
				getIcode:function(){
					
					var _this = this;
					_this.form.reqStep = 1;
					$.ajax({
			             url: "${basePath}/user/channelRegister",
			             type: "POST",
			             xhrFields: {
			                 withCredentials: true
			             },
						 data: _this.form,
			             crossDomain: true,
			             success: function (res) {
										 if(res.code=="1"){
											 if(_this.timeFlag){
												 _this.timeFlag = false;
												 _this.icodeTxt = _this.time+"秒后重试"
												 let stop = setInterval(()=>{
													 _this.time-=1
													 _this.icodeTxt = _this.time+"秒后重试"
													 // console.log(this.time);
													 if(_this.time<=0){
														 _this.timeFlag = true;
														 _this.time = 60;
														 _this.icodeTxt = "再次发送"
														 clearInterval(stop);
													 }
												 },1000);
											 }
											 _this.isDisabled = false;
										 }else{
											 if(res.msg == "该手机号已被注册，请更换手机号"){
												 document.getElementById('mt').style.display='block';
												 document.getElementById('errorShow').style.display='block';
												 return false;
										     }
										 	 $toast(res.msg);
											 return false;
										 }
			             }
			  });
				},
				// 获取图形验证码
				getCaptImg:function(){
					this.CaptImg = "${basePath}/captcha4j/getCaptImg?phone="+this.form.phone+"&appVersion=1&client=WAP&v="+this.CapyImg_i++;
				},
      }
    })
    function GetQueryString(name) {
    　　　var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
         var r = window.location.search.substr(1).match(reg);
    　　　if (r!=null) return (r[2]);
    　　　return null;
    }
		function $toast(content) {
		var toast = $('<div style="z-index:3;position:fixed;top: 50%; left: 0; bottom: 0; right: 0;margin: auto;width:auto;'
				+ 'text-align:center;color:white;"><div style="font-size: 18px"><span style="padding:11px 20px;border-radius:10px;background:rgba(0,0,0,0.6);">' + content + '</span></div></div>');
		$('body').append(toast);
		setTimeout(function () {
				toast.remove();
		}, 2000);
	}

</script>

<!--rem start-->
<script type="text/javascript">
    new function (){
        var _self = this;
        _self.width = 750;//设置默认最大宽度
        _self.fontSize = 100;//默认字体大小
        _self.widthProportion = function(){var p = (document.body&&document.body.clientWidth||document.getElementsByTagName("html")[0].offsetWidth)/_self.width;return p>1?1:p<0.5?0.5:p;};
        _self.changePage = function(){
            document.getElementsByTagName("html")[0].setAttribute("style","font-size:"+_self.widthProportion()*_self.fontSize+"px !important");
        }
        _self.changePage();
        window.addEventListener('resize',function(){_self.changePage();},false);
    };
</script>
<script type="text/javascript">
    document.body.addEventListener("focusout", () => {
        setTimeout(() => {
            const scrollHeight =
                document.documentElement.scrollTop || document.body.scrollTop || 0;
            window.scrollTo(0, Math.max(scrollHeight - 1, 0));
        }, 100);
    });
</script>
</html>
