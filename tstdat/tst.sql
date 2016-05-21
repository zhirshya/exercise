SELECT tt002.hojin_code as hojin_code_tt002, tm005.hojin_code as hojin_code_tm005, kbn, recno, tt002.newko as newko_tt002, tm005.harai_sak_code as harai_sak_code_tm005, tm005.harai_sak_name as harai_sak_name_tm005, tt002.zuonr as zuonr_tt002, tm014.kostl as kostl_tm014, tm025.orgel as orgel_tm025, tt002.hkont as hkont_tt002, tm017.SAKNR as SAKNR_tm017, tt002.kostl as kostl_tt002, sgtxt, xref3  
  FROM wz11tt_002 as tt002 
  full outer join wz11tm_005 as tm005 on (tt002.hojin_code = tm005.hojin_code and tt002.newko = tm005.harai_sak_code) 
  full outer join wz11tm_014 as tm014 on (right(tt002.zuonr,7) = right(tm014.kostl,7)) 
  full outer join wz11tm_017 as tm017 on (tt002.hkont = tm017.SAKNR) 
  full outer join wz11tm_025 as tm025 on (right(tt002.zuonr,7) = tm025.orgel and tm025.chohyo_id = 'ODWZD23T')
  ;
psql -h 10.200.234.96 -p 5452 -U wz11adm01 -d twz11 -c "\COPY wz11tt_002(hojin_code, shori_ymd, kbn, recno, bukrs, blart, belnr, bldat, budat, wwert, xblnr, bktxt, waers, kursf, xmwst, bschl, mwskz, fwste, hwste, bschl2, umskz, zumsk, newko, hkont, gsber, mwskz2, dmbtr, wrbtr, zuonr, sgtxt, kostl, vault, aufnr, prctr, paobj1, paobj2, paobj3, paobj4, zterm, zfbdt, zbd1t, zlsch, zlspr, hbkid, bvtyp, rstgr, matnr, vptnr, xref1, xref2, xref3, dtws1, dtws2, dtws3, lzbkz, pycur, anred, name1, name2, name3, name4, stras, ort01, pstlz, bankl, banks, bankn, bkont, dtaws, wdate, wname, wort01, wbzog, wort2, wbank, wevwv, wstat, errmsg, userid, rnew_day) FROM 'C:/tmp/tt002.csv' DELIMITER ',' CSV ENCODING 'utf-8';"
psql -h 10.200.234.96 -p 5452 -U wz11adm01 -d twz11 -c "\COPY wz11tt_002(hojin_code, shori_ymd, kbn, recno, bukrs, blart, belnr, bldat, budat, wwert, xblnr, bktxt, waers, kursf, xmwst, bschl, mwskz, fwste, hwste, bschl2, umskz, zumsk, newko, hkont, gsber, mwskz2, dmbtr, wrbtr, zuonr, sgtxt, kostl, vault, aufnr, prctr, paobj1, paobj2, paobj3, paobj4, zterm, zfbdt, zbd1t, zlsch, zlspr, hbkid, bvtyp, rstgr, matnr, vptnr, xref1, xref2, xref3, dtws1, dtws2, dtws3, lzbkz, pycur, anred, name1, name2, name3, name4, stras, ort01, pstlz, bankl, banks, bankn, bkont, dtaws, wdate, wname, wort01, wbzog, wort2, wbank, wevwv, wstat, errmsg, userid, rnew_day) TO 'c:/tmp/tt002.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';"

SELECT tt002.zuonr,tm025.orgel from wz11tt_002 as tt002 full outer join wz11tm_025 as tm025 on right(tt002.zuonr,7) = tm025.orgel;
SELECT tm014.kostl,tm025.orgel from wz11tm_014 as tm014 full outer join wz11tm_025 as tm025 on right(tm014.kostl,7) = tm025.orgel;
SELECT id, hojin_code, shori_ymd, kbn, recno, budat, bschl, bschl2, hkont, zuonr, sgtxt, xref3, userid, rnew_day FROM wz11tt_002;

UPDATE wz11tt_002 SET kbn='1';

UPDATE wz11tt_002 SET  bschl='40' where bschl = '' or bschl is null;

select distinct(kostl) from WZ11TM_014;

select distinct(SAKNR) from WZ11TM_017;

select harai_sak_code,harai_sak_name from WZ11TM_005;
select chohyo_id,orgel from wz11tm_025;
UPDATE wz11tt_002 SET recno = '4' where recno like '2016%';

UPDATE wz11tt_002 SET newko = '1278540804' where newko = '2001000001';
UPDATE wz11tt_002 SET newko = '9500070803' where newko = 'XA164021';
UPDATE wz11tt_002 SET newko = '1019280802' where newko = '1011010000';
UPDATE wz11tt_002 SET hkont = '5031380010' where hkont = '1000000003';
UPDATE wz11tt_002 SET hkont = '8511240000' where hkont = '1000000002';
UPDATE wz11tt_002 SET hkont = '6011060160' where hkont = '1000000001';
