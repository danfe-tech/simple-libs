/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.danfe.simplelibs.simplejdbc.core;

import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author Suraj Chhetry
 */
public class ExecutableStatement {

    private Connection con;
    private PreparedStatement stmt;
    private JdbcTemplate jdbc;

    public ExecutableStatement(JdbcTemplate jdbc, String sql) {
        this.jdbc = jdbc;
        try {
            con = jdbc.getConnection();
            stmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new JdbcException("Error running query:\n" + sql + "\n\nError: " + e.getMessage(), e);
        }
    }

    public void addBatch() {
        try {
            stmt.addBatch();
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public int execute() {
        try {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public int[] executeBatch() {
        try {
            return stmt.executeBatch();
        } catch (SQLException e) {
            throw new JdbcException(e);
        } finally {
            JdbcUtil.close(stmt);
            jdbc.freeConnection(con);
        }
    }

    public void setParams(Object... params) {
        try {
            jdbc.fillStatement(stmt, params);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setNull(int parameterIndex, int sqlType) {
        try {
            stmt.setNull(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setBoolean(int parameterIndex, boolean x) {
        try {
            stmt.setBoolean(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setByte(int parameterIndex, byte x) {
        try {
            stmt.setByte(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setShort(int parameterIndex, short x) {
        try {
            stmt.setShort(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setInt(int parameterIndex, int x) {
        try {
            stmt.setInt(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setLong(int parameterIndex, long x) {
        try {
            stmt.setLong(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setFloat(int parameterIndex, float x) {
        try {
            stmt.setFloat(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setDouble(int parameterIndex, double x) {
        try {
            stmt.setDouble(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) {
        try {
            stmt.setBigDecimal(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setString(int parameterIndex, String x) {
        try {
            stmt.setString(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setBytes(int parameterIndex, byte x[]) {
        try {
            stmt.setBytes(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setDate(int parameterIndex, java.sql.Date x) {
        try {
            stmt.setDate(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setTime(int parameterIndex, java.sql.Time x) {
        try {
            stmt.setTime(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setTimestamp(int parameterIndex, java.sql.Timestamp x) {
        try {
            stmt.setTimestamp(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) {
        try {
            stmt.setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setBinaryStream(int parameterIndex, java.io.InputStream x,
            int length) {
        try {
            stmt.setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    void setBlob(int parameterIndex, Blob x) {
        try {
            stmt.setBlob(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    void setClob(int parameterIndex, Clob x) {
        try {
            stmt.setClob(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    void setArray(int parameterIndex, Array x) {
        try {
            stmt.setArray(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void clearParameters() {
        try {
            stmt.clearParameters();
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) {
        try {
            stmt.setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public void setObject(int parameterIndex, Object x) {
        try {
            stmt.setObject(parameterIndex, x);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

}
