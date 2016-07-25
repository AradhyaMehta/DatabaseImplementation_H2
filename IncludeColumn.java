/**
 * IncludeColumn.java
 */
package org.h2.table;

/**
 * This represents an include column item of an index.
 * 
 * @author Siddharth Khialani
 * 
 */
public class IncludeColumn {

	public String columnName;

	public Column column;

    /**
     * Create an array of include columns from a list of columns.
     *
     * @param columns the column list
     * @return the include column array
     */
	public static IncludeColumn[] wrap(Column[] columns) {
		IncludeColumn[] list = new IncludeColumn[columns.length];
		for (int i = 0; i < list.length; i++) {
			list[i] = new IncludeColumn();
			list[i].column = columns[i];
		}
		return list;
	}

    /**
     * Map the columns using the column names and the specified table.
     *
     * @param includeColumns the column list with column names set
     * @param table the table from where to map the column names to columns
     */
	public static void mapColumns(IncludeColumn[] includeColumns, Table table) {
		if (includeColumns != null) {
			for (IncludeColumn col : includeColumns) {
				col.column = table.getColumn(col.columnName);
			}
		}
	}

}
