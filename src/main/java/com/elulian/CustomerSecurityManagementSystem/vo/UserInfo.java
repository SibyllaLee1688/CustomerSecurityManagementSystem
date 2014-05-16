package com.elulian.CustomerSecurityManagementSystem.vo;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "userinfo", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "email"}))
public class UserInfo extends BaseObject implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6013770907577732201L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 50)
	private String realname;
	@Basic(optional = false)
	@Column(nullable = false, name = "name", length = 50)
	private String username;
	@Basic(optional = false)
	@Column(nullable = false, length = 50)
	private String password;
	@Basic(optional = false)
	@Column(nullable = false, length = 50)
	private String branch;
	@Basic(optional = false)
	@Column(nullable = false)
	private String passwordHint;
	@Basic(optional = false)
	@Column(nullable = false, length = 50)
	private String email;
	@Basic(optional = false)
	@Column(nullable = false, length = 20)
	private String phoneNumber;
	@Basic(optional = false)
	@Column(nullable = false)
	private Date registerTime;
	
	@Basic(optional = false)
	@Column(nullable = false, name="accountEnabled")
	private boolean enabled;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean accountExpired;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean accountLocked;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean credentialsExpired;
	@Basic(optional = false)
	@Column(nullable = false)
	private Date expiredTime;
	
	@Transient
	private String confirmPassword;
	
	@Transient
	private Set<Role> roles = new HashSet<Role>();

	public UserInfo() {

	}
	
	public UserInfo(Integer id, String realname, String username,
			String password, String branch, String passwordHint, String email,
			String phoneNumber, Date registerTime, boolean enabled,
			boolean accountExpired, boolean accountLocked,
			boolean credentialsExpired, Date expiredTime) {
		super();
		this.id = id;
		this.realname = realname;
		this.username = username;
		this.password = password;
		this.branch = branch;
		this.passwordHint = passwordHint;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.registerTime = registerTime;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.expiredTime = expiredTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
		authorities.addAll(getRoles());
		return authorities;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		
		if(roles.isEmpty()){
			if(null == branch)
				roles.add(new Role("ROLE_ANONYMOUS"));
			if("ALL".equalsIgnoreCase(branch)){
				roles.add(new Role("ROLE_ADMIN"));
			} else
				roles.add(new Role("ROLE_USER"));			
		}
		
		return roles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role) {
		getRoles().add(role);
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserInfo)) {
			return false;
		}

		final UserInfo user = (UserInfo) o;

		return !(username != null ? !username.equals(user.getUsername()) : user
				.getUsername() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getCanonicalName()).append("username")
				.append(this.username).append("enabled").append(this.enabled)
				.append("accountExpired").append(this.accountExpired)
				.append("credentialsExpired").append(this.credentialsExpired)
				.append("accountLocked").append(this.accountLocked);

		if (roles != null) {
			sb.append("Granted Authorities: ");

			int i = 0;
			for (Role role : roles) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(role.toString());
				i++;
			}
		} else {
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}
}
