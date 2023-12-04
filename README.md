<p align="center"><img width="50%" alt="Triplet_logo" src="https://github.com/YooHyunJi/Triplet/assets/88645538/6b2791cf-6e45-4b37-967b-bb9edf9f91c7"></p>
<p align="center" style="font-weight: bold;font-style: italic">Let's Trip, Triplet!</p>
<br>

<p align="center">Triplet은 공공 데이터를 이용한 여행 계획 웹 서비스입니다</p>  
<p align="center">모두를 위한 여행지 정보 제공부터 회원만을 위한 나만의 여행 계획, 여행 정보를 얻을 수 있는 게시판과 채팅까지!</p>  
<p align="center">여행을 위한 모든 준비를 도와드립니다</p>

<br>

## :luggage: 주요 기능
### :mag: 여행지 검색
<img width="2545" alt="attr_search" src="https://github.com/YooHyunJi/Triplet/assets/88645538/3b5f15c8-eb9d-4a0a-8f73-f847d5e59bc5">

- 시/도, 여행지 유형, 검색어에 따른 여행지를 검색할 수 있습니다
- 검색 결과는 검색창 아래에 목록으로 나열되며, 각각의 여행지는 지도에 marker로 나타납니다
- 지도 위 marker에 마우스 커서를 올리거나 목록에 있는 여행지를 클릭하는 경우, 해당 여행지를 중심으로 지도가 이동하며 해당 여행지의 정보가 overlay로 나타납니다
- overlay에는 해당 여행지의 좋아요 수가 나타나며, 해당 기능은 회원만 이용 가능합니다

### :world_map: 나만의 여행 계획
<img width="2545" alt="plan_make" src="https://github.com/YooHyunJi/Triplet/assets/88645538/ec2424d4-ce13-48a3-8632-63f42285f9cb">

- 여행의 제목, 인원, 일자, 각 일자에 방문할 여행지, 여행지 별 예산과 메모를 등록해 나만의 여행 계획을 만들 수 있습니다
- 여행지를 추가할 때에는 검색 결과/좋아요 누른 여행지 목록으로부터 Drag&Drop 형식으로 추가할 수 있습니다
- 여행지 별 예산을 등록하고 인원을 변경할 때마다 일자 별 예산, 총 예산이 자동으로 변경됩니다
- 경로 계산 버튼을 누르면 해당 일자에 방문할 여행지들 간 최단 경로를 확인할 수 있습니다
- 우측 상단 저장 버튼으로 여행 계획을 저장할 수 있습니다

<br>

<img width="1825" alt="plan_detail" src="https://github.com/YooHyunJi/Triplet/assets/88645538/4e41c263-ea2d-4a1c-a0b4-76339b612c06">

- 저장된 여행 계획은 마이페이지를 통해 다시 열람할 수 있습니다
- 저장된 여행 계획에서도 경로 계산 기능을 사용할 수 있습니다


### :clipboard: 여행 정보 게시판
<img width="1825" alt="board_list" src="https://github.com/YooHyunJi/Triplet/assets/88645538/7d6a5924-349c-4e54-a9ad-40b1c6decb6b">

- 여행과 관련된 정보를 자유롭게 교환할 수 있는 게시판입니다
- 게시글의 제목, 작성자 닉네임, 글 번호로 검색이 가능합니다

<br>

<img width="2560" alt="board_detail" src="https://github.com/YooHyunJi/Triplet/assets/88645538/06e5af6f-971e-4169-8fe9-bd0d8e96e189">

- 각각의 게시글에는 좋아요와 댓글 기능이 제공됩니다
- 각각의 게시글과 댓글은 수정 및 삭제가 가능합니다
- 사용자가 프로필 이미지나 닉네임을 변경할 경우, 댓글의 정보도 함께 변경됩니다

### :speech_balloon: 여행지 별 채팅
<img width="2560" alt="main_chatting_list" src="https://github.com/YooHyunJi/Triplet/assets/88645538/482807d5-fd4d-451b-978b-c21e7f88c9b4">

- 메인 페이지에서 각 여행지 별 채팅방 목록을 확인할 수 있습니다
- 각각의 채팅방 목록을 클릭하면 해당 여행지의 채팅방으로 입장할 수 있습니다

<br>

<img width="2560" alt="chatting_chattingroom" src="https://github.com/YooHyunJi/Triplet/assets/88645538/e894b810-7ef3-4c77-8c57-6701daaaaf6f">

- 채팅방에서는 실시간으로 다른 사용자와 대화할 수 있습니다
- 위의 이미지처럼 각각 다른 계정으로 로그인한 사용자들이 같은 채팅방에서 실시간으로 대화를 나눌 수 있습니다

<br>

## :books: 기술 스택
### FrontEnd
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"><img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"><img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"><img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">

### BackEnd
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/MyBatis-b9271a?style=for-the-badge&logo=&logoColor=white">

### DataBase
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/mongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">

### API
<img src="https://img.shields.io/badge/kakaoMap-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"><img src="https://img.shields.io/badge/kakaoMobility-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">

### Collaboration Tool
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"><img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"><img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">

<br>

## :gear: 실행 환경 설정
### FrontEnd
1. 폴더 이동 > node modules 설치 > FrontEnd 서버 실행
    ```bash
    cd go-trip-with-jun-yeop
    npm i
    npm run dev
    ```

### BackEnd
1. docker hub 설치
2. docker 컨테이너 생성 > 실행
    ```bash
    cd docker
    docker-compoese up -d
    ```
3. MySql 설치
4. src > main > java > com.sj.trip > BackEndApplication 실행

<br>

## :bar_chart: 사용한 데이터
1. 공공데이터포털 한국관광공사_국문 관광정보 서비스_GW
https://www.data.go.kr/iim/api/selectAPIAcountView.do

2. 공공데이터포털 행정안전부_행정표준코드_법정동코드
https://www.data.go.kr/iim/api/selectAPIAcountView.do
