package main.mapper;

import main.dto.PlayerDTO;
import main.model.PlayerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(source = "id", target = "playerId")
    PlayerModel toModel(PlayerDTO dto);

    @Mapping(source = "playerId", target = "id")
    PlayerDTO toDTO(PlayerModel model);
}
