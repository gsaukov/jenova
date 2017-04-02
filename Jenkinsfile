#!groovy

node {

	stage "Checkout"
	checkout scm
	
	stage "Prepare Java"
	env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"
	bat "java -version"

	stage "Build"
	bat 'gradlew clean build -x test'

	stage "Test"
	bat 'gradlew test'
	
	// stage "Question"
	// input message: 'Are you sure?'
	
}
