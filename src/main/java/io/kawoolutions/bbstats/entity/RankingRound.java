package io.kawoolutions.bbstats.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "RankingRounds")
@DiscriminatorValue("RANKING_ROUND")
public class RankingRound extends Round
{
    private static final long serialVersionUID = 1L;

    public RankingRound()
    {
    }

    public RankingRound(RankingRound r)
    {
        this(r.getNbr());
    }

    public RankingRound(String nbr)
    {
        super(nbr);
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + "]";
    }
}
