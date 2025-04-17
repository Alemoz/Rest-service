package main.mapper;

import javax.annotation.Generated;
import main.dto.TeamDTO;
import main.model.TeamModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T17:33:18+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamDTO toDTO(TeamModel model) {
        if ( model == null ) {
            return null;
        }

        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setId( model.getTeamId() );
        teamDTO.setName( model.getName() );

        return teamDTO;
    }

    @Override
    public TeamModel toModel(TeamDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TeamModel teamModel = new TeamModel();

        teamModel.setTeamId( dto.getId() );
        teamModel.setName( dto.getName() );

        return teamModel;
    }
}
