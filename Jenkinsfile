#!groovy

properties([disableConcurrentBuilds(), [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '5', artifactNumToKeepStr: '3', daysToKeepStr: '5', numToKeepStr: '3']]])

node {

	stage("Prepare Environment") {
		jdk = tool(name: "JDK8")
		env.JAVA_HOME = "${jdk}"

		echo("JDK Installation Path: ${jdk}")

		sh '$JAVA_HOME/bin/java -version'
	}

	stage("Checkout") {
		checkout scm
	}

	stage("Gathering Info") {
		env.version = sh(script: "./gradlew printVersion -q", returnStdout: true).trim()
		currentBuild.description = env.version
		print "Build version assigned: " + env.version

		env.instancesCount = sh(script: "cat ./ansible/inventories/inventory.ini | grep ansible_connection | wc -l",
				returnStdout: true).trim()
		print "Expected server instances: " + env.instancesCount
	}

	stage("Build Artifacts") {
		sh "./gradlew -PversionToUse=" + env.version + " clean build buildDeb"
	}

	stage("Check Availability") {
		sh "ansible all -i ./ansible/inventories/inventory.ini -m ping"
	}

	stage("Install Artifacts") {
		sh "ansible-playbook ./ansible/playbooks/distribute.yml -i ./ansible/inventories/inventory.ini " +
				"--extra-vars \"version=" + env.version + "\""
	}

	stage("Waiting for Startup") {
		int expected = env.instancesCount as Integer
		int actual = 0

		// 30 attempts x 10 seconds = 5 minutes until aborting.
		for (int attempt in 1..30) {
			actual = sh(script: "curl -s http://deepwater:8761/eureka/apps | grep instanceId | wc -l",
					returnStdout: true).trim() as Integer

			if (actual > 0) actual += 1 // Compensate for the eureka instance which is not registered with itself.

			print "Attempt: " + attempt + ", Status: " + actual + " out of " + expected

			if (actual < expected) {
				sleep 10 // Wait for some time until next retry (in seconds).
			} else {
				break
			}
		}

		if (actual != expected) {
			error "Not all instances were started within 5 minutes time."
		}

	}

}
