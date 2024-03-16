package br.api.backend.controller;

import br.api.backend.JwtService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping(path = "/token", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@RequestBody Map<String, Object> claims) {
        
        System.out.println(claims);
        System.out.println(claims.get("user"));
        if ((claims.get("user").equals("hugo")) && (claims.get("pass").equals("1234"))) {
            return ResponseEntity.ok(jwtService.generateJWT(claims));
        } 
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado");
    }

}
