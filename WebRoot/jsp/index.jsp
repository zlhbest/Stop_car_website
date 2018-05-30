<%@ page language="java" pageEncoding="GBK"%>
<%String path=request.getContextPath();%>
<html>
<head>
    <title>停车场后台管理系统</title>
    <link href="<%=path%>/css/style_log.css" rel="stylesheet" type="text/css">
    <link rel="bookmark"  type="image/x-icon"  href="<%=path %>/pic/favicon.ico"/>
	<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/pic/favicon.ico"/>
</head>
<body class="login">
<form action="<%=path %>/d1001.html" method="post">
	<div class="login_m">
    	<div class="login_logo">
    		<img src="<%=path%>/pic/logo.png" width="100" height="100">
    	</div>
    	<div class="login_boder">
        	<div class="login_content" id="login_model">
           	 	<h2>用户名</h2>
            	<label>
                	<input type="text" id="username" class="txt_input txt_input2" name="name">
            	</label>
            	<h2>密码</h2>
            	<label>
                	<input type="password" name="pwd" id="userpwd" class="txt_input" >
            	</label>
            	<div class="error_message">
                	<p class="error">
                		${requestScope.msg }
                	</p>
              	 	<p class="forgot"><a id="iforget" href="javascript:void(0);">忘记密码?</a></p>
            	</div>
            	<div class="rem_sub">
                	<div class="rem_sub_l">
                    	<input type="checkbox" name="checkbox" id="save_me">
                    	<label for="checkbox">记住我</label>
                	</div>
                	<label>
                    	<input type="submit" class="sub_button" id="button" value="登录" style="opacity: 0.7;">
                	</label>
            	</div>
        	</div>
        	<!--login_content  Sign up end-->
    	</div>
    	<!--login_boder end-->
	</div>
</form>
	<!--login_m end-->
</body>
</html>