String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
function send(event, pageCount) {
	if (event.keyCode == 13) {		
		checkPageIndex(pageCount);
	}
}
function checkPageIndex(pageCount) {
	
	var pageIndex = document.getElementById("pageIndex").value;
	if (pageIndex != null && pageIndex != '') {
		pageIndex = pageIndex.trim();
		reg = /^[-]?\d*$/gi;
		if (!reg.test(pageIndex)) {
			alert("页码只能是合法的数字!");
			document.getElementById("pageIndex").value = 1;
			return false;
		}
		if (parseInt(pageIndex) <= 0
				|| parseInt(pageIndex) > parseInt(pageCount)) {
			alert("页码范围只能在1~" + pageCount + "之间!");
			document.getElementById("pageIndex").value = 1;
			return false;
		}
	} else {
		document.getElementById("pageIndex").value = 1;
		return false;
	}
	return true;
}

//返回上一页
function toBack(url){
	if($.browser.msie || $.browser.mozilla) { 
		history.back();return false;
	}else { 
		window.location.href=url;
	} 
}
//实现分页
function toPage(pageIndex,status){
	var flag = true;
	var totalPage = document.getElementById("totalPage").value;
	if(totalPage==""){
		document.getElementById("pageIndex").value = pageIndex;
	}else{		
		if(status != null){		
			document.getElementById("pageIndex").value = document.getElementById("goPage").value;
			if(checkPageIndex(totalPage)){
				document.getElementById("pageIndex").value = pageIndex;
			}else{
				document.getElementById("goPage").value = 1;
				return false;
				flag=false;
			}
		}else{
			if(pageIndex<1 || pageIndex>totalPage){
				return false;
				flag=false;
			}else{
				document.getElementById("pageIndex").value = pageIndex;
			}
		}
	}
	return flag;
}

//所有基础数据的分页查询 
function dataQuery(pageIndex,status){	
	if(toPage(pageIndex,status)){
		toQuery();
	}
}

//带操作提示的查询
function toWaitQuery(){
	 $("body").mask("正在查询，请稍后...");
	 toQuery();
	 return;
	 $("body").unmask();
}
//不带操作提示的查询
function toQuery(){
	 var url = $("#searchFrom").attr("action");
	 var formObj = document.getElementById("searchFrom");
	 with (formObj) {
		action = url;
		method = "get";
		formObj.submit();
		action = "";
	 }
}

//所有基础数据的分页查询 
function supervisoryDataQuery(pageIndex,status){
	if(checkSupervisoryTime()){
		if(toPage(pageIndex,status)){
			toWaitQuery();
		}
	}
}

