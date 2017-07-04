<html>
	<head>
		<#include "include/common.header.ftl" >
	</head>
	<body style="margin: 15px;">
		<#--客户信息展示-->
		<div class="easyui-panel" title="客户基本信息" style="width: 600px;height: 100px;padding: 10px">
		    <table cellspacing="8px">
		        <input type="hidden" id="cusId" name="cusId" value="${customerId?c}"/>
		        <tr>
		        	<td>客户编号</td>
		            <td><input type="text" id="khno" name="khno" readonly="readonly" value="${customer.khno?if_exists}"/></td>
		            <td>客户名称：</td>
		            <td><input type="text" id="name" name="name" readonly="readonly" value="${customer.name?default('')}" /></td>
		            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		        </tr>
		        
		    </table>
		</div>
		
		<#--交往记录展示-->
		<br/>
		<table id="dg" title="交往记录信息管理" style="width:700px;height:250px"
		       toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
		    <thead>
		    <tr>
		        <th field="id" width="20">编号</th>
		        <th field="cusId" hidden="true"></th>
		        <th field="contactTime" width="60" editor="{type:'datetimebox',options:{required:true}}">交往时间</th>
		        <th field="address" width="100" editor="{type:'validatebox',options:{required:true}}">交往地址</th>
		        <th field="overview" width="100" editor="{type:'validatebox',options:{required:true}}">概要</th>
		    </tr>
		    </thead>
		</table>
		<div id="toolbar">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addContact();">添加</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteContact();">删除</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveContact();">保存</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelRow();">撤销行</a>
		</div>
		<script src="${ctx}/js/contact.js" ></script>
	</body>
</html>