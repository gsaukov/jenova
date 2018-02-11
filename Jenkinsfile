#!groovy

node {

	stage("Checkout") {
		checkout scm
	}

	stage("Prepare") {
		scriptOuput = sh(script: "ls ./earthrise/build/libs/", returnStdout: true).trim()
		scriptOuput = scriptOuput.substring(scriptOuput.firstIndexOf("-") + 1).trim()
		scriptOuput = scriptOuput.substring(scriptOuput.firstIndexOf(".jar")).trim()
		currentBuild.description = scriptOuput
	}

	stage("Clean and Build") {
		sh "./gradlew clean build -x test"
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
