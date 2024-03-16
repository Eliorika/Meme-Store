package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.usecase.ifc.TenantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantsController {

    private final TenantUseCase tenantUseCase;

    @GetMapping("/me")
    public ResponseEntity<BasicApiResponse<?>> getTenant() {
        return new ResponseEntity<>(
                new BasicApiResponse<>(false, tenantUseCase.getTenant()),
                HttpStatus.OK
        );
    }
}