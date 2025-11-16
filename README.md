# Oops_Log - 실수와 성취를 기록하는 성장 다이어리

<img width="1322" alt="Image" src="./image/banner.png" />

<br>

---

## <span id="1"> 🖼️ DevOops 팀원 소개</span>

<div align="center">

|   |   |   |   |   |
| :--------------------------------------------------------------: | :--------------------------------------------------------------: | :--------------------------------------------------------------------------: | :-----------------------------------------------------------: | :--------------------------------------------------------------: |
|      <img src="./image/kang.png" width="350px;" alt=""/>      |      <img src="./image/jung.png" width="350px;" alt=""/>      |            <img src="./image/lee.png" width="350px;" alt=""/>            |    <img src="./image/song.png" width="350px;" alt=""/>     |      <img src="./image/yoon.png" width="350px;" alt=""/>      |
|      [강지현](https://github.com/Kjandgo)      |      [정건일](https://github.com/kyuhon)      |      [이순우](https://github.com/SoonWooLee1)      |      [송형석](https://github.com/thdgudtjr0415)      |      [윤서진](https://github.com/ysj0826)      |

</div>

 
<br>

-----

## 🗂️ **프로젝트 기획서: Oops_Log**

<br>

### **1-1. 개요 (Overview)**

> ### **"실수와 성취를 기록하며 자기수용과 회복력을 키우는 조용한 서재"**
>
>   완벽하지 않아도 괜찮습니다.
>
>   완성되지 않아도 괜찮습니다.
>

<br>

Oops_Log는 실수(Oops)와 성취(Ooh)를 함께 기록하며 성장하는 공간입니다.

흔들렸다면 살아있다는 증거이고, 빛났다면 성장했다는 증표입니다.

당신의 하루는 기록될 가치가 있습니다.

<br>

-----

### **1-2. 프로젝트 배경 (Background)**

#### **🤔 기존의 문제점**

우리는 완벽을 강요받는 시대에 살고 있습니다.

SNS에는 성공과 행복만이 가득하고, 실수와 좌절은 숨겨야 할 것처럼 여겨집니다.

<br>

#### **💡 새로운 기록 공간의 필요성**

하지만 진짜 성장은 실수를 인정하고, 작은 성취를 축하하고, 흔들리는 나 자신을 받아들일 때 시작됩니다.

Oops_Log는 당신이 완벽하지 않아도 괜찮다고 말합니다. 오늘 넘어졌다면, 다시 일어났다는 것이 더 중요합니다.

이곳은 당신을 위한 조용한 서재입니다. 천천히, 솔직하게, 당신만의 이야기를 기록해보세요.

<br>

> #### **✨ 기대 효과 및 핵심 목표 ✨**
>
> -----
>
>   - **✅ 실수의 자산화:** 실수를 감추지 않고 기록함으로써 성장의 패치노트로 만듭니다.
>
>   - **🤝 성취의 축하:** 작은 성취(Ooh)도 기록하며 스스로를 인정하고 동기를 부여합니다.
>
>   - **📈 성장 과정 추적:** 감정 강도와 태그를 통해 나의 감정 패턴을 파악합니다.
>
>   - **🔒 안전한 공간:** 비공개/익명 공개 옵션을 통해 솔직한 이야기를 안전하게 기록합니다.
>

---

# **✨ Oops_Log: 주요 기능 ✨**

각 기능이 어떻게 실제 데이터와 연결되는지 함께 확인해 보세요.

### 😭 Oops - 실수의 기록

- **불안, 후회, 피로**의 순간을 기록합니다.
  
- 실수는 버그가 아니라 성장의 패치노트라는 관점으로 스스로를 용서하고 다시 일어서는 연습을 합니다.
  
- AI가 사용자의 글을 분석하여 감정을 공감하고 따뜻한 피드백을 제공합니다.
  
  > 💾 **관련 기능:** OopsCommandController, OopsQueryController, AiController

---

### 🥰 Ooh - 성취의 기록

- **감사, 만족, 희망**의 순간을 기록합니다.
  
- 작은 성취도 큰 의미가 있음을 되새기며 오늘의 빛나는 순간을 놓치지 않습니다.
  
- 다른 사용자의 Ooh 기록에 '좋아요'를 누르고 댓글로 응원할 수 있습니다.
  
  > 💾 **관련 기능:** OohCommandController, OohQueryController, LikesCommandController, CommentsCommandController

---

### 🏷️ 감정 강도와 태그

- 오늘의 감정 강도와 유형을 표시하고 태그를 남깁니다.
  
- 시간이 지나며 나의 감정 패턴을 파악할 수 있습니다.
  
  > 💾 **관련 기능:** TagCommandController, TagQueryController, AdminReadController

---

### 🔒 안전한 기록 공간

- 모든 기록은 비공개(기본) 또는 익명 공개를 선택할 수 있습니다.
  
- 다른 사람과 공유 시에는 익명으로 표시되어 안전하게 소통할 수 있습니다.
  
  > 💾 **관련 기능:** OohCommandEntity (isPrivate), OopsCommandEntity (oopsIsPrivate)

---

### 🛡️ 커뮤니티 안전장치

- 불쾌한 게시글이나 댓글은 신고 기능을 통해 관리자에게 알릴 수 있습니다.
  
- 관리자는 신고 내역을 상세 조회하고 '승인' 또는 '반려' 처리를 할 수 있습니다.
  
- 신고 승인 시 해당 게시물이나 댓글은 숨김(소프트 삭제) 처리됩니다.
  
  > 💾 **관련 기능:** ReportController, ReportReadController, ReportSerivceImpl

---

### 📈 성장 그래프 및 활동

- 마이페이지에서 내가 기록한 Oops/Ooh 기록을 잔디 그래프로 확인할 수 있습니다.
  
- 팔로우/팔로워 기능을 통해 다른 사용자와 소통하고, 북마크 기능으로 의미 있는 기록을 저장할 수 있습니다.
  
  > 💾 **관련 기능:** RecordHistoryQueryController, FollowQueryController, BookmarkQueryController

<br>

---

## 📋 2. 기능 명세서

## 2-1. WBS

<img src = "./image/devoopsWBS.png" width = "1000"> </img><br>

## 2-2. 요구사항 명세서 

<img src = "./image/요구사항명세서1.png" width = "1000"> </img>
<img src = "./image/요구사항명세서2.png" width = "1000"> </img> <br>

---

## 📐 3. DDD 설계

<details>
    <summary>이벤트 도메인</summary>
<img src = "./image/eventdomainDDD.png" width = "1000"> </img>
</details>

<details>
    <summary>Policy-Context</summary>
<img src = "./image/devoopsDDD.png" width = "1000"> </img>
</details>

<br>

---

## 💾 4. DB 설계

<details>
    <summary>논리 모델링</summary>
<img src = "./image/논리.png" width = "1000"> </img>
</details>

<details>
    <summary>물리 모델링</summary>
<img src = "./image/erd.png" width = "1000"> </img>
</details>

<br>

---

## 🎨 5. Figma

<details>
    <summary>Figma 디자인</summary>
<img src = "./image/figma.png" width = "1000"> </img>
</details>

<br>

---

## 🔄 6. CI/CD 프로젝트 아키텍쳐

<details>
    <summary>CI/CD</summary>
<img src = "./image/cicd아키텍쳐.png" width = "1000"> </img>
</details>

<br>

---

## 🧪 7. CI/CD 테스트

<details>
    <summary>CI/CD 테스트</summary>

- jenkins stage view

<img src = "./image/cicd.png" width = "1000"> </img>

- argoCD pipeline
	
<img width="1836" height="841" alt="스크린샷 2025-11-14 120616" src="https://github.com/user-attachments/assets/b65391e9-d513-4d7c-9e08-e7b37ae763fa" />

</details>

<br>

---

## 🧪 8. 단위 테스트

<details>
    <summary>단위 테스트</summary>
<img src = "./image/단위1.png" width = "1000"> </img>
<img src = "./image/단위2.png" width = "1000"> </img>
	<details>
    		<summary>세부 내용</summary>
		<img src = "./image/단위세부.png" width = "1000"> </img>
	</details>
</details>


<br>

---

## 🤖 9. 젠킨스 파이프라인 파일 스크립트 코드

```groovy
pipeline {
    agent any

    tools {
        gradle 'gradle'
        jdk 'openJDK17'
    }

    environment {
        SOURCE_GITHUB_URL = 'https://github.com/DevOops-be19/Oops_log-backend.git'
        MANIFESTS_GITHUB_URL = 'https://github.com/DevOops-be19/oops_log_manifest.git'
        GIT_USERNAME = 'kjandgo'
        GIT_EMAIL = 'rptmffld0204@gmail.com'
    }

    stages {
        stage('Preparation') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker --version'
                    } else {
                        bat 'docker --version'
                    }
                }
            }
        }
        stage('Source Build') {
            steps {
                git branch: 'main', url: "${env.SOURCE_GITHUB_URL}"
                script {
                    withCredentials([file(credentialsId: 'oops_yml', variable: 'secretFile')]){
                        if (isUnix()) {
                            sh 'cp $secretFile ./src/main/resources/application.yml'
                            sh "chmod +x ./gradlew"
                            sh "./gradlew clean build -x test"
                        } else {
                            bat "copy %secretFile% .\\src\\main\\resources\\application.yml"
                            bat "gradlew.bat clean build -x test"
                        }
                    }
                }
            }
        }
        stage('Container Build and Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        if (isUnix()) {
                            sh "docker build -t ${DOCKER_USER}/oops_log_back:${currentBuild.number} ."
                            sh "docker build -t ${DOCKER_USER}/oops_log_back:latest ."
                            sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
                            sh "docker push ${DOCKER_USER}/oops_log_back:${currentBuild.number}"
                            sh "docker push ${DOCKER_USER}/oops_log_back:latest"
                        } else {
                            bat "docker build -t ${DOCKER_USER}/oops_log_back:${currentBuild.number} ."
                            bat "docker build -t ${DOCKER_USER}/oops_log_back:latest ."
                            bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                            bat "docker push ${DOCKER_USER}/oops_log_back:${currentBuild.number}"
                            bat "docker push ${DOCKER_USER}/oops_log_back:latest"
                        }
                    }
                }
            }
        }
        stage('K8S Manifest Update') {
            steps {
                // k8s-manifests 리포지토리를 main 브랜치에서 클론한다. 이때 자격 증명 github가 사용된다.
                git credentialsId: 'github',
                    url: "${env.MANIFESTS_GITHUB_URL}",
                    branch: 'main'
                
                script { 
                    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                        def githubUrl = env.MANIFESTS_GITHUB_URL.replace('https://', '')
                        if (isUnix()) {
                            // Unix 시스템에서 boot-deployment.yml 파일 수정 후 commit 후 push
                            sh "sed -i '' 's/argo_boot:.*\$/oops_log_back:${currentBuild.number}/g' kubernetes_manifest/oops-backend-deploy.yml"
                            sh "git add kubernetes_manifest/oops-backend-deploy.yml"
                            sh "git config --global user.name '${env.GIT_USERNAME}'"
                            sh "git config --global user.email '${env.GIT_EMAIL}'"
                            sh "git commit -m '[UPDATE] ${currentBuild.number} image versioning'"
                            // 인증 정보 포함하여 push
                            sh "git push https://${GIT_USER}:${GIT_PASS}@${githubUrl} main"
                        } else {
                            // Windows 시스템에서 boot-deployment.yml 파일 수정 후 commit 후 push
                            bat "powershell -Command \"(Get-Content kubernetes_manifest/oops-backend-deploy.yml) -replace 'oops_log_back:.*', 'oops_log_back:${currentBuild.number}' | Set-Content kubernetes_manifest/oops-backend-deploy.yml\""
                            bat "git add kubernetes_manifest/oops-backend-deploy.yml"
                            bat "git config --global user.name '${env.GIT_USERNAME}'"
                            bat "git config --global user.email '${env.GIT_EMAIL}'"
                            bat "git commit -m \"[UPDATE] ${currentBuild.number} image versioning\""
                            // Windows에서 변수 참조 방식 사용
                            bat "git push https://%GIT_USER%:%GIT_PASS%@${githubUrl} main"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (isUnix()) {
                    sh 'docker logout'
                } else {
                    bat 'docker logout'
                }
            }
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
```

<br>

---

## 💻 10. 기술 스택

### BACKEND
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
<img src="https://img.shields.io/badge/JPA-Hibernate-blue?style=for-the-badge&logo=hibernate&logoColor=white">
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">

### FRONTEND
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Vue.js](https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)
![Vue Router](https://img.shields.io/badge/Vue_Router-4FC08D?style=for-the-badge)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)
<img src="https://img.shields.io/badge/Pinia-FFD859?style=for-the-badge&logo=pinia&logoColor=white"> 

### DB
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-FFB725?style=for-the-badge&logo=MyBatis&logoColor=black)
<img src="https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=redis&logoColor=white"> 

