#!/bin/bash

# greet the user
function login() {
 	 java -jar target/burhan-1.0-SNAPSHOT.jar "login" $1
}

function logouts() {
	 java -jar target/burhan-1.0-SNAPSHOT.jar "logout"
}