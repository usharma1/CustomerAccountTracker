# CustomerAccountTracker-Case-Study-SpringBoot-Crud

Project: Customer Account Tracker
The objective of this sample case study is to build Spring Boot application incrementally by learning Spring REST, Spring Data & spring TEST.

A Leading private bank looking for solution to track customers and their account details. As part of requirements, the below mentioned specification has been given to the partner to implement.

Requirements Specification:

1.Able to create new account for a customer (only one account type / customer)
	Account type may be savings (individual/joint) & current etc.,
2.Able to edit customer personal details                                 
3.Able to fetch one or more customer personal details including account details too
4.Customers can transfer funds from one account to another account. If enough fund exists.


#To Add Account 									:-localhost:8080/account/add
#To EDIT Customer details							:-localhost:8080/customer/update/(id)
#To FETCH ACCOUNT DETAILS including Customer 		:-localhost:8080/account/getAllAccount
#To Transfer FUNDS 									:-localhost:8080/account/transfer/(from)/(to)/(amount)

