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

	stage "Parallel"
	parallel (
		"Tests - Data": {
			input "Are u ok!?"
		},
		"Tests - Rest": {
			bat 'gradlew test --tests "com.pro.jenova.data.*"'
		}

	)
	
}