### DEVOPS
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> 
<img src="https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/Argo%20CD-FE6A16?style=for-the-badge&logo=argo&logoColor=white">

### TOOL
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
  
  <br>
</div>

<br>

---

## ✍️ 11. 개인 회고록

### 강지현

**이번 프로젝트에서 맡은 역할**
- 백엔드 패키지 구조화
- 회원가입
- 회원 로그인
- java mail, redis를 이용한 아이디,비밀번호 찾기
- security 적용
- JWT 토큰 필터 적용
- 쿠버네티스 클러스터 구축
- jenkins,argocd등을 이용한 빌드/배포 자동화 환경 구축

**잘한 점**
- 다른 팀에 비해 인원수가 적었지만 그래도 팀원들 간에 소통이 원활하게 진행되고 서로 부족한 부분을 보완해 가면서 한정된 시간과 자원 안에서 해결할 수 있는 문제들을 거의 다 해결했다는 것에 만족한다.

**아쉬운 점**
- 추가로 sse나 Oauth2.0 등의 도입을 생각했지만 우선적인 개발 순서에 밀려 구현할 시간이 부족했다는 점이 아쉬움에 남는다. 추후에 프로젝트를 fork해서 추가로 구현하는 등의 계획을 가질 생각이다.

**배운 점**
- 모놀리식 개발이라서 고려하지는 않았지만 msa,쿠버네티스 환경으로 scale out한다면 고려해야하는 문제들에 대해서 많은 생각을 가질 수 있는 시간이였다고 생각한다.(예를 들어 sse를 통한 실시간 알림 기능을 구현할 때도 모놀리식에서는 단순히 메세지 send만 하면 되지만 쿠버네티스 클러스터에서 여러 replicaSet을 구축하거나 msa처럼 여러 서버를 나누게 되면 동시성 문제등을 고려해야 함)

