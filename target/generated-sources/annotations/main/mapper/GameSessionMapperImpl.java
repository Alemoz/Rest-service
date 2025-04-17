package main.mapper;

import javax.annotation.Generated;
import main.dto.GameSessionDTO;
import main.model.GameSessionModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T14:57:21+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class GameSessionMapperImpl implements GameSessionMapper {

    @Override
    public GameSessionDTO toDTO(GameSessionModel model) {
        if ( model == null ) {
            return null;
        }

        GameSessionDTO gameSessionDTO = new GameSessionDTO();

        gameSessionDTO.setId( model.getGameSessionId() );
        gameSessionDTO.setSessionStart( model.getSessionStart() );
        gameSessionDTO.setSessionEnd( model.getSessionEnd() );
        gameSessionDTO.setScore( model.getScore() );

        return gameSessionDTO;
    }

    @Override
    public GameSessionModel toModel(GameSessionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GameSessionModel gameSessionModel = new GameSessionModel();

        gameSessionModel.setGameSessionId( dto.getId() );
        gameSessionModel.setSessionStart( dto.getSessionStart() );
        gameSessionModel.setSessionEnd( dto.getSessionEnd() );
        gameSessionModel.setScore( dto.getScore() );

        return gameSessionModel;
    }
}
