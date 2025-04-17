package main.mapper;

import javax.annotation.Generated;
import main.dto.GameDTO;
import main.model.GameModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T14:13:07+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class GameMapperImpl implements GameMapper {

    @Override
    public GameModel toModel(GameDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GameModel gameModel = new GameModel();

        gameModel.setGameId( dto.getId() );
        gameModel.setTitle( dto.getTitle() );
        gameModel.setGenre( dto.getGenre() );

        return gameModel;
    }

    @Override
    public GameDTO toDTO(GameModel model) {
        if ( model == null ) {
            return null;
        }

        GameDTO gameDTO = new GameDTO();

        gameDTO.setId( model.getGameId() );
        gameDTO.setTitle( model.getTitle() );
        gameDTO.setGenre( model.getGenre() );

        return gameDTO;
    }
}
