package main.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.dto.MatchStatDTO;
import main.service.MatchStatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/match-stat")
public class MatchStatServlet extends HttpServlet {

    private final MatchStatService matchStatService = new MatchStatService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                MatchStatDTO dto = matchStatService.findById(id);

                if (dto != null) {
                    out.println(gson.toJson(dto));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"Match stat not found\"}");
                }
            } else {
                List<MatchStatDTO> allStats = matchStatService.findAll();
                out.println(gson.toJson(allStats));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            MatchStatDTO dto = gson.fromJson(reader, MatchStatDTO.class);
            matchStatService.save(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"Match stat created successfully\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            MatchStatDTO dto = gson.fromJson(reader, MatchStatDTO.class);
            matchStatService.update(dto);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"Match stat updated successfully\"}");
        } catch (Exception e) {
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
                matchStatService.delete(id);

                resp.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Match stat deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Match stat ID is required\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
