package com.mycomp.execspec.jiraplugin.ao;

import net.java.ao.test.jdbc.JdbcConfiguration;

/**
 * Configuration for HSQL DB that uses file, to be used in tests.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
public class HsqlDbFileJdbcConfiguration implements JdbcConfiguration {

    @Override
    public String getUrl() {
        return "jdbc:hsqldb:hsql://localhost/fil_db";
    }

    @Override
    public String getSchema() {

        return null;
    }

    @Override
    public String getUsername() {
        return "sa";
    }

    @Override
    public String getPassword() {
        return "";
    }

}
