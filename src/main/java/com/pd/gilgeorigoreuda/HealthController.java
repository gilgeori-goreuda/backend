package com.pd.gilgeorigoreuda;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public String health() {
		return "OK";
	}

	@GetMapping("/hana")
	public String hana() {
		return "hana-heo";
	}

}
