package main.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.dto.GameSessionDTO;
import main.service.GameSessionService;
import main.service.LocalDateTimeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/game-session")
public class GameSessionServlet extends HttpServlet {

    private final GameSessionService gameSessionService = new GameSessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                GameSessionDTO gameSession = gameSessionService.findById(id);

                if (gameSession != null) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    out.println(gson.toJson(gameSession));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"Game session not found\"}");
                }
            } else {
                List<GameSessionDTO> gameSessions = gameSessionService.findAll();

                if (!gameSessions.isEmpty()) {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    out.println(gson.toJson(gameSessions));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\": \"No game sessions found\"}");
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            GameSessionDTO gameSessionDTO = gson.fromJson(reader, GameSessionDTO.class);

            gameSessionService.save(gameSessionDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"Game session created successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader()) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            GameSessionDTO gameSessionDTO = gson.fromJson(reader, GameSessionDTO.class);

            gameSessionService.update(gameSessionDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"Game session updated successfully\"}");
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

                gameSessionService.delete(id);

                resp.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Game session deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\": \"Game session ID is required for deletion\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}