**다음 프로젝트에 적용하고 싶은 것**
- sse를 이용한 실시간 알림
- 여러 replicaSet 서버 환경에서 sse등 비동기 서비스가 안정적으로 작동하게 하기 위한 브로커 메세지 서버 구축(rabbitMQ)

### 정건일

**이번 프로젝트에서 맡은 역할**
- 11

**잘한 점**
- 11

**아쉬운 점**
- 11

**배운 점**
- 11

**다음 프로젝트에 적용하고 싶은 것**
- 11

### 송형석

**이번 프로젝트에서 맡은 역할**
- Oops 기록 백엔드 개발 및 프론트 개발
- Ooh 기록 백엔드 개발 및 프론트 개발
- Notice(공지사항) 백엔드 개발 및 프론트 개발
- MyBatis + JPA 혼합 구조로 R/ CUD 를 적용

**잘한 점**
- DDD 설계및 물리 모델링 시점 부터 설게, 구현 까지 흐름을 일관성 있게 완성하려 함.
- 시간 및 기간 내에 일정 관리 및 각 기능에 대한 처리를 완료함

**아쉬운 점**
- 프로트 및 백엔드를 번갈아가면 수정하는 것이 당연하지만 빈도수가 잦아 아쉬웠음.
- DevOps 부분(Docker/K8s)에서 더 깊게 관여하고 싶었지만 구현 우선순위 때문에 충분히 참여하지 못한 점이 아쉬움.
- 테스트 코드 작성 비중이 낮음.

