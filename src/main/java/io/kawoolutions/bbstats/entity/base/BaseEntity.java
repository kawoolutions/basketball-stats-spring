package io.kawoolutions.bbstats.entity.base;

import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntity<K> implements Entity<K> {
    private static final long serialVersionUID = 1L;

    @Transient
    @JsonIgnore
    private Integer sequentialNumber;

    @Transient
    @JsonIgnore
    private LifecycleState lifecycleState = LifecycleState.LIVE;

    protected BaseEntity() {
    }

    protected BaseEntity(K pk) {
        setPk(pk);
    }

    @Override
    public Integer getSequentialNumber() {
        return sequentialNumber;
    }

    @Override
    public void setSequentialNumber(Integer sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }

    @Override
    public boolean isNewEntity() {
        return getLifecycleState() == LifecycleState.NEW;
    }

    @Override
    public void setNewEntity() {
        setLifecycleState(LifecycleState.NEW);
    }

    @Override
    public boolean isLiveEntity() {
        return getLifecycleState() == LifecycleState.LIVE;
    }

    @Override
    @PostLoad // TODO: doesn't work on Hibernate?
    public void setLiveEntity() {
        setLifecycleState(LifecycleState.LIVE);
    }

    @Override
    public boolean isDeadEntity() {
        return getLifecycleState() == LifecycleState.DEAD;
    }

    @Override
    public void setDeadEntity() {
        setLifecycleState(LifecycleState.DEAD);
    }

    @Override
    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public void setLifecycleState(LifecycleState lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    // @Override
    // public int hashCode()
    // {
    // final int prime = 31;
    // int result = 1;
    //
    // K pk = getPk();
    // result = prime * result + ( pk == null ? 0 : pk.hashCode() );
    //
    // return result;
    // }
    //
    // @Override
    // public boolean equals( Object obj )
    // {
    // if ( this == obj )
    // {
    // return true;
    // }
    //
    // if ( obj == null )
    // {
    // return false;
    // }
    //
    // if ( getClass() != obj.getClass() )
    // {
    // return false;
    // }
    //
    // @SuppressWarnings( "unchecked" )
    // Entity<K> other = ( Entity<K> ) obj;
    // K pk = getPk();
    //
    // if ( pk == null )
    // {
    // if ( other.getPk() != null )
    // {
    // return false;
    // }
    // }
    // else if ( !pk.equals( other.getPk() ) )
    // {
    // return false;
    // }
    //
    // return true;
    // }
    //
    // /**
    // * Return a string of the form "Competition[pk=4711|REGULAR_SEASON]".
    // */
    // @Override
    // public String toString()
    // {
    // return EntityUtils.getPkStringFor( this );
    // }
}
