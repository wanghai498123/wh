package cn.com.tass.codeg.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.com.tass.codeg.ConfigConsts;
import cn.com.tass.codeg.model.ColumnInfo;
import cn.com.tass.codeg.model.TableInfo;

public class AnalysisDB {

	public static final List<TableInfo> getAllTableInfos() {
		List<TableInfo> list = new ArrayList<TableInfo>();
		String sql = "SELECT TABLE_NAME ,TABLE_COMMENT FROM information_schema.tables " + " WHERE table_schema ='"
				+ ConfigConsts.DB_NAME + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = DBHandler.createConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				TableInfo tm = new TableInfo();
				tm.setSchemaName(ConfigConsts.DB_NAME);
				tm.setTableName(rs.getString("TABLE_NAME"));
				tm.setComment(rs.getString("TABLE_COMMENT"));
				list.add(tm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				DBHandler.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (TableInfo tableInfo : list) {
			fillTableColInfo(tableInfo);
		}

		return list;
	}

	public static final TableInfo getTableInfoByName(String tableName) {

		String sql = "SELECT TABLE_NAME ,TABLE_COMMENT FROM information_schema.tables  WHERE table_schema ='"
				+ ConfigConsts.DB_NAME + "' and TABLE_NAME='" + tableName + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableInfo tableInfo = new TableInfo();
		try {
			ps = DBHandler.createConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {

				tableInfo.setSchemaName(ConfigConsts.DB_NAME);
				tableInfo.setTableName(rs.getString("TABLE_NAME"));
				tableInfo.setComment(rs.getString("TABLE_COMMENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				DBHandler.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		fillTableColInfo(tableInfo);

		return tableInfo;
	}

	private static final void fillTableColInfo(TableInfo table) {
		List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		String sql = "SELECT COLUMN_NAME ,ORDINAL_POSITION ,IS_NULLABLE ,COLUMN_DEFAULT ,COLUMN_TYPE ,COLUMN_KEY ,EXTRA ,COLUMN_COMMENT "
				+ " FROM information_schema.columns WHERE table_schema ='"
				+ ConfigConsts.DB_NAME
				+ "' AND table_name = '" + table.getTableName() + "'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = DBHandler.createConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ColumnInfo fm = new ColumnInfo();

				fm.setColumnName(rs.getString("COLUMN_NAME"));
				fm.setOrdinalPosition(rs.getString("ORDINAL_POSITION"));
				fm.setIsNullable(rs.getString("IS_NULLABLE"));
				fm.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
				fm.setColumnType(rs.getString("COLUMN_TYPE"));
				fm.setColumnKey(rs.getString("COLUMN_KEY"));
				fm.setExtra(rs.getString("EXTRA"));
				fm.setColumnComment(rs.getString("COLUMN_COMMENT"));

				list.add(fm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				DBHandler.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		table.setColumns(list);
	}
}