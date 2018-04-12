# Mall JDBC

Welcome to the Mall SQL Assistant!

It's purpose is to assistant you (the database manager) to run SQL statements for your Mall database. To run the program, please make sure the mariadb-java-client is included in your classpath. Then, compile and run Mall.java.

### Mall Structure
The Mall database consists of 7 relations, described below.

* Employees(eid: integer, name: String, position: String, salary: double)
* Employs(store: String, eid: integer)
* Stores(name: String, location: String)
* Items(item: String, brand: String, price: double)
* Inventory(name: String, item: String, quantity: integer)
* Customers(name: String, address: String, aid: integer)
* Transactions(tid: integer, store: String, item: String, customer: String, date: Date)
* Accounts(aid: integer, balance: double,type: String) *type must be one of credit, checking, or cash*

### Advanced Users
Click the "Advanced" button at the top of the page. From there, enter any SQL statement you would like to execute. If there was an error in your syntax, the error will be displayed for your reference. Otherwise, the resulting table will be displayed.

### Inexperienced Users
Use the tabs at the top to look at each table in the database. To refine the columns displayed, use the radio buttons below the table. 
*IN PROGRESS* : search, update, insert, and delete functionality
