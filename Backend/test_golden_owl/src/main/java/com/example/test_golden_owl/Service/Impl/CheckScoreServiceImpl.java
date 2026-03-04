    package com.example.test_golden_owl.Service.Impl;

    import com.example.test_golden_owl.Repository.DiemThiRepository;
    import com.example.test_golden_owl.Repository.MonThiRepository;
    import com.example.test_golden_owl.Repository.NgoaiNguRepository;
    import com.example.test_golden_owl.Service.CheckScoreService;
    import com.example.test_golden_owl.dto.Response.CheckScoreResponse;
    import com.example.test_golden_owl.entity.DiemThi;
    import com.example.test_golden_owl.entity.MonThi;
    import com.example.test_golden_owl.entity.NgoaiNgu;
    import com.example.test_golden_owl.exception.AppException;
    import com.example.test_golden_owl.exception.ErrorCode;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Slf4j
    public class CheckScoreServiceImpl implements CheckScoreService {
        DiemThiRepository diemThiRepository;
        @Override
        public CheckScoreResponse checkScore(String sbd) {


            List<DiemThi> diemThis = diemThiRepository.findAllBySbd(sbd);
            if(diemThis==null)
                throw new AppException(ErrorCode.CANDIDATE_NOT_EXIST);

            if (diemThis.isEmpty()) {
                throw new AppException(ErrorCode.CANDIDATE_NOT_EXIST);
            }

            CheckScoreResponse response = CheckScoreResponse.builder()
                    .sbd(sbd)
                    .build();

            for (DiemThi diemThi : diemThis) {

                String tenMon = diemThi.getMonThi().getTenMon();
                Double diem = diemThi.getDiem();

                switch (tenMon) {
                    case "Toán" -> response.setToan(diem);
                    case "Ngữ Văn" -> response.setNguVan(diem);
                    case "Vật Lý" -> response.setVatLi(diem);
                    case "Hóa Học" -> response.setHoaHoc(diem);
                    case "Sinh Học" -> response.setSinhHoc(diem);
                    case "Lịch Sử" -> response.setLichSu(diem);
                    case "Địa Lý" -> response.setDiaLi(diem);
                    case "GDCD" -> response.setGdcd(diem);
                    case "Ngoại Ngữ" -> {
                        response.setNgoaiNgu(diem);

                        if (diemThi.getNgoaiNgu() != null) {
                            response.setMaNgoaiNgu(
                                    diemThi.getNgoaiNgu().getMaNgoaiNgu()
                            );
                        }
                    }
                }
            }

            return response;
        }
    }
