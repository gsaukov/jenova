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

	stage("Version Info") {
		env.version = sh(script: "./gradlew printVersion -q", returnStdout: true).trim()
		currentBuild.description = env.version
		print "Build version assigned: " + env.version
	}

	stage("Build Artifacts") {
		sh "./gradlew --version"
		sh "./gradlew -PversionToUse=" + env.version + " clean build buildDeb"
	}

	stage("Check Availability") {
		sh "ansible all -i ./ansible/inventories/inventory.ini -m ping"
	}

	stage("Install Artifacts") {
		sh "ansible-playbook ./ansible/playbooks/distribute.yml -i ./ansible/inventories/inventory.ini " +
				"--extra-vars \"version=" + env.version + "\""
	}

}
