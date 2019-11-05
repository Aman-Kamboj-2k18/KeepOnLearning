package annona.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class BlackListFilter extends OncePerRequestFilter {

	protected static Logger logger = Logger.getLogger("filter");

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.debug("Running blacklist filter");

		// Retrieve user details
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		// Filter only if user details is not empty; otherwise there's nothing
		// to filter
		if (authentication != null) {

			// If the username is equal to mike, deny access
			if (authentication.getName().equals("mike") == true) {
				logger.error("Username and password match. Access denied!");
				throw new AccessDeniedException(
						"Username and password match. Access denied!");
			}

		}

		// User details are not empty
		logger.debug("Continue with remaining filters");
		filterChain.doFilter(request, response);
	}

}
