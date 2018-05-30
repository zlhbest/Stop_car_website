<%@ page language="java" pageEncoding="GBK"%>
<%@taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path=request.getContextPath();%>
<html>
<head>
	<title>停车场后台管理系统</title>
	<script type="text/javascript" src="<%=path %>/js/parkone.js"></script>
</head>
<body>
${msg }
	<form action="<%=path %>/e1001.html" method="post">
	<!-- 用于查询条件 -->
		<table border="1" width="95%" align="center">
			<caption>
			停车位管理
			<hr width="160">
			</caption>
			<tr>
				<td>车位号</td>
				<td>
					<e:text name="qcb001" autofocus="true"/>
				</td>
				<td>车位状态</td>
				<td>
					<e:select name="qcb002" value="无限制:'',有车:1,预约:2,无车:0"/>
				</td>
			</tr>
		</table>
		<!-- 数据迭代 -->
		<table border="1" width="95%" align="center">
			<tr>
				<td>&nbsp;</td>
				<td>序号</td>
      			<td>停车位id</td>
      			<td>停车位所在停车场</td>
      			<td>停车位用户</td>
      			<td>停车位状态</td>
      			<td>备注</td>
      			<td>&nbsp;</td>
			</tr>
			<c:choose>
				<c:when test="${rows!=null }">
					<c:forEach items="${rows }" var="ins" varStatus="vs">
						<tr>
							<td>
								<input type="checkbox" name="idlist"
										value="${ins.cb001 }" onclick="onSelect(this.checked)">
							</td>
							<td>${vs.count }</td>
							<td>
								<a href="#" onclick="onEdit('${ins.cb001 }','<%=path %>')">${ins.cb001 }</a>
							</td>
							<td>${ins.ca002 }</td>
							<td>${ins.aa002 }</td>
							<td>${ins.cb002 }</td>
							<td>${ins.cb004 }</td>
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
             			</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		<!-- 功能按钮 -->
		<table border="1" width="95%" align="center"> 
			 <tr>
      			<td align="center">
        			<input type="submit" name="next" value="查询" formaction="<%=path%>/e1001.html?ca001=${param.ca001 }"> 
        			<input type="submit" name="next" value="添加" formaction="<%=path%>/jsp/addpark.jsp">
       				 <input type="submit" name="next" value="删除" disabled="disabled" formaction="<%=path%>/c1015.html">
       				 <input type="submit" name="next" value="返回" formaction="<%=path%>/c1012.html">
     			</td>
   			</tr>
		</table>
		 <input  name="ca001" type="hidden" value="${param.ca001 }">
	</form>	
</body>
</html>