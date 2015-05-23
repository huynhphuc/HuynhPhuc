--------------------------------------------------------
--  File created - Thursday-April-24-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SEQ_BANGCAP_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_BANGCAP_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_CHINHANH_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_CHINHANH_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_GA_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_GA_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_GHENGOI_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_GHENGOI_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_GIATHUCPHAM_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_GIATHUCPHAM_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_LOAIMAYBAY_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_LOAIMAYBAY_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_PHIVCHH_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_PHIVCHH_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_THUCPHAM_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_THUCPHAM_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_TRANGTHAITG_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_TRANGTHAITG_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_TUYENBAY_AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "SEQ_TUYENBAY_AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table BANGCAP
--------------------------------------------------------

  CREATE TABLE "BANGCAP" 
   (	"MSBC" NUMBER(*,0), 
	"TENBANGCAP" VARCHAR2(100), 
	"TRUONGDAOTAO" VARCHAR2(100), 
	"NAMDAT" CHAR(4), 
	"MSNV" CHAR(12)
   ) ;
--------------------------------------------------------
--  DDL for Table CALAMVIEC
--------------------------------------------------------

  CREATE TABLE "CALAMVIEC" 
   (	"MSCLV" CHAR(2), 
	"TUGIO" CHAR(2), 
	"DENGIO" CHAR(2)
   ) ;
