package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Assignments\"")
@IdClass(AssignmentId.class)
public class Assignment extends BaseEntity<AssignmentId>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "referee_id")
    private Integer refereeId;

    @Id
    @Column(name = "club_id")
    private Integer clubId;

    @Id
    @Column(name = "season_start_year")
    private Integer seasonStartYear;

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Basic
    @Column(name = "was_absent")
    private Boolean wasAbsent = Boolean.FALSE;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    @JsonIgnore
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_club_id")
    @JsonIgnore
    private Club ownerClub;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "referee_id", referencedColumnName = "referee_id", insertable = false, updatable = false)
    @JoinColumn(name = "club_id", referencedColumnName = "club_id", insertable = false, updatable = false)
    @JoinColumn(name = "season_start_year", referencedColumnName = "season_start_year", insertable = false, updatable = false)
    @JsonIgnore
    private RefpoolMember refpoolMember;

    public Assignment()
    {
    }

    public Assignment(Assignment a)
    {
        this(a.getRefereeId(), a.getClubId(), a.getSeasonStartYear(), a.getGameId(), a.getOwnerClubId());

        this.wasAbsent = a.getWasAbsent();
    }

    public Assignment(Integer refereeId, Integer clubId, Integer seasonStartYear, Integer gameId)
    {
        this(refereeId, clubId, seasonStartYear, gameId, null);
    }

    public Assignment(Integer refereeId, Integer clubId, Integer seasonStartYear, Integer gameId, Integer ownerClubId)
    {
        this.refereeId = Objects.requireNonNull(refereeId);
        this.clubId = Objects.requireNonNull(clubId);
        this.seasonStartYear = Objects.requireNonNull(seasonStartYear);
        this.gameId = Objects.requireNonNull(gameId);

        this.game = new Game();
        this.game.setId(gameId);

        this.ownerClub = new Club();
        this.ownerClub.setId(ownerClubId);

        this.refpoolMember = new RefpoolMember(refereeId, clubId, seasonStartYear);
    }

    @Override
    public AssignmentId getPk()
    {
        return new AssignmentId(refereeId, clubId, seasonStartYear, gameId);
    }

    @Override
    public void setPk(AssignmentId pk)
    {
        this.refereeId = pk.getRefereeId();
        this.clubId = pk.getClubId();
        this.seasonStartYear = pk.getSeasonStartYear();
        this.gameId = pk.getGameId();
    }

    public Integer getRefereeId()
    {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId)
    {
        this.refereeId = refereeId;
    }

    public Integer getClubId()
    {
        return clubId;
    }

    public void setClubId(Integer clubId)
    {
        this.clubId = clubId;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        this.seasonStartYear = seasonStartYear;
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    public Integer getOwnerClubId()
    {
        return ownerClub.getId();
    }

    public void setOwnerClubId(Integer ownerClubId)
    {
        ownerClub.setId(ownerClubId);
    }

    public Boolean getWasAbsent()
    {
        return wasAbsent;
    }

    public void setWasAbsent(Boolean wasAbsent)
    {
        this.wasAbsent = wasAbsent;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public Club getOwnerClub()
    {
        return ownerClub;
    }

    public void setOwnerClub(Club ownerClub)
    {
        this.ownerClub = ownerClub;
    }

    public RefpoolMember getRefpoolMember()
    {
        return refpoolMember;
    }

    public void setRefpoolMember(RefpoolMember refpoolMember)
    {
        this.refpoolMember = refpoolMember;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (clubId == null) ? 0 : clubId.hashCode() );
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (refereeId == null) ? 0 : refereeId.hashCode() );
        result = prime * result + ( (seasonStartYear == null) ? 0 : seasonStartYear.hashCode() );
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
        Assignment other = ( Assignment ) obj;
        if ( clubId == null )
        {
            if ( other.clubId != null )
                return false;
        }
        else if ( !clubId.equals( other.clubId ) )
            return false;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
            return false;
        if ( refereeId == null )
        {
            if ( other.refereeId != null )
                return false;
        }
        else if ( !refereeId.equals( other.refereeId ) )
            return false;
        if ( seasonStartYear == null )
        {
            if ( other.seasonStartYear != null )
                return false;
        }
        else if ( !seasonStartYear.equals( other.seasonStartYear ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + refereeId + ", " + clubId + ", " + seasonStartYear + ", " + gameId + ", " + wasAbsent + "]";
    }
}
