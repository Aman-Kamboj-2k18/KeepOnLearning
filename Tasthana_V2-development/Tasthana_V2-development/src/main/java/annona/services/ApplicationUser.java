package annona.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ApplicationUser extends User {
	private static final long serialVersionUID = 1L;
    private final String organization;
    public ApplicationUser(String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<GrantedAuthority> authorities,
        String organization) 
    {
            super(username, password, enabled, accountNonExpired,
               credentialsNonExpired, accountNonLocked, authorities);
            
            
            this.organization = organization;
    }
	public String getOrganization()
	{
		return organization;
	}
}
