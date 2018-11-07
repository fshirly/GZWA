<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${ sessionScope.Title}</title>

<style>
    *{ margin:0; padding:0; list-style:none;}
    body{ font-size:12px;}
	#menu{width:360px; overflow:hidden; margin:30px auto;border:1px solid #0061a6;}
    #menu #nav {display:block;width:100%;padding:0;margin:0;list-style:none;}
	#menu #nav li {float:left;width:179px;}
	#menu #nav li a {display:block;line-height:27px;text-decoration:none;padding:0 0 0 5px; text-align:center; color:#fff;}
	#menu_con{ width:360px; height:240px; border-top:none}
    .tag{ padding:10px; overflow:hidden;}
    .selected{background:#0061a6; color:#fff;}
    .btn-style-cer{
	    border-style:none;
	    padding:6px 20px;
	    line-height:24px;
	    color:#000;
	    cursor:pointer;
	    box-shadow:inset 0px 0px 1px #fff;/*内发光效果*/
	    -webkit-border-radius:4px;
	    -moz-border-radius:4px;
	    border-radius:4px;/*边框圆角*/
	    background-color:#cceaff;
	    background-image: -webkit-gradient(linear, 0 0%, 0 100%, from(#cceaff), to(#cceaff));
	    background-image: -webkit-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -moz-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -ms-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -o-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: linear-gradient(top, #cceaff 0%, #cceaff 100%);/*颜色渐变效果*/
	}
	.btn-style-cer:hover {
	    background-color:#cceaff;
	    background-image: -webkit-gradient(linear, 0 0%, 0 100%, from(#cceaff), to(#cceaff));
	    background-image: -webkit-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -moz-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -ms-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: -o-linear-gradient(top, #cceaff 0%, #cceaff 100%);
	    background-image: linear-gradient(top, #cceaff 0%, #cceaff 100%);
	}
	.messager-icon + div{
	 color:red!important;
	 font-size:16px!important;
	}
</style>
<link rel="shortcut icon" href="${sessionScope.filePath}${ sessionScope.LogoIcon}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/login.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login/login.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
</head>
<body>

<div id="main">
</div>

<script type="text/javascript">
<%

String flag  = (String)request.getSession(true).getAttribute("SPRING_SECURITY_LAST_EX_MSG");

if(flag == null) {
	flag = "";
}

if(!flag.trim().equals("")) {
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG", "");
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG_2", flag);
 %>
	top.location.href = getRootName() + '/login.jsp';
<% } else {
	flag = (String)request.getSession(true).getAttribute("SPRING_SECURITY_LAST_EX_MSG_2");
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG_2", "");
	if(flag == null) {
		flag = "";
	}
%>
	document.all.main.innerHTML=
	'<table>                                                                                                                                                   '+
	'<tbody>                                                                                                                                                   '+
	'	<tr>                                                                                                                                                   '+
	'		<td class="logo" align="center">                                                                                                                   '+
	'			<div><img src="${sessionScope.filePath}${ sessionScope.LoginIcon}" onerror="this.src=\'${pageContext.request.contextPath}/style/images/logo.png\'"/></div>                                                              '+
	'		</td>                                                                                                                                              '+
	'		<td class="login">                                                                                                                                 '+
	'			<form id="loginForm" name="loginForm" action="fable_security" method="post">                                                                   '+
	'            <div id="menu">                                                                                                                               '+
// 	'            <ul id="nav">                                                                                                                                             '+
//     '                <li><a href="#" class="selected" id="normal">普通登录</a></li>                                                                                                    '+
//去掉证书登录，20170107 liuwx
//     '               <li><a href="#" class="" id="cert">证书登录</a></li>                                                                                                             '+
//     '            </ul>                                                                                                                                                     '+
    '            <div id="menu_con">                                                                                                                                       '+
    '                <div class="tag" style="display:block">                                                                                                               '+
	'            				<div class="content" style="margin-left:13%">                                                                                                                      '+
	'            					<div class="tips" style="margin-top:20px"><%=flag%></div>                                                                                 '+
	'            					<div class="inputtext" style=""><span>用户名：</span><input type="text" value="" id="iptUserAccount" name="username"/></div>                     '+
	'            					<div class="inputtext"><span>密 &nbsp; 码：</span><input type="password" value="" id="iptUserPassword" name="password"/></div>          '+
	'            					<!--<div>                                                                                                                                  '+
	'            						<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" />                                    '+
	'            						<label for="_spring_security_remember_me">记住密码</label>                                                                         '+
	'            						<input class="btnreset" type="reset" onclick="doReset();" onmouseover="doChange(this);" onmouseout="doBack(this);" value="重置" />'+
	'            					</div>-->                                                                                                                                 '+
	'            				</div>                                                                                                                                     '+
	'            				<div class="btnsubmit" style="margin-top:92px">                                                                                                                    '+
	'            					<input type="submit" onmouseover="doChange(this);" onmouseout="doBack(this);" value=" " />                                             '+
	'            				</div>                                                                                                                                     '+
    '                </div>                                                                                                                                                '+
//去掉证书登录，20170107 liuwx
//     '                <div class="tag" style="display:none;text-align:center">                                                                                                                '+
//     '            		<input type="button" class="btn-style-cer" style="margin-top:72px" value="插入证书,点击登录!">                                                     '+
//     '                </div>                                                                                                                                                '+
    '            </div>                                                                                                                                                    '+
	'             </div>																																	   '+
	'			</form>                                                                                                                                        '+
	'		</td>                                                                                                                                              '+
	'	</tr>                                                                                                                                                  '+
	'	<tr>                                                                                                                                                   '+
	'		<%-- <td colspan="2" id="footer">版权所有&nbsp;&copy;&nbsp;&nbsp;${ sessionScope.CopyRight}</td> 2013-2014&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司--%>                                              '+
	 '		<td colspan="2" id="footer">系统适用浏览器下载：<a title="支持系统所有功能,点击下载" id="downloadForWin7">google浏览器(win7)</a>&nbsp;&nbsp;<a title="支持系统所有功能,点击下载" id="downloadForXp">google浏览器(xp)</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版权所有&nbsp;&copy;&nbsp;&nbsp;<c:if test="${ sessionScope.CopyRight eq '' }">2003-2017&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司</c:if>${ sessionScope.CopyRight}&nbsp;&nbsp;${sessionScope.Version}</td>'+ 
	 /*'		<td colspan="2" id="footer">版权所有&nbsp;&copy;&nbsp;&nbsp;<c:if test="${ sessionScope.CopyRight eq '' }">2003-2017&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司</c:if>${ sessionScope.CopyRight}&nbsp;&nbsp;${sessionScope.Version}</td>'+*/
	'	</tr>                                                                                                                                                  '+
	'</tbody>                                                                                                                                                  '+
	'</table>                                                                                                                                                  ';
<% } %>

//判断浏览器是否是IE8及以下
function myBrowser(){
					var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
					var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
					var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
					if (isIE) {
						var IE5 = IE55 = IE6 = IE7 = IE8 = false;
						var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
						reIE.test(userAgent);
						var fIEVersion = parseFloat(RegExp["$1"]);
						IE55 = fIEVersion == 5.5;
						IE6 = fIEVersion == 6.0;
						IE7 = fIEVersion == 7.0;
						IE8 = fIEVersion == 8.0;
						if (IE55) {
							return "1";
						}
						if (IE6) {
							return "1";
						}
						if (IE7) {
							return "1";
						}
						if (IE8) {
							return "1";
						}
					}//isIE end
				}
if (myBrowser() == "1") {
	$.messager.alert('提示','当前版本过低，请下载IE10以上的浏览器！','info');
}

</script>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
<script type="text/javascript">
var tabs=function(){
    function tag(name,elem){
        return (elem||document).getElementsByTagName(name);
    }
    //获得相应ID的元素
    function id(name){
        return document.getElementById(name);
    }
    function first(elem){
        elem=elem.firstChild;
        return elem&&elem.nodeType==1? elem:next(elem);
    }
    function next(elem){
        do{
            elem=elem.nextSibling;
        }while(
            elem&&elem.nodeType!=1
        )
        return elem;
    }
    return {
        set:function(elemId,tabId){
            var elem=tag("li",id(elemId));
            var tabs=document.getElementsByClassName("tag");
            var listNum=elem.length;
            var tabNum=tabs.length;
            for(var i=0;i<listNum;i++){
                    elem[i].onclick=(function(i){
                        return function(){
                            for(var j=0;j<tabNum;j++){
                                if(i==j){
                                    tabs[j].style.display="block";
                                    elem[j].firstChild.className="selected";
                               		if(i==1){
                               			var httpsUrl = 'https://<%=request.getServerName() %>:8443<%=request.getContextPath() %>/login.jsp';
                               			window.location.href=httpsUrl;
                               		}else{
                               			var httpUrl = 'http://<%=request.getServerName() %>:8088<%=request.getContextPath() %>/commonLogin/toMain';
                               			window.location.href=httpUrl;
                               		}
                                }
                                else{
                                    tabs[j].style.display="none";
                                    elem[j].firstChild.className="";
                                }
                            }
                        }
                    })(i)
            }
        }
    }
}();
tabs.set("nav","menu_con");//执行
if ('<%=request.getScheme() %>'=="https") {
    $("#cert").addClass('selected');
    $("#normal").removeClass('selected');
    $(".tag")[0].style.display="none";
    $(".tag")[1].style.display="block";
}
$(".btn-style-cer").on('click',function(){
	$("#loginForm")[0].submit();
});
</script>
</html>
