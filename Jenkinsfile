pipeline {
    agent any

    tools{
        jdk 'jdk17'
        maven 'maven3'
    }

    environment {
        // GCP Artifact Registry Details
        GCP_REGION   = 'asia-southeast1' // Replace with your region (e.g., us-central1)
        GCP_PROJECT  = 'gcp-web-example'
        // Jenkins Credentials ID containing your GCP Service Account JSON key
        GCP_CRED_ID  = 'gcp-service-niwatz3000-key' 

        //REGISTRY_URL = 'asia-east1-docker.pkg.dev'        


        //asia-southeast1-docker.pkg.dev/gcp-web-example/niwatz3000-docker-repo

        REPO_NAME    = 'niwatz3000-docker-repo'
        IMAGE_NAME   = 'gcp-api-fnc-example'
        IMAGE_TAG    = "${BUILD_NUMBER}"
        
        REGISTRY_URL = "${GCP_REGION}-docker.pkg.dev"


        FULL_IMAGE   = "${REGISTRY_URL}/${GCP_PROJECT}/${REPO_NAME}/${IMAGE_NAME}:${IMAGE_TAG}"
        SHORT_IMAGE   = "${REGISTRY_URL}/${GCP_PROJECT}/${REPO_NAME}/${IMAGE_NAME}"
        
    }    

    stages {
        stage('Code Checkout') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/niwatz3000/gcp_api_function.git'
            }
        }
        
        // stage('OWASP Dependency Check'){
        //     steps{
        //         dependencyCheck additionalArguments: '--scan ./ --format HTML ', odcInstallation: 'db-check'
        //         dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        //     }
        // }

        // stage('Sonarqube Analysis') {
        //     steps {
        //         sh ''' mvn sonar:sonar \
        //            -Dsonar.host.url=http://localhost:9000/ \
        //             -Dsonar.login=squ_9bd7c664e4941bd4e7670a88ed93d68af40b42a3 '''
        //     }
        // }

        stage('Clean & Package'){
            steps{
                sh "mvn clean package -D gcp_api_function"
            }
        }


        
       stage("Docker Build & Push"){
            steps{
                script{




                    //withDockerRegistry([file(credentialsId: GCP_CRED_ID, variable: 'GCP_KEY_FILE')], toolName: 'docker') {
                    //withDockerRegistry([file(credentialsId: GCP_CRED_ID, variable: 'GCP_KEY_FILE')], toolName: 'docker') {
                    withCredentials([file(credentialsId: GCP_CRED_ID, variable: 'GCP_KEY_FILE')]) {    
                        
                        sh 'gcloud auth activate-service-account --key-file=$GCP_KEY_FILE'

                        sh 'gcloud auth configure-docker asia-southeast1-docker.pkg.dev --quiet'
                        
                        echo "https://${REGISTRY_URL}"
                        sh "docker build -t ${FULL_IMAGE} -f Dockerfile.final ."

                        // def imageName = "${IMAGE_NAME}:${IMAGE_TAG}"
                        
                        //echo "${imageName}"

                        // def buildTag = "${FULL_IMAGE}"
                        // def latestTag = "${SHORT_IMAGE}:latest"  // Define latest tag
                        
                        
                        cat " ${GCP_KEY} | docker login -u _json_key --password-stdin https://${REGISTRY_URL} "

                        //cat $GCP_KEY_FILE | docker login -u _json_key --password-stdin https://REGION-docker.pkg.dev

                        //echo "https://${buildTag}"

                        // sh "docker tag ${imageName} abdeod/${buildTag}"
                        // sh "docker tag ${imageName} abdeod/${latestTag}"  // Tag with latest




                        sh "docker push ${FULL_IMAGE}"
                        sh "docker push ${SHORT_IMAGE}:latest"  // Push latest tag


                        //docker tag my-app asia-southeast1-docker.pkg.dev/gcp-web-example/niwatz3000-docker-repo/my-app:v1
                        //docker push asia-southeast1-docker.pkg.dev/gcp-web-example/niwatz3000-docker-repo/my-app:v1                        

                                 
                        //sh "docker rmi ${FULL_IMAGE} || true"

                        //env.BUILD_TAG = buildTag

                    }

                    // withCredentials([file(credentialsId: GCP_CRED_ID, variable: 'GCP_KEY')]) {
                    //     sh '''
                    //         # ตัวอย่าง: Login Docker เข้า GCP Artifact Registry
                    //         cat $GCP_KEY | docker login -u _json_key --password-stdin https://${REGISTRY_URL}
                            
                    //         # ตัวอย่าง: Authenticate gcloud CLI
                    //         # gcloud auth activate-service-account --key-file=$GCP_KEY
                    //     '''
                    // }

                        
                }
            }
        }



        // stage('Build Docker Image') {
        //     steps {
        //         script {
        //             sh "docker build -t ${FULL_IMAGE} ."
        //         }
        //     }
        // }

        // stage('Authenticate & Push to GCP') {
        //     steps {
        //         // Secret file binding for GCP Service Account JSON Key
        //         withCredentials([file(credentialsId: GCP_CRED_ID, variable: 'GCP_KEY')]) {
        //             sh '''
        //                 # Authenticate Docker using the GCP Service Account Key
        //                 cat $GCP_KEY | docker login -u _json_key --password-stdin https://${REGISTRY_URL}
                        
        //                 # Push the image
        //                 docker push ${FULL_IMAGE}
                        
        //                 # Optional: Push latest tag
        //                 docker tag ${FULL_IMAGE} ${REGISTRY_URL}/${GCP_PROJECT}/${REPO_NAME}/${IMAGE_NAME}:latest
        //                 docker push ${REGISTRY_URL}/${GCP_PROJECT}/${REPO_NAME}/${IMAGE_NAME}:latest
        //             '''
        //         }
        //     }
        // }

        
        // stage('Vulnerability scanning'){
        //     steps{
        //         sh " trivy image abdeod/${buildTag}"
        //     }
        // }

        // stage("Staging"){
        //     steps{
        //         sh 'docker-compose up -d'
        //     }
        // }

    }

    post {
        always {
            // Cleanup local docker images to save disk space
            // sh "docker rmi ${FULL_IMAGE} || true"
            // sh "docker logout https://${REGISTRY_URL} || true"
            //   sh "docker build -t ${FULL_IMAGE} -f Dockerfile.final ."               
            //   sh "docker rmi ${FULL_IMAGE} || true"
                 echo " ${FULL_IMAGE}  "
                 echo " ${SHORT_IMAGE}:latest "


                 
        }
    }


}
