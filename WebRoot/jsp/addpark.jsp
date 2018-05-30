<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
<%String path = request.getContextPath(); %>
<html>
<head>
	<title>停车场后台管理系统</title>
	<script type="text/javascript" src="<%=path %>/js/addpark.js" charset="gb2312"></script>
</head>
<body onload="load(${msg })">
	<form action="" method="post">
		<table border="1" align="center" width="55%">
    		<caption>
    			停车场${empty param.ca001?'建档':'修改' }
    			<hr width="160"/>
    		</caption>
    		<tr>
    			<td>停车场名称</td>
    			<td>
    				<e:text name="ca002" autofocus="true" defval="${ins.ca002 }"/>
    			</td>
    			<td>停车场位置</td>
    			<td>
    				<e:text name="ca003" defval="${ins.ca003 }"/>
    			</td>
    		</tr>
    		<tr>
    			<td>管理员id</td>
    			<td>
    				<e:text name="ba001" defval="${ins.ba002 }"/>
    			</td>
    			<td>停车场车位数</td>
    			<td>
    				<e:text name="ca004" defval="${ins.ca004 }"/>
    			</td>
    		</tr>
    		<tr>
    			<td>备注</td>
    			<td colspan="3">
    				<e:textarea rows="5" cols="45" name="ca005" defval="${ins.ca005 }"/>
    			</td>
    		</tr>
    		 <tr>
      			<td colspan="4" align="center">
        			<input type="submit" name="next" value="${empty param.ca001?'添加':'修改' }" 
        	   			formaction="<%=path%>/c101${empty param.ca001?'1':'3' }.html">
        			<input type="submit" name="next" value="返回" formaction="<%=path%>/c1010.html">
        			<input type="${empty param.ca001?'hidden':'submit' }" onclick="lookpark('${param.ca001 }','<%=path %>')" 
        				name="next" value="查看"> 
      			</td>
    		</tr>
    	</table>
    	 <input  name="ca001" type="hidden" value="${param.ca001 }">
	</form>
</body>
</html>