package main.mapper;

import main.dto.GameSessionDTO;
import main.model.GameSessionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameSessionMapper {
    GameSessionMapper INSTANCE = Mappers.getMapper(GameSessionMapper.class);
    @Mapping(source = "gameSessionId", target = "id")
    GameSessionDTO toDTO(GameSessionModel model);
    @Mapping(source = "id", target = "gameSessionId")
    GameSessionModel toModel(GameSessionDTO dto);
}
