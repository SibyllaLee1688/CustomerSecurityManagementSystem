package com.elulian.CustomerSecurityManagementSystem.vo;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class Role extends BaseObject implements Serializable, GrantedAuthority {

	private Long id;
	private String name;
	private String description;

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

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO) public Long getId() {
	 * return id; }
	 *//**
	 * @return the name property (getAuthority required by Acegi's
	 *         GrantedAuthority interface)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */

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
