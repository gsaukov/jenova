#!groovy

properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '10', artifactNumToKeepStr: '5', daysToKeepStr: '10', numToKeepStr: '5']]])

node {

	stage("Checkout") {
		checkout scm
	}

	stage("Clean and Build") {
		sh "./gradlew clean build -x test"
	}

	stage("Gather Info") {
		scriptOuput = sh(script: "ls ./earthrise/build/libs/", returnStdout: true).trim()
		echo scriptOuput
		echo scriptOuput.indexOf("-")
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf("-") + 1).trim()
		echo scriptOuput
		echo scriptOuput.indexOf(".jar")
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf(".jar")).trim()
		echo scriptOuput
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
