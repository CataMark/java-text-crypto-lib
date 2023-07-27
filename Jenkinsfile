pipeline {
    agent any
    stages {
        stage ('clean') {
            steps {
                bat "mvn clean"
            }
        }
        stage ('install in local maven repository') {
            steps {
                bat "mvn install"
            }
        }
    }
}
