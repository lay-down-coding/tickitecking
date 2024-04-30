티켓 예매 서비스, Tickitecking!
---

## 프로젝트 팀원

##### 리더 : 정현준

* https://github.com/hu6r1s
* https://hu-bris.tistory.com/

##### 부리더 : 임현태

* https://github.com/jinkshower
* https://jinkshower.github.io/

---

> ##### 스파르타 코딩클럽 내일배움캠프 스프링 4기
> ##### 개발기간 : 2024/03/26 ~ 2023/04/30

##### Back-End

<img src="https://img.shields.io/badge/Java-61DAFB?style=for-the-badge&logo=java&logoColor=black">  <img src="https://img.shields.io/badge/gitHub-'181717'?style=for-the-badge&logo=gitHub&logoColor=white">    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">    <img src="https://img.shields.io/badge/gradle-2088FF?style=for-the-badge&logo=gradle&logoColor=white">

##### Infrastructure

<img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">  <img src="https://img.shields.io/badge/aws ECR-DC322D?style=for-the-badge&logo=aws&logoColor=white">    <img src="https://img.shields.io/badge/AWS ECS Fargate-632.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/AWS EC2-232.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/AWS RDS-1032.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/AWS Elasticache-132.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/AWS S3-932.svg?&style=for-the-badge&logo=jmeter&logoColor=white">  


##### Data base

<img src="https://img.shields.io/badge/MySql-4479A1?style=for-the-badge&logo=MySql&logoColor=white">    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">

##### Monitoring

<img src="https://img.shields.io/badge/actuator-262.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/prometheus-332.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    <img src="https://img.shields.io/badge/Grafana-832.svg?&style=for-the-badge&logo=jmeter&logoColor=white">    

##### Load Test

<img src="https://img.shields.io/badge/jmeter-232.svg?&style=for-the-badge&logo=jmeter&logoColor=white">

## Technical Decisions

---

|         | 선택지                       | 기술 선택 이유                                                                                                                                                    |
| ------- | ------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 서버환경    | ECS + Fargate             | - EC2을 사용하면 직접 서버를 구성하는데 필요한 요소들을 설정을 해줘야 하는 번거로움이 있음.<br>- ECS와 Fargate를 사용하면 서버리스로 컨테이너 실행 환경을 제공해주기 때문에 별다른 관리가 필요없으며, EC2보다 쉽게 오토 스케일링을 수행할 수 있기 때문에 도입 |
| 이미지 저장소 | S3                        | 이미지 파일을 데이터베이스나 서버 공간에 저장할 시 스토리지 용량에 대한 걱정을 덜어낼 수 있는 확장성을 가지고 있으며, 데이터 손실 위험이 적기 떄문에 도입                                                                    |
| 글로벌 캐시  | Redis                     | - 서버 분산시 로컬캐시는 데이터 정합성과 관리에 문제가 있음을 인지<br>- 글로벌 캐시 벤더 중 가장 많이 쓰이고 다양한 자료구조, 영속화 기능을 지원하여 캐싱 이외에도 쓰일 수 있는 확장성을 지닌 Redis를 도입                                  |
| CI/CD   | Github Actions            | - 매번 반복되는 코드통합/배포과정의 자동화 필요성을 인지, CI/CD 툴의 도입을 고려하게 됨.<br>- 별도의 서버 설치가 필요하지 않고 팀원 모두 사용한 경험이 있어 학습비용이 낮을 것으로 예상되는 Github Actions 선택                         |
| 모니터링    | Grafana<br>Prometheus<br> | - 운영중 서버의 상태와 비정상 수치를 추적하고 이를 시각화하여 팀원과 공유할 필요성을 인지<br>- Spring Boot 프로젝트와 연동이 쉽고 레퍼런스가 많으며 무료인 Actuator, Prometheus, Grafana 선택<br>                        |
| 부하테스트   | Jmeter                    | - 서버 가용성에 대한 테스팅 도구 필요성을 인지<br>- 가장 레퍼런스가 많고 꾸준한 Release로 안정성을 인정받는 Jmeter 선택                                                                               |

## **Performance Improvement**

---

- **좌석조회 API 성능 30배 개선**
    -  문제점
        - 10만개의 데이터가 있을 때 좌석 조회 API의 평균 응답시간 28000ms, TPS 2.9  
       
    - 해결 방안 :  `쿼리 최적화`,`인덱스`, `캐싱`
        - 쿼리 최적화 : 풀테이블 스캔 쿼리 2개 -> 1개
        - 인덱스 : 테이블 설계 변경, 좌석 상태를 enum status로 모으고 해당 부분 인덱스 처리
        - 캐싱 : 변하지 않는 데이터 캐싱 처리
    - 개선 후 : 평균 응답시간 960ms, TPS 99.1로 30배 개선


- **읽기, 쓰기 동시 요청시 읽기 속도 개선**
    -  문제점
        - 읽기와 쓰기가 동시에 요청될 때 읽기 속도 평균 응답시간 12231ms, TPS 7.7

    - 해결 방안 :  `DB Replication`
        - DB Replication : 쓰기 전용 DB Source, 읽기 전용 DB Replica로 구성
        - @Transactional의 읽기 전용 여부에 따라 Source, Replica로 분기하여 처리
    - 개선 후 : 평균 응답시간 7610ms, TPS 12.3으로로 60% 개선


- **좌석 생성, 수정 속도 개선**
    -  문제점
        - 좌석 생성, 수정시 1분 이상이 걸림

    - 해결 방안 :  `알고리즘 개선`, `batch 처리`
        - 알고리즘 개선 : n^3의 시간 복잡도를 n^2로 개선
        - batch 처리 : Jdbc Template의 batchupdate를 사용하여 쿼리 한번으로 처리 
    - 개선 후 : 좌석 생성 , 수정 80초 -> 1초로 개선

## **Trouble Shooting**

---

- **동시 요청시 예매 중복 생성 문제**
    - 문제점
        - 멀티 쓰레드 환경을 고려하지 않아 동시에 같은 좌석을 예매할 시 예매가 중복 생성됨
    - 해결 방안 :  `Redis`
        - DB락, SQS, Redis를 각각 적용 후 성능 측정 및 장단점 비교
        - Redis 선택 :  Redis의 Set 자료구조를 사용해 예매 중복 생성 체크 로직을 담당하게 하여 해결


- **CI Build시 오류**
    - 문제점
        - Github Actions의 Runner 서버에 Redis가 설치되어 있지 않아 CI Build시 오류 발생
    - 해결 방안 : `추상화`, `기술 의존성 제거`
        - Root Cause는 테스트가 Redis에 의존하고 있는 것이라고 판단
        - 해당 로직을 Interface로 추상화하여 기술이 바뀌더라도 기존 코드 변경을 최소화
        - 테스트 코드에서는 더블 객체를 통해 Redis에 의존하지 않도록 변경 
        - CI Build에서 Redis 설치 생략 후 시간 40초 단축

## API

---

[API 명세서 링크](https://www.notion.so/teamsparta/83ae30a7b10a47438af40aaf7b190b3c?v=8e7002a52899442781b78544c2338cb2&pvs=4)

## 🗺️ERD

---

![image](https://github.com/lay-down-coding/tickitecking/assets/67190090/242fc7c8-fc01-4096-b97b-8a1afe41514c)

## 🧭서비스 아키텍처

---

![image](https://github.com/jinkshower/jinkshower.github.io/assets/135244018/1e1fc70c-6434-4a78-84eb-aca5c0b507c8)

