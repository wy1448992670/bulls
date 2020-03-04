var comm={
	formatFloat:function(val, digit) {
		//处理JS计算精度有误差问题
		var m = Math.pow(10, 3);

	    return (parseInt(val * m, 10) / m).toFixed(2);
	},
	delPic:function(self){

			$(self).parent().siblings(".upimg-view").find("div>img").attr("src","");
			$(self).parent().siblings(".upimg-view").find("input[type='file']").attr("data-url","");

	},
	imgNotFind:function(){
		var img=event.srcElement;
		img.src="http://resource.0elem.com/resource/public/images/default.jpg";
		img.onerror=null;// 控制不要一直跳动
	},
	//[确定]只是提示，不阻塞,无回调
	zeroAlert:function(msg){
		var confirm = $.scojs_confirm({
				content: msg,
				isAlert:true,
				action: function() {
					this.destroy();
				}
		});
		confirm.show();
	},
	//[确定]阻塞，有回调
	zeroAlertBack:function(msg,fn){
		var confirm = $.scojs_confirm({
				content: msg,
				isAlert:true,
				action: function() {
					this.destroy();
					fn();
				}
		});
		confirm.show();
	},
	//confirm [确定][取消]提示弹窗
	zeroConfirm:function(msg,fn){
		var confirm = $.scojs_confirm({
				content: msg,
				action: function() {
					this.destroy();
					fn();
				}
		});
		confirm.show();
	},
	//倒计时跳转弹窗mcl-2016-10-20
	timeLocation:function (TIME,URL,STR) {
		var num=TIME;
		var str=STR;
		if (URL) {
			var confirm = $.scojs_confirm({
				content: str+"<br/>浏览器将在<span id='timeLocationNum'></span>秒后跳转",
				isAlert:true,
				action: function() {
					if (URL) {
						window.location.href=URL;
					} else {
						confirm.destroy();
					}
					this.destroy();
				}
			});
		} else {
			var confirm = $.scojs_confirm({
				content: str+"<br/>窗口将在<span id='timeLocationNum'></span>秒后自动关闭",
				isAlert:true,
				action: function() {
					if (URL) {
						window.location.href=URL;
					} else {
						confirm.destroy();
					}
					this.destroy();
				}
			});
		}
		confirm.show();
		$("#timeLocationNum").text(num);
		out();
		function out() {
			zeroTime=setTimeout(function () {
				$("#timeLocationNum").text(num);
				if (num<=0) {
					clearTimeout(zeroTime);
					if (URL) {
						window.location.href=URL;
					} else {
						confirm.destroy();
					}
				}
				out();
			},1000)
			num--;
		}
	},
	//打开新页面mcl-2016-10-20
	timeLocationOpen:function (URL,STR) {
		var str=STR;
		out();
		function out () {
			var confirm = $.scojs_confirm({
				content: str,
				isAlert:true,
				action: function() {
					//fn();
					window.open(URL);
					this.destroy();
				}
			});
			confirm.show();
		}
	},
	loginModel:function(){
		/*登录模拟框*/
		if($('#loginModal').length<=0){
			$("body").append('<div class="loginModel"></div>');
			$(".loginModel").load("/web/html/include/loginModel.html",function(){
				$('#loginModal').modal('show');
				var url=window.location.href;
				var hidereturnUrl="<input type='hidden' id='returnUrl'  value='"+url+"'/>";
				$('#loginModal').append(hidereturnUrl);
			});
		}else{
			$('#loginModal').modal('show');
		}
	},
	qualificationModel:function(option){
		option.btnText = option.btnText?option.btnText:'确定';
		option.msg = option.msg?option.msg:'提示信息';
		option.url = option.url?option.url:'javascript:;';
		/*认证模拟框*/
		if($('#qualificationModel').length<=0){
			$("body").append('<div class="qualificationModel"></div>');
			$(".qualificationModel").load("/web/html/include/qualificationModel.html",function(){
				$("#tipContent").html(option.msg);
				$("#gotoUrl").attr('href',option.url).text(option.btnText);
				$('#qualificationModel').modal('show');
			});
		}else{
			$("#tipContent").html(option.msg);
			$("#gotoUrl").attr('href',option.url).text(option.btnText);
			$('#qualificationModel').modal('show');
		}
	},
	fixedImg:"http://resource.0elem.com/resource/img/",
	feachDate:function(urls,type,data,callback){
		/*同步请求*/
		$.ajax({
			url:urls,
			type: type,
			data:data,
			cache : false,
			async : false,
			dataType:'json',
			success:callback
		});
	},
	zeroAjax:function(urls,type,data,callback){
		/*同步请求*/
		$.ajax({
			url:urls,
			type: type,
			data:data,
			cache : false,
			async : false,
			dataType:'json',
			success:callback

		});
	},
	asyncAjax:function(urls,type,data,callback){
		/*异步请求*/
		$.ajax({
			url:urls,
			type: type,
			data:data,
			cache : false,
			dataType:'json',
			success:callback
		});
	},
	getUrlParam:function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r!=null) return unescape(r[2]); return ''; //返回参数值
	},
	getQueryString:function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if ( r != null ){
	       return decodeURI(r[2]);
	    }else{
	       return null;
	    }
	 },
	isEmpty:function(data){
		var data = $.trim(data);
		return (data == "" || data == undefined || data == null) ? true
				: false;
	},
	getServiceTime:function(){
		var serviceTime = (new Date()).getTime();
		comm.asyncAjax('/web/front/page/getServerDate','get',null,function(data){
			serviceTime = data;
		})
		return serviceTime;
	},
	//验证号码是1座机号、2手机号、0不合法
	checkTel:function(val){
	    var isTel = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	    var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	    if(isTel.test(val)){
	        return 1;
	    }else if(isMob.test(val)){
	    	return 2;
	    }else{
	        return 0;
	    }
    },
    //毫秒时间格式化 Ex:'yyyy年MM月dd日 HH:mm:ss'
    timeFormat:function(time, format){
    	if(time!=''&&time!=undefined&&time!=null){
		var t = new Date(time);
		var tf = function(i){return (i < 10 ? '0' : '') + i};
		return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
			switch(a){
				case 'yyyy':
					return tf(t.getFullYear());
					break;
				case 'MM':
					return tf(t.getMonth() + 1);
					break;
				case 'mm':
					return tf(t.getMinutes());
					break;
				case 'dd':
					return tf(t.getDate());
					break;
				case 'HH':
					return tf(t.getHours());
					break;
				case 'ss':
					return tf(t.getSeconds());
					break;
			};
		});
		}else{
			return null;
		}
	},
	isLowIE:function(){
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") {
			return true;
		}else if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0") {
			return true;
		}else{
			return false;
		}
	},
	previewPrint:function (oper){
		if (oper<10){
			bdhtml=window.document.body.innerHTML;//获取当前页的html代码
			sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域
			eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域
			prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html
			prnhtmlprnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
			window.document.body.innerHTML=prnhtml;
			window.print();
			window.document.body.innerHTML=bdhtml;
		} else {
			window.print();
		}
	},
	//上传图片-,>本地预览
	PreviewImage:function(obj, imgPreviewId, divPreviewId, imgLookId) {

		var allowExtention = ".jpg,.png";
		//,允许上传文件的后缀名
		var extention = obj.value.substring(obj.value.lastIndexOf(".") + 1).toLowerCase();
		var browserVersion = window.navigator.userAgent.toUpperCase();
		if (allowExtention.indexOf(extention) > -1) {
			if (obj.files) {
				//兼容chrome、火狐等，HTML5获取路径
				if ( typeof FileReader !== "undefined") {
					var reader = new FileReader();
					reader.onload = function(e) {
						document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
						if(!imgLookId==''){
							$("#"+imgLookId).attr("href", e.target.result);
							$(document.getElementById(imgLookId)).fancybox({
								helpers: {
									title : {type : 'outside'},
									overlay : {speedOut : 0}
								}
							});
						}
					}
					reader.readAsDataURL(obj.files[0]);
				} else if (browserVersion.indexOf("SAFARI") > -1) {
					alert("暂时不支持Safari浏览器!");
				}
				//图片窗口查看

			}else if (browserVersion.indexOf("MSIE") > -1) {
					obj.select();
					var newPreview = document.getElementById(divPreviewId + "New");
					if (newPreview == null) {
						newPreview = document.createElement("div");
						newPreview.setAttribute("id", divPreviewId + "New");
						newPreview.style.width = '100%';
						newPreview.style.height = 150;
						newPreview.style.border = "solid 1px #d2e2e2";
					}
					newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
					var tempDivPreview = document.getElementById(divPreviewId);
					tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
					tempDivPreview.style.display = "none";


			} else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
				var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
				if (firefoxVersion < 7) {//firefox7以下版本
					document.getElementById(imgPreviewId).setAttribute("src", obj.files[0].getAsDataURL());
				} else {//firefox7.0+
					document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(obj.files[0]));
				}
			} else {
				document.getElementById(divPreviewId).setAttribute("src", obj.value);

			}
		} else {
			comm.zeroAlert("仅支持" + allowExtention + "为后缀名的文件!");
			obj.value = "";
			//清空选中文件
			if (browserVersion.indexOf("MSIE") > -1) {
				obj.select();
				document.selection.clear();
			}
			obj.outerHTML = obj.outerHTML;
		}
	},
	select_all:function(names){
   var s="";
    $("input[name="+names+"]").prop("checked", true);
	var ss=$("input[name="+names+"]").length;
	var b=$("input[name="+names+"]");
	    for(var i=1;i<ss;i++){
	    	s+=$("input[name="+names+"]").eq(i).val()+",";
	    }
	return s;
	},
	select_other:function(names){
	    jQuery.each($("input[name="+names+"]"), function(i, n){
	    n.checked = !n.checked;
	    });
	},
	unselect_all:function(names){
	    $("input[name="+names+"]").prop("checked", false);
	},
	//3个参数: 当前dom对象, 服务器时间, 倒计时结束时间, 结束回调
	//comm.timeContDowm($("#ccd1"),'2016/08/04 16:23:10','2016/08/04 19:10:10');
	timeContDowm:function(el,serviceTime,overTime,backFn){
		//if(serviceTime!="")
		if(serviceTime!=undefined&&serviceTime!='undefined'&&serviceTime!=''){
			var nowDate = (new Date(serviceTime.replace(/-/g,"/"))).getTime(),
				overDate = (new Date(overTime.replace(/-/g,"/"))).getTime(),
				localDate = (new Date()).getTime(),
				timeSpace = nowDate - localDate,
				intDiff = 0,
				oldTime = localDate;
			function timer(){
				var day=0,hour=0,minute=0,second=0;//时间默认值
					intDiff = vidteLocalTime();
				    if(intDiff > 0){
				        day = Math.floor(intDiff / (60 * 60 * 24));
				        hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				        minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
				        second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
				    }else{
				    	if((typeof backFn)=='function'){
				    		clearInterval(setIner)
				    		backFn();
				    	}
				    }
				    if (minute <= 9) minute = '0' + minute;
				    if (second <= 9) second = '0' + second;
				    if(!el.find(".cont-day").length){
					    el.html(
					    	'<span class="cont-day">'+day+'</span>'+'天'+
					    	'<span class="cont-hour">'+hour+'</span>'+'时'+
					    	'<span class="cont-minute">'+minute+'</span>'+'分'+
					    	'<span class="cont-second">'+second+'</span>'+'秒'
					    )
				    }else{
				    	el.find(".cont-day").text(day);
				    	el.find(".cont-hour").text(hour);
				    	el.find(".cont-minute").text(minute);
				    	el.find(".cont-second").text(second);
				    }
			}
			function vidteLocalTime(){
				var nowTime = (new Date()).getTime();
				if( Math.abs(nowTime-oldTime) >3000){
					timeSpace = timeSpace - (nowTime - oldTime) ;
				}
				oldTime = nowTime;
				return parseInt((overDate - nowTime - timeSpace)/1000);
			}
			var setIner = null;
			if(vidteLocalTime()>0){
				setIner = window.setInterval(function(){
				    timer();
			    }, 1000);
			}
			timer();
		    return setIner;
		}else{
			if((typeof backFn)=='function'){
	    		backFn();
	    	}
		}
	},
	backDivTop:function(el){
		$('html,body').animate({'scrollTop':el.offset().top-100},300);
	},
	binarySearch:function(data, dest){
	    var h = data.length - 1,
	        l = 0;
	    while(l <= h){
	        var m = Math.floor((h + l) / 2);
	        if(data[m] == dest){
	            return m;
	        }
	        if(dest > data[m]){
	            l = m + 1;
	        }else{
	            h = m - 1;
	        }
	    }
	    return false;
	},
	sortCompare:function(keyName,sortNum) {
		/*数组对象排序,keyName:排序依据属性名,sortNum:1位倒序-1为正序*/
		return function (object1, object2) {
			var value1 = object1[keyName];
			var value2 = object2[keyName];
			if (value2 < value1) {
				return -1*sortNum;
			}
			else if (value2 > value1) {
				return 1*sortNum;
			}
			else {
				return 0;
			}
		}
	},
	JSONStingIE8:function(str){
		return str.replace(new RegExp("null","gm"),"")
	},
	zeroLoading:function(msg){
		/*请求等待过程*/
		var loading = {
				loadingTime : null,
				loadingAnimate : {cur:0,ary:['.','..','...','']},
				loadingConfirm : $.scojs_confirm({
					content: "<span class='cmain bold' id='zeroLoading'>"+msg+"<b></b></span>",
					isLoading: true
				})
		}
		loading.loadingConfirm.show();
		loading.loadingTime = setInterval(function(){
			$("#zeroLoading > b").text(loading.loadingAnimate.ary[loading.loadingAnimate.cur]);
			loading.loadingAnimate.cur = loading.loadingAnimate.cur>=loading.loadingAnimate.ary.length-1?0:++loading.loadingAnimate.cur;
	    }, 500);
		return loading;
	},
	zeroTips:function(msg){
		/*2秒,自动关闭提示*/
		var loading = {
				loadingTime : null,
				loadingConfirm : $.scojs_confirm({
					content: "<span class='cmain bold' id='zeroLoading'>"+msg+"<b></b></span>",
					isLoading: true
				})
		}
		loading.loadingConfirm.show();
		loading.loadingTime = setTimeout(function(){
			loading.loadingConfirm.destroy();
			clearTimeout(loading.loadingTime);
	    }, 2000);
		return loading;

	},
	zeroGetUser:function(){
		/*获取当前登录信息*/
		var userData = null;
		comm.zeroAjax('/web/front/login/getUser','post',null,function(data){
			if (data.code == 'GL_S000') {
				userData = data.value;
			}
		});
		return userData;
	},
	// 切换手机模式下 筛选条件
	toggleSearch:function() {
		console.log('toggleSearch');
		$('.search1').toggle(50);
	}
}
//图片上传
var imgUpload={
	 	getLocalFileName:function(el){
	 		if(el.length>0){
				var filePath=el.val();
		        var arr=filePath.split('\\');
		        return arr[arr.length-1];
	       	}else{
	       		return false;
	       	}
		},
		imgFormatGoods:function(str,imgId){
			var allowExtention = ".pdf,.png,.jpg";
			//,允许上传文件的后缀名
			var extention = str.substring(str.lastIndexOf(".") + 1).toLowerCase();
			return (allowExtention.indexOf(extention) > -1)
		},
		imgFormatGoodsx:function(str,imgId){
			var allowExtention = ".png,.jpg";
			//,允许上传文件的后缀名
			var extention = str.substring(str.lastIndexOf(".") + 1).toLowerCase();
			return (allowExtention.indexOf(extention) > -1)
		},
		uploadImg:function (fileId,index){
			$("#"+fileId).live('change',function(){
				var nowEl = $("#"+fileId);
				      $("#fileimg_a"+index).attr("data-url","");
				      $(this).siblings(".uploadOk").css("display","none");
					comm.PreviewImage($(this)[0],$(this).attr("imgView"),$(this).attr("upimg"),$(this).attr("imgLook"));
					 $("#btnUpload"+index).removeAttr("disabled");

			});
			if(comm.isLowIE()){
				$("#"+$("#"+fileId).attr("imgLook")).hide();
				 $("#btnUpload"+index).removeAttr("disabled");
			}

		},
		newFileUpload:function (fileId,upUrl,callBack){
			$.ajaxFileUpload({
				url:upUrl,
				secureuri:false,
				fileElementId:fileId,
				dataType: 'json',
				success: callBack,
				error: function (data, status, e){
					alert("上传发生异常,请重试");
				}
			});
			return false;
		},
		ajaxFileUpload:function (fileId,upUrl,index){
			$.ajaxFileUpload({
				url:upUrl,
				secureuri:false,
				fileElementId:fileId,
				dataType: 'json',
				success: function (data, status){
					if(data.fileSize!=undefined&&data.fileSize!=''){
						var msg = '<span class="ft12">'+data.fileSize+'</span>';
						comm.zeroAlert(msg);
					}else{
						var fileInput = $("#"+fileId);
						 $("#btnUpload"+index).removeClass("br");
						 $("#btnUpload"+index).next("label").remove();
						fileInput.attr("data-url",data.url).siblings(".uploadOk").show();
						 $("#btnUpload"+index).attr("disabled",true);
					}
				},error: function (data, status, e){
					console.log(data);
					if(data.responseText != null){
						var msg = '<span class="ft12">请选择图片</span>';
						comm.zeroAlert(msg);
					}else{
						//这里处理的是网络异常，返回参数解析异常，DOM操作异常
						var msg = '<span class="cred mr5"></span><span class="ft12">请确认好文件后重试！</span>';
						comm.zeroAlert(msg);
					}
				}
			});
			return false;
		},
				ajaxFile:function (fileId,upUrl,callback){

		$.ajaxFileUpload({
				url:upUrl,
				secureuri:false,
				fileElementId:fileId,
				dataType: 'json',
				success:callback,
				error: function (data, status, e){
					//这里处理的是网络异常，返回参数解析异常，DOM操作异常
					var msg = '<span class="cred mr5"></span><span class="ft12">请确认好文件后重试！</span>';
					comm.zeroAlert(msg);
					callback();
				}
			});
			return false;
		},
		ajaxPdfUpload:function (fileId,upUrl){

			$.ajaxFileUpload({
				url:upUrl,
				secureuri:false,
				fileElementId:fileId,
				dataType: 'json',
				success: function (data, status){
					comm.zeroAlert("ok");
				},error: function (data, status, e){
					//这里处理的是网络异常，返回参数解析异常，DOM操作异常
				comm.zeroAlert("上传发生异常");
				}
			});
			return false;
		},
		delImg:function (fileId){
			var nowEl = $("#"+fileId),
				upEl=$('[ctrl-dom="goodsImg"][for-file='+fileId+']');
			upEl.attr("disabled",false);
			nowEl.attr({"data-url":"","picname":''}).val('');
			nowEl.after(nowEl.clone(true).val(''));
			nowEl.remove();
			$("#"+nowEl.attr("upimg")+"New").remove();
			$("#"+nowEl.attr("upimg")).siblings(".uploadOk").hide();
			var defualtSrc = "http://resource.0elem.com/resource/public/images/blank.gif";
			$("#"+nowEl.attr("upimg")).show().find("img").attr("src",defualtSrc);
		}
}
/*jquery 扩展*/

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options = $.extend({}, options); // clone object since it's unexpected behavior if the expired property were changed
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // NOTE Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};
Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}

	if(/(y+)/.test(format)) {
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
	if(new RegExp("("+ k +")").test(format)) {
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	}
	}
	return format;
	} ;