--------------------------------------------------------
--  DDL for Table CHINHANH
--------------------------------------------------------

  CREATE TABLE "CHINHANH" 
   (	"MSCN" NUMBER(*,0), 
	"TENCHINHANH" VARCHAR2(30), 
	"THANHPHO" VARCHAR2(30), 
	"QUOCGIA" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table CHUYENBAY
--------------------------------------------------------

  CREATE TABLE "CHUYENBAY" 
   (	"MSCB" CHAR(9), 
	"TRANGTHAI" CHAR(2), 
	"SOGHETRONG" NUMBER(*,0), 
	"THOIDIEMDI" TIMESTAMP (6), 
	"THOIDIEMDEN" TIMESTAMP (6), 
	"MSMB" CHAR(6), 
	"MSTB" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CHUYENBAYTHUCPHAM
--------------------------------------------------------

  CREATE TABLE "CHUYENBAYTHUCPHAM" 
   (	"MSCB" CHAR(9), 
	"MSTP" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CHUYENMONBD
--------------------------------------------------------

  CREATE TABLE "CHUYENMONBD" 
   (	"MSNV" CHAR(12), 
	"MSLMB" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table GA
--------------------------------------------------------

  CREATE TABLE "GA" 
   (	"MSG" NUMBER(*,0), 
	"TENSB" VARCHAR2(50), 
	"THANHPHO" VARCHAR2(50), 
	"QUOCGIA" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table GHEKHACH
--------------------------------------------------------

  CREATE TABLE "GHEKHACH" 
   (	"MSKH" CHAR(12), 
	"GHESO" CHAR(3), 
	"GIA" FLOAT(63)
   ) ;
--------------------------------------------------------
--  DDL for Table GHENGOI
--------------------------------------------------------

  CREATE TABLE "GHENGOI" 
   (	"MSG" NUMBER(*,0), 
	"GHESO" CHAR(3), 
	"LOAIGHE" VARCHAR2(10), 
	"MSLMB" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table GIATHUCPHAM
--------------------------------------------------------

  CREATE TABLE "GIATHUCPHAM" 
   (	"MSGTP" NUMBER(*,0), 
	"GIA" NUMBER(*,0), 
	"NGAYAPDUNG" DATE, 
	"MSTP" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table KHACHHANG
--------------------------------------------------------

  CREATE TABLE "KHACHHANG" 
   (	"MSKH" CHAR(12), 
	"HOTEN" VARCHAR2(50), 
	"NGAYSINH" DATE, 
	"GIOITINH" VARCHAR2(3), 
	"QUOCTICH" VARCHAR2(30), 
	"SODT" CHAR(13), 
	"DIACHI" VARCHAR2(100), 
	"MSTTTG" NUMBER(*,0), 
	"MSPHH" NUMBER(*,0), 
	"MSCB" CHAR(9), 
	"KHOILUONGVUOT" FLOAT(126)
   ) ;
--------------------------------------------------------
--  DDL for Table KHACHHANGNL
--------------------------------------------------------

  CREATE TABLE "KHACHHANGNL" 
   (	"MSKH" CHAR(12), 
	"CMND" CHAR(9), 
	"PASSPORT" CHAR(9)
   ) ;
--------------------------------------------------------
--  DDL for Table KHACHHANGTE
--------------------------------------------------------

  CREATE TABLE "KHACHHANGTE" 
   (	"MSKH" CHAR(12), 
	"THONGTINKSINH" VARCHAR2(100), 
	"MSNGH" CHAR(12)
   ) ;
--------------------------------------------------------
--  DDL for Table KIEMTRA
--------------------------------------------------------

  CREATE TABLE "KIEMTRA" 
   (	"MSNV" CHAR(12), 
	"MSCB" CHAR(9)
   ) ;
--------------------------------------------------------
--  DDL for Table LAI
--------------------------------------------------------

  CREATE TABLE "LAI" 
   (	"MSNV" CHAR(12), 
	"MSLMB" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAIMAYBAY
--------------------------------------------------------

  CREATE TABLE "LOAIMAYBAY" 
   (	"MSLMB" NUMBER(*,0), 
	"HANGSX" VARCHAR2(50), 
	"MODEL" VARCHAR2(20), 
	"SOGHEVIP" NUMBER(*,0), 
	"SOGHEPT" NUMBER(*,0), 
	"TONGSOGHE" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MAYBAY
--------------------------------------------------------

  CREATE TABLE "MAYBAY" 
   (	"MSMB" CHAR(6), 
	"TONGGIOBAY" FLOAT(63), 
	"NAMSX" NUMBER(*,0), 
	"THOIDIEMSD" DATE, 
	"MSLMB" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table NHANVIEN
--------------------------------------------------------

  CREATE TABLE "NHANVIEN" 
   (	"MSNV" CHAR(12), 
	"HOTEN" VARCHAR2(50), 
	"NGAYSINH" DATE, 
	"GIOITINH" VARCHAR2(3), 
	"QUOCTICH" VARCHAR2(30), 
	"CMND" CHAR(9), 
	"PASSPORT" CHAR(9), 
	"NGAYVAOLAM" DATE, 
	"DIACHI" VARCHAR2(100), 
	"SODT" CHAR(13), 
	"TIENLUONG" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table NHANVIENMD
--------------------------------------------------------

  CREATE TABLE "NHANVIENMD" 
   (	"MSNV" CHAR(12), 
	"MSCN" NUMBER(*,0), 
	"MSNV_TRUONG" CHAR(12)
   ) ;
--------------------------------------------------------
--  DDL for Table NVMATDAT_CALV
--------------------------------------------------------

  CREATE TABLE "NVMATDAT_CALV" 
   (	"MSNV" CHAR(12), 
	"MSCLV" CHAR(2), 
	"NGAYBATDAU" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table PHICONG
--------------------------------------------------------

  CREATE TABLE "PHICONG" 
   (	"MSNV" CHAR(12), 
	"LOAIPHICONG" CHAR(2)
   ) ;
--------------------------------------------------------
--  DDL for Table PHIVCHH
--------------------------------------------------------

  CREATE TABLE "PHIVCHH" 
   (	"MSPHH" NUMBER(*,0), 
	"LOAIVE" VARCHAR2(3), 
	"TRONGLUONGDM" FLOAT(126), 
	"DONGIA_KG" FLOAT(126), 
	"THOIDIEMAPDUNG" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table THUCPHAM
--------------------------------------------------------

  CREATE TABLE "THUCPHAM" 
   (	"MSTP" NUMBER(*,0), 
	"TEN" VARCHAR2(20), 
	"MOTA" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table TIEPVIEN
--------------------------------------------------------

  CREATE TABLE "TIEPVIEN" 
   (	"MSNV" CHAR(12), 
	"NGOAINGUTHONGTHAO" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table TRANGTHAITG
--------------------------------------------------------

  CREATE TABLE "TRANGTHAITG" 
   (	"MSTTTG" NUMBER(*,0), 
	"TENTT" CHAR(2), 
	"PHANTRAMTP" FLOAT(63)
   ) ;
--------------------------------------------------------
--  DDL for Table TUYENBAY
--------------------------------------------------------

  CREATE TABLE "TUYENBAY" 
   (	"MSTB" NUMBER(*,0), 
	"MSG_DI" NUMBER(*,0), 
	"MSG_DEN" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table VANHANH
--------------------------------------------------------

  CREATE TABLE "VANHANH" 
   (	"MSNV" CHAR(12), 
	"MSCB" CHAR(9)
   ) ;

---------------------------------------------------
--   DATA FOR TABLE BANGCAP
--   FILTER = none used
---------------------------------------------------
REM INSERTING into BANGCAP
Insert into BANGCAP (MSBC,TENBANGCAP,TRUONGDAOTAO,NAMDAT,MSNV) values (1,'C? nhân lái máy bay','H?c vi?n hàng không','2003','PC0000000001');
Insert into BANGCAP (MSBC,TENBANGCAP,TRUONGDAOTAO,NAMDAT,MSNV) values (2,'K? s? c? khí','??i h?c Bách Khoa TP.H? Chí Minh','2003','TV0000000001');
Insert into BANGCAP (MSBC,TENBANGCAP,TRUONGDAOTAO,NAMDAT,MSNV) values (3,'C? Nhân','??i h?c Kinh t?','2003','KT0000000001');
Insert into BANGCAP (MSBC,TENBANGCAP,TRUONGDAOTAO,NAMDAT,MSNV) values (4,'Ti?p viên chuyên nghi?p','H?c vi?n hàng không','2003','DH0000000001');

---------------------------------------------------
--   END DATA FOR TABLE BANGCAP
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE CALAMVIEC
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CALAMVIEC
Insert into CALAMVIEC (MSCLV,TUGIO,DENGIO) values ('C1','01','09');
Insert into CALAMVIEC (MSCLV,TUGIO,DENGIO) values ('C2','09','18');
Insert into CALAMVIEC (MSCLV,TUGIO,DENGIO) values ('C3','18','24');

---------------------------------------------------
--   END DATA FOR TABLE CALAMVIEC
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE CHINHANH
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CHINHANH
Insert into CHINHANH (MSCN,TENCHINHANH,THANHPHO,QUOCGIA) values (1,'VietNamAriline','Thành Ph? H? Chí Minh','Vi?t Nam');
Insert into CHINHANH (MSCN,TENCHINHANH,THANHPHO,QUOCGIA) values (2,'VietAriline','Viêng Ch?n','Lào');
Insert into CHINHANH (MSCN,TENCHINHANH,THANHPHO,QUOCGIA) values (3,'Jetstair','Thành Ph? H? Chí Minh','Vi?t Nam');
Insert into CHINHANH (MSCN,TENCHINHANH,THANHPHO,QUOCGIA) values (4,'LaoAriline','Viêng Ch?n','Lào');

---------------------------------------------------
--   END DATA FOR TABLE CHINHANH
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE CHUYENBAY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CHUYENBAY
Insert into CHUYENBAY (MSCB,TRANGTHAI,SOGHETRONG,THOIDIEMDI,THOIDIEMDEN,MSMB,MSTB) values ('SA0000001','CB',48,to_timestamp('15/05/2014 14:00','DD/MM/YYYY HH24:MI'),to_timestamp('15/05/2014 14:00','DD/MM/YYYY HH24:MI'),'SAP007',2);
Insert into CHUYENBAY (MSCB,TRANGTHAI,SOGHETRONG,THOIDIEMDI,THOIDIEMDEN,MSMB,MSTB) values ('SA0000002','DB',28,to_timestamp('16/05/2013 14:00','DD/MM/YYYY HH24:MI'),to_timestamp('19/05/2013 05:00','DD/MM/YYYY HH24:MI'),'SAP008',1);
Insert into CHUYENBAY (MSCB,TRANGTHAI,SOGHETRONG,THOIDIEMDI,THOIDIEMDEN,MSMB,MSTB) values ('SA0000003','CB',16,to_timestamp('22/05/2014 14:00','DD/MM/YYYY HH24:MI'),to_timestamp('24/05/2014 06:00','DD/MM/YYYY HH24:MI'),'SAP009',3);
Insert into CHUYENBAY (MSCB,TRANGTHAI,SOGHETRONG,THOIDIEMDI,THOIDIEMDEN,MSMB,MSTB) values ('SA0000004','CB',50,to_timestamp('15/08/2014 14:00','DD/MM/YYYY HH24:MI'),to_timestamp('17/08/2014 14:00','DD/MM/YYYY HH24:MI'),'SAP010',4);

---------------------------------------------------
--   END DATA FOR TABLE CHUYENBAY
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE CHUYENBAYTHUCPHAM
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CHUYENBAYTHUCPHAM
Insert into CHUYENBAYTHUCPHAM (MSCB,MSTP) values ('SA0000001',1);
Insert into CHUYENBAYTHUCPHAM (MSCB,MSTP) values ('SA0000002',2);
Insert into CHUYENBAYTHUCPHAM (MSCB,MSTP) values ('SA0000003',3);
Insert into CHUYENBAYTHUCPHAM (MSCB,MSTP) values ('SA0000004',4);

---------------------------------------------------
--   END DATA FOR TABLE CHUYENBAYTHUCPHAM
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE CHUYENMONBD
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CHUYENMONBD
Insert into CHUYENMONBD (MSNV,MSLMB) values ('KT0000000001',1);
Insert into CHUYENMONBD (MSNV,MSLMB) values ('KT0000000002',1);
Insert into CHUYENMONBD (MSNV,MSLMB) values ('KT0000000003',1);
Insert into CHUYENMONBD (MSNV,MSLMB) values ('KT0000000004',3);

---------------------------------------------------
--   END DATA FOR TABLE CHUYENMONBD
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE GA
--   FILTER = none used
---------------------------------------------------
REM INSERTING into GA
Insert into GA (MSG,TENSB,THANHPHO,QUOCGIA) values (1,'Tân S?n Nh?t','TP.H? Chí Minh','Vi?t Nam');
Insert into GA (MSG,TENSB,THANHPHO,QUOCGIA) values (2,'?à N?ng','?à N?ng','Vi?t Nam');
Insert into GA (MSG,TENSB,THANHPHO,QUOCGIA) values (3,'Mumbai','Mumbai','?n ??');
Insert into GA (MSG,TENSB,THANHPHO,QUOCGIA) values (4,'N?i Bài','Hà N?i','Vi?t Nam');

---------------------------------------------------
--   END DATA FOR TABLE GA
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE GHEKHACH
--   FILTER = none used
---------------------------------------------------
REM INSERTING into GHEKHACH
Insert into GHEKHACH (MSKH,GHESO,GIA) values ('TE0000000001','A05',300000);
Insert into GHEKHACH (MSKH,GHESO,GIA) values ('TE0000000002','B02',300000);
Insert into GHEKHACH (MSKH,GHESO,GIA) values ('NL0123456789','C11',300000);
Insert into GHEKHACH (MSKH,GHESO,GIA) values ('NL1111111112','D08',300000);

---------------------------------------------------
--   END DATA FOR TABLE GHEKHACH
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE GHENGOI
--   FILTER = none used
---------------------------------------------------
REM INSERTING into GHENGOI
Insert into GHENGOI (MSG,GHESO,LOAIGHE,MSLMB) values (1,'A05','VIP',1);
Insert into GHENGOI (MSG,GHESO,LOAIGHE,MSLMB) values (2,'B06','VIP',2);
Insert into GHENGOI (MSG,GHESO,LOAIGHE,MSLMB) values (3,'C07','VIP',3);
Insert into GHENGOI (MSG,GHESO,LOAIGHE,MSLMB) values (4,'D08','VIP',4);

---------------------------------------------------
--   END DATA FOR TABLE GHENGOI
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE GIATHUCPHAM
--   FILTER = none used
---------------------------------------------------
REM INSERTING into GIATHUCPHAM
Insert into GIATHUCPHAM (MSGTP,GIA,NGAYAPDUNG,MSTP) values (1,15000,to_timestamp('23/09/2004 00:00','DD/MM/YYYY HH24:MI'),1);
Insert into GIATHUCPHAM (MSGTP,GIA,NGAYAPDUNG,MSTP) values (2,30000,to_timestamp('15/04/2004 00:00','DD/MM/YYYY HH24:MI'),2);
Insert into GIATHUCPHAM (MSGTP,GIA,NGAYAPDUNG,MSTP) values (3,35000,to_timestamp('25/11/2005 00:00','DD/MM/YYYY HH24:MI'),3);
Insert into GIATHUCPHAM (MSGTP,GIA,NGAYAPDUNG,MSTP) values (4,50000,to_timestamp('19/03/2005 00:00','DD/MM/YYYY HH24:MI'),4);

---------------------------------------------------
--   END DATA FOR TABLE GIATHUCPHAM
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE KHACHHANG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into KHACHHANG
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('NL0123456789','Nguy?n Xuân Di?u',to_timestamp('10/05/1986 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+840167587689','326 Xô Vi?t Ngh? T?nh, p25, Bình Th?nh, TP.H? Chí Minh',1,2,'SA0000001',0);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('NL0000000001','Cù Huy C?n',to_timestamp('19/12/1988 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+840167587688','127 Xô Vi?t Ngh? T?nh, p24, Bình Th?nh, TP.H? Chí Minh',2,3,'SA0000002',2);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('NL0000000002','Lê V?n Tám',to_timestamp('23/01/1985 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+840167587799','123/14 ?inh B? L?nh, p25, Bình Th?nh, TP.H? Chí Minh',2,4,'SA0000003',3);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('NL0000000003','Nguy?n Long Giang',to_timestamp('10/09/1984 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+840167587790','123/16 ?inh B? L?nh, p25, Bình Th?nh, TP.H? Chí Minh',3,2,'SA0000003',0);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('TE0000000001','Nguy?n Huy Khánh',to_timestamp('15/10/2005 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+84012332112 ','76/22A Lê V?n Chí, Linh Trung, Th? ??c, TP.H? Chí Minh',2,3,'SA0000002',0);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('TE0000000002','Bùi Th? T??i',to_timestamp('03/05/2005 00:00','DD/MM/YYYY HH24:MI'),'Nu','Vi?t Nam','+84012345432 ','76/22A Lê V?n Chí, Linh Trung, Th? ??c, TP.H? Chí Minh',1,1,'SA0000003',0);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('TE0000000003','Nguy?n V?n Tèo',to_timestamp('14/08/2006 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+84012345444 ','123 Cách M?ng Tháng Tám, p15, Qu?n 10, TP.H? Chí Minh',3,1,'SA0000003',0);
Insert into KHACHHANG (MSKH,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,SODT,DIACHI,MSTTTG,MSPHH,MSCB,KHOILUONGVUOT) values ('TE0000000004','Tr?n ??c Nam',to_timestamp('09/01/2007 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','+84012345455 ','324 Cách M?ng Tháng Tám, p15, Qu?n 10, TP.H? Chí Minh',2,1,'SA0000001',0);

---------------------------------------------------
--   END DATA FOR TABLE KHACHHANG
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE KHACHHANGNL
--   FILTER = none used
---------------------------------------------------
REM INSERTING into KHACHHANGNL
Insert into KHACHHANGNL (MSKH,CMND,PASSPORT) values ('NL0123456789','272987678','B6543222 ');
Insert into KHACHHANGNL (MSKH,CMND,PASSPORT) values ('NL0000000001','270989888','B6543222 ');
Insert into KHACHHANGNL (MSKH,CMND,PASSPORT) values ('NL0000000002','270989889','B6543333 ');
Insert into KHACHHANGNL (MSKH,CMND,PASSPORT) values ('NL0000000003','270989881','B6543444 ');

---------------------------------------------------
--   END DATA FOR TABLE KHACHHANGNL
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE KHACHHANGTE
--   FILTER = none used
---------------------------------------------------
REM INSERTING into KHACHHANGTE
Insert into KHACHHANGTE (MSKH,THONGTINKSINH,MSNGH) values ('TE0000000001','Cha: Cù Huy C?n. M?: Phan Th? Vân','NL0000000001');
Insert into KHACHHANGTE (MSKH,THONGTINKSINH,MSNGH) values ('TE0000000002','Cha: Lê V?n Tám. M?: Tr??ng Th? My','NL0000000002');
Insert into KHACHHANGTE (MSKH,THONGTINKSINH,MSNGH) values ('TE0000000003','Cha: Nguy?n Long Giang. M?: ?oàn Th? ?i?m ','NL0000000003');
Insert into KHACHHANGTE (MSKH,THONGTINKSINH,MSNGH) values ('TE0000000004','Cha: Nguy?n Xuân Di?u, M?: H? Xuân H??ng','NL0123456789');

---------------------------------------------------
--   END DATA FOR TABLE KHACHHANGTE
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE KIEMTRA
--   FILTER = none used
---------------------------------------------------
REM INSERTING into KIEMTRA
Insert into KIEMTRA (MSNV,MSCB) values ('KT0000000001','SA0000001');
Insert into KIEMTRA (MSNV,MSCB) values ('KT0000000002','SA0000001');
Insert into KIEMTRA (MSNV,MSCB) values ('KT0000000003','SA0000001');
Insert into KIEMTRA (MSNV,MSCB) values ('KT0000000004','SA0000003');

---------------------------------------------------
--   END DATA FOR TABLE KIEMTRA
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE LAI
--   FILTER = none used
---------------------------------------------------
REM INSERTING into LAI
Insert into LAI (MSNV,MSLMB) values ('PC0000000001',1);
Insert into LAI (MSNV,MSLMB) values ('PC0000000002',2);
Insert into LAI (MSNV,MSLMB) values ('PC0000000003',3);
Insert into LAI (MSNV,MSLMB) values ('PC0000000004',4);

---------------------------------------------------
--   END DATA FOR TABLE LAI
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE LOAIMAYBAY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into LOAIMAYBAY
Insert into LOAIMAYBAY (MSLMB,HANGSX,MODEL,SOGHEVIP,SOGHEPT,TONGSOGHE) values (1,'Airbus','Q300',10,40,50);
Insert into LOAIMAYBAY (MSLMB,HANGSX,MODEL,SOGHEVIP,SOGHEPT,TONGSOGHE) values (2,'Airbus','Q400',20,40,60);
Insert into LOAIMAYBAY (MSLMB,HANGSX,MODEL,SOGHEVIP,SOGHEPT,TONGSOGHE) values (3,'Boeing','AK47',10,80,90);
Insert into LOAIMAYBAY (MSLMB,HANGSX,MODEL,SOGHEVIP,SOGHEPT,TONGSOGHE) values (4,'Boeing','AK48',30,80,110);

---------------------------------------------------
--   END DATA FOR TABLE LOAIMAYBAY
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE MAYBAY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into MAYBAY
Insert into MAYBAY (MSMB,TONGGIOBAY,NAMSX,THOIDIEMSD,MSLMB) values ('SAP007',0,1990,to_timestamp('06/02/2007 00:00','DD/MM/YYYY HH24:MI'),2);
Insert into MAYBAY (MSMB,TONGGIOBAY,NAMSX,THOIDIEMSD,MSLMB) values ('SAP008',0,1992,to_timestamp('02/07/2008 00:00','DD/MM/YYYY HH24:MI'),3);
Insert into MAYBAY (MSMB,TONGGIOBAY,NAMSX,THOIDIEMSD,MSLMB) values ('SAP009',0,1991,to_timestamp('04/07/2009 00:00','DD/MM/YYYY HH24:MI'),1);
Insert into MAYBAY (MSMB,TONGGIOBAY,NAMSX,THOIDIEMSD,MSLMB) values ('SAP010',0,1996,to_timestamp('05/08/2005 00:00','DD/MM/YYYY HH24:MI'),4);

---------------------------------------------------
--   END DATA FOR TABLE MAYBAY
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE NHANVIEN
--   FILTER = none used
---------------------------------------------------
REM INSERTING into NHANVIEN
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('PC0000000001','Gia Cát L??ng',to_timestamp('12/03/1977 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012345678','B4561237 ',to_timestamp('14/10/2007 00:00','DD/MM/YYYY HH24:MI'),'2 Thành Thái, Qu?n 10, TP.H? Chí Minh','+84123568754 ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('PC0000000002','Nguy?n Hoài Linh',to_timestamp('07/05/1975 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','272000001','B4561231 ',to_timestamp('16/04/2006 00:00','DD/MM/YYYY HH24:MI'),'145 B?ch ??ng, P25, Bình Th?nh, TP.H? Chí Minh','+84166876543 ',60000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('PC0000000003','Nguy?n Th? Lan',to_timestamp('12/03/1977 00:00','DD/MM/YYYY HH24:MI'),'Nu','Vi?t Nam','272000002','B4561232 ',to_timestamp('18/02/2005 00:00','DD/MM/YYYY HH24:MI'),'267 B?ch ??ng, P25, Bình Th?nh, TP.H? Chí Minh','+84168111111 ',80000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('PC0000000004','Tr?n V?n ??c',to_timestamp('12/03/1977 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','272000003','B4561233 ',to_timestamp('20/11/2008 00:00','DD/MM/YYYY HH24:MI'),'98 Nguy?n Xí, p25, Bình Th?nh, TP.H? Chí Minh','+84169222222 ',75000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('TV0000000001','Ch? Lan Viên',to_timestamp('02/11/1969 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','015784587','B4458737 ',to_timestamp('30/12/2002 00:00','DD/MM/YYYY HH24:MI'),'15A Lý Th??ng Ki?t, Qu?n 10, TP.H? Chí Minh','+84127588754 ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('TV0000000002','Nguy?n Th? L?',to_timestamp('03/11/1968 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','015777777','B1111111 ',to_timestamp('29/12/2002 00:00','DD/MM/YYYY HH24:MI'),'15 Nguy?n Xí, p25, Bình Th?nh, TP.H? Chí Minh','+8412222222  ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('TV0000000003','Ngô Th?a Ân',to_timestamp('04/11/1967 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','015888888','B1111112 ',to_timestamp('27/12/2003 00:00','DD/MM/YYYY HH24:MI'),'28 Nguy?n Xí, p25, Bình Th?nh, TP.H? Chí Minh','+8412333333  ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('TV0000000004','L?u Di?c Phi',to_timestamp('05/11/1966 00:00','DD/MM/YYYY HH24:MI'),'Nu','Vi?t Nam','015999999','B1111113 ',to_timestamp('25/12/2004 00:00','DD/MM/YYYY HH24:MI'),'35 Nguy?n Xí, p25, Bình Th?nh, TP.H? Chí Minh','+84987111111 ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('KT0000000001','Châu Tinh Trì',to_timestamp('06/06/1980 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012369878','B6958737 ',to_timestamp('19/07/2008 00:00','DD/MM/YYYY HH24:MI'),'10A Võ V?n Ngân, Th? ??c, TP.H? Chí Minh','+84123325554 ',50000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('DH0000000001','S?n Gô Han',to_timestamp('14/02/1969 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012343254','B4561658 ',to_timestamp('14/04/2001 00:00','DD/MM/YYYY HH24:MI'),'26 Hai Bà Tr?ng, Hoàn Ki?m, Hà N?i','+84127896544 ',60000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('KT0000000002','B? Kinh Vân',to_timestamp('06/07/1981 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012366666','B6957777 ',to_timestamp('11/08/2006 00:00','DD/MM/YYYY HH24:MI'),'12A Võ V?n Ngân, Th? ??c, TP.H? Chí Minh','+84123321111 ',40000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('KT0000000003','Nguy?n Th? C?m H??ng',to_timestamp('06/08/1982 00:00','DD/MM/YYYY HH24:MI'),'Nu','Vi?t Nam','012377777','B6958888 ',to_timestamp('18/08/2005 00:00','DD/MM/YYYY HH24:MI'),'13A Võ V?n Ngân, Th? ??c, TP.H? Chí Minh','+84123320000 ',32000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('KS0000000001','Nguy?n Bính',to_timestamp('07/09/1983 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012388888','B6959999 ',to_timestamp('22/12/2009 00:00','DD/MM/YYYY HH24:MI'),'9A Võ V?n Ngân, Th? ??c, TP.H? Chí Minh','+84123323333 ',45000000);
Insert into NHANVIEN (MSNV,HOTEN,NGAYSINH,GIOITINH,QUOCTICH,CMND,PASSPORT,NGAYVAOLAM,DIACHI,SODT,TIENLUONG) values ('KT0000000004','Nguy?n Thanh Tùng',to_timestamp('04/11/1994 00:00','DD/MM/YYYY HH24:MI'),'Nam','Vi?t Nam','012377788','B6958888 ',to_timestamp('18/08/2005 00:00','DD/MM/YYYY HH24:MI'),'13A Võ V?n Ngân, Th? ??c, TP.H? Chí Minh','+84123320000 ',32000000);

---------------------------------------------------
--   END DATA FOR TABLE NHANVIEN
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE NHANVIENMD
--   FILTER = none used
---------------------------------------------------
REM INSERTING into NHANVIENMD
Insert into NHANVIENMD (MSNV,MSCN,MSNV_TRUONG) values ('DH0000000001',1,'DH0000000001');
Insert into NHANVIENMD (MSNV,MSCN,MSNV_TRUONG) values ('KS0000000001',1,'DH0000000001');
Insert into NHANVIENMD (MSNV,MSCN,MSNV_TRUONG) values ('KT0000000001',1,'DH0000000001');
Insert into NHANVIENMD (MSNV,MSCN,MSNV_TRUONG) values ('KT0000000002',1,'DH0000000001');

---------------------------------------------------
--   END DATA FOR TABLE NHANVIENMD
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE NVMATDAT_CALV
--   FILTER = none used
---------------------------------------------------
REM INSERTING into NVMATDAT_CALV
Insert into NVMATDAT_CALV (MSNV,MSCLV,NGAYBATDAU) values ('KS0000000001','C1',to_timestamp('24/12/2007 00:00','DD/MM/YYYY HH24:MI'));
Insert into NVMATDAT_CALV (MSNV,MSCLV,NGAYBATDAU) values ('DH0000000001','C2',to_timestamp('09/08/2006 00:00','DD/MM/YYYY HH24:MI'));
Insert into NVMATDAT_CALV (MSNV,MSCLV,NGAYBATDAU) values ('KT0000000001','C2',to_timestamp('04/12/2008 00:00','DD/MM/YYYY HH24:MI'));
Insert into NVMATDAT_CALV (MSNV,MSCLV,NGAYBATDAU) values ('KT0000000002','C3',to_timestamp('14/02/2007 00:00','DD/MM/YYYY HH24:MI'));

---------------------------------------------------
--   END DATA FOR TABLE NVMATDAT_CALV
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE PHICONG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into PHICONG
Insert into PHICONG (MSNV,LOAIPHICONG) values ('PC0000000001','CT');
Insert into PHICONG (MSNV,LOAIPHICONG) values ('PC0000000002','CT');
Insert into PHICONG (MSNV,LOAIPHICONG) values ('PC0000000003','PL');
Insert into PHICONG (MSNV,LOAIPHICONG) values ('PC0000000004','PL');

---------------------------------------------------
--   END DATA FOR TABLE PHICONG
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE PHIVCHH
--   FILTER = none used
---------------------------------------------------
REM INSERTING into PHIVCHH
Insert into PHIVCHH (MSPHH,LOAIVE,TRONGLUONGDM,DONGIA_KG,THOIDIEMAPDUNG) values (1,'VIP',30,20000,to_timestamp('02/03/1998 00:00','DD/MM/YYYY HH24:MI'));
Insert into PHIVCHH (MSPHH,LOAIVE,TRONGLUONGDM,DONGIA_KG,THOIDIEMAPDUNG) values (2,'VIP',30,100000,to_timestamp('08/07/2002 00:00','DD/MM/YYYY HH24:MI'));
Insert into PHIVCHH (MSPHH,LOAIVE,TRONGLUONGDM,DONGIA_KG,THOIDIEMAPDUNG) values (3,'PT',20,80000,to_timestamp('11/05/2001 00:00','DD/MM/YYYY HH24:MI'));
Insert into PHIVCHH (MSPHH,LOAIVE,TRONGLUONGDM,DONGIA_KG,THOIDIEMAPDUNG) values (4,'PT',20,120000,to_timestamp('03/08/2005 00:00','DD/MM/YYYY HH24:MI'));

---------------------------------------------------
--   END DATA FOR TABLE PHIVCHH
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE THUCPHAM
--   FILTER = none used
---------------------------------------------------
REM INSERTING into THUCPHAM
Insert into THUCPHAM (MSTP,TEN,MOTA) values (1,'C?m Gà','C?m ?n v?i ?ùi gà r?t chi là ngon');
Insert into THUCPHAM (MSTP,TEN,MOTA) values (2,'Bánh Bao','Nh?ng chi?c bánh bao th?m ph?c');
Insert into THUCPHAM (MSTP,TEN,MOTA) values (3,'C?m s??n','C?m v?i s??n ngon tuy?t làm vui lòng khách hàng');
Insert into THUCPHAM (MSTP,TEN,MOTA) values (4,'Tr?ng kho','Tr?ng kho v?i th?t');

---------------------------------------------------
--   END DATA FOR TABLE THUCPHAM
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE TIEPVIEN
--   FILTER = none used
---------------------------------------------------
REM INSERTING into TIEPVIEN
Insert into TIEPVIEN (MSNV,NGOAINGUTHONGTHAO) values ('TV0000000001','ti?ng Anh, ti?ng Pháp, ti?ng ??c');
Insert into TIEPVIEN (MSNV,NGOAINGUTHONGTHAO) values ('TV0000000002','ti?ng Anh, ti?ng Lào, ti?ng ??c');
Insert into TIEPVIEN (MSNV,NGOAINGUTHONGTHAO) values ('TV0000000003','ti?ng Anh, ti?ng Nh?t, ti?ng Thái Lan');
Insert into TIEPVIEN (MSNV,NGOAINGUTHONGTHAO) values ('TV0000000004','ti?ng Anh, ti?ng Hàn, ti?ng Trung');

---------------------------------------------------
--   END DATA FOR TABLE TIEPVIEN
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE TRANGTHAITG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into TRANGTHAITG
Insert into TRANGTHAITG (MSTTTG,TENTT,PHANTRAMTP) values (1,'TG',78.2);
Insert into TRANGTHAITG (MSTTTG,TENTT,PHANTRAMTP) values (2,'HH',67.9);
Insert into TRANGTHAITG (MSTTTG,TENTT,PHANTRAMTP) values (3,'DD',46.5);

---------------------------------------------------
--   END DATA FOR TABLE TRANGTHAITG
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE TUYENBAY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into TUYENBAY
Insert into TUYENBAY (MSTB,MSG_DI,MSG_DEN) values (1,1,3);
Insert into TUYENBAY (MSTB,MSG_DI,MSG_DEN) values (2,2,3);
Insert into TUYENBAY (MSTB,MSG_DI,MSG_DEN) values (3,1,2);
Insert into TUYENBAY (MSTB,MSG_DI,MSG_DEN) values (4,3,4);

---------------------------------------------------
--   END DATA FOR TABLE TUYENBAY
---------------------------------------------------


---------------------------------------------------
--   DATA FOR TABLE VANHANH
--   FILTER = none used
---------------------------------------------------
REM INSERTING into VANHANH
Insert into VANHANH (MSNV,MSCB) values ('PC0000000001','SA0000001');
Insert into VANHANH (MSNV,MSCB) values ('PC0000000002','SA0000002');
Insert into VANHANH (MSNV,MSCB) values ('TV0000000001','SA0000003');
Insert into VANHANH (MSNV,MSCB) values ('TV0000000002','SA0000001');

---------------------------------------------------
--   END DATA FOR TABLE VANHANH
---------------------------------------------------

--------------------------------------------------------
--  Constraints for Table KHACHHANGNL
--------------------------------------------------------

  ALTER TABLE "KHACHHANGNL" ADD CONSTRAINT "KHACHHANGNL_CONSTRAINT_1" CHECK (REGEXP_LIKE(CMND, '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "KHACHHANGNL" ADD CONSTRAINT "KHACHHANGNL_CONSTRAINT_2" CHECK (REGEXP_LIKE(PASSPORT, '[A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "KHACHHANGNL" MODIFY ("MSKH" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANGNL" MODIFY ("CMND" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANGNL" ADD PRIMARY KEY ("MSKH") ENABLE;
 
  ALTER TABLE "KHACHHANGNL" ADD UNIQUE ("CMND") ENABLE;
--------------------------------------------------------
--  Constraints for Table TUYENBAY
--------------------------------------------------------

  ALTER TABLE "TUYENBAY" MODIFY ("MSTB" NOT NULL ENABLE);
 
  ALTER TABLE "TUYENBAY" MODIFY ("MSG_DI" NOT NULL ENABLE);
 
  ALTER TABLE "TUYENBAY" MODIFY ("MSG_DEN" NOT NULL ENABLE);
 
  ALTER TABLE "TUYENBAY" ADD PRIMARY KEY ("MSTB") ENABLE;
--------------------------------------------------------
--  Constraints for Table GA
--------------------------------------------------------

  ALTER TABLE "GA" MODIFY ("MSG" NOT NULL ENABLE);
 
  ALTER TABLE "GA" MODIFY ("TENSB" NOT NULL ENABLE);
 
  ALTER TABLE "GA" MODIFY ("THANHPHO" NOT NULL ENABLE);
 
  ALTER TABLE "GA" MODIFY ("QUOCGIA" NOT NULL ENABLE);
 
  ALTER TABLE "GA" ADD PRIMARY KEY ("MSG") ENABLE;
--------------------------------------------------------
--  Constraints for Table CALAMVIEC
--------------------------------------------------------

  ALTER TABLE "CALAMVIEC" ADD CONSTRAINT "CALAMVIEC_CONSTRAINT_1" CHECK (REGEXP_LIKE(MSCLV, '[C][1-3]')) ENABLE;
 
  ALTER TABLE "CALAMVIEC" MODIFY ("MSCLV" NOT NULL ENABLE);
 
  ALTER TABLE "CALAMVIEC" MODIFY ("TUGIO" NOT NULL ENABLE);
 
  ALTER TABLE "CALAMVIEC" MODIFY ("DENGIO" NOT NULL ENABLE);
 
  ALTER TABLE "CALAMVIEC" ADD PRIMARY KEY ("MSCLV") ENABLE;
--------------------------------------------------------
--  Constraints for Table THUCPHAM
--------------------------------------------------------

  ALTER TABLE "THUCPHAM" MODIFY ("MSTP" NOT NULL ENABLE);
 
  ALTER TABLE "THUCPHAM" MODIFY ("TEN" NOT NULL ENABLE);
 
  ALTER TABLE "THUCPHAM" ADD PRIMARY KEY ("MSTP") ENABLE;
 
  ALTER TABLE "THUCPHAM" ADD UNIQUE ("TEN") ENABLE;
--------------------------------------------------------
--  Constraints for Table GIATHUCPHAM
--------------------------------------------------------

  ALTER TABLE "GIATHUCPHAM" ADD CONSTRAINT "GIATHUCPHAM_CONSTRAINT_1" CHECK (GIA >= 0) ENABLE;
 
  ALTER TABLE "GIATHUCPHAM" MODIFY ("MSGTP" NOT NULL ENABLE);
 
  ALTER TABLE "GIATHUCPHAM" MODIFY ("GIA" NOT NULL ENABLE);
 
  ALTER TABLE "GIATHUCPHAM" MODIFY ("NGAYAPDUNG" NOT NULL ENABLE);
 
  ALTER TABLE "GIATHUCPHAM" MODIFY ("MSTP" NOT NULL ENABLE);
 
  ALTER TABLE "GIATHUCPHAM" ADD PRIMARY KEY ("MSGTP") ENABLE;
--------------------------------------------------------
--  Constraints for Table MAYBAY
--------------------------------------------------------

  ALTER TABLE "MAYBAY" ADD CONSTRAINT "MAYBAY_CONSTRAINT_1" CHECK (REGEXP_LIKE(MSMB, '[SAP][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "MAYBAY" ADD CONSTRAINT "MAYBAY_CONSTRAINT_2" CHECK (TONGGIOBAY >= 0) ENABLE;
 
  ALTER TABLE "MAYBAY" MODIFY ("MSMB" NOT NULL ENABLE);
 
  ALTER TABLE "MAYBAY" MODIFY ("TONGGIOBAY" NOT NULL ENABLE);
 
  ALTER TABLE "MAYBAY" MODIFY ("NAMSX" NOT NULL ENABLE);
 
  ALTER TABLE "MAYBAY" MODIFY ("THOIDIEMSD" NOT NULL ENABLE);
 
  ALTER TABLE "MAYBAY" MODIFY ("MSLMB" NOT NULL ENABLE);
 
  ALTER TABLE "MAYBAY" ADD PRIMARY KEY ("MSMB") ENABLE;
--------------------------------------------------------
--  Constraints for Table TRANGTHAITG
--------------------------------------------------------

  ALTER TABLE "TRANGTHAITG" MODIFY ("MSTTTG" NOT NULL ENABLE);
 
  ALTER TABLE "TRANGTHAITG" MODIFY ("TENTT" NOT NULL ENABLE);
 
  ALTER TABLE "TRANGTHAITG" MODIFY ("PHANTRAMTP" NOT NULL ENABLE);
 
  ALTER TABLE "TRANGTHAITG" ADD PRIMARY KEY ("MSTTTG") ENABLE;
--------------------------------------------------------
--  Constraints for Table TIEPVIEN
--------------------------------------------------------

  ALTER TABLE "TIEPVIEN" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "TIEPVIEN" MODIFY ("NGOAINGUTHONGTHAO" NOT NULL ENABLE);
 
  ALTER TABLE "TIEPVIEN" ADD PRIMARY KEY ("MSNV") ENABLE;
--------------------------------------------------------
--  Constraints for Table VANHANH
--------------------------------------------------------

  ALTER TABLE "VANHANH" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "VANHANH" MODIFY ("MSCB" NOT NULL ENABLE);
 
  ALTER TABLE "VANHANH" ADD PRIMARY KEY ("MSNV", "MSCB") ENABLE;
--------------------------------------------------------
--  Constraints for Table PHICONG
--------------------------------------------------------

  ALTER TABLE "PHICONG" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "PHICONG" MODIFY ("LOAIPHICONG" NOT NULL ENABLE);
 
  ALTER TABLE "PHICONG" ADD PRIMARY KEY ("MSNV") ENABLE;
--------------------------------------------------------
--  Constraints for Table BANGCAP
--------------------------------------------------------

  ALTER TABLE "BANGCAP" MODIFY ("MSBC" NOT NULL ENABLE);
 
  ALTER TABLE "BANGCAP" MODIFY ("TENBANGCAP" NOT NULL ENABLE);
 
  ALTER TABLE "BANGCAP" MODIFY ("TRUONGDAOTAO" NOT NULL ENABLE);
 
  ALTER TABLE "BANGCAP" MODIFY ("NAMDAT" NOT NULL ENABLE);
 
  ALTER TABLE "BANGCAP" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "BANGCAP" ADD PRIMARY KEY ("MSBC") ENABLE;
--------------------------------------------------------
--  Constraints for Table GHEKHACH
--------------------------------------------------------

  ALTER TABLE "GHEKHACH" ADD CONSTRAINT "GHEKHACH_CONSTRAINT_1" CHECK (GIA >= 0) ENABLE;
 
  ALTER TABLE "GHEKHACH" MODIFY ("MSKH" NOT NULL ENABLE);
 
  ALTER TABLE "GHEKHACH" MODIFY ("GHESO" NOT NULL ENABLE);
 
  ALTER TABLE "GHEKHACH" MODIFY ("GIA" NOT NULL ENABLE);
 
  ALTER TABLE "GHEKHACH" ADD PRIMARY KEY ("MSKH", "GHESO") ENABLE;
--------------------------------------------------------
--  Constraints for Table GHENGOI
--------------------------------------------------------

  ALTER TABLE "GHENGOI" ADD CONSTRAINT "GHENGOI_CONSTRAINT_1" CHECK (REGEXP_LIKE(GHESO, '[A-Z][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "GHENGOI" MODIFY ("MSG" NOT NULL ENABLE);
 
  ALTER TABLE "GHENGOI" MODIFY ("GHESO" NOT NULL ENABLE);
 
  ALTER TABLE "GHENGOI" MODIFY ("LOAIGHE" NOT NULL ENABLE);
 
  ALTER TABLE "GHENGOI" MODIFY ("MSLMB" NOT NULL ENABLE);
 
  ALTER TABLE "GHENGOI" ADD PRIMARY KEY ("MSG") ENABLE;
--------------------------------------------------------
--  Constraints for Table CHUYENMONBD
--------------------------------------------------------

  ALTER TABLE "CHUYENMONBD" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENMONBD" MODIFY ("MSLMB" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENMONBD" ADD PRIMARY KEY ("MSNV", "MSLMB") ENABLE;
--------------------------------------------------------
--  Constraints for Table CHINHANH
--------------------------------------------------------

  ALTER TABLE "CHINHANH" MODIFY ("MSCN" NOT NULL ENABLE);
 
  ALTER TABLE "CHINHANH" MODIFY ("TENCHINHANH" NOT NULL ENABLE);
 
  ALTER TABLE "CHINHANH" MODIFY ("THANHPHO" NOT NULL ENABLE);
 
  ALTER TABLE "CHINHANH" MODIFY ("QUOCGIA" NOT NULL ENABLE);
 
  ALTER TABLE "CHINHANH" ADD PRIMARY KEY ("MSCN") ENABLE;
--------------------------------------------------------
--  Constraints for Table PHIVCHH
--------------------------------------------------------

  ALTER TABLE "PHIVCHH" ADD CONSTRAINT "PHIVCHH_CONSTRAINT_1" CHECK (TRONGLUONGDM >= 0) ENABLE;
 
  ALTER TABLE "PHIVCHH" ADD CONSTRAINT "PHIVCHH_CONSTRAINT_2" CHECK (DONGIA_KG >= 0) ENABLE;
 
  ALTER TABLE "PHIVCHH" MODIFY ("MSPHH" NOT NULL ENABLE);
 
  ALTER TABLE "PHIVCHH" MODIFY ("LOAIVE" NOT NULL ENABLE);
 
  ALTER TABLE "PHIVCHH" MODIFY ("TRONGLUONGDM" NOT NULL ENABLE);
 
  ALTER TABLE "PHIVCHH" MODIFY ("DONGIA_KG" NOT NULL ENABLE);
 
  ALTER TABLE "PHIVCHH" MODIFY ("THOIDIEMAPDUNG" NOT NULL ENABLE);
 
  ALTER TABLE "PHIVCHH" ADD PRIMARY KEY ("MSPHH") ENABLE;
--------------------------------------------------------
--  Constraints for Table NHANVIENMD
--------------------------------------------------------

  ALTER TABLE "NHANVIENMD" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIENMD" MODIFY ("MSCN" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIENMD" MODIFY ("MSNV_TRUONG" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIENMD" ADD PRIMARY KEY ("MSNV") ENABLE;
--------------------------------------------------------
--  Constraints for Table NVMATDAT_CALV
--------------------------------------------------------

  ALTER TABLE "NVMATDAT_CALV" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "NVMATDAT_CALV" MODIFY ("MSCLV" NOT NULL ENABLE);
 
  ALTER TABLE "NVMATDAT_CALV" MODIFY ("NGAYBATDAU" NOT NULL ENABLE);
 
  ALTER TABLE "NVMATDAT_CALV" ADD PRIMARY KEY ("MSNV", "MSCLV") ENABLE;
--------------------------------------------------------
--  Constraints for Table KIEMTRA
--------------------------------------------------------

  ALTER TABLE "KIEMTRA" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "KIEMTRA" MODIFY ("MSCB" NOT NULL ENABLE);
 
  ALTER TABLE "KIEMTRA" ADD PRIMARY KEY ("MSNV", "MSCB") ENABLE;
--------------------------------------------------------
--  Constraints for Table NHANVIEN
--------------------------------------------------------

  ALTER TABLE "NHANVIEN" ADD CONSTRAINT "NHANVIEN_CONSTRAINT_1" CHECK (REGEXP_LIKE(MSNV, '[PC|TV|KT|DH|KS][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "NHANVIEN" ADD CONSTRAINT "NHANVIEN_CONSTRAINT_2" CHECK (REGEXP_LIKE(SODT, '[+84][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9|]')) ENABLE;
 
  ALTER TABLE "NHANVIEN" ADD CONSTRAINT "NHANVIEN_CONSTRAINT_3" CHECK (REGEXP_LIKE(PASSPORT, '[A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "NHANVIEN" ADD CONSTRAINT "NHANVIEN_CONSTRAINT_4" CHECK (TIENLUONG >= 0) ENABLE;
 
  ALTER TABLE "NHANVIEN" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("HOTEN" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("NGAYSINH" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("GIOITINH" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("QUOCTICH" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("CMND" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("NGAYVAOLAM" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("DIACHI" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("SODT" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" MODIFY ("TIENLUONG" NOT NULL ENABLE);
 
  ALTER TABLE "NHANVIEN" ADD PRIMARY KEY ("MSNV") ENABLE;
--------------------------------------------------------
--  Constraints for Table KHACHHANGTE
--------------------------------------------------------

  ALTER TABLE "KHACHHANGTE" MODIFY ("MSKH" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANGTE" MODIFY ("MSNGH" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANGTE" ADD PRIMARY KEY ("MSKH") ENABLE;
--------------------------------------------------------
--  Constraints for Table LAI
--------------------------------------------------------

  ALTER TABLE "LAI" MODIFY ("MSNV" NOT NULL ENABLE);
 
  ALTER TABLE "LAI" MODIFY ("MSLMB" NOT NULL ENABLE);
 
  ALTER TABLE "LAI" ADD PRIMARY KEY ("MSNV", "MSLMB") ENABLE;
--------------------------------------------------------
--  Constraints for Table KHACHHANG
--------------------------------------------------------

  ALTER TABLE "KHACHHANG" ADD CONSTRAINT "KHACHHANG_CONSTRAINT_1" CHECK (REGEXP_LIKE(MSKH, '[NL|TE][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "KHACHHANG" ADD CONSTRAINT "KHACHHANG_CONSTRAINT_2" CHECK (REGEXP_LIKE(SODT, '[+84][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9|]')) ENABLE;
 
  ALTER TABLE "KHACHHANG" MODIFY ("MSKH" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANG" MODIFY ("HOTEN" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANG" MODIFY ("MSTTTG" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANG" MODIFY ("MSPHH" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANG" MODIFY ("MSCB" NOT NULL ENABLE);
 
  ALTER TABLE "KHACHHANG" ADD PRIMARY KEY ("MSKH") ENABLE;
--------------------------------------------------------
--  Constraints for Table CHUYENBAY
--------------------------------------------------------

  ALTER TABLE "CHUYENBAY" ADD CONSTRAINT "CHUYENBAY_CONSTRAINT_1" CHECK (REGEXP_LIKE(MSCB, '[SA][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')) ENABLE;
 
  ALTER TABLE "CHUYENBAY" ADD CONSTRAINT "CHUYENBAY_CONSTRAINT_2" CHECK (SOGHETRONG >= 0) ENABLE;
 
  ALTER TABLE "CHUYENBAY" MODIFY ("MSCB" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("TRANGTHAI" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("SOGHETRONG" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("THOIDIEMDI" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("THOIDIEMDEN" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("MSMB" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" MODIFY ("MSTB" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAY" ADD PRIMARY KEY ("MSCB") ENABLE;
--------------------------------------------------------
--  Constraints for Table CHUYENBAYTHUCPHAM
--------------------------------------------------------

  ALTER TABLE "CHUYENBAYTHUCPHAM" MODIFY ("MSCB" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAYTHUCPHAM" MODIFY ("MSTP" NOT NULL ENABLE);
 
  ALTER TABLE "CHUYENBAYTHUCPHAM" ADD PRIMARY KEY ("MSCB", "MSTP") ENABLE;
--------------------------------------------------------
--  Constraints for Table LOAIMAYBAY
--------------------------------------------------------

  ALTER TABLE "LOAIMAYBAY" ADD CONSTRAINT "LOAIMAYBAY_CONSTRAINT_1" CHECK (SOGHEVIP >= 0) ENABLE;
 
  ALTER TABLE "LOAIMAYBAY" ADD CONSTRAINT "LOAIMAYBAY_CONSTRAINT_2" CHECK (SOGHEPT >= 0) ENABLE;
 
  ALTER TABLE "LOAIMAYBAY" ADD CONSTRAINT "LOAIMAYBAY_CONSTRAINT_3" CHECK (TONGSOGHE >= 0) ENABLE;
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("MSLMB" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("HANGSX" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("MODEL" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("SOGHEVIP" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("SOGHEPT" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" MODIFY ("TONGSOGHE" NOT NULL ENABLE);
 
  ALTER TABLE "LOAIMAYBAY" ADD PRIMARY KEY ("MSLMB") ENABLE;
--------------------------------------------------------
--  DDL for Index GHENGOI
--------------------------------------------------------

  CREATE INDEX "GHENGOI" ON "GHENGOI" ("GHESO", "MSLMB") 
  ;
--------------------------------------------------------
--  DDL for Index KHACHHANG
--------------------------------------------------------

  CREATE INDEX "KHACHHANG" ON "KHACHHANG" ("MSKH", "MSTTTG", "MSCB") 
  ;
--------------------------------------------------------
--  DDL for Index TRANGTHAI
--------------------------------------------------------

  CREATE INDEX "TRANGTHAI" ON "TRANGTHAITG" ("MSTTTG", "TENTT") 
  ;
--------------------------------------------------------
--  DDL for Index GHEKHACH
--------------------------------------------------------

  CREATE INDEX "GHEKHACH" ON "GHEKHACH" ("GHESO", "MSKH") 
  ;
--------------------------------------------------------
--  DDL for Index CHUYENBAY
--------------------------------------------------------

  CREATE INDEX "CHUYENBAY" ON "CHUYENBAY" ("MSCB", "TRANGTHAI", "THOIDIEMDI", "MSTB", "MSMB") 
  ;
--------------------------------------------------------
--  DDL for Index MAYBAY
--------------------------------------------------------

  CREATE INDEX "MAYBAY" ON "MAYBAY" ("MSMB", "MSLMB") 
  ;
--------------------------------------------------------
--  Ref Constraints for Table BANGCAP
--------------------------------------------------------

  ALTER TABLE "BANGCAP" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;


--------------------------------------------------------
--  Ref Constraints for Table CHUYENBAY
--------------------------------------------------------

  ALTER TABLE "CHUYENBAY" ADD FOREIGN KEY ("MSMB")
	  REFERENCES "MAYBAY" ("MSMB") ENABLE;
 
  ALTER TABLE "CHUYENBAY" ADD FOREIGN KEY ("MSTB")
	  REFERENCES "TUYENBAY" ("MSTB") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CHUYENBAYTHUCPHAM
--------------------------------------------------------

  ALTER TABLE "CHUYENBAYTHUCPHAM" ADD FOREIGN KEY ("MSCB")
	  REFERENCES "CHUYENBAY" ("MSCB") ENABLE;
 
  ALTER TABLE "CHUYENBAYTHUCPHAM" ADD FOREIGN KEY ("MSTP")
	  REFERENCES "THUCPHAM" ("MSTP") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CHUYENMONBD
--------------------------------------------------------

  ALTER TABLE "CHUYENMONBD" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;
 
  ALTER TABLE "CHUYENMONBD" ADD FOREIGN KEY ("MSLMB")
	  REFERENCES "LOAIMAYBAY" ("MSLMB") ENABLE;


--------------------------------------------------------
--  Ref Constraints for Table GHENGOI
--------------------------------------------------------

  ALTER TABLE "GHENGOI" ADD FOREIGN KEY ("MSLMB")
	  REFERENCES "LOAIMAYBAY" ("MSLMB") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GIATHUCPHAM
--------------------------------------------------------

  ALTER TABLE "GIATHUCPHAM" ADD FOREIGN KEY ("MSTP")
	  REFERENCES "THUCPHAM" ("MSTP") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table KHACHHANG
--------------------------------------------------------

  ALTER TABLE "KHACHHANG" ADD FOREIGN KEY ("MSTTTG")
	  REFERENCES "TRANGTHAITG" ("MSTTTG") ENABLE;
 
  ALTER TABLE "KHACHHANG" ADD FOREIGN KEY ("MSPHH")
	  REFERENCES "PHIVCHH" ("MSPHH") ENABLE;
 
  ALTER TABLE "KHACHHANG" ADD FOREIGN KEY ("MSCB")
	  REFERENCES "CHUYENBAY" ("MSCB") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table KHACHHANGNL
--------------------------------------------------------

  ALTER TABLE "KHACHHANGNL" ADD FOREIGN KEY ("MSKH")
	  REFERENCES "KHACHHANG" ("MSKH") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table KHACHHANGTE
--------------------------------------------------------

  ALTER TABLE "KHACHHANGTE" ADD FOREIGN KEY ("MSNGH")
	  REFERENCES "KHACHHANGNL" ("MSKH") ENABLE;
 
  ALTER TABLE "KHACHHANGTE" ADD FOREIGN KEY ("MSKH")
	  REFERENCES "KHACHHANG" ("MSKH") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table KIEMTRA
--------------------------------------------------------

  ALTER TABLE "KIEMTRA" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;
 
  ALTER TABLE "KIEMTRA" ADD FOREIGN KEY ("MSCB")
	  REFERENCES "CHUYENBAY" ("MSCB") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LAI
--------------------------------------------------------

  ALTER TABLE "LAI" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;
 
  ALTER TABLE "LAI" ADD FOREIGN KEY ("MSLMB")
	  REFERENCES "LOAIMAYBAY" ("MSLMB") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table MAYBAY
--------------------------------------------------------

  ALTER TABLE "MAYBAY" ADD FOREIGN KEY ("MSLMB")
	  REFERENCES "LOAIMAYBAY" ("MSLMB") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table NHANVIENMD
--------------------------------------------------------

  ALTER TABLE "NHANVIENMD" ADD FOREIGN KEY ("MSCN")
	  REFERENCES "CHINHANH" ("MSCN") ENABLE;
 
  ALTER TABLE "NHANVIENMD" ADD FOREIGN KEY ("MSNV_TRUONG")
	  REFERENCES "NHANVIENMD" ("MSNV") ENABLE;
 
  ALTER TABLE "NHANVIENMD" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NVMATDAT_CALV
--------------------------------------------------------

  ALTER TABLE "NVMATDAT_CALV" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIENMD" ("MSNV") ENABLE;
 
  ALTER TABLE "NVMATDAT_CALV" ADD FOREIGN KEY ("MSCLV")
	  REFERENCES "CALAMVIEC" ("MSCLV") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PHICONG
--------------------------------------------------------

  ALTER TABLE "PHICONG" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;


--------------------------------------------------------
--  Ref Constraints for Table TIEPVIEN
--------------------------------------------------------

  ALTER TABLE "TIEPVIEN" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table TUYENBAY
--------------------------------------------------------

  ALTER TABLE "TUYENBAY" ADD FOREIGN KEY ("MSG_DI")
	  REFERENCES "GA" ("MSG") ENABLE;
 
  ALTER TABLE "TUYENBAY" ADD FOREIGN KEY ("MSG_DEN")
	  REFERENCES "GA" ("MSG") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table VANHANH
--------------------------------------------------------

  ALTER TABLE "VANHANH" ADD FOREIGN KEY ("MSNV")
	  REFERENCES "NHANVIEN" ("MSNV") ENABLE;
 
  ALTER TABLE "VANHANH" ADD FOREIGN KEY ("MSCB")
	  REFERENCES "CHUYENBAY" ("MSCB") ENABLE;
--------------------------------------------------------
--  DDL for Trigger BANGCAP_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BANGCAP_AUTO_INCREMENT" 
   before insert on BANGCAP
   for each row 
begin  
   :NEW.MSBC := SEQ_BANGCAP_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "BANGCAP_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CHINHANH_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CHINHANH_AUTO_INCREMENT" 
   before insert on CHINHANH
   for each row 
begin  
   :NEW.MSCN := SEQ_CHINHANH_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "CHINHANH_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger GA_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "GA_AUTO_INCREMENT" 
   before insert on GA
   for each row 
begin  
   :NEW.MSG := SEQ_GA_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "GA_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger GHENGOI_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "GHENGOI_AUTO_INCREMENT" 
   before insert on GHENGOI
   for each row 
begin  
   :NEW.MSG := SEQ_GHENGOI_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "GHENGOI_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger GIATHUCPHAM_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "GIATHUCPHAM_AUTO_INCREMENT" 
   before insert on GIATHUCPHAM
   for each row 
begin  
   :NEW.MSGTP := SEQ_GIATHUCPHAM_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "GIATHUCPHAM_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERT_KHACHHANG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "INSERT_KHACHHANG" 
BEFORE INSERT ON KhachHang
FOR EACH ROW
DECLARE 
    GHETRONG CHUYENBAY.SOGHETRONG%TYPE;
    
BEGIN
    SELECT soghetrong 
    INTO GHETRONG
    FROM ChuyenBay CB
    WHERE CB.MSCB = :NEW.MSCB;
    IF GHETRONG = 0 THEN
      RAISE_APPLICATION_ERROR(-20001,'CHUYENBAY WAS FULL');
    ELSE
      UPDATE ChuyenBay
      SET SoGheTrong = SoGheTrong - 1
      WHERE MSCB = :NEW.MSCB;
    END IF;
END;
/
ALTER TRIGGER "INSERT_KHACHHANG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERT_MAYBAY
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "INSERT_MAYBAY" 
BEFORE INSERT ON MAYBAY
FOR EACH ROW
BEGIN
	:NEW.TONGGIOBAY := 0;
END;
/
ALTER TRIGGER "INSERT_MAYBAY" ENABLE;
--------------------------------------------------------
--  DDL for Trigger LOAIMAYBAY_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "LOAIMAYBAY_AUTO_INCREMENT" 
   before insert on LOAIMAYBAY
   for each row 
begin  
   :NEW.MSLMB := SEQ_LOAIMAYBAY_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "LOAIMAYBAY_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger LOAIMAYBAY_TONGSOGHE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "LOAIMAYBAY_TONGSOGHE" 
AFTER INSERT OR UPDATE ON LoaiMayBay
FOR EACH ROW  WHEN ( NEW.TONGSOGHE <> (NEW.SoGheVip + NEW.SoGhePT)) BEGIN
		RAISE_APPLICATION_ERROR (-20500, 'ERROR');
END;
/
ALTER TRIGGER "LOAIMAYBAY_TONGSOGHE" ENABLE;
--------------------------------------------------------
--  DDL for Trigger PHIVCHH_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "PHIVCHH_AUTO_INCREMENT" 
   before insert on PHIVCHH
   for each row 
begin  
   :NEW.MSPHH := SEQ_PHIVCHH_AUTO_INCREMENT .NEXTVAL;
END;
/
ALTER TRIGGER "PHIVCHH_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger THUCPHAM_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "THUCPHAM_AUTO_INCREMENT" 
   before insert on THUCPHAM
   for each row 
begin  
   :NEW.MSTP := SEQ_THUCPHAM_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "THUCPHAM_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRANGTHAITG_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRANGTHAITG_AUTO_INCREMENT" 
   before insert on TRANGTHAITG
   for each row 
begin  
   :NEW.MSTTTG := SEQ_TRANGTHAITG_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "TRANGTHAITG_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TUYENBAY_AUTO_INCREMENT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TUYENBAY_AUTO_INCREMENT" 
   before insert on TUYENBAY
   for each row 
begin  
   :NEW.MSTB := SEQ_TUYENBAY_AUTO_INCREMENT.NEXTVAL;
END;
/
ALTER TRIGGER "TUYENBAY_AUTO_INCREMENT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger UPDATE_CHUYENBAY
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "UPDATE_CHUYENBAY" 
BEFORE UPDATE OF TRANGTHAI ON CHUYENBAY
FOR EACH ROW
BEGIN 
  IF :OLD.TRANGTHAI = 'CB' AND :NEW.TRANGTHAI = 'DB' 
  THEN
    UPDATE MAYBAY MB
    SET TONGGIOBAY = TONGGIOBAY + (TO_DATE(:old.THOIDIEMDEN) - TO_DATE(:old.THOIDIEMDI))*24
    WHERE MSMB = :OLD.MSMB;
  END IF;
END;
/
ALTER TRIGGER "UPDATE_CHUYENBAY" ENABLE;
--------------------------------------------------------
--  DDL for Trigger UPDATE_KHACHHANG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "UPDATE_KHACHHANG" 
BEFORE UPDATE OF MSTTTG
ON KhachHang
FOR EACH ROW
DECLARE
  TRANGTHAI_MOI TRANGTHAITG.TENTT%TYPE;
  TRANGTHAI_CU TRANGTHAITG.TENTT%TYPE;
  TONGGHE LOAIMAYBAY.TONGSOGHE%TYPE;
BEGIN
  SELECT TenTT
  INTO TRANGTHAI_MOI
  FROM TrangThaiTG
  WHERE MSTTTG = :NEW.MSTTTG;
  
  SELECT TenTT
  INTO TRANGTHAI_CU
  FROM TrangThaiTG
  WHERE MSTTTG = :OLD.MSTTTG;
  
  SELECT LMB.TONGSOGHE
  INTO TONGGHE
  FROM chuyenbay CB, MAYBAY MB, LOAIMAYBAY LMB
  WHERE MSCB = :OLD.MSCB AND CB.MSMB = MB.MSMB AND MB.MSLMB = LMB.MSLMB;
  
  IF  TRANGTHAI_CU = 'TG' THEN 
      IF TRANGTHAI_MOI = 'HH' OR TRANGTHAI_MOI = 'DD' THEN
         UPDATE ChuyenBay
         SET SoGheTrong = SoGheTrong + 1
         WHERE MSCB = :OLD.MSCB;
      END IF;
  ELSE
      IF TRANGTHAI_MOI = 'TG' THEN
         IF TONGGHE > 0 THEN
            UPDATE ChuyenBay
            SET SoGheTrong = SoGheTrong - 1
            WHERE MSCB = :OLD.MSCB;
          ELSE
            RAISE_APPLICATION_ERROR(-20001,'CHUYENBAY WAS FULL');
          END IF;
       END IF;
  END IF;
END;
/
ALTER TRIGGER "UPDATE_KHACHHANG" ENABLE;
--------------------------------------------------------
--  DDL for Function TIENPHIVCHH
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "TIENPHIVCHH" 
      (maSoKH IN KHACHHANG.MSKH% TYPE, khoiLuong IN PHIVCHH.TRONGLUONGDM% TYPE)    
      --  argument 1, 2 là mã khách hàng và kh?i l??ng cân ???c
      RETURN FLOAT
      AS
      
      CURSOR c1 IS                      --  cursor dùng ?? ki?m tra lo?i vé (víp hay ph? thông) và l?y ??n giá
        SELECT P.LOAIVE, P.DONGIA_KG 
        FROM KHACHHANG K, PHIVCHH P
        WHERE K.MSPHH = P.MSPHH AND K.MSKH = maSoKH;
      
      tienPhi FLOAT := 0.0;                   -- kh?i t?o k?t qu? tr? v? = 0
      khoiLuongVV FLOAT :=  khoiLuong - 30;   -- do vé víp (VV) mi?n phí 30 kg
      khoiLuongVPT FLOAT := khoiLuong - 20;   -- do vé ph? thông (VPT) mi?n phí 20 kg
BEGIN
  FOR lv IN c1
  LOOP
  IF (khoiLuongVPT <=  0 AND lv.LOAIVE = 'PT') OR (khoiLuongVV <= 0 AND lv.LOAIVE = 'VIP') THEN  
  -- n?u kh?i l??ng ch?a ??t 20 kg v?i VPT or 30 kg v?i VV
      RETURN  tienPhi;
  ELSIF KhoiLuongVPT > 0  AND lv.LOAIVE = 'PT' THEN    --  n?u kh?i l??ng > 20 kg v?i VPT
      tienPhi :=  lv.DONGIA_KG  * khoiLuongVPT;
      RETURN tienPhi;
  ELSIF  khoiLuongVV  > 0 AND lv.LOAIVE = 'VIP' THEN   --  n?u kh?i l??ng > 30 kg v?i VV
      tienPhi :=  lv.DONGIA_KG  * khoiLuongVV;
      RETURN tienPhi;
  ELSE 
      DBMS_OUTPUT.PUT_LINE('ERROR');      -- n?u không th?y error
  END IF;
  END LOOP;
END;

/

--------------------------------------------------------
--  DDL for Procedure CHECKINGIADINH
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "CHECKINGIADINH" 
    (tongKL IN PHIVCHH.TRONGLUONGDM% TYPE, maNguoiDaiDien IN KHACHHANG.MSKH% TYPE, soNguoiVV INT, soNguoiVPT INT)
    AS
    khoiLuongDM FLOAT :=  soNguoiVV * 30  + soNguoiVPT  * 20; -- tính kh?i l??ng ??nh m?c
    khoiLuong_Vuot  FLOAT :=  tongKL  - khoiLuongDM;          -- tính kh?i l??ng v??t
BEGIN
    IF khoiLuong_Vuot > 0 THEN    -- n?u kh?i l??ng v??t > 0 thì c?p nh?t cho ng??i ??i di?n
      UPDATE KHACHHANG
        SET KHOILUONGVUOT =  khoiLuong_Vuot
        WHERE MSKH  = maNguoiDaiDien;
    ELSE                          -- n?u kh?i l??ng v??t <= 0 thì gán 0
      UPDATE KHACHHANG
        SET KHOILUONGVUOT =  0
        WHERE MSKH  = maNguoiDaiDien;
  END IF;
END;

/

--------------------------------------------------------
--  DDL for Procedure KHOILUONGVUOTQUYDINH
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "KHOILUONGVUOTQUYDINH" 
      (maSoKH IN KHACHHANG.MSKH% TYPE, khoiLuong IN PHIVCHH.TRONGLUONGDM% TYPE)    
      --  argument 1, 2 là mã khách hàng và kh?i l??ng cân ???c
      AS
      
      CURSOR c1 IS                      --  cursor dùng ?? ki?m tra lo?i vé (víp hay ph? thông)
        SELECT LOAIVE 
        FROM KHACHHANG K, PHIVCHH P
        WHERE K.MSPHH = P.MSPHH AND K.MSKH = maSoKH;
      
      khoiLuongVV FLOAT :=  khoiLuong - 30;   -- do vé víp (VV) mi?n phí 30 kg
      khoiLuongVPT FLOAT := khoiLuong - 20;   -- do vé ph? thông (VPT)mi?n phí 20 kg
BEGIN
  FOR lv IN c1
  LOOP
  IF (khoiLuongVPT <=  0 AND lv.LOAIVE = 'PT') OR (khoiLuongVV <= 0 AND lv.LOAIVE = 'VIP') THEN  
  -- n?u kh?i l??ng ch?a ??t 20 kg v?i VPT or 30 kg v?i VV
      UPDATE KHACHHANG                    -- thì update kh?i l??ng v??t = 0
          SET KHOILUONGVUOT = 0
          WHERE MSKH = maSoKH;
  ELSIF KhoiLuongVPT > 0  AND lv.LOAIVE = 'PT' THEN   --  n?u kh?i l??ng > 20 kg v?i VPT
      UPDATE KHACHHANG                                --  thì update  l?i kh?i l??ng v??t
          SET KHOILUONGVUOT = khoiLuongVPT
          WHERE MSKH = maSoKH;
  ELSIF  khoiLuongVV  > 0 AND lv.LOAIVE = 'VIP' THEN  --  n?u kh?i l??ng > 30 kg v?i VV
      UPDATE KHACHHANG                                --  thì upadte l?i kh?i l??ng v??t
          SET KHOILUONGVUOT = khoiLuongVV
          WHERE MSKH = maSoKH;
  ELSE 
      DBMS_OUTPUT.PUT_LINE('ERROR');      -- n?u không th?y error
  END IF;
  END LOOP;
END;

	-- Bai 2 b

/

--------------------------------------------------------
--  DDL for Procedure TINHPHIVCHH
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "TINHPHIVCHH" (maSoKH IN KHACHHANG.MSKH% TYPE) -- argument là khách hàng
    AS
    ketQua  FLOAT :=  0.0;      -- kh?i t?o k?t qu? = 0
    CURSOR c1
    IS 
      SELECT K.MSKH, K.KHOILUONGVUOT, P.DONGIA_KG
      FROM KHACHHANG K, PHIVCHH P
      WHERE K.MSPHH = P.MSPHH AND K.MSKH  = maSoKH;  
BEGIN
    FOR Phi IN c1
    LOOP
      IF Phi.MSKH = maSoKH  THEN
        ketQua  :=  Phi.KHOILUONGVUOT  * Phi.DonGia_kg;
      END IF;
      DBMS_OUTPUT.PUT_LINE(maSoKH || ' Phi Hang Hoa: ' ||  ketQua);
    END LOOP;
END;

/

