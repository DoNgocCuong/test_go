CREATE TABLE mon_thi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ten_mon VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE ngoai_ngu (
    ma_ngoai_ngu VARCHAR(10) PRIMARY KEY
);

CREATE TABLE diem_thi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    sbd VARCHAR(8) NOT NULL,
    mon_thi_id BIGINT NOT NULL,
    diem DOUBLE NOT NULL,
    ma_ngoai_ngu VARCHAR(10),

    CONSTRAINT fk_diem_monthi
        FOREIGN KEY (mon_thi_id)
        REFERENCES mon_thi(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_diem_ngoaingu
        FOREIGN KEY (ma_ngoai_ngu)
        REFERENCES ngoai_ngu(ma_ngoai_ngu)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE UNIQUE INDEX uk_sbd_mon
ON diem_thi (sbd, mon_thi_id);