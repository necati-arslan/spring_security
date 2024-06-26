package spring_security.spring_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}

@RestController
class BasicController {
	@Autowired
	private JwtUtil jwtUtil;

	// injecting authentication manager
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {
		// Creating UsernamePasswordAuthenticationToken object
		// to send it to authentication manager.
		// Attention! We used two parameters constructor.
		// It sets authentication false by doing this.setAuthenticated(false);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		// we let the manager do its job.
		authenticationManager.authenticate(token);
		// if there is no exception thrown from authentication manager,
		// we can generate a JWT token and give it to user.
		String jwt = jwtUtil.generate(request.getUsername());
		return ResponseEntity.ok(jwt);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> get(){
		return ResponseEntity.ok("Hello");
	}
}

@Data
@NoArgsConstructor
class LoginRequestDTO {
	private String username;
	private String password;
}

/*
 * This is Spring Security configuration step
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// Custom filter
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	// Custom UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void configurePasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
		// adding custom UserDetailsService and encryption bean to Authentication Manager
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// disabling csrf since we won't use form login
				.csrf().disable()

				// giving every permission to every request for /login endpoint
				.authorizeRequests().antMatchers("/login").permitAll()
				// for everything else, the user has to be authenticated
				.anyRequest().authenticated()
				// setting stateless session, because we choose to implement Rest API
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// adding the custom filter before UsernamePasswordAuthenticationFilter in the filter chain
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}

/*
 * Custom filter will run once per request. We add this to Filter Chain
 */
@Component
class JwtTokenFilter extends OncePerRequestFilter {
	// Simple JWT implementation
	@Autowired
	private JwtUtil jwtUtil;

	// Spring Security will call this method during filter chain execution
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
									HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {

		// trying to find Authorization header
		final String authorizationHeader = httpServletRequest.getHeader("Authorization");
		if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")){
			// if Authorization header does not exist, then skip this filter
			// and continue to execute next filter class
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		final String token = authorizationHeader.split(" ")[1].trim();
		if (!jwtUtil.validate(token)) {
			// if token is not valid, then skip this filter
			// and continue to execute next filter class.
			// This means authentication is not successful since token is invalid.
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		// Authorization header exists, token is valid. So, we can authenticate.
		String username = jwtUtil.getUsername(token);
		// initializing UsernamePasswordAuthenticationToken with its 3 parameter constructor
		// because it sets super.setAuthenticated(true); in that constructor.
		UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
		upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		// finally, give the authentication token to Spring Security Context
		SecurityContextHolder.getContext().setAuthentication(upassToken);

		// end of the method, so go for next filter class
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}

/*
 * Custom UserDetailsService implementation
 */
@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// This is where you should fetch the user from database.
		// We keep it simple to focus on authentication flow.
		Map<String, String> users = new HashMap<>();
		users.put("martin", passwordEncoder.encode("123"));
		if (users.containsKey(username))
			return new User(username, users.get(username), new ArrayList<>());
		// if this is thrown, then we won't generate JWT token.
		throw new UsernameNotFoundException(username);
	}
}