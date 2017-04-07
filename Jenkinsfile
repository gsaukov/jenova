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

	stage "Stop Server"
	env.GRADLE_OPTS = '-Dspring.batch.job.names=test'
	bat 'gradlew :batch:bootRun'
	
}
