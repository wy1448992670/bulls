<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户列表</title>
</head>
<body>
	<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
        	<div class="row">
               	<div class="col-md-12">
                 	<div class="widget-container fluid-height">
	                     <div class="heading">
	                         <i class="icon-bar-chart"></i>
	                     </div>
	                     <div class="widget-content padded text-center">
	                         <div class="graph-container">
	                             <div class="caption"></div>
	                             <div id="user-line" style="height: 300px;"></div>
	                         </div>
	                    </div>
               	 	</div>
        		</div>
        	</div> 
    </div>
    </div>
    <script type="text/javascript" src="${basePath}js/echart/echarts-all.js"></script>
     <script type="text/javascript" src="${basePath}js/echart/macarons.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#home-list a:eq(0)").addClass("current");
    		
    		<%-- option = {
    			    tooltip : {
    			        trigger: 'item'
    			    },
    			    toolbox: {
    			        show : true,
    			        feature : {
    			            mark : {show: true},
    			            dataZoom : {show: true},
    			            dataView : {show: true, readOnly: false},
    			            restore : {show: true},
    			            saveAsImage : {show: true}
    			        }
    			    },
    			    dataRange: {
    			        min: 0,
    			        max: 100,
    			        y: 'center',
    			        text:['高','低'],           // 文本，默认为数值文本
    			        color:['lightgreen','yellow'],
    			        calculable : true
    			    },
    			    xAxis : [
    			        {
    			            type : 'value',
    			            scale : true
    			        }
    			    ],
    			    yAxis : [
    			        {
    			            type : 'value',
    			            position:'right',
    			            scale : true
    			        }
    			    ],
    			    animation: false,
    			    series : [
    			        {
    			            name:'scatter1',
    			            type:'scatter',
    			            symbolSize:4,
    			            data: []
    			        }
    			    ]
    			};
    			                    
            
    		$.ajax({
              	url : "${basePath}report/test/data",
              	dataType : "json",
              	success : function(data){
              		if(data){
              			var dataArray = [];
              			$.each(data,function(i,v){
                  			dataArray.push([v.data,v.data]);
                  		});
              			option.series[0].data = dataArray;
              			createChart('user-line', option); 
              		}
              	}
              }); --%>
              
              option = {
            		    title : {
            		        text: '某楼盘销售情况',
            		        subtext: '纯属虚构'
            		    },
            		    tooltip : {
            		        trigger: 'axis'
            		    },
            		    legend: {
            		        data:['意向','预购','成交']
            		    },
            		    toolbox: {
            		        show : true,
            		        feature : {
            		            mark : {show: true},
            		            dataView : {show: true, readOnly: false},
            		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            		            restore : {show: true},
            		            saveAsImage : {show: true}
            		        }
            		    },
            		    calculable : true,
            		    xAxis : [
            		        {
            		            type : 'category',
            		            boundaryGap : false,
            		            data : ['周一','周二','周三','周四','周五','周六','周日']
            		        }
            		    ],
            		    yAxis : [
            		        {
            		            type : 'value'
            		        }
            		    ],
            		    series : [
            		        {
            		            name:'成交',
            		            type:'line',
            		            smooth:true,
            		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            		            data:[10, 12, 21, 54, 260, 830, 710]
            		        },
            		        {
            		            name:'预购',
            		            type:'line',
            		            smooth:true,
            		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            		            data:[30, 182, 434, 791, 390, 30, 10]
            		        },
            		        {
            		            name:'意向',
            		            type:'line',
            		            smooth:true,
            		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            		            data:[1320, 1132, 601, 234, 120, 90, 20]
            		        }
            		    ]
            		};
            		                    
              
              createChart('user-line', option); 
            		                    
    	});
    	
    	function createChart(id,option){
    		var myChart = echarts.init(document.getElementById(id),theme);
    		myChart.setOption(option);
    	}
    </script>
</body>
</html>