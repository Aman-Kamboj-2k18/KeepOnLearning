package annona.filters;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

public class SingleEntryFilter extends OncePerRequestFilter {

	protected static Logger logger = Logger.getLogger("service");

	private String redirectURI;

	private List<String> guardURI;

	@Resource(name = "sessionRegistry")
	private SessionRegistry sessionRegistry;

	// Redirect strategy implementation as copied from the
	// ConcurrentSessionFilter
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.debug("Running blacklist filter");

		HttpSession session = request.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry
					.getSessionInformation(session.getId());

			if (info != null) {

				// Flag to indicate if user has already logged-in and session is
				// still valid
				Boolean hasLoggedIn = (Boolean) session
						.getAttribute("hasLoggedIn");

				if (hasLoggedIn != null) {
					logger.debug("User is trying to access site for the second time");
					logger.debug("Request URI: " + request.getRequestURI());

					// Loop list. We might have multiple URIs to guard for!
					for (String guard : guardURI) {
						if (request.getRequestURI().equals(guard) == true) {

							logger.debug("Redirecting");

							// Force the user to the main page
							redirectStrategy.sendRedirect(request, response,
									redirectURI);

						}
					}

				} else {
					// User is accessing site for the first time
					// Either he's old session has expired or he purposely
					// logged-out
					logger.debug("User is accessing the site for the first time");

					// This will only be removed once the session has expired
					// If it doesn't expire the user will not be able to login
					// again!
					session.setAttribute("hasLoggedIn", new Boolean(true));

					// Refreshes the internal lastRequest to the current date
					// and time.
					info.refreshLastRequest();
				}
			} else {
				logger.debug("Info is null");
			}
		} else {
			logger.debug("Session is null");
		}

		logger.debug("Continue with remaining filters");
		filterChain.doFilter(request, response);
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}

	public List<String> getGuardURI() {
		return guardURI;
	}

	public void setGuardURI(List<String> guardURI) {
		this.guardURI = guardURI;
	}

}
