package main.service;

import main.dao.MatchDAO;
import main.dto.MatchDTO;
import main.mapper.MatchMapper;
import main.model.MatchModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MatchService {
    private final MatchDAO matchDAO;
    private final MatchMapper mapper;

    public MatchService(MatchDAO matchDAO, MatchMapper mapper) {
        this.matchDAO = matchDAO;
        this.mapper = mapper;
    }
    public MatchService() {
        this.matchDAO = new MatchDAO();
        this.mapper = MatchMapper.INSTANCE;
    }


    public List<MatchDTO> findAll() throws SQLException {
        List<MatchModel> matches = matchDAO.findAll();
        return matches.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MatchDTO findById(Long id) throws SQLException {
        MatchModel match = matchDAO.findById(id);
        if (match != null) {
            return mapper.toDTO(match);
        }
        return null;
    }

    public void save(MatchDTO matchDTO) throws SQLException {
        if(matchDTO == null){
            throw new NullPointerException("MatchDTO cannot be null");
        }
        MatchModel matchModel = mapper.toModel(matchDTO);
        matchDAO.save(matchModel);
    }

    public void update(MatchDTO matchDTO) throws SQLException {
        if(matchDTO == null){
            throw new NullPointerException("MatchDTO cannot be null");
        }
        MatchModel matchModel = mapper.toModel(matchDTO);
        matchDAO.update(matchModel);
    }

    public void delete(Long id) throws SQLException {
        if(id == null){
            throw new NullPointerException("MatchDTO cannot be null");
        }
        matchDAO.delete(id);
    }
}
