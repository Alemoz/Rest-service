package main.mapper;

import main.dto.TeamDTO;
import main.model.TeamModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    @Mapping(source = "teamId", target = "id")
    TeamDTO toDTO(TeamModel model);

    @Mapping(source = "id", target = "teamId")
    TeamModel toModel(TeamDTO dto);
}
