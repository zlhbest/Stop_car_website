<%@ page language="java" pageEncoding="GBK"%>
<%@taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path=request.getContextPath();%>
<html>
<head>
	<script type="text/javascript" src="<%=path %>/js/allpark.js"></script>
	<title>停车场后台管理系统</title>
</head>
<body>
${msg }
	<form action="<%=path %>/c1010.html" method="post">
	<!-- 用于查询条件 -->
		<table border="1" width="95%" align="center">
			<caption>
			停车场管理
			<hr width="160">
			</caption>
			<tr>
				<td>名称</td>
				<td>
					<e:text name="qca002" autofocus="true"/>
				</td>
				<td>位置</td>
				<td>
					<e:text name="qca003"/>
				</td>
			</tr>
		</table>
		<!-- 数据迭代 -->
		<table border="1" width="95%" align="center">
			<tr>
				<td>&nbsp;</td>
      			<td>停车场id</td>
      			<td>停车场名称</td>
      			<td>停车场位置</td>
      			<td>管理员名称</td>
      			<td>停车场车位数</td>
      			<td>停车场目前车位</td>
      			<td>备注</td>
      			<td>&nbsp;</td>
			</tr>
			<c:choose>
				<c:when test="${rows!=null }">
					<c:forEach items="${rows }" var="ins" varStatus="vs">
						<tr>
							<td>
								<input type="checkbox" name="idlist"
										value="${ins.ca001 }" onclick="onSelect(this.checked)">
							</td>
							<td>${vs.count }</td>
							<td>
								<a href="#" onclick="onEdit('${ins.ca001 }','<%=path %>')">${ins.ca002 }</a>
							</td>
							<td>${ins.ca003 }</td>
							<td>${ins.ba002 }</td>
							<td>${ins.ca004 }</td>
							<td>${ins.ca006 }</td>
							<td>${ins.ca005 }</td>
							<td>
			    				<a href="#" onclick = "onDel('${ins.ca001 }','<%=path %>')" >[删除]</a>
			  				</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach begin="1" step="1" end="10">
						<tr>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
               				<td>&nbsp;</td>
             			</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		<!-- 功能按钮 -->
		<table border="1" width="95%" align="center"> 
			 <tr>
      			<td align="center">
        			<input type="submit" name="next" value="查询" > 
        			<input type="submit" name="next" value="添加" formaction="<%=path%>/jsp/addpark.jsp">
       				 <input type="submit" name="next" value="删除" disabled="disabled" formaction="<%=path%>/c1015.html">
     			</td>
   			</tr>
		</table>
	</form>	
</body>
</html>