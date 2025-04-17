package main.mapper;

import javax.annotation.Generated;
import main.dto.MatchDTO;
import main.model.GameMapModel;
import main.model.GameModel;
import main.model.MatchModel;
import main.model.TeamModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T16:10:24+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Amazon.com Inc.)"
)
public class MatchMapperImpl implements MatchMapper {

    @Override
    public MatchDTO toDTO(MatchModel model) {
        if ( model == null ) {
            return null;
        }

        MatchDTO matchDTO = new MatchDTO();

        matchDTO.setId( model.getMatchId() );
        matchDTO.setGameId( modelGameModelGameId( model ) );
        matchDTO.setTeamAId( modelTeamATeamId( model ) );
        matchDTO.setTeamBId( modelTeamBTeamId( model ) );
        matchDTO.setWinnerId( modelWinnerTeamId( model ) );
        matchDTO.setMapId( modelMapGameMapId( model ) );
        matchDTO.setMatchDate( model.getMatchDate() );

        return matchDTO;
    }

    @Override
    public MatchModel toModel(MatchDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MatchModel matchModel = new MatchModel();

        matchModel.setGameModel( matchDTOToGameModel( dto ) );
        matchModel.setTeamA( matchDTOToTeamModel( dto ) );
        matchModel.setTeamB( matchDTOToTeamModel1( dto ) );
        matchModel.setWinner( matchDTOToTeamModel2( dto ) );
        matchModel.setMap( matchDTOToGameMapModel( dto ) );
        matchModel.setMatchId( dto.getId() );
        matchModel.setMatchDate( dto.getMatchDate() );

        return matchModel;
    }

    private Long modelGameModelGameId(MatchModel matchModel) {
        GameModel gameModel = matchModel.getGameModel();
        if ( gameModel == null ) {
            return null;
        }
        return gameModel.getGameId();
    }

    private Long modelTeamATeamId(MatchModel matchModel) {
        TeamModel teamA = matchModel.getTeamA();
        if ( teamA == null ) {
            return null;
        }
        return teamA.getTeamId();
    }

    private Long modelTeamBTeamId(MatchModel matchModel) {
        TeamModel teamB = matchModel.getTeamB();
        if ( teamB == null ) {
            return null;
        }
        return teamB.getTeamId();
    }

    private Long modelWinnerTeamId(MatchModel matchModel) {
        TeamModel winner = matchModel.getWinner();
        if ( winner == null ) {
            return null;
        }
        return winner.getTeamId();
    }

    private Long modelMapGameMapId(MatchModel matchModel) {
        GameMapModel map = matchModel.getMap();
        if ( map == null ) {
            return null;
        }
        return map.getGameMapId();
    }

    protected GameModel matchDTOToGameModel(MatchDTO matchDTO) {
        if ( matchDTO == null ) {
            return null;
        }

        GameModel gameModel = new GameModel();

        gameModel.setGameId( matchDTO.getGameId() );

        return gameModel;
    }

    protected TeamModel matchDTOToTeamModel(MatchDTO matchDTO) {
        if ( matchDTO == null ) {
            return null;
        }

        TeamModel teamModel = new TeamModel();

        teamModel.setTeamId( matchDTO.getTeamAId() );

        return teamModel;
    }

    protected TeamModel matchDTOToTeamModel1(MatchDTO matchDTO) {
        if ( matchDTO == null ) {
            return null;
        }

        TeamModel teamModel = new TeamModel();

        teamModel.setTeamId( matchDTO.getTeamBId() );

        return teamModel;
    }

    protected TeamModel matchDTOToTeamModel2(MatchDTO matchDTO) {
        if ( matchDTO == null ) {
            return null;
        }

        TeamModel teamModel = new TeamModel();

        teamModel.setTeamId( matchDTO.getWinnerId() );

        return teamModel;
    }

    protected GameMapModel matchDTOToGameMapModel(MatchDTO matchDTO) {
        if ( matchDTO == null ) {
            return null;
        }

        GameMapModel gameMapModel = new GameMapModel();

        gameMapModel.setGameMapId( matchDTO.getMapId() );

        return gameMapModel;
    }
}
