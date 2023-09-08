package com.fiona.savingsManagementApi.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info =
        @Info(
                title = "Savings management API",
                version="1.0",
                description = "Documentation for savings API ",
                license = @License()

        )
)
public class ApiDocConfig {
}
