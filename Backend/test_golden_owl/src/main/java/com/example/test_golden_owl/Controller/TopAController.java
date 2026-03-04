package com.example.test_golden_owl.Controller;

import com.example.test_golden_owl.Service.TopAService;
import com.example.test_golden_owl.dto.Response.ApiResponse;
import com.example.test_golden_owl.dto.Response.TopResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/topA")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopAController {
    TopAService topAService;
    @GetMapping
    public ApiResponse<List<TopResponse>> searchTopA(){
        return ApiResponse.<List<TopResponse>>builder()
                .message("successfully")
                .result(topAService.topA())
                .build();
    }
}
