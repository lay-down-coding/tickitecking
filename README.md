## 프로젝트 설명

### 프로젝트 명: tickitecking

### 프로젝트 한줄 소개: 콘서트 티켓 예매 서비스

## API 명세서

[API 명세서 링크](https://www.notion.so/teamsparta/83ae30a7b10a47438af40aaf7b190b3c?v=8e7002a52899442781b78544c2338cb2&pvs=4)

## 와이어 프레임

![image](https://github.com/lay-down-coding/tickitecking/assets/67190090/779117df-a2a2-4e98-a56d-4bcef288b586)

## ERD 다이어그램

![image](https://github.com/lay-down-coding/tickitecking/assets/67190090/242fc7c8-fc01-4096-b97b-8a1afe41514c)

## 서비스 아키텍처 구성도

![image](https://github.com/lay-down-coding/tickitecking/assets/67190090/f2eca068-3064-4867-b17e-80a62ace667a)

## 기술 스택

| 카테고리           | 사용 기술                                                                                           |
|--------------------|-----------------------------------------------------------------------------------------------------|
| Backend frameworks | Spring Boot, Spring JPA, Spring Security, JWT, QueryDSL                                             |
| TEST               | Junit, Jmeter                                                                                       |
| CI/CD              | Github Actions                                                                                      |
| Database           | AWS RDS, AWS ElastiCache                                                                            |
| Cloud services     | AWS ECS, Fargate, AWS ALB, AWS S3                                                                   |
| APM                | Prometheus, Grafana                                                                                 |

## 기술적 의사결정

| 요구사항         | 선택지                                   | 기술 선택 이유                                                                                                                                                                                                                                                                      |
|--------------|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 배포  | ECS + Fargate               | - 대용량 트래픽을 다룰수 있는 서버환경을 위해 오토스케일링을 염두에 두고 EC2와 ECS 중 고민을 하다 EC2는 서버를 돌리기 위해 필요한 JDK, Docker 등 설치 및 설정을 해줘야 하지만 ECS + Fargate를 사용한다면 그러한 설정이 필요하지 않아 서버 관리에 효율적이라 생각하여 선택하게 되었음. |
| 예매 동시성 제어    | Redis                | - 예매에서 같은 좌석 동시 요청시 중복예매가 발생<br> - Redis는 단일 스레드 기반 커맨드를 순차적으로 실행하고 결과를 전달하기 때문에 동시성 이슈를 해결할 수 있으며, 다양한 데이터 타입과 커맨드를 제공해 주기 때문에 사용                                                                                                   |
| 데이터베이스      | RDS, ElastiCache | - 오토 스케일링을 염두에 두고 있었기 때문에 분산 서버마다 관리, 설정해야하는 로컬 데이터베이스보다 RDS와 ElastiCache를 통해 하나의 데이터베이스로 관리할 수 있기 때문에 사용<br> - EC2 인스턴스를 만들어 도커를 통해 MySQL 및 Redis를 만들어주고 설정할 수 있기는 하지만 AWS가 편리하게 제공하는 RDS, Elasticache를 사용함.     |
| CI/CD | Github Actions                 | - Github Actions를 팀원 모두 사용해본 경험이 있어 다른 툴(ex) Jenkins)를 새롭게 배우는 것보다 학습비용이 현저히 낮을 것이라 판단함.<br> - 쉽게 CI/CD를 구축할 수 있게 해주며, 별도의 서버 설치 없이도 사용 가능한 편리함을 이용하기로 결정                                                         |
| 모니터링  | Grafana<br>Prometheus<br> ~~ELK~~          | - 시스템 관점에서 중요한 지표인 CPU 사용률, 메모리, 디스크 IO 등을 시각화하는 데 특화된 Prometheus를 선택하였습니다.<br> - Prometheus는 이런 지표들을 직관적으로 제공함으로써, 시스템 상태의 이해를 높이고 이상 징후를 빠르게 파악할 수 있게 도와줍니다. 또한, Prometheus의 Service Discovery 기능은 Auto scaling에 따른 동적인 시스템 변화에도 자동으로 대응하여 지표 수집을 보장하기 때문에 선택하게 되었습니다.                                                                                  |
