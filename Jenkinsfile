#!groovy

properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10']]]);

node {

	stage("Checkout") {
		checkout scm
	}

	stage("Clean and Build") {
		sh "./gradlew clean build -x test"
	}

	stage("Gather Info") {
		scriptOuput = sh(script: "ls ./earthrise/build/libs/", returnStdout: true).trim()
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf("-") + 1).trim()
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf(".jar")).trim()
		currentBuild.description = scriptOuput
	}

	stage("Execute Tests") {
		sh "./gradlew test"
	}

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage("Deploy") {
			echo "to be implemented"
		}

	}

}
