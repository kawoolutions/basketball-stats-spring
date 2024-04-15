package io.kawoolutions.bbstats.entity.base;

import java.io.Serializable;

public interface Entity<K> extends Serializable {
    public K getPk();

    public void setPk(K pk);

    public Integer getSequentialNumber();

    public void setSequentialNumber(Integer sequentialNumber);

    public void setLifecycleState(LifecycleState status);

    public LifecycleState getLifecycleState();

    public boolean isNewEntity();

    public void setNewEntity();

    public boolean isLiveEntity();

    public void setLiveEntity();

    public boolean isDeadEntity();

    public void setDeadEntity();
}
