package main.service;

import main.dao.MatchStatDAO;
import main.dto.MatchStatDTO;
import main.mapper.MatchStatMapper;
import main.model.MatchModel;
import main.model.MatchStatModel;
import main.model.PlayerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MatchStatService {

    private final MatchStatDAO dao;
    private final MatchStatMapper mapper;

    public MatchStatService(MatchStatDAO dao, MatchStatMapper mapper){
        this.dao = dao;
        this.mapper = mapper;
    }

    public MatchStatService(){
        this.dao = new MatchStatDAO();
        this.mapper = MatchStatMapper.INSTANCE;
    }
    public MatchStatDTO findById(Long id) throws SQLException {
        MatchStatModel model = dao.findById(id);
        return model != null ? mapper.toDTO(model) : null;
    }

    public List<MatchStatDTO> findAll() throws SQLException {
        return dao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void save(MatchStatDTO dto) throws SQLException {
        if (dto == null) {
            throw new NullPointerException("MatchStatDTO cannot be null");
        }
        MatchStatModel model = new MatchStatModel();
        model.setKills(dto.getKills());
        model.setDeaths(dto.getDeaths());
        model.setAssists(dto.getAssists());
        model.setScore(dto.getScore());

        PlayerModel player = new PlayerModel();
        player.setPlayerId(dto.getPlayerId());

        MatchModel match = new MatchModel();
        match.setMatchId(dto.getMatchId());

        model.setPlayer(player);
        model.setMatch(match);

        dao.save(model);
    }

    public void update(MatchStatDTO dto) throws SQLException {
        if(dto == null){
            throw new NullPointerException("MatchStatDTO cannot be null");
        }
        MatchStatModel model = new MatchStatModel();
        model.setMatchStatId(dto.getId());
        model.setKills(dto.getKills());
        model.setDeaths(dto.getDeaths());
        model.setAssists(dto.getAssists());
        model.setScore(dto.getScore());

        PlayerModel player = new PlayerModel();
        player.setPlayerId(dto.getPlayerId());
        model.setPlayer(player);

        MatchModel match = new MatchModel();
        match.setMatchId(dto.getMatchId());
        model.setMatch(match);

        dao.update(model);
    }

    public void delete(Long id) throws SQLException {
        if(id == null){
            throw new NullPointerException("MatchStatDTO cannot be null");
        }
        dao.delete(id);
    }
}