;(function($, undefined) {
	"use strict";

	var pluginName = 'scojs_modal';

	function Modal(options) {
		this.options = $.extend({}, $.fn[pluginName].defaults, options);
		this.$modal = $(this.options.target).attr('class', 'modal fade').hide();
		var self = this;

		function init() {
			if (self.options.title === '') {
				self.options.title = '&nbsp;';
			}
		};

		init();
	}


	$.extend(Modal.prototype, {
		show: function() {
			var self = this
				,$backdrop;

			if (!this.options.nobackdrop) {
				$backdrop = $('.modal-backdrop');
			}
			if (!this.$modal.length) {
				this.$modal = $('<div class="modal fade" id="' + this.options.target.substr(1) + '"><div class="modal-header"><a class="close" href="#" data-dismiss="modal">×</a><h3>&nbsp;</h3></div><div class="inner"/></div>').appendTo(this.options.appendTo).hide();
			}

			this.$modal.find('.modal-header h3').html(this.options.title);

			if (this.options.cssclass !== undefined) {
				this.$modal.attr('class', 'modal fade ' + this.options.cssclass);
			}

			if (this.options.width !== undefined) {
				this.$modal.width(this.options.width);
			}

			if (this.options.left !== undefined) {
				this.$modal.css({'left': this.options.left});
			}

			if (this.options.height !== undefined) {
				this.$modal.height(this.options.height);
			}

			if (this.options.top !== undefined) {
				this.$modal.css({'top': this.options.top});
			}

			if (this.options.keyboard) {
				this.escape();
			}

			if (!this.options.nobackdrop) {
				if (!$backdrop.length) {
					$backdrop = $('<div class="modal-backdrop fade" />').appendTo(this.options.appendTo);
				}
				$backdrop[0].offsetWidth; // force reflow
				$backdrop.addClass('in');
			}

			this.$modal.off('close.' + pluginName).on('close.' + pluginName, function() {
				self.close.call(self);
			});
			if (this.options.remote !== undefined && this.options.remote != '' && this.options.remote !== '#') {
				var spinner;
				if (typeof Spinner == 'function') {
					spinner = new Spinner({color: '#3d9bce'}).spin(this.$modal[0]);
				}
				this.$modal.find('.inner').load(this.options.remote, function() {
					if (spinner) {
						spinner.stop();
					}
					if (self.options.cache) {
						self.options.content = $(this).html();
						delete self.options.remote;
					}
				});
			} else {
				this.$modal.find('.inner').html(this.options.content);
			}

			this.$modal.show().addClass('in');
			return this;
		}

		,close: function() {
			this.$modal.remove();
			$(document).off('keyup.' + pluginName);
			$('.modal-backdrop').remove();
			this.$modal = null;
			return this;
//			this.$modal.hide().off('.' + pluginName).find('.inner').html('');
//			if (this.options.cssclass !== undefined) {
//				this.$modal.removeClass(this.options.cssclass);
//			}
//			$(document).off('keyup.' + pluginName);
//			$('.modal-backdrop').remove();
//			if (typeof this.options.onClose === 'function') {
//				this.options.onClose.call(this, this.options);
//			}
//			return this;
		}

		,destroy: function() {
			this.$modal.remove();
			$(document).off('keyup.' + pluginName);
			$('.modal-backdrop').remove();
			this.$modal = null;
			return this;
		}

		,escape: function() {
			var self = this;
			$(document).on('keyup.' + pluginName, function(e) {
				if (e.which == 27) {
					self.close();
				}
			});
		}
	});


	$.fn[pluginName] = function(options) {
		return this.each(function() {
			var obj;
			if (!(obj = $.data(this, pluginName))) {
				var  $this = $(this)
					,data = $this.data()
					,opts = $.extend({}, options, data)
					;
				if ($this.attr('href') !== '' && $this.attr('href') != '#') {
					opts.remote = $this.attr('href');
				}
				obj = new Modal(opts);
				$.data(this, pluginName, obj);
			}
			obj.show();
		});
	};


	$[pluginName] = function(options) {
		return new Modal(options);
	};


	$.fn[pluginName].defaults = {
		title: '&nbsp;'		// modal title
		,target: '#modal'	// the modal id. MUST be an id for now.
		,content: ''		// the static modal content (in case it's not loaded via ajax)
		,appendTo: 'body'	// where should the modal be appended to (default to document.body). Added for unit tests, not really needed in real life.
		,cache: false		// should we cache the output of the ajax calls so that next time they're shown from cache?
		,keyboard: false
		,nobackdrop: false
	};


	$(document).on('click.' + pluginName, '[data-trigger="modal"]', function() {
		$(this)[pluginName]();
		if ($(this).is('a')) {
			return false;
		}
	}).on('click.' + pluginName, '[data-dismiss="modal"]', function(e) {
		e.preventDefault();
		$(this).closest('.modal').trigger('close');
	});
})(jQuery);

