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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import tech.danfe.simplelibs.simplejdbc.mapper.RowMapper;

/**
 *
 * @author Suraj Chhetry
 */
public class JdbcTemplate implements NamedParameterJdbcOperations, JdbcOperations, AutoCloseable {

    private final int DEFAULT_INDEX = 1;

    private final DataSource dataSource;

    private final ThreadLocal<TransactionContext> currentTransaction;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        currentTransaction = new ThreadLocal<>();
    }

    public void beginTransaction() {
        TransactionContext context = this.currentTransaction.get();
        if (context == null) {
            try {
                Connection connection = this.dataSource.getConnection();
                connection.setAutoCommit(false);
                this.currentTransaction.set(new TransactionContext(connection));
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        }
    }

    public void commitTransaction() {
        TransactionContext context = this.currentTransaction.get();
        if (context != null) {
            try (Connection connection = context.getConnection()) {
                connection.commit();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            } finally {
                this.currentTransaction.remove();
            }
        }
    }

    public void rollbackTransaction() {
        TransactionContext context = this.currentTransaction.get();
        if (context != null) {
            try (Connection connection = context.getConnection()) {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            } finally {
                this.currentTransaction.remove();
            }
        }
    }

    protected Connection getConnection() {
        try {
            if (this.currentTransaction.get() != null) {
                return this.currentTransaction.get().getConnection();
            }
            return this.dataSource.getConnection();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void close() throws Exception {
        Connection connection = this.getConnection();
        if (!connection.isClosed()) {
            if (connection.getAutoCommit()) {
                connection.close();
            } else {
                this.rollbackTransaction();
                connection.close();
            }
        }

    }

    @Override
    public int executeUpdate(String sql) {
        try {
            Connection connection = this.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                return ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public int executeUpdate(String sql, QueryParameterCollection parameters) {
        Connection connection = this.getConnection();
        try (MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters)) {
            return ps.executeUpdate();
        }
    }

    public int[] executeBatch(String sql, List<BatchParameter> parameters) {
        Connection connection = this.getConnection();
        try (BatchStatement ps = new BatchStatement(connection, sql, parameters)) {
            return ps.executeBatch();
        }
    }

    public long executeBatch(String sql, List<BatchParameter> parameters, int batchSize) {
        Connection connection = this.getConnection();
        try (BatchStatement ps = new BatchStatement(connection, sql, parameters)) {
            return ps.executeBatch(batchSize);
        }
    }

    @Override
    public <T> ArrayList<T> queryForList(String sql, final RowMapper<T> mapper) {
        final ArrayList<T> list = new ArrayList<>();
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(ResultSetUtils.processResultSet(rs, mapper));
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }

        return list;
    }

    @Override
    public <T> ArrayList<T> queryForList(String sql, final RowMapper<T> mapper, QueryParameterCollection parameters) {
        final ArrayList<T> list = new ArrayList<>();
        Connection connection = this.getConnection();
        try (MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(ResultSetUtils.processResultSet(rs, mapper));
            }
            return list;

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public <T> T queryForObject(String sql, final RowMapper<T> mapper, QueryParameterCollection parameters) {
        Connection connection = this.getConnection();
        try (
                MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return ResultSetUtils.processResultSet(rs, mapper);
            }
            return null;

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public <T> T queryForObject(String sql, final RowMapper<T> mapper) {
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return ResultSetUtils.processResultSet(rs, mapper);
            }
            return null;

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public String queryForString(String sql) {
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(DEFAULT_INDEX);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public String queryForString(String sql, QueryParameterCollection parameters) {
        Connection connection = this.getConnection();
        try (MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(DEFAULT_INDEX);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<String> queryForStringList(String sql) {
        List<String> list = new ArrayList<>();
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            int index = DEFAULT_INDEX;
            while (rs.next()) {
                list.add(rs.getString(index++));
            }
            return list;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public List<String> queryForStringList(String sql, QueryParameterCollection parameters) {
        List<String> list = new ArrayList<>();
        Connection connection = this.getConnection();
        try (MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters);
                ResultSet rs = ps.executeQuery()) {
            int index = DEFAULT_INDEX;
            while (rs.next()) {
                list.add(rs.getString(index++));
            }
            return list;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public Integer queryForInt(String sql) {
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(DEFAULT_INDEX);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public List<Integer> queryForIntList(String sql) {
        List<Integer> integers = new ArrayList<>();
        Connection connection = this.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                integers.add(rs.getInt(DEFAULT_INDEX));
            }
            return integers;

        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }

    }

    @Override
    public Integer queryForInt(String sql, QueryParameterCollection parameters) {
        Connection connection = this.getConnection();
        try (MappedPreparedStatement ps = new MappedPreparedStatement(connection, sql, parameters);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(DEFAULT_INDEX);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
