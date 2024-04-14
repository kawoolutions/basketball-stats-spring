package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Users\"")
@NamedQuery(name = User.FIND_ALL, query = "SELECT us FROM User us ORDER BY us.name")
@NamedQuery(name = User.FIND_BY_NAME, query = "SELECT us FROM User us LEFT JOIN FETCH us.person WHERE us.name = :name")
@NamedEntityGraph(name = User.FETCH_PERSON, attributeNodes = @NamedAttributeNode("person"))
public class User extends BaseEntity<String>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_BY_NAME = "User.findByName";
    public static final String FETCH_PERSON = "User.fetchPerson";

    @Id
    @Column
    private String name;

    @Basic(optional = false)
    @Column
    private String password;

    @Basic
    @Column(name = "new_password")
    private String newPassword;

    @Basic
    @Column
    private String salt;

    @Basic(optional = false)
    @Column(name = "is_enabled")
    private Boolean enabled = Boolean.TRUE;

    @Basic(optional = false)
    @Column(name = "locale_code")
    private String localeCode = "de";

    @Basic
    @Column(name = "theme_name")
    private String themeName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonbTransient
    private Person person;

    @ManyToMany
    @JoinTable(name = "UserRoleLinks", joinColumns = @JoinColumn(name = "user_name"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonbTransient
    private List<Role> roles;

    public User()
    {
    }

    public User(User u)
    {
        this(u.getName(), u.getPersonId(), u.getPassword(), u.getNewPassword(), u.getSalt(), u.getThemeName());

        this.enabled = u.getEnabled();
        this.localeCode = u.getLocaleCode();
    }

    public User(String name)
    {
        this(name, (Integer) null);
    }

    public User(String name, Integer personId)
    {
        this(name, personId, null, null, null, null);
    }

    public User(String name, String password)
    {
        this(name, null, password, null, null, null);
    }

    public User(String password, String newPassword, String salt, String themeName)
    {
        this(null, null, password, newPassword, salt, themeName);
    }

    public User(String name, Integer personId, String password, String newPassword, String salt, String themeName)
    {
        this.name = Objects.requireNonNull(name);
        this.password = password;
        this.newPassword = newPassword;
        this.salt = salt;
        this.themeName = themeName;

        this.person = new Person();
        this.person.setId(personId);
    }

    @Override
    public String getPk()
    {
        return name;
    }

    @Override
    public void setPk(String pk)
    {
        this.name = pk;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getPersonId()
    {
        return person.getId();
    }

    public void setPersonId(Integer personId)
    {
        person.setId(personId);
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getLocaleCode()
    {
        return localeCode;
    }

    public void setLocaleCode(String localeCode)
    {
        this.localeCode = localeCode;
    }

    public String getThemeName()
    {
        return themeName;
    }

    public void setThemeName(String themeName)
    {
        this.themeName = themeName;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (name == null) ? 0 : name.hashCode() );
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
        User other = ( User ) obj;
        if ( name == null )
        {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + name + ", " + password + ", " + newPassword + ", " + salt + ", " + enabled + ", " + localeCode + ", " + themeName + "]";
    }
}
