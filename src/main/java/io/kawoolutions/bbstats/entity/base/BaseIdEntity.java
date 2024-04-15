package io.kawoolutions.bbstats.entity.base;

public abstract class BaseIdEntity extends BaseEntity<Integer> implements IdEntity {
    private static final long serialVersionUID = 1L;

    protected BaseIdEntity() {
        super();
    }

    protected BaseIdEntity(Integer id) {
        super(id);
    }

    @Override
    public Integer getPk() {
        return getId();
    }

    @Override
    public void setPk(Integer pk) {
        setId(pk);
    }

//    @Override
//    public int hashCode()
//    {
//        final int prime = 31;
//        int result = 1;
//        Integer id = getId();
//        result = prime * result + ( id == null ? 0 : id.hashCode() );
//        return result;
//    }
//
//    @Override
//    public boolean equals( Object obj )
//    {
//        if ( this == obj )
//        {
//            return true;
//        }
//        if ( obj == null )
//        {
//            return false;
//        }
//        if ( getClass() != obj.getClass() )
//        {
//            return false;
//        }
//
//        IdEntity other = ( IdEntity ) obj;
//        Integer id = getId();
//        if ( id == null )
//        {
//            if ( other.getId() != null )
//            {
//                return false;
//            }
//        }
//        else if ( !id.equals( other.getId() ) )
//        {
//            return false;
//        }
//        return true;
//    }
}