**배운 점**
- 도메인 기반 설계의 중요성
   - 테이블 설계 → API 설계 → 프론트까지 이어지는 전체 흐름을 경험하면서 초반 설계의 명확성이 얼마나 중요한지 체감함.
- 협업의 중요성
   - 프론트 요구사항 반영, 응답 필드 수정 등 작은 변화라도 빠르고 명확한 소통이 매우 중요함을 느꼈음.

### 이순우

**이번 프로젝트에서 맡은 역할**
- 북마크 (Bookmark) 기능 백엔드 API 개발 및 프론트엔드 연동
- 팔로우 (Follow) 기능 백엔드 API 개발 및 프론트엔드 연동
- JPA를 활용하여 사용자(Member), 게시글(Oops/Ooh), 팔로우(Follow) 간의 연관 관계를 매핑하고 CUD 로직을 구현
- MyBatis를 활용하여 북마크 목록, 팔로잉 피드 등 복합적인 조회(R) 쿼리를 구현

**잘한 점**
- 사용자(Member)와 게시글(Oops/Ooh) 간의 북마크, 사용자(Member)와 사용자(Member) 간의 팔로우 관계를 처음부터 명확하게 도메인 모델링하여 설계에 반영하려 노력함.
- JPA를 활용해 CUD(북마크 추가/삭제, 팔로우/언팔로우) 로직을 객체 지향적으로 처리하고, MyBatis를 사용해 북마크 목록 조회나 팔로잉 피드 조회 같은 복합 조회(R) 성능을 확보하는 하이브리드 방식을 성공적으로 적용함.

