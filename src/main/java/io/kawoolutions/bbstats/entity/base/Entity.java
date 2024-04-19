package io.kawoolutions.bbstats.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface Entity<K> extends Serializable {
    public K getPk();

    public void setPk(K pk);

    public Integer getSequentialNumber();

    public void setSequentialNumber(Integer sequentialNumber);

    public void setLifecycleState(LifecycleState status);

    public LifecycleState getLifecycleState();

    @JsonIgnore
    public boolean isNewEntity();

    public void setNewEntity();

    @JsonIgnore
    public boolean isLiveEntity();

    public void setLiveEntity();

    @JsonIgnore
    public boolean isDeadEntity();

    public void setDeadEntity();
}
