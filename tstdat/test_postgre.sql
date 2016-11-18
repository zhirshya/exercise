/*

/* multiline comment
 * with nesting: /* nested block comment */
 */

-- Sequence: wz11tm_025_id_seq

-- DROP SEQUENCE wz11tm_025_id_seq;

CREATE SEQUENCE wz11tm_025_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE wz11tm_025_id_seq
  OWNER TO wz11adm01;

/* 課BOX変換 */
CREATE TABLE wz11tm_025 (
   id bigint NOT NULL DEFAULT nextval('wz11tm_025_id_seq'::regclass) -- id
 , REP_ID VARCHAR(10) NOT NULL -- 帳票ID
 , KA_BOX_CC VARCHAR(7) NOT NULL -- 課BOXコード
 , EXCHG_1_CC VARCHAR(50) NOT NULL -- 変換元コード１
 , EXCHG_2_CC VARCHAR(50) -- 変換元コード２
 , EXCHG_3_CC VARCHAR(50) -- 変換元コード３
 , EXCHG_4_CC VARCHAR(50) -- 変換元コード４
 , EXCHG_5_CC VARCHAR(50) -- 変換元コード５
 , EXCHG_6_CC VARCHAR(50) -- 変換元コード６
 , EXCHG_7_CC VARCHAR(50) -- 変換元コード７
 , EXCHG_8_CC VARCHAR(50) -- 変換元コード８
 , EXCHG_9_CC VARCHAR(50) -- 変換元コード９
 , EXCHG_10_CC VARCHAR(50) -- 変換元コード１０
);

INSERT INTO wz11tm_025(
            id, rep_id, ka_box_cc, exchg_1_cc, exchg_2_cc, exchg_3_cc, exchg_4_cc, 
            exchg_5_cc, exchg_6_cc, exchg_7_cc, exchg_8_cc, exchg_9_cc, exchg_10_cc)
    VALUES (?, ?, ?, ?, ?, ?, ?, 
            ?, ?, ?, ?, ?, ?);
*/

COPY wz11tm_025 (rep_id, ka_box_cc, exchg_1_cc, exchg_2_cc, exchg_3_cc, exchg_4_cc, 
            exchg_5_cc, exchg_6_cc, exchg_7_cc, exchg_8_cc, exchg_9_cc, exchg_10_cc)
     FROM 'C:/dbC/wz11tm_025.dat'
     DELIMITER ',' CSV;
