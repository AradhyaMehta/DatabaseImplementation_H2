1. Replace the below mentioned files in the H2 code base and compile the project again.
2. Run the file Server.java. This will start the server and open the console window.
3. Try running the following statements
	1. SELECT BIT_AND(column_name)
	   FROM table_name;
	   Similarly for BIT_OR and BIT_XOR.

	2. CREATE INDEX 
	   ON table_name(indexcolumn_name)
	   INCLUDE (includecolumn_name);


Below are the files to which the changes were made.

Aggregate.java	Package: org.h2.expression			Author: Siddharth Khialani
AggregateDataDefault.java	Package: org.h2.expression	Author: Siddharth Khialani
DataType.java	Package: org.h2.value				Author: Siddharth Khialani
Value.java	Packaege: org.h2.value				Author: Siddharth Khialani
ValueShort.java	Package: org.h2.value				Author: Siddharth Khialani
ValueByte.java	Package: org.h2.value				Author: Aradhya Mehta
ValueInt.java	Package: org.h2.value				Author: Siddharth Khialani
ValueLong.java	Package: org.h2.value				Author: Siddharth Khialani


Parser.java		Package: org.h2.command			Author: Siddharth Khialani
AlterTableAddConstraint.java	Package: org.h2.command.ddl	Author: Aradhya Mehta
CreateIndex.java		Package: org.h2.command.ddl	Author: Siddharth Khialani
CreateTable.java		Package: org.h2.command.ddl	Author: Siddharth Khialani
BaseIndex.java		Package: org.h2.index			Author: Siddharth Khialani
FunctionIndex.java	Package: org.h2.index			Author: Pranay Shashank Adavi
HashIndex.java		Package: org.h2.index			Author: Pranay Shashank Adavi
Index.java		Package: org.h2.index			Author: Siddharth Khialani
IndexCursor.java	Package: org.h2.index			Author: Siddharth Khialani
LinkedIndex.java	Package: org.h2.index			Author: Aradhya Mehta
MetaIndex.java		Package: org.h2.index			Author:	Aradhya Mehta
MultiVersionIndex.java	Package: org.h2.index			Author: Siddharth Khialani
NonUniqueHashIndex.java	Package: org.h2.index			Author: Siddharth Khialani
PageBTreeIndex.java	Package: org.h2.index			Author: Siddharth Khialani
PageDataIndex.java	Package: org.h2.index			Author: Siddharth Khialani
PageDelegateIndex.java	Package: org.h2.index			Author: Aradhya Mehta
RangeIndex.java		Package: org.h2.index			Author: Siddharth Khialani
ScanIndex.java		Package: org.h2.index			Author: Siddharth Khialani
SpatialTreeIndex.java	Package: org.h2.index			Author: Aradhya Mehta
TreeIndex.java		Package: org.h2.index			Author: Siddharth Khialani
MVDelegateIndex.java	Package: org.h2.mvstore.db		Author: Aradhya Mehta
MVPrimaryIndex.java	Package: org.h2.mvstore.db		Author: Aradhya Mehta
MVSecondaryIndex.java	Package: org.h2.mvstore.db		Author: Siddharth Khialani
MVSpatialIndex.java	Package: org.h2.mvstore.db		Author: Aradhya Mehta
MVTable.java		Package: org.h2.mvstore.db		Author: Aradhya Mehta
FunctionTable.java	Package: org.h2.table			Author: Siddharth Khialani
IncludeColumn.java	Package: org.h2.table			Author: Siddharth Khialani
MetaTable.java		Package: org.h2.table			Author: Pranay Shashank Adavi
RangeTable.java		Package: org.h2.table			Author: Pranay Shashank Adavi
RegularTable.java	Package: org.h2.table			Author: Pranay Shashank Adavi
Table.java		Package: org.h2.table			Author: Pranay Shashank Adavi
TableLink.java		Package: org.h2.table			Author:	Aradhya Mehta
TableView.java		Package: org.h2.table			Author:	Aradhya Mehta

TestTableEngines.java	Package: test.org.h2.test.db		Author:	Aradhya Mehta




