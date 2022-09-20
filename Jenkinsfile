#!groovy

DEFAULT_LINK_URL = env.BUILD_URL

properties([
    disableConcurrentBuilds()
])
    
    
properties([pipelineTriggers([githubPullRequests(events: [close()], spec: '', triggerMode: 'HEAVY_HOOKS'), githubPush()])])
node {

    stage('Checkout') {
        checkout([
                $class                         : 'GitSCM',
                branches                         : scm.branches,
                doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                extensions                     : [[$class: 'CloneOption', noTags: false, shallow: false, depth: 0, reference: '']],
                userRemoteConfigs               : scm.userRemoteConfigs,
        ])
    }
    
//     Properties properties = new Properties()
//     File propertiesFile = new File('/Users/user/Documents/kimilg/oop-project/local.properties')
//     propertiesFile.withInputStream {
//         properties.load(it)
//     }
//     
//     def POSTMAN_COLLECTION_UID = properties."POSTMAN_COLLECTION_UID"
//     def POSTMAN_ENVIRONMENT_UID = properties."POSTMAN_ENVIRONMENT_UID"
//     def POSTMAN_API_KEY = properties."POSTMAN_API_KEY"
    

    
    if(isMergeCommit() && env.BRANCH_NAME == "main"){
        echo "wow merge commit!"
    }
    else {
        echo "wow not merge commit@!"
    }
    
    
    stage('IntegrationTest') {
        integrationTest()
    }
    
}

def isMergeCommit() { 
    commitName = checkout(scm).GIT_COMMIT
    return sh (
        script : "git rev-parse --verify -q ${commitName}^2 > /dev/null;",  
        returnStatus: true
        ) == 0
}
  
def integrationTest() {
    nodeJsHome = tool name: 'nodejs', type: 'nodejs'
    //newmanHome = "${nodeJsHome}/bin"    
    commitName = checkout(scm).GIT_COMMIT
    branchName = checkout(scm).BRANCH_NAME
    echo "git commit is ${commitName}"
    echo "branch name is " + scm.branches[0].name //test-jenkins
    echo "branch name 2 is " + checkout(scm).GIT_BRANCH //origin/test-jenkins
    
    echo "원래 branch is " + env.BRANCH_NAME
    echo "target branch is " + env.CHANGE_TARGET
    
    echo "job name : " + env.JOB_NAME 
    repoName = checkout(scm).getUserRemoteConfig()[0].getUrl().tokenize('/').last()
        
    echo "repo name : " + repoName
    echo "repo name : " + $repoName
     
    withCredentials([string(credentialsId: 'secret-key', variable: 'key')]) {
        VARIABLE = "${key}" 
    }
    withCredentials([string(credentialsId: 'secret-key2', variable: 'key')]) {
        VARIABLE2 = "${key}" 
    }
    
    withCredentials([string(credentialsId: 'postman-collection-uid-platform-api', variable: 'collectionUid')]) {
        POSTMAN_COLLECTION_UID = "${collectionUid}"
    }
    withCredentials([string(credentialsId: 'postman-environment-uid-platform-api', variable: 'environmentUid')]) {
        POSTMAN_ENVIRONMENT_UID = "${environmentUid}"
    }
    withCredentials([string(credentialsId: 'postman-api-key', variable: 'apiKey')]) {
        POSTMAN_API_KEY = "${apiKey}"
    }
    withCredentials([string(credentialsId: 'postman-workspace-id-smarteditor', variable: 'workspaceId')]) {
        POSTMAN_WORKSPACE_ID = "${workspaceId}"
    }
    
    nodejs('nodejs') {
        try {
            
//             sh "${nodeJsHome}/bin/newman run ~/Downloads/platform-api.postman_collection.json " +
//             "--environment ~/Downloads/platform-api-dev.postman_environment.json " +
//             "--reporters cli,junit --reporter-junit-export 'newman/integration-test-result.xml'" +
//             "--working-dir /Users/user/Postman/files"
            
            sh 'npm install -g newman-reporter-postman-cloud'
            
            sh "${nodeJsHome}/bin/newman run https://api.getpostman.com/collections/${POSTMAN_COLLECTION_UID}?" +
            "apikey=${POSTMAN_API_KEY} " +
            "--environment https://api.getpostman.com/environments/${POSTMAN_ENVIRONMENT_UID}?" +
            "apikey=${POSTMAN_API_KEY} " +
            "--reporters postman-cloud --reporter-apikey ${POSTMAN_API_KEY} " +
            "--reporter-workspaceId ${POSTMAN_WORKSPACE_ID} "
        }
        catch(e) {
            //notifySlack("Integration Test Failed.", "danger", env.BUILD_URL + "testReport")
            throw e
        } finally {
//             junit 'newman/integration-test-result.xml' 
        }
    }
    
//         nodejs('nodejs') {
//             try { 
//                 sh "node -v"
//                 //sh "${newmanHome}/newman run ~/Downloads/ilgoo-test-collection.postman_collection.json"
//                 sh "${nodeJsHome}/bin/newman run ~/Downloads/ilgoo-test-collection.json " +
//                    //"--reporters htmlextra --reporter-htmlextra-export 'newman/newman-html-result.html' "
//                    //"--reporters html --reporter-html-export 'newman/newman-html-result.html' "                                  
//                 "--reporters cli,junit --reporter-junit-export 'newman/myreport.xml'" 
//             } catch(e) {
//                 echo "wow this fails!!"
//                 throw e
//             } finally {
//                 junit 'newman/myreport.xml' 
//             }
//         }
    
}

def isPR() {
    return (env.BRANCH_NAME ==~ /^PR-\d+$/)
}


@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

