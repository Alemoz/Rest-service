package main.service;

import main.dao.TeamDAO;
import main.dto.TeamDTO;
import main.mapper.TeamMapper;
import main.model.TeamModel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static main.mapper.TeamMapper.*;

public class TeamService {
    private final TeamDAO teamDAO;
    private final TeamMapper mapper;

    public TeamService(TeamDAO dao, TeamMapper mapper){
        this.teamDAO = dao;
        this.mapper = mapper;
    }
    public TeamService(){
        this.teamDAO = new TeamDAO();
        this.mapper = TeamMapper.INSTANCE;
    }
    public void create(TeamDTO dto) throws SQLException {
        TeamModel model = mapper.toModel(dto);
        model.setCreated_at(LocalDateTime.now());
        teamDAO.save(model);
    }

    public TeamDTO findById(Long id) throws SQLException {
        TeamModel model = teamDAO.findById(id);
        if(model != null){
            return mapper.toDTO(model);
        }
        return null;
    }

    public List<TeamDTO> findAll() throws SQLException {
        List<TeamModel> models = teamDAO.findAll();
        List<TeamDTO> dtos = new ArrayList<>();

        for (TeamModel model : models) {
            dtos.add(mapper.toDTO(model));
        }

        return dtos;
    }

    public void update(TeamDTO dto) throws SQLException {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Team ID cannot be null for update.");
        }

        TeamModel existingTeam = teamDAO.findById(dto.getId());
        if (existingTeam == null) {
            throw new IllegalArgumentException("Team with ID " + dto.getId() + " does not exist.");
        }

        TeamModel model = mapper.toModel(dto);
        teamDAO.update(model);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for deletion.");
        }

        teamDAO.delete(id);
    }
}
