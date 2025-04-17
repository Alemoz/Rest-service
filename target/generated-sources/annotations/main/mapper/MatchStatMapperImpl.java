package main.mapper;

import javax.annotation.Generated;
import main.dto.MatchStatDTO;
import main.model.MatchStatModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T16:50:14+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class MatchStatMapperImpl implements MatchStatMapper {

    @Override
    public MatchStatDTO toDTO(MatchStatModel model) {
        if ( model == null ) {
            return null;
        }

        MatchStatDTO matchStatDTO = new MatchStatDTO();

        matchStatDTO.setId( model.getMatchStatId() );
        matchStatDTO.setKills( model.getKills() );
        matchStatDTO.setDeaths( model.getDeaths() );
        matchStatDTO.setAssists( model.getAssists() );
        matchStatDTO.setScore( model.getScore() );

        return matchStatDTO;
    }

    @Override
    public MatchStatModel toModel(MatchStatDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MatchStatModel matchStatModel = new MatchStatModel();

        matchStatModel.setMatchStatId( dto.getId() );
        matchStatModel.setKills( dto.getKills() );
        matchStatModel.setDeaths( dto.getDeaths() );
        matchStatModel.setAssists( dto.getAssists() );
        matchStatModel.setScore( dto.getScore() );

        return matchStatModel;
    }
}
