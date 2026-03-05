package com.example.test_golden_owl.Service.Impl;

import com.example.test_golden_owl.Repository.DiemThiRepository;
import com.example.test_golden_owl.Repository.MonThiRepository;
import com.example.test_golden_owl.Service.TopAService;
import com.example.test_golden_owl.dto.Response.TopResponse;

import com.example.test_golden_owl.exception.AppException;
import com.example.test_golden_owl.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TopAServiceImpl implements TopAService {

    DiemThiRepository diemThiRepository;
    MonThiRepository monThiRepository;


    @Override
    @Cacheable(value = "topKhoiA")
    public List<TopResponse> topA() {
        Long toanId = monThiRepository.findByTenMon("Toán")
                .orElseThrow(()-> new AppException(ErrorCode.SUBJECT_NOT_EXIST)).getId();

        Long lyId = monThiRepository.findByTenMon("Vật Lý")
                .orElseThrow(()-> new AppException(ErrorCode.SUBJECT_NOT_EXIST)).getId();

        Long hoaId = monThiRepository.findByTenMon("Hóa Học")
                .orElseThrow(()-> new AppException(ErrorCode.SUBJECT_NOT_EXIST)).getId();
        Pageable pageable = PageRequest.of(0, 10);
        return diemThiRepository.topKhoiA(toanId, lyId, hoaId, pageable);
    }

}