package com.micro.discussions.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "advertisement")
public interface ApiClient {
    @GetMapping("/api/advertisement/{discussion}")
    Map<String, Object> getAdvertisement(@PathVariable long discussion,
                                         @RequestHeader(value = "X-User-Role", required = false) String role);
}
