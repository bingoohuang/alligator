package com.github.bingoohuang.biz;

import org.n3r.eql.eqler.annotations.EqlerConfig;
import org.n3r.eql.eqler.annotations.Sql;

import java.util.List;

/*
-- 创建内存表
CREATE TABLE MY_SEQ(
  NAME VARCHAR2(100) PRIMARY KEY,
  SEQ1 NUMBER DEFAULT 0 NOT NULL,
  SEQ2 NUMBER DEFAULT 0 NOT NULL,
  SEQ3 NUMBER DEFAULT 0 NOT NULL
) STORAGE(BUFFER_POOL KEEP);

-- 增加测试序列
INSERT INTO MY_SEQ(NAME) VALUES('XXX');
*/

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/12/8.
 */
@EqlerConfig("orcl")
public interface SeqDao {
    @Sql("SELECT NAME FROM MY_SEQ")
    List<String> selectSeqNames();

    @Sql("INSERT INTO MY_SEQ(NAME) VALUES(##)")
    void createSeq(String seqName);

    @Sql("{CALL UPDATE MY_SEQ SET SEQ1 = SEQ1 + 1 WHERE NAME = ## RETURNING SEQ1 INTO #:OUT# }")
    String nextSeq1(String seqName);

    @Sql("{CALL UPDATE MY_SEQ SET SEQ2 = SEQ2 + 1 WHERE NAME = ## AND SEQ2 < SEQ1 RETURNING SEQ2 INTO #:OUT# }")
    String nextSeq2(String seqName);

    @Sql("{CALL UPDATE MY_SEQ SET SEQ3 = SEQ3 + 1 WHERE NAME = ## AND SEQ3 < SEQ1 RETURNING SEQ3 INTO #:OUT# }")
    String nextSeq3(String seqName);
}
