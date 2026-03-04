package com.example.test_golden_owl.Controller;

import com.example.test_golden_owl.Service.CheckScoreService;
import com.example.test_golden_owl.dto.Response.ApiResponse;
import com.example.test_golden_owl.dto.Response.CheckScoreResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/checkScore")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckScoreController {
    CheckScoreService checkScoreService;
    @GetMapping("/{sbd}")
    public ApiResponse<CheckScoreResponse> checkScore(@PathVariable String sbd){
        return ApiResponse.<CheckScoreResponse>builder()
                .message("successfully")
                .result(checkScoreService.checkScore(sbd))
                .build();
    }
}
