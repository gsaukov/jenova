#!groovy

node {

	stage "Checkout"
	checkout scm
	
	stage "Prepare Environment"
	env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"

    scriptOuput = sh(script: "sudo ./gradlew :printVersion -q", returnStdout: true).trim()
    buildVersion = scriptOuput.substring(scriptOuput.lastIndexOf("-q") + 2).trim()

    stage "Perform Clean Build"
	sh "sudo ./gradlew clean build -x test"

	stage "Execute Tests"
	sh "sudo ./gradlew test"

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage "Build Debian"
        echo "to be implemented"

	}

}
