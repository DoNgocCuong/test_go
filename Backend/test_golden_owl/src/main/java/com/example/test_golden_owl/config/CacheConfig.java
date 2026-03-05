package com.example.test_golden_owl.config;

import com.example.test_golden_owl.Service.CheckScoreService;
import com.example.test_golden_owl.Service.TopAService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheConfig implements ApplicationRunner {
    private final TopAService topAService;

    @Override
    public void run(ApplicationArguments args) {
        topAService.topA();
    }
}
