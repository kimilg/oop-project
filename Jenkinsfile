#!groovy

DEFAULT_LINK_URL = env.BUILD_URL

properties([
    disableConcurrentBuilds()
])
    
    
//properties([pipelineTriggers([githubPullRequests(events: [close()], spec: '', triggerMode: 'HEAVY_HOOKS'), githubPush()])])
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
    
//     stage('deleteOldPostmanData') {
//         deleteOldPostmanData();
//     }

    
    
    if(isMergeCommit() && env.BRANCH_NAME == "main"){
        stage('UnitTest') {
            echo "hello"
        }
    }
    else {
        parallel(
            'UnitTest': {
                stage('UnitTest') {
                    echo "hello"
                }
            },
            'IntegrationTest': {
                if (false) {
                    stage('IntegrationTest') {
                        fetchPostmanData();
                        integrationTest();
                    }
                }
            }
        )
    }
    
    echo "${currentBuild.buildCauses}"
    echo "${currentBuild.getBuildCauses('hudson.model.Cause$UserCause')}"
    echo "${currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')}"
    echo "${currentBuild.getBuildCauses('hudson.triggers.TimerTrigger$TimerTriggerCause')}" 
    def isStartedByUser = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').size()
    echo "$isStartedByUser"
    if (isStartedByUser) {
        echo "This is triggered by build now !!!!!!!!! "
        checkout([
                    $class                         : 'GitSCM',
                    branches                         : [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                    extensions                     : [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'postman'],
                                                      [$class: 'CloneOption', noTags: false, shallow: false, depth: 0, reference: '']],
                    userRemoteConfigs               : [[credentialsId: scm.getUserRemoteConfigs()[0].credentialsId, url: 'https://github.com/kimilg/myhomepage.git']]
            ]) 
        
        // curl로 GET shell script 실행 with postman api
        
        // git add, commit, push
        withCredentials([gitUsernamePassword(credentialsId: scm.getUserRemoteConfigs()[0].credentialsId,
                                         gitToolName: 'Default')]) {
            sh 'rm postman/postman-data/myTestCollection.postman_collection.json'
            sh 'git add .'
            sh 'git commit -m "Update postman data"'
            sh 'git push'
        }
        
        
    }


//     stage('updatePostmanData') {
//         fetchPostmanData();
//     }
//     stage('IntegrationTest') {
//        integrationTest()
//     } 
    
}

def isMergeCommit() { 
    commitName = checkout(scm).GIT_COMMIT
    return sh (
        script : "git rev-parse --verify -q ${commitName}^2 > /dev/null;",  
        returnStatus: true
        ) == 0
}

def deleteOldPostmanData() {
    sh "if [ ! -d ../../postman ]; then mkdir postman; fi" 
    dir('../../postman') {
        sh "rm -rf oop-project"
    } 
}

def fetchPostmanData() {
    sh "if [ ! -d postman ]; then mkdir postman; fi" 
    
//     dir('postman') {
//         git credentialsId: '7ac8dbd8-7b01-4840-9544-93685a7883f1', url: 'https://github.com/kimilg/myhomepage.git'
//     }
    checkout([
            $class                         : 'GitSCM',
            branches                         : [[name: '*/master']],
            doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
            extensions                     : [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'postman'],
                                              [$class: 'CloneOption', noTags: false, shallow: false, depth: 0, reference: '']],
            userRemoteConfigs               : [[credentialsId: scm.getUserRemoteConfigs()[0].credentialsId, url: 'https://github.com/kimilg/myhomepage.git']]
    ]) 
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
    //repoName = scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split('\\.')[0]
    //echo "repo name : ${repoName}" 
    //echo scm.getUserRemoteConfigs()[0].credentialsId
     
    nodejs('nodejs') {       
        try {
            sh "${nodeJsHome}/bin/newman run postman/postman-data/myTestCollection.postman_collection.json " +
            "--reporters cli,junit --reporter-junit-export 'newman/integration-test-result.xml' " +
            "--working-dir postman/postman-data/file " +
            "--iteration-data postman/postman-data/file/firstFile " +
            "--verbose"
        }  
        catch(e) {
            //notifySlack("Integration Test Failed.", "danger", env.BUILD_URL + "testReport")
            throw e
        } finally {
            junit testResults: 'newman/integration-test-result.xml', skipPublishingChecks: true
            //junit 'newman/integration-test-result.xml' 
        }
    }
    
}

def isPR() {
    return (env.BRANCH_NAME ==~ /^PR-\d+$/)
}


@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

