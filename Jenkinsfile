#!groovy

node {

	stage "Checkout"
	checkout scm
	
	stage "Prepare Java"
	env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"
	bat "java -version"

	stage "Clean And Build"
	bat "gradlew clean build -x test"

	stage "Run Tests"
	bat "gradlew test"

    echo "${env.BRANCH_NAME}"
    echo "${env.GIT_BRANCH}"

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage "Build Debian"
        bat "gradlew buildDeb"

        stage "Upload Archives"
        bat "gradlew uploadArchives"

	}

}
