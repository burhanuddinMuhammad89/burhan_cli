#!/bin/bash

# login user
function login() {
 	 java -jar target/burhan-1.0-SNAPSHOT.jar "login" $1
}

# logout user
function logouts() {
	 java -jar target/burhan-1.0-SNAPSHOT.jar "logout"
}

# deposit balance user
function deposit() {
	java -jar target/burhan-1.0-SNAPSHOT.jar "deposit" $1
}

# transfer to other user
function transfer() {
	java -jar target/burhan-1.0-SNAPSHOT.jar "transfer" $1 $2
}