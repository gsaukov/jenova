#!groovy

properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '10', artifactNumToKeepStr: '5', daysToKeepStr: '10', numToKeepStr: '5']]])

node {

	stage("Checkout") {
		checkout scm
	}

	stage("Build and Package") {
		sh "./gradlew clean build buildDeb"
	}

	stage("Collect Version Info") {
		scriptOuput = sh(script: "ls ./earthrise/build/libs/", returnStdout: true).trim()
		scriptOuput = scriptOuput.substring(scriptOuput.indexOf("-") + 1).trim()
		env.version = scriptOuput.substring(0, scriptOuput.indexOf(".jar")).trim()

		currentBuild.description = "Version: " + env.version
	}

	stage("Check Server Availability") {
		sh "ansible all -i ./ansible/inventories/inventory.ini -m ping"
	}

	stage("Deploy Packages") {
		sh "ansible-playbook ./ansible/playbooks/distribute.yml -i ./ansible/inventories/inventory.ini " +
				"--extra-vars \"version=" + env.version + "\""
	}

}
