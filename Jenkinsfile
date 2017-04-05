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

	parallel (
		"Stream 01" : {
			stage ("Test 01") {
				bat 'gradlew test'
			}
		},
		"Stream 02" : {
			stage ("Test 02") {
				sleep 5000
			}
		}

	)
	
}
