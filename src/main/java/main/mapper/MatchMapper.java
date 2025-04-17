package main.mapper;

import main.dto.MatchDTO;
import main.model.MatchModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {GameMapper.class, TeamMapper.class, GameMapMapper.class})
public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "matchId", target = "id")
    @Mapping(source = "gameModel.gameId", target = "gameId")
    @Mapping(source = "teamA.teamId", target = "teamAId")
    @Mapping(source = "teamB.teamId", target = "teamBId")
    @Mapping(source = "winner.teamId", target = "winnerId")
    @Mapping(source = "map.gameMapId", target = "mapId")
    @Mapping(source = "matchDate", target = "matchDate")
    MatchDTO toDTO(MatchModel model);
    @Mapping(source = "id", target = "matchId")
    @Mapping(source = "gameId", target = "gameModel.gameId")
    @Mapping(source = "teamAId", target = "teamA.teamId")
    @Mapping(source = "teamBId", target = "teamB.teamId")
    @Mapping(source = "winnerId", target = "winner.teamId")
    @Mapping(source = "mapId", target = "map.gameMapId")
    @Mapping(source = "matchDate", target = "matchDate")
    MatchModel toModel(MatchDTO dto);
}
