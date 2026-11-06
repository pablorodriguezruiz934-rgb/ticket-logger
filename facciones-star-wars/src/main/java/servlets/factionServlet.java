package servlets;

import dao.FactionDAO;
import dao.FactionDAOImpl;
import entity.Faction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/factions")
public class factionServlet extends HttpServlet {

    private FactionDAO factionDAO;

    @Override
    public void init() throws ServletException {
        try {
            factionDAO = new FactionDAOImpl();
        } catch (Exception e) {
            throw new ServletException("Error al iniciar el servlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "new":
                    showNewForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                default:
                    listFacciones(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action == null) {
                action = "list"; // Control de nulos para POST
            }
            switch (action) {
                case "insert":
                    insertFacciones(req, resp);
                    break;
                case "update":
                    updateFacciones(req, resp);
                    break;
                case "delete":
                    deleteFacciones(req, resp);
                    break;
                default:
                    listFacciones(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listFacciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Faction> listFacciones = factionDAO.listAllFacciones();
        request.setAttribute("listFacciones", listFacciones);
        request.getRequestDispatcher("faction-form.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("faction-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Faction existingFaction = factionDAO.getFaccionesById(id);
        request.setAttribute("Facciones", existingFaction);
        request.getRequestDispatcher("faction-form.jsp").forward(request, response);
    }

    private void insertFacciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String code = request.getParameter("code").trim().toUpperCase();
        String name = request.getParameter("name").trim();

        if (code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos.");
            request.getRequestDispatcher("faction-form.jsp").forward(request, response);
            return;
        }

        if (factionDAO.existsFaccionesByCode(code)) {
            request.setAttribute("errorMessage", "El código de la faccion ya existe.");
            request.getRequestDispatcher("faction-form.jsp").forward(request, response);
            return;
        }

        Faction newFaction = new Faction(code, name);
        try {
            factionDAO.insertFacciones(newFaction);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                request.setAttribute("errorMessage", "El código de la faccion debe ser único.");
                request.getRequestDispatcher("faction-form.jsp").forward(request, response);
                return;
            } else {
                throw e;
            }
        }
        response.sendRedirect("factions");
    }

    private void updateFacciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code").trim().toUpperCase();
        String name = request.getParameter("name").trim();

        if (code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos.");
            request.getRequestDispatcher("faction-form.jsp").forward(request, response);
            return;
        }

        if (factionDAO.existsFaccionesByCodeAndNotId(code, id)) {
            request.setAttribute("errorMessage", "El código de la faccion ya existe para otra faccion.");
            request.getRequestDispatcher("faction-form.jsp").forward(request, response);
            return;
        }

        Faction updatedFaction = new Faction(id, code, name);
        try {
            factionDAO.updateFacciones(updatedFaction);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                request.setAttribute("errorMessage", "El código de la faccion debe ser único.");
                request.getRequestDispatcher("faction-form.jsp").forward(request, response);
                return;
            } else {
                throw e;
            }
        }
        response.sendRedirect("factions");
    }

    private void deleteFacciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        factionDAO.deleteFacciones(id);
        response.sendRedirect("factions");
    }
}
