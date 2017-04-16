#!groovy

node {

	stage "Checkout"
	checkout scm
	
	stage "Prepare Java"
	env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"
	bat "java -version"

	stage "Clean And Build"
	bat "gradlew -q clean build -x test"

	stage "Run Tests"
	bat "gradlew -q test"

	if (env.BRANCH_NAME.startsWith("/release")) {

        stage "Build Debian"
        bat "gradlew -q buildDeb"

        stage "Upload Archives"
        bat "gradlew -q uploadArchives"

	}

}
