#!groovy

node {

	stage "Checkout"
	checkout scm

	stage "Prepare"
    scriptOuput = sh(script: "gradlew :printVersion -q", returnStdout: true).trim()
    buildVersion = scriptOuput.su	bstring(scriptOuput.lastIndexOf("-q") + 2).trim()
	currentBuild.description = "Branch-Name: ".concat(env.BRANCH_NAME)

    stage "Perform Clean Build"
	sh "gradlew clean build -x test"

	stage "Execute Tests"
	sh "gradlew test"

	if ("${env.BRANCH_NAME}".startsWith("release/")) {

        stage "Deploy"
        echo "to be implemented"

	}

}
