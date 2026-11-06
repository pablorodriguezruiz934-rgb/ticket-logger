<%@ include file="header.jsp" %>

    <h1>Lista de Facciones</h1>
    <a href="factions?action=new">Añadir Facción</a>

    <table border="1">
         <thead>
             <tr>
                 <th>ID</th>
                 <th>Código</th>
                 <th>Nombre</th>
                 <th>Acciones</th>
             </tr>
         </thead>
         <tbody>

             <c:forEach var="faction" items="${listFacciones}">
             <tr>
                 <td>${faction.id}</td>
                 <td>${faction.code}</td>
                 <td>${faction.name}</td>
                 <td>
                     <a href="factions?action=edit&id=${faction.id}">Editar Facción</a>

                     <form action="factions" method="post" style="display:inline;">
                         <input type="hidden" name="action" value="delete"/>
                         <input type="hidden" name="id" value="${faction.id}"/>
                         <input type="submit" value="Eliminar Faccion" onclick="return confirm('¿Desea eliminar la Facción?')" />
                     </form>
                 </td>
             </tr>
             </c:forEach>
         </tbody>
    </table>

<%@ include file="footer.jsp" %>
