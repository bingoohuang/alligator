package com.github.bingoohuang.biz;

import lombok.Synchronized;
import lombok.experimental.UtilityClass;
import org.n3r.eql.eqler.EqlerFactory;

import java.util.HashSet;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2016/12/8.
 */
@UtilityClass
public class Seq {
    SeqDao seqDao = EqlerFactory.getEqler(SeqDao.class);
    HashSet<String/* seqName */> seqNames = new HashSet<>(seqDao.selectSeqNames());

    /**
     * 获取SEQ1（主序列），当名字不存在时，增加名字。
     *
     * @param seqName 序列名称
     * @return SEQ1下一个取值(从1开始)
     */
    public long nextSeq1(String seqName) {
        if (!seqNames.contains(seqName)) createSeq(seqName);

        return Long.parseLong(seqDao.nextSeq1(seqName));
    }

    /**
     * 获取SEQ2（追赶序列）。
     *
     * @param seqName 序列名称
     * @return SEQ2下一个取值(从1开始)，返回0表示序列不存在，或者已经追上主序列
     */
    public long nextSeq2(String seqName) {
        return nextSeqX(seqName, "SEQ2");
    }

    /**
     * 获取SEQ3（追赶序列）。
     *
     * @param seqName 序列名称
     * @return SEQ3下一个取值(从1开始)，返回0表示序列不存在，或者已经追上主序列
     */
    public long nextSeq3(String seqName) {
        return nextSeqX(seqName, "SEQ3");
    }

    private long nextSeqX(String seqName, String seqField) {
        String result = seqDao.nextSeqX(seqName, seqField);
        return result == null ? 0 : Long.parseLong(result);
    }


    @Synchronized("seqNames")
    private void createSeq(String seqName) {
        try {
            seqDao.createSeq(seqName);
        } catch (Exception ex) {
            // java.sql.SQLException: ORA-00001: 违反唯一约束条件 (SYSTEM.SYS_C007599)
            // 说明已经被人抢先增加了
            if (!ex.toString().contains("ORA-00001")) throw ex;
        }
        seqNames.add(seqName);
    }

    public static void main(String[] args) {
        System.out.println(Seq.nextSeq1("XXX"));
        System.out.println(Seq.nextSeq2("XXX"));
        System.out.println(Seq.nextSeq3("XXX"));
    }
}
