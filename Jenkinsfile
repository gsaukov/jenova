#!groovy

properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '10', artifactNumToKeepStr: '5', daysToKeepStr: '10', numToKeepStr: '5']]])

node {

	stage("Checkout") {
		checkout scm
	}

	stage("Collect Version Info") {
		env.version = sh(script: "./gradlew printVersion", returnStdout: true).trim()
		currentBuild.description = env.version
	}

	stage("Clean and Build") {
		sh "./gradlew -PversionToUse=" + env.version + " clean build"
	}

	stage("Create Packages") {
		sh "./gradlew -PversionToUse=" + env.version + " buildDeb"
	}

	stage("Check Availability") {
		sh "ansible all -i ./ansible/inventories/inventory.ini -m ping"
	}

	stage("Deploy Packages") {
		sh "ansible-playbook ./ansible/playbooks/distribute.yml -i ./ansible/inventories/inventory.ini " +
				"--extra-vars \"version=" + env.version + "\""
	}

}
