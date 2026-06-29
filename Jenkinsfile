pipeline {
    agent any
    tools {
        jdk 'jdk21'
        maven 'maven3'
        
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Nassimbhs/JOCL-BackEnd.git'
            }
        }
        stage('Check Java') {
            steps {
                sh 'java -version'
            }
        }
        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }

        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarQube') { 
                    sh 'mvn clean package -DskipTests sonar:sonar'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t jocl-backend .'
            }
        }
        stage('DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_TOKEN'
                )]) {
                    sh 'echo $DOCKER_TOKEN | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker tag jocl-backend $DOCKER_USER/jocl-backend:latest'
                    sh 'docker push $DOCKER_USER/jocl-backend:latest'
                }
            }
        }
        stage('Docker Compose Up') {
            steps {
                sh '''
                    docker-compose down --remove-orphans || true
                    docker stop jocl-backend || true
                    docker rm jocl-backend || true
                    docker stop jocl-frontend || true
                    docker rm jocl-frontend || true
                    docker stop jocl-mysql || true
                    docker rm jocl-mysql || true
                    docker-compose up -d --build
                '''
            }
        }
        stage('Push to Nexus') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'nexus-credentials',
                    usernameVariable: 'NEXUS_USER',
                    passwordVariable: 'NEXUS_PASSWORD'
                )]) {
                    sh '''
                        cat > /tmp/settings.xml << EOF
<settings>
  <servers>
    <server>
      <id>nexus-releases</id>
      <username>${NEXUS_USER}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
  </servers>
</settings>
EOF
                        mvn deploy -DskipTests \
                        -DaltDeploymentRepository=nexus-releases::http://192.168.137.15:8081/repository/jocl-releases/ \
                        --settings /tmp/settings.xml
                    '''
                }
            }
        }
    }
    
    post {
        success {
            mail to: 'barkia133@gmail.com',
                 subject: "✅ Pipeline SUCCESS - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """
                 Le pipeline ${env.JOB_NAME} a réussi !
                 Build : #${env.BUILD_NUMBER}
                 URL : ${env.BUILD_URL}
                 """
        }
        failure {
            mail to: 'barkia133@gmail.com',
                 subject: "❌ Pipeline FAILURE - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """
                 Le pipeline ${env.JOB_NAME} a échoué !
                 Build : #${env.BUILD_NUMBER}
                 URL : ${env.BUILD_URL}
                 Vérifiez les logs pour plus de détails.
                 """
        }
    }
}