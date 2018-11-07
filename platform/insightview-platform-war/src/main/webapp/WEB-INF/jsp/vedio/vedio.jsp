<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<style>
			*{
				padding:0;
				margin:0;
			}
			
			html,body{
				width:100%;
				height:100%;
			}
			#video{
				width:100%;
				height:100%;
				display: block;
			}
		</style>
	</head>
	<body>
		
<video id="video" src="${pageContext.request.contextPath}/style/images/tt.mp4" autoplay="autoplay"></video>
		<script>
			function launchFullscreen(element) {
				//æ­¤æ¹æ³ä¸å¯ä»¥å¨ç°æ­¥ä»»åä¸­å·è¡ï¼å¦åç«çç¡æ³å¨å±
				if(element.requestFullscreen) {
				element.requestFullscreen();
				} else if(element.mozRequestFullScreen) {
				element.mozRequestFullScreen();
				} else if(element.msRequestFullscreen){ 
				element.msRequestFullscreen(); 
				} else if(element.oRequestFullscreen){
				element.oRequestFullscreen();
				}
				else if(element.webkitRequestFullscreen)
				{
				element.webkitRequestFullScreen();
				}else{
				
					var docHtml = document.documentElement;
					var docBody = document.body;
					var videobox = document.getElementById('videobox');
					var cssText = 'width:100%;height:100%;overflow:hidden;';
					docHtml.style.cssText = cssText;
					docBody.style.cssText = cssText;
					videobox.style.cssText = cssText+';'+'margin:0px;padding:0px;';
					document.IsFullScreen = true;
				
				}
			}
			launchFullscreen(document.getElementById("video"));
		</script>
	</body>
</html>
