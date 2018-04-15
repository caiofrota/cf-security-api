package com.cftechsol.security.examples.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;
import com.cftechsol.security.examples.entities.ExampleUser;
import com.cftechsol.security.examples.services.ExampleService;

/**
 * Example secured controller to run the tests.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/example", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExampleSecuredController extends GenericSecuredController<ExampleService, ExampleUser, Long> {

	private String testOk = "{\"test\":\"ok\"}";

	public ExampleSecuredController() {
		super("EXAMPLE");
	}

	@GetMapping(path = "/test")
	public String test() {
		this.hasAnyRole("ONE_ROLE", "TEST_ROLE");
		return testOk;
	}
	
	@GetMapping(path = "/allRoles")
	public String allRoles() {
		this.hasAllRoles("ONE_ROLE", "TEST_ROLE");
		return testOk;
	}

}
