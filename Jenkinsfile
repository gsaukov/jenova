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
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf("-") + 1).trim()
		env.version = scriptOuput.substring(0, scriptOuput.indexOf(".jar")).trim()

		currentBuild.description = "Version: " + env.version
	}

	stage("Execute Tests") {
		sh "./gradlew test"
	}

	stage("Build Packages") {
		sh "./gradlew buildDeb"
	}

	stage("Check Availability") {
		sh "ansible all -i ./ansible/inventories/inventory.ini -m ping"
	}

	stage("Distribute Artifacts") {
		sh "ansible-playbook ./ansible/playbooks/distribute.yml -i ./ansible/inventories/inventory.ini " +
				"--extra-vars \"version=" + env.version + "\""
	}

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage("Deploy") {
			echo "to be implemented"
		}

	}

}
