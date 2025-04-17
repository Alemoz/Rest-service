package main.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.dto.GameDTO;
import main.service.GameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/game")
public class GameServlet extends HttpServlet {

    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                GameDTO game = gameService.findById(id);

                if (game != null) {
                    Gson gson = new Gson();
                    out.println(gson.toJson(game));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"Game not found\"}");
                }
            } else {
                List<GameDTO> games = gameService.findAll();

                if (!games.isEmpty()) {
                    Gson gson = new Gson();
                    out.println(gson.toJson(games));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"No games found\"}");
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
            GameDTO gameDTO = gson.fromJson(reader, GameDTO.class);

            gameService.save(gameDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"Game created successfully\"}");
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
            GameDTO gameDTO = gson.fromJson(reader, GameDTO.class);

            gameService.update(gameDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"Game updated successfully\"}");
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

                gameService.delete(id);

                resp.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Game deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Game ID is required for deletion\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}