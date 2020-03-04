<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营报表</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.table {
	table-layout: fixed;
}

.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}
ul,li{
	list-style:none;
	padding:0;
	margin:0;
}
.operateReport>ul>li{
	float:left;
	width:33%;
}
.displayTable{
	display:table-cell;
}
.vm{
	vertical-align: middle;
}
.operateReportOuter{
   width: 330px;
    background: #FF88C2;
    color: #FFF;
    padding: 20px 32px;
    border-radius: 10px;
}
.pr10{
	padding-right:10px;
}
.mt20{
	margin-top:20px !important;
}
.font{
    float: left;
    font-size: 25px;
    font-weight: bold;
}
 
</style>
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="../common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="page-title">
				<h1>报表统计</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							运营报表统计
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-12" id="form" action="return false;">
	                            <div class="row">
	                            	<div class="displayTable pr10">日期选择:</div>
	                                <div class="displayTable pr10">
	                                    <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
	                                           type="text" placeholder="请选择起始日期"/>
	                                </div>
	                                <div class="displayTable pr10">
	                                	—
	                                </div>
	                                <div class="displayTable pr10">
	                                    <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
	                                           type="text" placeholder="请选择结束日期"/>
	                                </div>
	                                <div class="displayTable vm">
	                                	<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">确定</button>
	                                </div>
	                            </div>
	                            <div class="row operateReport col-xs-12 col-lg-12">
	                            	<ul>
	                            		<li>
	                            			<div class="operateReportOuter">
		                            			<div style="text-align: right;line-height: 50px;"><span class="font investCount"></span>
		                            				<img src="data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAFEAAABOCAYAAABYD/p4AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAyPSURBVHhe7Zz5V1TJFcfzh8R9ARtENhFENgVXcEEQUERR3HdRRxQFF8AZBXd0HEXBfSGAAu4ozmQyLklmJicne3Imk82sk8XJoslNfastff3efb3Qr2mc0z98jna/qvtufd+tqvuqqvka7XpAAbwjIKIFBES0gICIFhAQ0QICIlpAQEQLsFzEj4sb6PqivfS/qk72uj/4T2UHnS/cSd9bd5q97i2WiPjFtht0dcEempucSQMHDaHC5OlsOX8BEccNT6HBg0OoaPQM+ZD/seMWW7YreCXiH8taaf+M9ZQcmUCDBtvsBIXQ4jG5bHl/AREnx4597aMgNSqJjs0qpT+Xt7N1PKFLIv636j6dm7uTEiPihUMh0ilEIMD/e7KIWj/he2LEKDpTsF2W4eq6g8ci/nZLCy1KzXnpxGuntM4tHJPD1vUXUsQRjiJq/QW5CRn0/fVn2fqu8EjE76w9RSmy64YYnFHAoZz4dBmtnA1/wEWiHlwbZougs3N2sDac4baInctrKTI00qkjANcxiHvTPazmy513Xg49rn0HldNWeJRduCXiJ+saKCo0yqUTdmyibDT9urSZteUPfre1haKFT/CN99kRtLNsyhLWFodLEf+y7bqMLE5A9eT034OOZYdZe/7g0eo6kd545j++r85ay9rT41LErZMXC4PGMRA3SY1OouVjZ7HXNqcvZO35g6N5mwxi4fPqcXMoP3GK4ZqWG4v3sTa1OBURGb4teKjBMG6K6Pxs0xX60VsXZBKrL4Mu/dmmRtZud4IJLit+EiOUjT5a9R49r7xHJZOKWCHxXWpUMv1VvExwthVORSxJh3FHgWA4ZmgM/ViIhzLPK+/TtLjxBifweVnaTIPN7ubK/F2sb6PC4+RQpcpViMlEX06VrS8od7Cpx1RE3CAhfCRj2EaN8952KHtWJN5mDpRPXiKftrZ8d/F4zQn5wPW+ITBKM4zDDYYmY1mbDJIXIlj05RWmIsKBICYKM0dOoBe6HPCZSCEmxowxOKDqZIvu1L6oRs6S/xRltXWt5tnO2/SLkit0MGcDhdv4lCw4KJQ+LW4w1P3V5iYpun4WDxsSLm3qyytMRWwp2mNwAE+wOruYLd+x9JBDWS2wA2KGDqcJQuzpIyfKhDwnPsNC0uUDTotOkUkzJx4YLN7tMQZybQBvTZrPtNsmZvgTbHlgKmLj/LflDfXGTszawpYHENjMeTt2MX0Nf2+7/8gonpZdY/0He7LWGGzg860lB9jywFTEqwv5SNw3Yz1bXqEGaH1dfwN/IkIi6NtrTrJ+K7aJJNvYbhs9FrkmVx6YiojpPyjIaGxpqusZ971ZpRQaPEyUN6Y+3Q18hh9IyT4urmf91ZKXMFnW0doICQ6jn2y8yJYHpiI+3XqNhosxbKCDQZv87jdbWtg6Wn6w4RytGJtPoUPCZCPsjel+8LpXlbnSrXVDCDV0yDBNe+0PYXRUIv1t+022DjAVEcxJzpRG9Eb3zljHludAMn4yv4yWipwxfUQaJUWM8ilYZcKKzUrxALHm6c4DV5SJdIxr76pxBWx5hVMR4YTeKMCU707X0IOVHUTEn8rbfMYXIr/Vp2DucFdkF/qUDqD9NxbvZ+sonIoIpxKGGRNufEaIq7eWN50na+ooLiyWbWeGiOp/Vdxl6ymcigjq8rcajKsbxIaNoMvzqvz2RuItEOd0wXbZs8za2Fy0m62rxaWI6IIzTVY68B3AaxHGvSdr62Rmjy6LbtXTQM/6ecllma5gZQdjtL1dfNuWpc1iNdHjUkSAPVt94q1FiYkxBU8VKzg9kpAo6R/WFpXPXHsArj1cdZzVQ49bIl4orHAqohH1dHsanK88ENFVYq6wJBK/ikBEDE+cHnq8EtFVl3gTMGtDt4k4NjpZJrjqpvrrCuWoP+D8AeoaUjW0g7vucxFxE8zIWEt8f8VReZwE63faMgCD+fyUbCoS4N/uZHbSNPnea/ApNIrenbmJPlj5rtxOPSB81wtuuYjcxIKbIIdUZf5d0fFyY/+1M/g/1vn8dUIMqRa2AfQ+YTFCW25f9jqHMqqcpSLiFBV3E6y9qTI4GYaurS2H/0NEra3uBFscnIhjo1McTmiYLcRaKiKOj3BbBWPEeKI2e5yJ2NMiUSvi56VN8s1LWwZgNednJZcMNjncEvHvO26+Oj6nvRE+FyZnyb2TL3feFmXeHBFxHW8v3HYqPmMlyN3XWbdEBFiTM0tzRoTFyJNiWDnWX+tpIgK8vSxJzZP+6q8BrH9i4uFscrgt4uebm6RY3E2xcMs70zNFVL7pv1PfI+3BezZnk8NtEUFz0TuvbqS/OUdPFpED5bCdem9ZLWvPDI9EBMgNcWzEHceUiJyd7oCbnc1AGRyZaVmwh7XlDI9FBNhjTotKljd25iCuYeMHOeQPN5yjS4VVMi2qFONr1TTrgd2a7GJ5dOSnGy/S77e2UmK483OJqg1YFvtw5TG2va7okogAh3zqZ5fTDDG7cW8qihFDY+R6I1Ik6bCYnDBB+Qq1KYaomho7Xi59cX4BTDB4yBfmVsiTE1w73aHLIiqQb+FA54PlR+TEwy05OYsEX8LdF9/FDxtJ3xW5L86fc23yFK9FVDzbcceQJ/ZE4J/KE63CMhG5NxYzBgwM9hnc/bQoEa08mG+ZiPa3GnMR+w8Ioj59B1DffoNkmaDgUMuBXdjv03egqaBKRK4NXcUyET9d12A4PaBAo4ZFDKf8wkVUXllD1YdO0IF3G2j/0XrLgL3qQ8dpy47dlJs/j0JCw6mvuC/nT2RIFP1ys3WneC0TcdEY+w+EtM4iGvr1H0TTc2ZT/cWrdP3+I7re+Zja7z0SPLQeZV/8e6z+Ek3MyBSRaRQSfjo7XucploiIn2hwpwfQgDnzl9K1Ox9SW8dDunr7m90GHtQ3rnfKB4ieoPcNZxhx5pxrj6dYIuKuzFXi6TqKiLEpbXwGNd3opNa732IbCto6PpLRgyiF2FwZPbCnorpV1OfKANi+2HKb4uKTRY8Y7OAf/NUuKnuDJSIi4TZ0ZTGRVNXUyoZyDQRo5PEzjVS6/R2q2H2QLrd2CIHMRQFKmO279sl6py60OI3yG51PZDmjiDa5ksO1x1O8FvHp1quGcyxwOHZkkhDlroguPgoxhlXuOUShYZHUu3d/OXOPnzSNzjffNI1cfH/6ciulpE6gPn0GUC9RLzI6jvbWnjQVEnXONrZTeGSMzBC0IqZFJ8s9Fq5dnuC1iDg2on+1wqyYPiVLCsU1DN328rW7FJ8w2mG86tWrH61at1l2Va4exrl5C1fQ13v1fVWnd5/+cthovvk+OxzgO1wbLYTvJ4YYVQ9vVnjtw/ESrl2e4LWIOMz52jE7ECY7t8BURHRJdMOQ0AiHfA7RmJ03h62nBEqfku0gPKILUXbp2h02grX19ClPREikXJXn2uUJXouIfQichtU6Z4/EbKeR2Nh+j5JGj5PdEnUgJrpn8cZymaJw9fD9ouXFjpEo6mAYaLn1gWkktohITB07SU52qp6KRO0PgrqK1yLiScYOHeFwLBljYtyoZGpsu8c2DEDgvbV1YkyLlaKjTuaMfLrS1mFaB5F2vummEG2q7Jqoh7G3tu6cjG6zOueabsj76MfEMZGJXq3eKLwW8UVlJ01lfpaGyNp94Jjp+AbahJBnrrRRZfVh2nfklBy7uC6pBdcb2+9TzeETtKvmCF0QExHscGUBZueyimp2dsYGP9cmT/FaRLBj6jLhlD5PHCgjxpUwuIZuisg0i0A9KIfyqOfMNqITGUJSyjghorYr2/PEI7klbHs8xRIRcVw3KIh7YxlEC5aulgmxWXfzFRAZ42ReQZHDRKTAOO7sZxWeYImIAF2D69LoRjMLFohu2/7qzQTdD3md5Qi78k3m/mM6eb6ZpmXN1E0mdhCFa8Y7/0WAJ1gmIqIRO2V6hwEmgKjhcVS4cDlVVdfSkbrzdKzhslwksAxhDxNMxe5DVDBvMYVHxLARiAeNv2VhVRQCy0QEh3M3GsZGhVpP1M6QvqC/iHxn94GI+GkJ539XsVRE7C9vmDjPVMjXiAQbSbbVwC57PzsQcFfmStZ3b7BURPC86j6Vv/yRoX6M9BfKD5yh5Hz2FstFVOC0hH3j3L2Nfl+gHiROr91ecpD10wp8JiL4Q1kb1eZtlD8Ut//s4eXes0+x7z1jksOGPH6fjU00zj+r8KmICvzJAvycAX/YDH/9CEvzWMuzCvyBt6VpefLPyGDMw8TxSXGDPHnB+WM13SLiV52AiBYQENECAiJaQEBECwiI6DUP6P+A/CKP/9IVJQAAAABJRU5ErkJggg=="/>
		                            			</div>
		                            			<div>
		                            				<span style="font-weight: bold;">物权交易总头数</span>
		                            				<span style="float: right;"><a style="color:#0000FF;" onclick="yyccheck2()" href="JavaScript:;">点击下载表格</a></span>
		                            			</div>
		                            		</div>	
	                            		</li>
	                            		<li>
	                            			<div class="operateReportOuter" style="background:#00AA55;">
		                            			<div style="text-align: right;line-height: 50px;"><span class="font buyBackCount"></span>
		                            				<img src="data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAFEAAABNCAYAAADem4jWAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAASdSURBVHhe7ZpJaxRBGIYjqAdXcPsB+g/8A4K4H7xFFExc8eRBVES8uIsiIuIC3lRUMJuJS9zRRBEVlySaGLeLSjYX0ARjjFr69XSTryvvpLt6qlfqg4eEt2u6pp+p7qnprqKismJhKBAYGtSAoUENGBrUgKFBDRga1IChQQ0YGtSAoUENGDL2vqwWd7qaxfWOxkxR/6lFLHt0BB6zMjBkkMCs1p6XVfCYlYEhgz61rNaO5gp4zMrAkGEk+gCGDFni7a4XYsvzs2Jbc5nY+iKH8z/9zYfTdqj2he5D3i5nb7rb7aPIVWwSSSBqlwYutz21jyJXsUmkTxe1SwNX2xvso8hVbBLptEDt0oCRqIHESDSnMwCGDDMSfQBDhhmJPoAhw0viiIrFYmL1ytChfni/QUisxBm3t4ovfd0Wn/u+a4f2+7WvR8y8s93VbxASK3F23U57S7g1r363q98gJFbirLod9pZwy0jUUJmWOKF6hXVKh8mcul3WlwvvNwiJkWjmiQAYMoxEH8CQYST6AIYMr2vi1MtrrcwLks//5mN7c7nY1HhajKpc6upHB4kdiXPrd9lb9FXv719iSs1qVz86SOxIDGOKQ79SJtescvWjg8RKpOmH7urp/xmKxFudz+0echXbI1NZ4vQbm8SNjiYLalsoNzubROXHB2L8+WWufnRA0pyFCJE+vKcOeckSDf+BIUOWmOYpTmjAkGFGog9gyPCSqHpTdlLNylDmgLECQ4aXRNWbsj9+94nNTWdc+0g9MGR4SaS7LKq1s0XT/CwpwJDhJTHIZFvbJDcpwJBhJPoAhgwviao3ZekO9bTata59pB4YMmSJWZsnDitbBHMlYMjIssTRVUtFxYcHYs3j43C7b2DIyKpEmqtebHtsHdOfv3/F8kdHYTtfwJDhdU1MI1wgr8A3JGDIyNpIJIGX2p7YRzNQ3f29YsHdPfA1nsCQkaWRSNdAJPDbrx/WzAG9xhcwZGRFIgmU12xTfe8vUCABQ0YWJA4lkJ4RodcoAUNG2q+JoQskYMiIQ+Lw8sLXIhJjqkrElfZn9jsfKPoS0SaQgCEjaokjK5ZYE+CDry/B7X6hEYgEah2BDjBkRHlNJIFlH+7bPQmxv/UCbOdFvhFIAunpJHpNQcCQEZVE+g177v2AQKdURY6tKhXXOtzPl6noFA5FIAFDRpSnMy0fQXXg1UXYXoZGYD6B2k9hDgwZUV8T1z07YffkLi+RNALlFQ5UJFDHAtEhgSEjaolEPpH5vmzGnS8d9D6pIhFIwJAhv7l3PR3WRZtyHdCKhH2t1YP69SsydoEEDBnoDequuq4W2Pf6hpN2C3cdel1rbadrYOwCCRgyopBI63hQ30Q+kcfeXhO17YN/idBiqPn1Ae/GBAWGDFr4E3bd+9QK+3bIJ1KuyEegAwwZJQ8PW8+J6QuFoHmiA8/kbTJOW7k97bv0offN0A0Np2xVuGIZgQ4wTCgbG7FIEhj4hqoOYJhgZJF0Csc2Ah1gmHAckTQCY7kGysAwBdA8cuG9fXBb5MDQoAYMDWrA0KAGDA1qwNCgBgwNasDQoAYMDQoUi39K6tEHn4LJygAAAABJRU5ErkJggg=="/>
		                            			</div>
		                            			<div>
		                            				<span style="font-weight: bold;">已回购总头数</span>
		                            				<span style="float: right;"><a style="color:#0000FF;" onclick="yyccheck2()" href="JavaScript:;">点击下载表格</a></span>
		                            			</div>
		                            		</div>	
	                            		</li>
	                            		<li>
	                            			<div class="operateReportOuter" style="background:#00DDAA;">
		                            			<div style="text-align: right;line-height: 50px;"><span class="font totalAmount"></span>
		                            				<img width="81px" height="77px" src="data:image/jpeg;base64,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"/>
		                            			</div>
		                            			<div>
		                            				<span style="font-weight: bold;">物权交易成功总额（含红包）</span>
		                            				<span style="float: right;"><a style="color:#0000FF;" onclick="yyccheck()" href="JavaScript:;">点击下载表格</a></span>
		                            			</div>
		                            		</div>	
	                            		</li>
	                            	</ul>
	                            </div>
                        	</form>
                        	<table style="width:90%;text-align: center;" class="table table-bordered table-hover">
                        		<tbody>
                        			<tr>
                        				<td>物权新手交易头数</td>
                        				<td width="150px" class="noobNum"></td>
                        				<td>物权30天交易头数</td>
                        				<td width="150px" class="monthNum"></td>
                        				<td>物权90天交易头数</td>
                        				<td width="150px" class="seasonNum"></td>
                        			</tr>
                        			<tr>
                        				<td style="background:#E6E6FA">物权180天交易头数</td>
                        				<td width="150px" class="halfYearNum"></td>
                        				<td style="background:#E6E6FA">物权360天交易头数</td>
                        				<td width="150px" class="yearNum"></td>
                        				<td style="background:#E6E6FA">牧场红包使用成功金额</td>
                        				<td width="150px" class="hongbaoMoney"></td>
                        			</tr>
                        		</tbody>
                        	</table>
                        	<form class="form-inline hidden-xs col-lg-12" id="form1" action="operateReportFeedCount">
	                       	 	<div class="row mt20">
			                   		<div class="col-lg-12">
			                        	<div class="displayTable pr10">日期选择:</div>
			                            <div class="displayTable pr10">
			                                <input class="form-control datepicker" value="${feedTime }" name="feedTime" id="feedTime"
			                                       type="text" placeholder="请选择日期"/>
			                            </div>
			                            <div class="displayTable vm">
			                            	<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search1()">确定</button>
			                            </div>
			                            <div class="displayTable pr10" style="color:#FF0000">
		                                	(注:筛选某一时间点下平台存在饲养期的牛只)
		                                </div>
			                         </div>
	                            </div>
                            </form>
                            <ul class="mt20">
                           		<li>
                           			<div class="operateReportOuter" style="background:#33CCFF;">
                            			<div style="text-align: right;line-height: 50px;"><span class="font feedCount"></span>
                            				<img  src="data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAE0AAABRCAYAAACXHStQAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAO/SURBVHhe7ZpJi9RAFMf9TooKOorbVT+AZ8FtBD+Ay8WjG+JhVERF8eA3UObirswGguACI+O4HnTGcWTEkef7dydQXVY6XS9V3ZX0e/Cjm6SS6vz6VaWSqjVrJ4gUP1SaAJUmQKUJUGkCVJoAlSZApQlQaQJUmgCVJkClCfCWdu0T0eQPoqeLzWJqiejErPuabbylQVhT4yonhOuabbyl4V9palz+6L5mG5VmRN+kTXBzvThPNMYVjn3IyL/js4i8bLfyVc9h77e2za1kF5FF36RBmKtcHXi4kF1EFn2Thn/PVa4OPLaupX/SOM1d5eqAShMwOGnaPMvRTNNM64iBSdswSbR9Jj6ox6xXQjLS9r0iWvzTZiECrXOvEu3nesx6JSQj7eDrbEfkGOV6zHolJCPtgEr7H5UWQdo27qTRRGNyiMHNwKxXwuCk6TitHJWm0jpiYH3a7hftbaWw7I7PAi4x5+eJNk111hOCZDLtMHfSoeP3X6JdATp+m2QyLcaQA08BO5ssDcOB0PGLMy2GtOfWdGTfpvBsaXtfEj3jMgBlq/KML2z8O9HW6c56QgBJ+cR31MliVGCGLW0YCH4jGAY00wQEl+b7EnIHE2MMFpPg0nxfQq7wnfFCzSacg0vDWwjfuFKzJh5cmmRw2+ugMhVUmoDg0nxfQuIN7B5+yDfPkTrVpTVsnLbOsc1GpRls5qHP+DeiU+/c+3NUWgbGivez9Wo8CqKTXZ5Dg/dpdcQUZkbRA/zQZxqEPXAIW+Z0O/LGfcxQZxr6MJewn6vtO7vrGDC00iDMXnOLWC4RBoZSWjdhmONwHWMydH1aVWGgFtLWB1iLBkZY2CPr9yPQ6fcqDCQvbSMLw4Dz1hf3/l5BhjmFeWRYTtJ9GoTdY2F53PjsLldGYYaxMMyeuY7pRrLS8Ax41xCWh6+4kWmiJy5h3CQlwkDSzRPLEVxxs0dxyLAiYb5N0iT5Pu30XFaRFWXikGH2DDoCwqouCExeGigSV3Rz2MLC7N+JCCEMVJY2v9LuZLE9BJjxvu5YHtCruNjCQGVpMWJyyV33mfdZAStuZ+LQh8UWBpKUhnUgrrpBkbg7XznjHSN9LJ4ZLXhbIcVbGhaKxI6ZgkzLKRJnR+gMy/GWdmy2PU+JG0ALfM8xt9n7bPKyVnmc+3gPq3fOloiLkWE53tJS4lyBOAgreoEYglpLA7a4VpOMKAzUXhrIxbWaZIQ+zKYR0gDGcUffuveFpjHS+olKE6DSBKg0ASpNgEoToNIEqDQBKs0bon9g1/Sa1tBNYgAAAABJRU5ErkJggg=="/>
                            			</div>
                            			<div>
                            				<span style="font-weight: bold;">饲养期总头数</span>
                            				<span style="float: right;"><a style="color:#0000FF;" onclick="yyccheck3()" href="JavaScript:;">点击下载表格</a></span>
                            			</div>
                            		</div>	
                            	</li>
                            </ul>
	                            
                        	<!-- <ul id="pagination" style="float: right"></ul> -->
						</div>
					</div>
				</div>
				<!-- end DataTables Example -->
			</div>
		</div>
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
	function search() {
		$.get('${basePath}/report/operateReportInvestAjax?'+$('#form').serialize(),function(res){
			var res = JSON.parse(res);
			$('.buyBackCount').text(res.buyBackCount);
			$('.halfYearNum').text(res.halfYearNum);
			$('.hongbaoMoney').text(res.hongbaoMoney);
			$('.investCount').text(res.investCount);
			$('.monthNum').text(res.monthNum);
			$('.noobNum').text(res.noobNum);
			$('.seasonNum').text(res.seasonNum);
			$('.totalAmount').text(res.totalAmount);
			$('.yearNum').text(res.yearNum);
		})
	};
	
	function search1() {
		$.get('${basePath}/report/operateReportFeedAjax?'+$('#form1').serialize(),function(res){
			var res = JSON.parse(res);
			$('.feedCount').text(res.feedCount);
		})
	};
	
	$(function () {
		search();
		search1();
	    $(".datepicker").datepicker({
	        format: 'yyyy-mm-dd'
	    });
	    /* $('.select2able').select2({width: ""});
	    $(".keyword").keyup(function (e) {
	        e = e || window.e;
	        if (e.keyCode == 13) {
	            $("#form").submit();
	        }
	    });
	    $('#pagination').bootstrapPaginator({
	        currentPage: parseInt('${page}'),
	        totalPages: parseInt('${pages}'),
	        bootstrapMajorVersion: 3,
	        alignment: "right",
	        pageUrl: function (type, page, current) {
	            return "" + page;
	        }
	    }); */
	    $(".datepicker").datepicker({
	        showSecond: true,
	        timeFormat: "hh:mm:ss",
	        dateFormat: "yy-mm-dd"
	    });
	});
</script>
<script>
	function yyccheck(){
	    var startDate = $("#startTime").val();
	    var endDate = $("#endTime").val();
	    var orderStatus = [1,2]; //订单状态:1饲养期,2已卖牛
	    if(startDate!="" && endDate!=""){
	        if(startDate > endDate){
	      	  alert("饲养结束时间必须大于等于饲养开始时间");
	            return;
	        }
	    }
	   location.href="${basePath}investment/orderListReport?startDate="+startDate+"&endDate="+endDate+"&orderStatus="+orderStatus;
	}
	function yyccheck2(){
		var payTimeStart = $("#startTime").val();
	    var payTimeEnd = $("#endTime").val();
	    var status = [3,4]; //物权订单状态:3已出售,4已回购
	    
	    location.href="${basePath}project/export?payTimeStart="+payTimeStart+"&payTimeEnd="+payTimeEnd+"&status="+status;
	}
	function yyccheck3(){
		var feedTime = $("#feedTime").val();
	    var status = [3,4]; //物权订单状态:3已出售,4已回购
	    
	    location.href="${basePath}project/export?status="+status+"&feedTime="+feedTime;
	}
</script>
</body>
</html>