**아쉬운 점**
- DevOps 부분(Docker/Kubernetes)에서 더 깊게 관여하고 싶었지만, 맡은 기능 구현의 우선순위 때문에 충분히 참여하지 못한 점이 아쉬움.
- 기능 구현 대비 테스트 코드 작성 비중이 낮았음. 특히 동시성 문제에 대한 테스트가 부족했음.

**배운 점**
- AuthenticationPrincipal을 통해 토큰에서 사용자 ID를 가져와 로직에 적용하는 인증 기반 서비스의 전체 흐름을 경험함.
- 프로젝트를 하면서 기능 별로 파일 구조와 브랜치 구조를 정하는 것이 매우 편리하다는 것을 배움.
- 팀원들의 의견을 사소한 것 하나라도 다 같이 모여서 공유하면서 의논하는 분위기 형성이 프로젝트 완성도에 좋은 기여를 한다는 것을 경험함.

**다음 프로젝트에 적용하고 싶은 것**
- CSS를 좀 더 다이어리 처럼 깨끗하게 적용함으로써 사용자들이 편안함을 느끼게끔 하고 싶음
- 다음 프로젝트에서는 기능 개발과 더불어 Docker/K8s 배포 자동화 부분에도 적극적으로 참여하여 DevOps 경험을 쌓고 싶음.
- 지도 api를 사용해서 일기에 적은 장소를 나타낼 수 있는 기능 같이, 다양한 api를 활용해서 현재 기능보다 더 다양한 기능을 구현하고 싶음

### 윤서진

**이번 프로젝트에서 맡은 역할**
- 백엔드 구현
  - 좋아요 도메인 CRUD 기능 구현
  - 댓글 도메인 CRUD 기능 구현
  - 태그 도메인 CRUD 기능 구현
  - 기록 작성 시 자동 카운트되는 기록 집계 기능 JPA로 구현
  - 좋아요, 댓글 CRD 테스트코드 작성
- 프론트엔드 구현
  - 백엔드에서 맡은 도메인 화면 및 기능 구현
  - 기록 집계를 토대로 깃허브의 잔디 기능 구현

**잘한 점**
- 맡은 도메인이 다른 여러 도메인과 엮여있어 전반적인 흐름을 파악할 수 있었음
- 거의 매일 회의를 하며 진행 상황을 공유해서 원활한 협업과 역할 분담이 가능했음
- 충돌을 처리하기 쉽도록 git의 브랜치를 여러 개로 나누어 관리함

**아쉬운 점**
- contribution 캘린더를 구현할 때 연도 선택 기능을 넣지 않아 1년간의 기록만 확인 가능했음
- 프론트에서 설계 오류로 댓글 삭제를 soft에서 hard delete로 바꿈
- 모든 도메인에서 적어도 조회 단위 테스트코드를 작성하고 싶었는데 시간이 부족해 일부만 작성함

**배운 점**
- pinia를 사용해 권한 설정을 하는 방법과 백엔드와 프론트엔드 모두에서 권한을 관리해야 함을 이해할 수 있었음
- github에서 팀원들과 협업하며 여러 브랜치로 나누어 관리하는 방식에 익숙해 질 수 있었음
- 테스트 코드를 여러번 작성해보며 service 계층에서의 단위 테스트를 작성하는 방법을 배울 수 있었음

**다음 프로젝트에 적용하고 싶은 것**
- github에서 issue와 pr 템플릿을 활용하여 더 체계적으로 진행상황을 관리하고 싶음

