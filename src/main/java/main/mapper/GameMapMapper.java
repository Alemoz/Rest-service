package main.mapper;

import main.dto.GameMapDTO;

import main.model.GameMapModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface GameMapMapper {
    GameMapMapper INSTANCE = Mappers.getMapper(GameMapMapper.class);
    @Mapping(source = "gameMapId", target = "id")
    GameMapDTO toDTO(GameMapModel model);
    @Mapping(source = "id", target = "gameMapId")
    GameMapModel toModel(GameMapDTO dto);
}
