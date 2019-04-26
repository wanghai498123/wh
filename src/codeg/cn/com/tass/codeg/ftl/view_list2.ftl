<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<title><spring:message code="common.productname" /></title>
		<style>
		#subMenuTree ul {
			margin: 0;
			padding-top: 15px;
			width: 295px;
		}
	
		#subMenuTree div ul li {
			list-style: none;
			padding: 2px;
			margin: 0;
		}
	
		.parentMenu {
			font-weight: bold;
		}
		</style>
		<%@ include file="/WEB-INF/views/common/commonCss.jsp"%>
	</head>
	<body>
		<div class="height-wrapper">
			<header>
				<div id="header-toolbar" class="row-fluid">
					<%@ include file="/WEB-INF/views/common/top.jsp"%>
				</div>
			</header>
			
			<section class="section">
			<div class="row-fluid">
				<div id="leftMenu">
					<%@ include file="/WEB-INF/views/common/left.jsp"%>
				</div>

				<div id="page-content">
					<div class="content-header">
						<ul class="breadcrumb" style="backgroud-color: ">
							<li>
								<a href="${r'${homepage}'}"><i class="icon-home"></i> <spring:message code="common.index" /> </a><span class="divider">›</span>
							</li>
							<li>
								<spring:message code="common.menu.${e_ModuleName}" />
								<span class="divider">›</span>
							</li>
							<li>
								<a href="${e_ClassName?uncap_first}/toList"><spring:message code="common.menu.${e_ModuleName}.${e_ClassName?uncap_first}" /> </a>
							</li>
						</ul>
					</div>
					<div class="row-fluid">
						<div class="span12">
							<div id="myChatTab" style="margin: 0px 0 0 0;">
								<div class="tab-pane fade in active" id="${e_ClassName}ManagerList">
									<div class="chat-messages" style="width: 100%">
										<div class="jarviswidget" id="widget-id-${e_ClassName?uncap_first}" role="widget">
											<header>
											<h2>
												<spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.${e_ClassName?uncap_first}List" />
											</h2>
											<div class="jarviswidget-ctrls" role="menu">
												<a class="button-icon jarviswidget-search-btn" href="javascript:void(0);" data-placement="left" rel="tooltip"> 
													<span class="search-10 "></span>
												</a>
											</div>
											</header>
											<div role="content">
												<div class="jarviswidget-editbox" id="tableList-search">
													<#list e_Tableinfo.columns?chunk(3) as row>
													<div class="row-fluid">
														<#list row as cell>
														<#if cell.propertyType=='Date'>
														<div class="span4">
															<label class="right"><spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.${cell.propertyName}" />:</label>
															<input type="text" class="focused span4 list-search-time"  rel='qTip' title='<spring:message code="common.valid.starttime"/>' readonly="true" id="startTime" name="startTime"/> - 
															<input type="text" class="focused span4 list-search-time"  rel='qTip' title='<spring:message code="common.valid.endtime"/>' readonly="true" id="endTime" name="endTime"/>
														</div>
														<#else>
														<div class="span4">
															<label class="right"><spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.${cell.propertyName}" />:</label>
															<input class="span8" type="text" name="${cell.propertyName}" />
														</div>
														</#if>
														</#list>
													</div>
													</#list>
													<div class="row-fluid">
														<div class="span4"></div>
														<div class="span4 center">
															<div class="btn-group">
																<a class="btn btn-small" id="search" href="javascript:void(0);"><i class="cus-find"></i> <spring:message code="common.form.search" /></a>
																<a class="btn btn-small" id="close" href="javascript:void(0);"><i class="cus-bin-closed"></i> <spring:message code="common.form.clear"/></a>
															</div>
														</div>
														<div class="span4"></div>
													</div>
												</div>

												<div class="row-fluid">
													<div class="span11 tableMenu">
														<div class="btn-group">
															<a class="btn btn-small"  href="${e_ClassName?uncap_first}/toAdd"><i class="cus-add"></i> <spring:message code='common.table.add' /> </a>
															<a class="btn btn-small" href="javascript:deleteObject(2);"><i class="cus-cross"></i> <spring:message code='common.table.delete' /></a>
														</div>
													</div>
												</div>
												<div class="inner-spacer">
													<form id="tableForm" action='' method="post">
														<table id="tableList" class="table table-striped table-bordered bootstrap-datatable datatable">

														</table>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="copyRight">
					<spring:message code="common.company" />
				</div>
			</div>
			</section>
			
			<a rel="to-top" href="#top"><div class="gotop"></div> </a>
		</div>
		
		<%@ include file="/WEB-INF/views/common/commonJS.jsp"%>
		<script>
		
		var oTable;
		
		$(function() {
			 initTableList();
		});
		
		function initTableList(){
			oTable = $("#tableList").dataTable({
					 "sAjaxSource": "${e_ClassName?uncap_first}/list",
					 "aoColumnDefs": [
							{ 'bSortable': false, 'aTargets': [0,1] } ,
							{ "sClass": "center", "aTargets": [ 0,1] }
					 ], 
					 "aaSorting": [[ 1, 'desc' ]],
					 "fnDrawCallback": function ( oSettings ) {
							/*qtipWithTitle("[rel='loginNameTip']",'ggg',function() {
									return "<pre>"+ $(this).attr("data")+ "</pre>";
							});*/
					 },
					 "aoColumns" : [ {
							"sTitle" : "<spring:message code='common.table.NO'/>",
							"sName" : "id",
							"mData" : "id",
							"sClass":"table-number center"
						}, {
							"sTitle" : "<input type='checkbox'/>",
							"mData" : function(data,type,row){
							   return "<input type='checkbox' enable='unlock' name='ids' value='"+data.${e_ClassName?uncap_first}ID+"'/>";
							},
							"sClass":"table-number center"
						}, <#list e_Tableinfo.columns as column>{
							"sTitle" : "<spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.${column.propertyName}" />",
							"sName" : "${column.propertyName}",
							"mData" : function(data,type,row){
								<#if column.propertyType=="Date">
								return formatDateTime(data.${column.propertyName});
								<#else>
								var name = data.${column.propertyName};
								return name;
								</#if>
							}
						},</#list>{
							"sTitle" : "<spring:message code='common.table.operator'/>",
							"mData" : function(data,type,row){
								var operator = "";
							   
								var modifyIcon = "<a href='${e_ClassName?uncap_first}/toEdit/"+data.${e_ClassName?uncap_first}ID+"' rel='tooltip' data-placement='left' title='<spring:message code='common.table.edit'/>'><i class='cus-pencil'></i></a>&nbsp;";
								var deleteIcon = "<a href='javascript:deleteObject(1,"+data.${e_ClassName?uncap_first}ID+")' rel='tooltip' data-placement='left' title='<spring:message code='common.table.delete'/>'><i class='cus-cross'></i></a>&nbsp;";
								operator+=modifyIcon+deleteIcon;
								return operator;
							}
						}]
				});	
		}
		
			
		function editObject(pID) {
			
				$("#${e_ClassName?uncap_first}EditDiv").dialog(
								{
									title : "<spring:message code='${e_ModuleName}.${e_ClassName?uncap_first}.edit'/>",
									autoOpen : true,
									width : 700,
									height : ${e_Tableinfo.columns?size*70},
									modal : true,
									buttons : {
										"<spring:message code="common.form.save"/>" : function() {
										   var result = validForm("${e_ClassName?uncap_first}EditForm");
										   if (result) {
												$("#${e_ClassName?uncap_first}EditForm").submit();
											}
										},
										"<spring:message code="common.form.cancel"/>" : function() {
											$(this).dialog("destroy");
										}
									},
									show : {
										effect : "fade",
										duration : 500
									},
									hide : {
										effect : "fade",
										duration : 500
									},
									closeOnEscape : false,
									beforeClose : function(event, ui) {

									},
									close : function(event, ui) {
										$(this).dialog("destroy");
									}
								});
								
				 initBlurValid("${e_ClassName?uncap_first}EditForm");
				 resetFormVal("${e_ClassName?uncap_first}EditForm");
				 $("#${e_ClassName?uncap_first}ID").val(pID);
				 var aoDatas=$("#tableList").dataTableSettings[0].aoData
				 for(var i=0;i<aoDatas.length;i++)
				 {
					if(aoDatas[i]._aData.${e_ClassName?uncap_first}ID==pID)
					{
							<#list e_Tableinfo.columns as column>
							<#if column.propertyType=='Date'>
							$("#${column.propertyName}").val(formatDateTime(aoDatas[i]._aData.${column.propertyName}));
							<#else>
							$("#${column.propertyName}").val(aoDatas[i]._aData.${column.propertyName});
							</#if>
							</#list>
							break;
					}
				 }
				
				 $('#${e_ClassName?uncap_first}EditForm').form({
						success : function(data) {
							$("#${e_ClassName?uncap_first}EditDiv").dialog("close");
							processJsonResponse(data);
							oTable.fnDraw(true);
						}
				});
			}

		function deleteObject(type,id){
				var ajaxObject={};
				ajaxObject.url="${e_ClassName?uncap_first}/delete";
				ajaxObject.oTable=oTable;
				if(type==2){
					var selected = $("#tableList").find("td > :input[type='checkbox'][checked='checked']");
					if(selected.size()<1){
					   showWaringDialog(rs_common_msg_confirm,rs_common_msg_delete_select);
						return;
					}else{
						ajaxObject.data=$("#tableForm").serialize();
					}
				}else{
					ajaxObject.data="ids="+id;
				}
			   showConfirmDialog('<spring:message code="common.msg.delete.confirm"/>',ajaxObject);
		}
	</script>
	</body>
</html>
