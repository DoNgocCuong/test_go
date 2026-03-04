package com.example.test_golden_owl.Service.Impl;

import com.example.test_golden_owl.Repository.DiemThiRepository;
import com.example.test_golden_owl.Service.TopAService;
import com.example.test_golden_owl.dto.Response.TopResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TopAServiceImpl implements TopAService {
    DiemThiRepository diemThiRepository;
    @Override
    public List<TopResponse> topA() {
        Pageable pageable = PageRequest.of(0, 10);
        return diemThiRepository.topKhoiA(pageable);
    }
}
