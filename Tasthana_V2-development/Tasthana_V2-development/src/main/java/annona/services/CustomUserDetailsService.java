package annona.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import annona.dao.EndUserDAO;
import annona.domain.EndUser;
import annona.domain.Role;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	EndUserDAO endUser;

	/**
	 * Returns a populated {@link UserDetails} object. The username is first
	 * retrieved from the database and then mapped to a {@link UserDetails} object.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<EndUser> domainUser = endUser.findByUsername(username).getResultList();

		boolean enabled = true;
		boolean accountNonExpired = true;

		if (domainUser.get(0).getStartDate() != null) {
			if (DateService.compareDate(domainUser.get(0).getStartDate(), DateService.getCurrentDate()) < 0) {
				accountNonExpired = false;
			}

		}

		if (domainUser.get(0).getAccExpiryDate() != null) {

			if (DateService.compareDate(DateService.getCurrentDateTime(), domainUser.get(0).getAccExpiryDate()) < 0)

			{
				accountNonExpired = false;
			}
		}

		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		if (!domainUser.get(0).getStatus().equals("Approved")) {
			accountNonLocked = false;
		}

		return new User(domainUser.get(0).getUserName(), domainUser.get(0).getPassword().toLowerCase(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				getAuthorities(domainUser.get(0).getRoles()));

	}

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * 
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 * 
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(List<Role> role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(role);
		return authList;
	}

	/**
	 * Converts a numerical role to an equivalent list of roles
	 * 
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	/*
	 * public List<String> getRoles(Integer role) { List<String> roles = new
	 * ArrayList<String>();
	 * 
	 * if (role.intValue() == 1) { roles.add("ROLE_ADMIN");
	 * 
	 * } else if (role.intValue() == 2) { roles.add("ROLE_BANKEMP"); } else if
	 * (role.intValue() == 3) { roles.add("ROLE_APPROVALMNG"); } else if
	 * (role.intValue() == 4) { roles.add("ROLE_USER"); }else if (role.intValue() ==
	 * 5) { roles.add("ROLE_VP"); } // } else if (role.intValue() == 5) { //
	 * roles.add("ROLE_USERBRANCH"); // } else if (role.intValue() == 6) { //
	 * roles.add("ROLE_USERSUBSIDIARY"); // } else if (role.intValue() == 7) { //
	 * roles.add("ROLE_VENDOR"); // } else if (role.intValue() == 8) { //
	 * roles.add("ROLE_BUYER"); // } else if (role.intValue() == 9) { //
	 * roles.add("ROLE_CUSTOMERADMIN"); // } else if (role.intValue() == 10) { //
	 * roles.add("ROLE_CUSTOMERAPPMNG"); // } else if (role.intValue() == 11) { //
	 * roles.add("ROLE_VP"); // }
	 * 
	 * return roles; }
	 */

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * 
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { // TODO Auto-generated method stub return null; }
	 */
}