;(function($, undefined) {
	"use strict";

	var pluginName = 'scojs_confirm';

	function Confirm(options) {
		this.options = $.extend({}, $.fn[pluginName].defaults, options);
		var $modal = $(this.options.target);
		if (!$modal.length) {
			var confirmText = this.options.yesText?this.options.yesText:'确定',
				cancelText = this.options.cancelText?this.options.cancelText:'取消';
			if(this.options.isAlert){
				$modal = $('<div class="modal" id="' + this.options.target.substr(1) + '"><div class="modal-body inner"/><div class="modal-footer"><a href="#" class="btn btn-main mr10" data-action="1">'+confirmText+'</a></div></div>').appendTo(this.options.appendTo).hide();
			}else if(this.options.isLoading){
				$modal = $('<div class="modal" id="' + this.options.target.substr(1) + '"><div class="modal-body inner"/></div>').appendTo(this.options.appendTo).hide();
			}else{
				$modal = $('<div class="modal" id="' + this.options.target.substr(1) + '"><div class="modal-body inner"/><div class="modal-footer"><a href="#" class="btn btn-main mr10" data-action="1">'+confirmText+'</a><a class="btn cancel" href="#" data-dismiss="modal">'+cancelText+'</a></div></div>').appendTo(this.options.appendTo).hide();
			}

			if (typeof this.options.action == 'function') {
				var self = this;
				$modal.find('[data-action]').attr('href', '#').on('click.' + pluginName, function(e) {
					e.preventDefault();
					self.options.action.call(self);
					if($("#confirm_modal").length>0)
						self.close();
				});
			} else if (typeof this.options.action == 'string') {
				$modal.find('[data-action]').attr('href', this.options.action);
			}
		}
		this.scomodal = $.scojs_modal(this.options);
	}

	$.extend(Confirm.prototype, {
		show: function() {
			this.scomodal.show();
			return this;
		}

		,close: function() {
			this.scomodal.close();
			return this;
		}

		,destroy: function() {
			this.scomodal.destroy();
			return this;
		}
	});


	$.fn[pluginName] = function(options) {
		return this.each(function() {
			var obj;
			if (!(obj = $.data(this, pluginName))) {
				var $this = $(this)
					,data = $this.data()
					,title = $this.attr('title') || data.title
					,opts = $.extend({}, $.fn[pluginName].defaults, options, data)
					;
				if (!title) {
					title = 'this';
				}
				opts.content = opts.content.replace(':title', title);
				if (!opts.action) {
					opts.action = $this.attr('href');
				} else if (typeof window[opts.action] == 'function') {
					opts.action = window[opts.action];
				}
				obj = new Confirm(opts);
				$.data(this, pluginName, obj);
			}
			obj.show();
		});
	};

	$[pluginName] = function(options) {
		return new Confirm(options);
	};

	$.fn[pluginName].defaults = {
		content: '服务器正在积极抢修，请稍后重试'
		,cssclass: 'confirm_modal'
		,target: '#confirm_modal'	// this must be an id. This is a limitation for now, @todo should be fixed
		,appendTo: 'body'	// where should the modal be appended to (default to document.body). Added for unit tests, not really needed in real life.
	};

	$(document).on('click.' + pluginName, '[data-trigger="confirm"]', function() {
		$(this)[pluginName]();
		return false;
	});

})(jQuery);
