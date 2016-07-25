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

Aggregate.java	Package: org.h2.expression			      
AggregateDataDefault.java	Package: org.h2.expression	
DataType.java	Package: org.h2.value				            
Value.java	Packaege: org.h2.value				            
ValueShort.java	Package: org.h2.value				          
ValueByte.java	Package: org.h2.value				          
ValueInt.java	Package: org.h2.value				            
ValueLong.java	Package: org.h2.value				          


Parser.java		Package: org.h2.command			                 
AlterTableAddConstraint.java	Package: org.h2.command.ddl	 
CreateIndex.java		Package: org.h2.command.ddl	           
CreateTable.java		Package: org.h2.command.ddl	           
BaseIndex.java		Package: org.h2.index			               
FunctionIndex.java	Package: org.h2.index			             
HashIndex.java		Package: org.h2.index			
Index.java		Package: org.h2.index			
IndexCursor.java	Package: org.h2.index		
LinkedIndex.java	Package: org.h2.index			
MetaIndex.java		Package: org.h2.index			
MultiVersionIndex.java	Package: org.h2.index			
NonUniqueHashIndex.java	Package: org.h2.index			
PageBTreeIndex.java	Package: org.h2.index			
PageDataIndex.java	Package: org.h2.index			
PageDelegateIndex.java	Package: org.h2.index			
RangeIndex.java		Package: org.h2.index			
ScanIndex.java		Package: org.h2.index			
SpatialTreeIndex.java	Package: org.h2.index	
TreeIndex.java		Package: org.h2.index			
MVDelegateIndex.java	Package: org.h2.mvstore.db	
MVPrimaryIndex.java	Package: org.h2.mvstore.db		
MVSecondaryIndex.java	Package: org.h2.mvstore.db	
MVSpatialIndex.java	Package: org.h2.mvstore.db		
MVTable.java		Package: org.h2.mvstore.db		
FunctionTable.java	Package: org.h2.table			
IncludeColumn.java	Package: org.h2.table			
MetaTable.java		Package: org.h2.table		
RangeTable.java		Package: org.h2.table		
RegularTable.java	Package: org.h2.table		
Table.java		Package: org.h2.table			
TableLink.java		Package: org.h2.table	
TableView.java		Package: org.h2.table	

TestTableEngines.java	Package: test.org.h2.test.db		



