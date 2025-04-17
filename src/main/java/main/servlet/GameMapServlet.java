package main.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.dto.GameMapDTO;
import main.service.GameMapService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/game-map")
public class GameMapServlet extends HttpServlet {

    private final GameMapService gameMapService = new GameMapService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                GameMapDTO gameMap = gameMapService.findById(id);

                if (gameMap != null) {
                    Gson gson = new Gson();
                    out.println(gson.toJson(gameMap));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"Game map not found\"}");
                }
            } else {
                List<GameMapDTO> gameMaps = gameMapService.findAll();

                if (!gameMaps.isEmpty()) {
                    Gson gson = new Gson();
                    out.println(gson.toJson(gameMaps));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"No game maps found\"}");
                }
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
            Gson gson = new Gson();
            GameMapDTO gameMapDTO = gson.fromJson(reader, GameMapDTO.class);

            gameMapService.save(gameMapDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"Game map created successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            GameMapDTO gameMapDTO = gson.fromJson(reader, GameMapDTO.class);

            gameMapService.update(gameMapDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"Game map updated successfully\"}");
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
                Long id = Long.valueOf(idParam);

                gameMapService.delete(id);

                resp.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Game map deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Game map ID is required for deletion\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}