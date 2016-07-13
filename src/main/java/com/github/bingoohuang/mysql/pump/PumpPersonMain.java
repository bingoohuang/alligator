package com.github.bingoohuang.mysql.pump;

import com.github.bingoohuang.utils.Durations;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.n3r.eql.Eql;
import org.n3r.eql.EqlTran;
import org.n3r.eql.Eqll;
import org.n3r.eql.config.EqlJdbcConfig;
import org.n3r.eql.eqler.EqlerFactory;
import org.n3r.eql.impl.EqlBatch;

public class PumpPersonMain {
    public static void main(String[] args) {
        new PumpPersonMain().pump(args);
    }

    Integer batchSize;
    Integer batchNum;

    private void pump(String[] args) {
        parseArgs(args);

        // pumpByDao();
        pumpByBatch();
    }

    EqlJdbcConfig dbaConfig = new EqlJdbcConfig(
        "com.mysql.cj.jdbc.Driver",
        "jdbc:mysql://192.168.99.100:13306/dba?useSSL=false" +
            "&useUnicode=true&characterEncoding=UTF-8" +
            "&connectTimeout=3000&socketTimeout=3000&autoReconnect=true",
        "root", "my-secret-pw");

    private void pumpByBatch() {
        Eqll.choose(dbaConfig);

        PersonDao personDao = EqlerFactory.getEqler(PersonDao.class);

        personDao.truncatePerson();

        for (int i = 0; i < batchNum; ++i) {
            long startMillis = System.currentTimeMillis();

            EqlTran eqlTran = new Eql(dbaConfig).newTran();
            eqlTran.start();
            EqlBatch eqlBatch = new EqlBatch(batchSize);

            for (int j = 0; j < batchSize; ++j)
                new Eql(dbaConfig).useBatch(eqlBatch).useTran(eqlTran)
                    .params(i + "x" + j, "bingoo" + i + "x" + j, 1, "大蓝鲸人")
                    .execute("insert into person(id, name, sex, addr) values(##, ##, ##, ##)");

            eqlBatch.executeBatch();
            eqlTran.commit();

            long endMillis = System.currentTimeMillis();

            System.out.println("Batch:" + i + ", BatchSize:" + batchSize
                + ", Cost:" + Durations.readableDuration(endMillis - startMillis));
        }
    }

    private void pumpByDao() {
        Eqll.choose(dbaConfig);

        PersonDao personDao = EqlerFactory.getEqler(PersonDao.class);

        personDao.truncatePerson();

        for (int i = 0; i < batchNum; ++i) {
            long startMillis = System.currentTimeMillis();

            for (int j = 0; j < batchSize; ++j)
                personDao.addPerson(Person.create(i + "x" + j, "bingoo" + i + "x" + j, 1, "大蓝鲸人"));

            long endMillis = System.currentTimeMillis();

            System.out.println("Batch:" + i + ", BatchSize:" + batchSize
                + ", Cost:" + Durations.readableDuration(endMillis - startMillis));
        }
    }

    private void parseArgs(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> batchSizeOption = parser.accepts("batchSize")
            .withOptionalArg().ofType(Integer.class).defaultsTo(100000);
        OptionSpec<Integer> batchNumOption = parser.accepts("batchNum")
            .withOptionalArg().ofType(Integer.class).defaultsTo(1000);

        OptionSet options = parser.parse(args);
        batchSize = batchSizeOption.value(options);
        batchNum = batchNumOption.value(options);
    }
}
