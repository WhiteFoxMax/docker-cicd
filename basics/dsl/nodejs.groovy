job('NodeJS example') {
    scm {
        git('git://github.com/wardviaene/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install")
    }
}

job('NodeJS Docker example Max') {
    scm {
        git('git://github.com/WhiteFoxMax/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs-new') 
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('whitefoxmax/test_repo')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('whitefoxmax')
            forcePull(false)
            forceTag(false)
            buildContext('./basics')
            createFingerprints(false)
            skipDecorate()
        }
    }
}

