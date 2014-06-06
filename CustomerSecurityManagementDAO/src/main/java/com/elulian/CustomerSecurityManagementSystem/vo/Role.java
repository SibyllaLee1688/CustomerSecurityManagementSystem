package com.elulian.CustomerSecurityManagementSystem.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@NamedQueries({
    @NamedQuery(
            name = "findRoleByName",
            query = "select r from Role r where r.name = :name "
    )
})
public class Role extends BaseObject implements Serializable, GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	public Integer getId() {
		return id;
	}

	@Basic(optional = false)
	@Column(nullable = false, length = 50, unique = true)
	private String name;
	@Column(length = 150)
	private String description;

	
	/*
	 * without this, when delete role, relationship in database
	 * is not deleted, dirty data remains
	 * unless manually remove them during remove role
	 * the other idea is to stop remove role unless all the 
	 * users are not bind to this role */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<UserInfo> users; // = new HashSet<UserInfo>();
	
	public Set<UserInfo> getUsers() {
		return users;
	}

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Role() {
	}

	/**
	 * Create a new instance and set the name.
	 * 
	 * @param name
	 *            name of the role.
	 */
	public Role(final String name) {
		this.name = name;
	}

	/**
	 * @return the name property (getAuthority required by Acegi's
	 *         GrantedAuthority interface)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Transient
	public String getAuthority() {
		return getName();
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	/*
	 * public void setId(Long id) { this.id = id; }
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {

		/*
		 * if(null == o) return false;
		 */

		if (this == o)
			return true;

		if (!(o instanceof Role)) {
			return false;
		}

		final Role role = (Role) o;

		return !(name != null ? !name.equals(role.name) : role.name != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return new StringBuilder().append(this.getClass().getCanonicalName())
				.append(this.name).toString();
	}

}
