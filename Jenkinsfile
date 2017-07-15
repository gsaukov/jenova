#!groovy

node {

	stage "Checkout"
	checkout scm
	
	stage "Prepare"
	env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"

    scriptOuput = bat(script: "gradlew :printVersion -q", returnStdout: true).trim()
    buildVersion = scriptOuput.substring(scriptOuput.lastIndexOf("-q") + 2).trim()

    stage buildVersion

    stage "Clean and Build"
	bat "gradlew clean build -x test"

	stage "Execute Tests"
	bat "gradlew test"

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage "Build Debian"
        bat "gradlew buildDeb"

        stage "Upload Archives"
        bat "gradlew uploadArchives"

	}

}
