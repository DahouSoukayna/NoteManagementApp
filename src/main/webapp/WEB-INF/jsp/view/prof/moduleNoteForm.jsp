<%@page import="com.gsnotes.web.models.ModuleNoteModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>


<jsp:include page="../fragments/profHeader.jsp" />


<jsp:include page="../fragments/profmenu.jsp" />




	<div class="mt-5 mb-5">
		<h3 class="text-center"><img src="../../../../resources/img/excel.png">Générer un fichier de notes pour un module</h3>
	</div>

	<div>

		<f:form action="${pageContext.request.contextPath}/prof/exportModuleNotes"
			method="POST" modelAttribute="moduleNoteModel">

			<div class="row ">

				<div class="col-md-6 offset-md-3">
					<label>Selectionez le module</label>
<!--  path name is declared on model and itemValue and itemLabel names are declared in dataBase -->
					<f:select path="moduleId" multiple="false" size="1"
						class="form-control">
						<f:options items="${moduleList}" itemValue="idModule"
							itemLabel="titre" />
					</f:select>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-6 offset-md-3">
					<label>Selectionez la session</label>

					<f:select path="sessionId" multiple="false" size="1"
  						class="form-control">   
  						<f:options items="${sessionList}" itemValue="idSession"   
  							itemLabel="nomSession" />   
  					</f:select>  
				</div>
			</div>
			

			
			

			<div class="md-2 offset-md-9" >
				<button type="submit" class="btn btn-success">Create</button>
				<button type="reset" class="btn btn-secondary">Rest</button>
			</div>
			
			

		</f:form>
	</div>




		<jsp:include page="../fragments/userfooter.jsp" />