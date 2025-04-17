package main.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.dto.MatchDTO;
import main.service.GsonProvider;
import main.service.MatchService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/match")
public class MatchServlet extends HttpServlet {

    private final MatchService matchService = new MatchService();
    private final Gson gson = GsonProvider.createGson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                MatchDTO match = matchService.findById(id);
                if (match != null) {
                    out.println(gson.toJson(match));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"Match not found\"}");
                }
            } else {
                List<MatchDTO> matches = matchService.findAll();
                out.println(gson.toJson(matches));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            MatchDTO dto = gson.fromJson(reader, MatchDTO.class);
            matchService.save(dto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"Match created successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            MatchDTO dto = gson.fromJson(reader, MatchDTO.class);
            matchService.update(dto);
            resp.getWriter().println("{\"message\": \"Match updated successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                matchService.delete(id);
                out.println("{\"message\": \"Match deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Match ID is required\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}