package main.mapper;

import main.dto.GameDTO;
import main.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(source = "id", target = "gameId")
    GameModel toModel(GameDTO dto);

    @Mapping(source = "gameId", target = "id")
    GameDTO toDTO(GameModel model);
}
