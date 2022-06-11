<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>





<jsp:include page="../fragments/profHeader.jsp" />
	<jsp:include page="../fragments/profmenu.jsp" />
	


<div class="wrapper">
    <div class="section">
        <div class="box-area">
            
            <s:authorize access="isAuthenticated()">
    			<h2>Welcome 
			 <s:authentication property="principal.firstName" />
			 <s:authentication property="principal.LastName" /></h2>
			 
			 <h3>Email : <s:authentication property="principal.email" /></h3><br>
<%-- 			<h3>Role : <s:authentication property="principal.username" /> </h3><br> --%>
			
		</s:authorize>
        </div>
        
    </div>
</div>
	
	
</body>
</html>