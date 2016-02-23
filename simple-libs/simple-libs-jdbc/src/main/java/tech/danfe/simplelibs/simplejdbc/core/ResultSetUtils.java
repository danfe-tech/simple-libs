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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suraj Chhetry
 */
public class ResultSetUtils {

    /**
     * *
     * returns the list of column names present in given
     * <p>
     * resultSet
     * </p>
     *
     * @param resultSet
     * @return list of column names
     * @throws
     * <p>
     * IllegalArgumentException
     * </p> if
     * <p>
     * resultSet</p> null
     */
    public static List<String> extractColumns(ResultSet resultSet) {
        try {
            List<String> columns = new ArrayList<>();
            if (resultSet == null) {
                throw new IllegalArgumentException("ResultSet is null");
            }
            ResultSetMetaData metaData = resultSet.getMetaData();
            int totalColumn = metaData.getColumnCount();
            if (totalColumn <= 0) {
                return Collections.EMPTY_LIST;
            }
            for (int index = 0; index < totalColumn; index++) {
                String columnName = metaData.getColumnName(index);
                columns.add(columnName);
            }
            return columns;
        } catch (SQLException ex) {
            Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public static boolean doesColumnExists(ResultSet resultSet, String columnName) {
        try {
            resultSet.findColumn(columnName);
            return true;
        } catch (SQLException ex) {

        }
        return false;
    }

    public static boolean doesColumnExists(ResultSet resultSet, int index) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (index > metaData.getColumnCount()) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ResultSetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static <T> Object getAny(ResultSet resultSet, String columnLabel, Class type, T defaultValue) {
        try {
            if (doesColumnExists(resultSet, columnLabel)) {

                if (type == String.class) {
                    return resultSet.getString(columnLabel);
                }
                if (type == double.class) {
                    return resultSet.getDouble(columnLabel);
                }
                if (type == byte.class) {
                    return resultSet.getByte(columnLabel);
                }
                if (type == boolean.class) {
                    return resultSet.getByte(columnLabel);
                }
                if (type == short.class) {
                    return resultSet.getShort(columnLabel);
                }
                if (type == int.class) {
                    return resultSet.getInt(columnLabel);
                }
                if (type == long.class) {
                    return resultSet.getInt(columnLabel);
                }
                if (type == float.class) {
                    return resultSet.getFloat(columnLabel);
                }
                if (type == java.sql.Date.class) {
                    return resultSet.getDate(columnLabel);
                }
                if (type == java.sql.Time.class) {
                    return resultSet.getTime(columnLabel);
                }
                if (type == java.sql.Timestamp.class) {
                    return resultSet.getTimestamp(columnLabel);
                }
                if (type == BigDecimal.class) {
                    return resultSet.getBigDecimal(columnLabel);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ResultSetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return defaultValue;
    }

    public static String getString(ResultSet resultSet, String columnLabel, String defaultValue) {
        return (String) getAny(resultSet, columnLabel, String.class, defaultValue);
    }

    public static double getDouble(ResultSet resultSet, String columnLabel, double defaultValue) {
        return (double) getAny(resultSet, columnLabel, double.class, defaultValue);
    }

    public static byte getByte(ResultSet resultSet, String columnLabel, byte defaultValue) {
        return (byte) getAny(resultSet, columnLabel, byte.class, defaultValue);
    }

    public static boolean getBoolean(ResultSet resultSet, String columnLabel, boolean defaultValue) {
        return (boolean) getAny(resultSet, columnLabel, boolean.class, defaultValue);
    }

    public static int getInt(ResultSet resultSet, String columnLabel, int defaultValue) {
        return (int) getAny(resultSet, columnLabel, int.class, defaultValue);
    }

    public static long getLong(ResultSet resultSet, String columnLabel, long defaultValue) {
        return (long) getAny(resultSet, columnLabel, long.class, defaultValue);
    }

    public static float getFloat(ResultSet resultSet, String columnLabel, float defaultValue) {
        return (float) getAny(resultSet, columnLabel, float.class, defaultValue);
    }

    public static byte[] getBytes(ResultSet resultSet, String columnLabel) {
        try {
            if (doesColumnExists(resultSet, columnLabel)) {
                return resultSet.getBytes(columnLabel);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ResultSetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static java.sql.Date getDate(ResultSet resultSet, String columnLabel) {
        return (java.sql.Date) getAny(resultSet, columnLabel, java.sql.Date.class, null);
    }

    public static java.sql.Time getTime(ResultSet resultSet, String columnLabel) {
        return (java.sql.Time) getAny(resultSet, columnLabel, java.sql.Time.class, null);
    }

    public static java.sql.Timestamp getTimestamp(ResultSet resultSet, String columnLabel) {
        return (java.sql.Timestamp) getAny(resultSet, columnLabel, java.sql.Timestamp.class, null);
    }

    public static BigDecimal getBigDecimal(ResultSet resultSet, String columnLabel, BigDecimal defaultValue) {
        return (BigDecimal) getAny(resultSet, columnLabel, BigDecimal.class, defaultValue);
    }
}
