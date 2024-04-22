package io.kawoolutions.bbstats.entity;

import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "Scores")
@IdClass(ScoreId.class)
@NamedEntityGraph(name = Score.FETCH_PLAYER_STATS,
                  attributeNodes = {@NamedAttributeNode(value = "playerStats", subgraph = PlayerStat.FETCH_STATS)},
                  subgraphs = @NamedSubgraph(name = PlayerStat.FETCH_STATS, attributeNodes = @NamedAttributeNode("stats"))
)
public class Score extends BaseEntity<ScoreId>
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_CLUB_AND_STATS = "Score.fetchClubAndStats";
    public static final String FETCH_PLAYER_STATS = "Score.fetchPlayerStats";

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Id
    @Column(name = "is_home")
    private Boolean home;

    @Basic
    @Column(name = "final_score")
    private Integer finalScore;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    @JsonIgnore
    private Game game;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id")
    @JsonIgnore
    private Roster roster;

    @OneToMany(mappedBy = "score")
    @MapKey(name = "jerseyNbr")
    @OrderBy("starter DESC, jerseyNbr")
    @JsonIgnore
    private Map<Integer, PlayerStat> playerStats;

    public Score()
    {
    }

    public Score(Score s)
    {
        this(s.getGameId(), s.getHome(), s.getRosterId(), s.getFinalScore());
    }

    public Score(Integer finalScore)
    {
        this(null, null, null, finalScore);
    }

    public Score(Integer gameId, Boolean home)
    {
        this(gameId, home, null);
    }

    public Score(Integer gameId, Boolean home, Integer rosterId)
    {
        this(gameId, home, rosterId, null);
    }

    public Score(Integer gameId, Boolean home, Integer rosterId, Integer finalScore)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.home = Objects.requireNonNull(home);
        this.finalScore = finalScore;

        this.game = new Game();
        this.game.setId(gameId);

        this.roster = new Roster();
        this.roster.setId(rosterId);
    }

    @Override
    public ScoreId getPk()
    {
        return new ScoreId(gameId, home);
    }

    @Override
    public void setPk(ScoreId pk)
    {
        this.gameId = pk.getGameId();
        this.home = pk.getHome();
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    public Boolean getHome()
    {
        return home;
    }

    public void setHome(Boolean home)
    {
        this.home = home;
    }

    public Integer getRosterId()
    {
        return roster.getId();
    }

    public void setRosterId(Integer rosterId)
    {
        roster.setId(rosterId);
    }

    public Integer getFinalScore()
    {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore)
    {
        this.finalScore = finalScore;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public Roster getRoster()
    {
        return roster;
    }

    public void setRoster(Roster roster)
    {
        this.roster = roster;
    }

    public Map<Integer, PlayerStat> getPlayerStats()
    {
        return playerStats;
    }

    public void setPlayerStats(Map<Integer, PlayerStat> playerStats)
    {
        this.playerStats = playerStats;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (home == null) ? 0 : home.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Score other = ( Score ) obj;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
            return false;
        if ( home == null )
        {
            if ( other.home != null )
                return false;
        }
        else if ( !home.equals( other.home ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + gameId + ", " + home + ", " + finalScore + "]";
    }
}
