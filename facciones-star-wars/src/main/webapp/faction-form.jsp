
<%@ include file="header.jsp" %>

 <h1>
     <c:choose>
         <c:when test="${faction == null}">
             Añadir Facción
         </c:when>
         <c:otherwise>
             Editar Facción
         </c:otherwise>
     </c:choose>
 </h1>

 <c:if test="${not empty errorMessage}">
     <div class="error-message">${errorMessage}</div>
 </c:if>

 <form action="factions" method="post">
     <input type="hidden" name="id" value="${faction.id}" />
     <input type="hidden" name="action" value="${faction == null ? 'insert' : 'update'}" />

     <p>
         <label for="code">faction.code:</label>
         <input type="text" name="code" id="code" value="${faction.code}" required />
     </p>

     <p>
         <label for="name">faction.name:</label>
         <input type="text" name="name" id="name" value="${faction.name}" required />
     </p>

     <c:choose>
         <c:when test="${faction == null}">
             <input type="submit" value="Crear" />
         </c:when>
         <c:otherwise>
             <input type="submit" value="Actualizar" />
         </c:otherwise>
     </c:choose>
 </form>

 <br/>
 <a href="factions">Volver a la lista</a>

<%@ include file="footer.jsp" %>
