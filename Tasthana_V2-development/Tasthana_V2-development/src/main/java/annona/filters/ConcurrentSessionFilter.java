package annona.filters;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

public class ConcurrentSessionFilter {

	@Resource(name = "sessionRegistry")
	private SessionRegistry sessionRegistry;

	// Redirect strategy implementation as copied from the
	// ConcurrentSessionFilter
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry
					.getSessionInformation(session.getId());

			if (info != null) {
				if (info.isExpired()) {
					// Expired - abort processing
					doLogout(request, response);

					String targetUrl = determineExpiredUrl(request, info);

					if (targetUrl != null) {
						redirectStrategy.sendRedirect(request, response,
								targetUrl);

						return;
					} else {
						response.getWriter()
								.print("This session has been expired (possibly due to multiple concurrent "
										+ "logins being attempted as the same user).");
						response.flushBuffer();
					}

					return;
				} else {
					// Non-expired - update last request date/time
					info.refreshLastRequest();
				}
			}
		}

		chain.doFilter(request, response);
	}

	private void doLogout(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private String determineExpiredUrl(HttpServletRequest request,
			SessionInformation info) {
		// TODO Auto-generated method stub
		return null;
	}

}
