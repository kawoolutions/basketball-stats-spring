package io.kawoolutions.bbstats.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EliminationRounds")
@DiscriminatorValue("ELIMINATION_ROUND")
public class EliminationRound extends Round
{
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "best_of")
    private Integer bestOf;

    public EliminationRound()
    {
    }

    public EliminationRound(EliminationRound e)
    {
        this(e.getNbr(), e.getBestOf());
    }

    public EliminationRound(Integer bestOf)
    {
        this(null, bestOf);
    }

    public EliminationRound(String nbr, Integer bestOf)
    {
        super(nbr);

        this.bestOf = bestOf;
    }

    public Integer getBestOf()
    {
        return bestOf;
    }

    public void setBestOf(Integer bestOf)
    {
        this.bestOf = bestOf;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + ", " + bestOf + "]";
    }
}
