package main.mapper;

import javax.annotation.Generated;
import main.dto.PlayerDTO;
import main.model.PlayerModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T17:32:50+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public PlayerModel toModel(PlayerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PlayerModel playerModel = new PlayerModel();

        playerModel.setPlayerId( dto.getId() );
        playerModel.setUsername( dto.getUsername() );
        playerModel.setEmail( dto.getEmail() );
        playerModel.setRank( dto.getRank() );

        return playerModel;
    }

    @Override
    public PlayerDTO toDTO(PlayerModel model) {
        if ( model == null ) {
            return null;
        }

        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setId( model.getPlayerId() );
        playerDTO.setUsername( model.getUsername() );
        playerDTO.setEmail( model.getEmail() );
        playerDTO.setRank( model.getRank() );

        return playerDTO;
    }
}
