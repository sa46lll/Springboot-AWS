# Springboot-AWS


> 테스트 코드
- Junit assertThat
- assertj assertThat
    - CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않다.
    - 자동완성이 좀 더 확실하게 지원된다.

> JPA
- 배경
  - 패러다임 불일치
    - 관계형 데이터베이스
      - 어떻게 데이터를 저장할지 초점이 맞춰진 기술
    - 객체지향 프로그래밍 언어
      - 기능과 속성을 한 곳에서 관리하는 기술
    
    ```
    User user = findUser();
    Group group = user.getGroup();
    ```
    여기서 데이터 베이스를 추가하면,
    ```
    User user = userDao.findUser();
    Group group = groupDao.findFroup(user.getGroupId);
    ```
    웹 애플리케이션 개발이 데이터베이스 모델링에만 집중하게 된다.
  

- JPA
  - 역활
    - 관계형 데이터베이스에 맞게 SQL을 대신해서 실행
    - 객체 중심 개발 -> 생산성 향상, 유지보수 유리
  - Spring Data JPA
    - Spring에서 JPA의 구현체들을 직접 다루지 않고 더 쉽게 사용하고자 추상화시킨 Spring Data JPA 모듈을 사용
    - JPA <- Hibernate <- Spring Data JPA
    - 장점
      - 구현체 교체의 용이성
        - Hibernate 외의 다른 구현체로 쉽게 교체하기 위함.
      - 저장소 교체의 용이성
        - 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함.
        - 많아지는 트래픽에 MongoDB로 교체가 필요하다면 Spring Data JPA에서 Spring Data MongoDB로 의존성만 교체해주면 됨.
        - Spring Data의 하위 프로젝트들은 save(), findAll(), findOne() 등 기본적인 기능은 가지고 있기 때문에 변경할 것이 없기 때문에 권장함.
    - 실무에서의 장점
      - CRUD 쿼리를 직접 작성할 필요가 없음.
      - 부모-자식 관계 표현, 1:N 관계 표현, 상태와 행위를 한 곳에서 관리하는 등 객체지향 프로그래밍을 쉽게 할 수 있음.
      - 높은 트래픽과 대용량 데이터 처리 (네이티브 쿼리만큼의 퍼포먼스를 낼 수 있다.)
    - But,
      - 높은 러닝 커브
        - JPA를 잘쓰려면 객체지향 프로그래밍과 관계형 데이터베이스 둘다 이해해야 하기 때문에 실무에서 잘 사용하지 못한다.
  
  
  

    