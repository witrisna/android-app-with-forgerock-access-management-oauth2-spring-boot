package com.forgerock.demo.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/products")
@Validated
public class ProductController {

  @PreAuthorize("#oauth2.hasScope('MY_CUSTOM_SCOPE')")
  @GetMapping
  public ResponseEntity<?> products() {
    return new ResponseEntity<>("product endpoint", HttpStatus.OK);
  }

}
