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
		test01: {
			stage ("Test 01") {
				sleep 50
			}
		},
		test02: {
			stage ("Test 02") {
				sleep 50
			}
		}

	)
	
}
