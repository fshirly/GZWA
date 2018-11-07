<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Language" content="zh-cn" />
	<title>${ sessionScope.ChineseName}</title> <!-- mainframe -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
	<!-- my ui -->
	<%-- <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/style/css/base.css" /> --%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/layout.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<!-- dTree -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="gz-tabs">
 		<div title="运行情况">
			<iframe mode="gz_run" id="component_2" name="component_2" src="" frameborder="0"></iframe>
 		</div>
 		<div title="告警派单">
 			<iframe mode="gz_order" id="component_2" name="component_2" src="" frameborder="0"></iframe>
 		</div>
	</div>
	<input type="hidden" id="gzSign" value="gzSign"/>
	<div id="popWin" class="popWin"></div>
	<div id="popWin2" class="popWin2"></div>
	<div id="popWin3" class="popWin3"></div>
	<div id="popWin4" class="popWin4"></div>
	<script>
			$('#gz-tabs').tabs({
			    border:false,
			    fit:true,
			    onSelect:function(title){
			    	if ('告警派单' == title) {
						$('iframe[mode="gz_order"]').attr('src', '${pageContext.request.contextPath}/monitor/alarmActive/toAlarmActiveList');
					} else if ('运行情况' == title) {
						$('iframe[mode="gz_run"]').attr('src', '${pageContext.request.contextPath}/${url}');
					}
			    }
			});
	</script>
</body>
</html>