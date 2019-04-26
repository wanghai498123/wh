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
							<div class="jarviswidget" id="widget-id-${e_ClassName?uncap_first}" role="widget">
								<header>
								<h2>
              						<spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.add" />
								</h2>
								</header>
								<div role="content">
									<div class="inner-spacer">
										<div class="widget alert alert-info adjusted">
											<button class="close" data-dismiss="alert">×</button>
											<i class="cus-exclamation"></i>
											<strong><spring:message code="common.friendly.tip" />：</strong>"<i class="cus-sport-tennis"></i>"
											<spring:message code="common.required.mark" />
										</div>
										<!-- content goes here -->
										<form commandName="${e_ClassName?uncap_first}" class="form-horizontal themed" id="F_${e_ClassName?uncap_first}Form" action="${e_ClassName?uncap_first}/add" method="post">
											<fieldset>
												<#list e_Tableinfo.columns?chunk(2) as row>
												<div class="control-group">
													<div class="row-fluid">
														<#list row as cell>
														<div class="span6">
															<label class="control-label pull-right" for="${cell.propertyName}"><#if cell.propertyName==e_ClassName?uncap_first+'Name'><i class="cus-sport-tennis"></i></#if><spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.${cell.propertyName}" /></label>
															<div class="controls">
															
																<#if cell.propertyType=='Date'>
																<input type="text" class="focused span11 list-search-time"  rel='qTip'  readonly="true" name="${cell.propertyName}" />
																<#else>
																<input type="text" value="" title="<spring:message code='common.valid.name'/>" valid="<#if cell.propertyName==e_ClassName?uncap_first+'Name'>required</#if> checkName" rel="qTip" class="span12 focused" id="${cell.propertyName}" name="${cell.propertyName}" />
																</#if>
															</div>
														</div>
														</#list>
													</div>
												</div>
												</#list>
												
												<div class="form-actions">
													<input type="button" id="add" value="<spring:message code='common.form.save'/>" class="btn medium btn-primary">
													<input type="button" id="cancel" value="<spring:message code='common.form.cancel'/>" class="btn medium btn-danger">
												</div>
											</fieldset>
										</form>
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
			<a rel="to-top" href="#top"><div class="gotop"></div>
			</a>
		</div>
		<%@ include file="/WEB-INF/views/common/commonJS.jsp"%>
<script>
	$(function() {
	
	   //initSelectData("#selectUser","initUserList","",{id:function(obj) {return obj.userID;},formatSelection : function(obj) {return obj.loginName;}});
        
		$("#cancel").click(function() {
			history.back();
			})

		$("#add").click(function(e) {
			var result = validForm("F_${e_ClassName?uncap_first}Form");
			if (result) {
			      var ${e_ClassName?uncap_first}Name = $("#${e_ClassName?uncap_first}Name").val();
                  var condition = [ {
										"name" : "${e_ClassName?uncap_first}Name",
										"operator" : "=",
										"value" : ${e_ClassName?uncap_first}Name
									} ];
				  //同名校验
				  isExist("${e_ClassName}", condition,
						function() {
							       $("#F_${e_ClassName?uncap_first}Form").submit();
								   },
				  '<spring:message code="${e_ModuleName}.${e_ClassName?uncap_first}.RecordExist"/>');
			}
		});
		
		initBlurValid("F_${e_ClassName?uncap_first}Form");
	});
</script>
	</body>
</html>
