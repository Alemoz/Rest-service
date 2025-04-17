package main.mapper;

import main.dto.MatchStatDTO;
import main.model.MatchStatModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface MatchStatMapper {
    MatchStatMapper INSTANCE = Mappers.getMapper(MatchStatMapper.class);
    @Mapping(source = "matchStatId", target = "id")
    MatchStatDTO toDTO(MatchStatModel model);
    @Mapping(source = "id", target = "matchStatId")
    MatchStatModel toModel(MatchStatDTO dto);
}
