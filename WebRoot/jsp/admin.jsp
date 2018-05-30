<%@ page language="java" pageEncoding="GBK"%>
<%String path=request.getContextPath();%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>停车场后台管理系统</title>
	<link rel="bookmark"  type="image/x-icon"  href="<%=path %>/pic/favicon.ico"/>
	<link rel="shortcut icon" href="<%=path %>/pic/favicon.ico"/>
</head>
<body>
<table width="99%" align="center" border="1">
	<tr>
		<td width="10%" align="center" valign="top">
			<a href="<%=path %>/c1010.html" target="VIEW">停车场信息</a><br>
			<a href="<%=path %>/a1010.html" target="VIEW">用户信息</a><br>
			<a href="<%=path %>/b1001.html" target="VIEW">管理员信息</a><br>  
		</td>
		<td width="90%">
	  		<iframe width="100%" height="730" name="VIEW"></iframe>    
      	</td>
   </tr>
</table>
</body>
</html>