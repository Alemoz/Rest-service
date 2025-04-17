package main.mapper;

import javax.annotation.Generated;
import main.dto.GameMapDTO;
import main.model.GameMapModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T14:13:07+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class GameMapMapperImpl implements GameMapMapper {

    @Override
    public GameMapDTO toDTO(GameMapModel model) {
        if ( model == null ) {
            return null;
        }

        GameMapDTO gameMapDTO = new GameMapDTO();

        gameMapDTO.setId( model.getGameMapId() );
        gameMapDTO.setName( model.getName() );
        gameMapDTO.setLocation( model.getLocation() );

        return gameMapDTO;
    }

    @Override
    public GameMapModel toModel(GameMapDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GameMapModel gameMapModel = new GameMapModel();

        gameMapModel.setGameMapId( dto.getId() );
        gameMapModel.setName( dto.getName() );
        gameMapModel.setLocation( dto.getLocation() );

        return gameMapModel;
    }
}
