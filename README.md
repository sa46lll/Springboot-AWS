# Springboot-AWS


> **테스트 코드**
- Junit assertThat
- assertj assertThat
  - CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않다.
  - 자동완성이 좀 더 확실하게 지원된다.
- @WebMvcTest
- @SpringBootTest, TestRestTemplate
  - JPA 기능까지 한번에 테스트
  

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
  

- **JPA**
  - **역활**
    - 관계형 데이터베이스에 맞게 SQL을 대신해서 실행
    - 객체 중심 개발 -> 생산성 향상, 유지보수 유리
  - **Spring Data JPA**
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
  - **아키텍처 구조**
    - Controller
      - 웹 계층
    - Service
      - 비즈니스 로직, 트랜잭션 처리
    - Repository
      - JPA로 DB에 직접 접근하는 계층
    - Domain
      - 엔티티가 모여있는 계층 (모든 계층에서 사용)
  - **개발순서, 특징**
    - Domain
      - 데이터베이스와 맞닿은 핵심 클래스 (테이블 생성, 스키마 변경)
    - Repository
      - JpaRepository<Entity 클래스, PK타입>을 상속하면 기본 CRUD 메소드 자동으로 생성
      - Entity Repository와 함께 위치해야한다.
    - Service
      - 트랜잭션과 도메인 간의 순서만 보장
      - Bean 주입 받는 방식 3가지
        - @Autowired
        - setter
        - 생성자 (권장)
          - RequiredArgsConstructor
            - 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움 해결
    - Controller
      - 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번
        - Dto를 분리해서 사용 (Entity 클래스 X)
  - **application.properties**
    - 스프링 부트가 자동으로 로딩하게 되는 규약들
    - key-value 형식으로 값을 정의하면 애플리케이션에서 참조하여 사용할 수 있다.
    ```
    my.name = sa46lll
    
    > app 
    @Value("${my.name}")
    String name;
    ```
  - **등록/수정/조회 API 생성**
    - 준비물
      - Request 데이터를 받을 Dto
      - API 요청을 받을 Controller
      - 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
        - Service에서는 트랜잭션, 도메인 간 순서 보장의 역활. (비즈니스 로직은 Domain에서 처리)
  - **영속성 컨텍스트**
    - 엔티티를 영구 저장하는 환경
      - 엔티티 매니저를 사용해 회원 엔티티를 영속성 컨텍스트에 저장
        ```EntityManager.persist(member);```
      - 더티체킹
        - Entity 객체의 값만 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함. (쿼리를 날릴 필요가 없음)
    - 특징
      - 엔티티 매니저를 생성할 때 하나 만들어짐
      - 엔티티 매니저를 통해서 영속성 컨텍스트에 접근하고 관리할 수 있음.
  - **JPA Auditing**
    - DB에 삽입할 때 테이블에 자동으로 시간을 매핑하여 넣어줌. (반복적인 코드 감소)
    - LocalDate, LocalDateTime의 등장
      - 기존 날짜 타입인 Date, Calander의 문제점을 Java8부터 해결함.
        - 문제 1. 불변 객체가 아니다.
          - 멀티스레드 환경에서 언제든 문제가 발생할 수 있다.
        - 문제 2. Calendar는 월 (Month) 값 설계가 잘못되었다.
          - 10월을 나타내는 Calendar.OCTOBER의 숫자 값이 '9'
    
  - Note (code)
    - Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.
      - 대신, 해당 필드의 값 변경이 필요하면 명확히 목적과 의도를 나타낼 수 있는 메소드를 추가해야 한다.
      ```java
      public class Order{
          public void cancelOrder(){
              this.status = false;
         }
      }
      
      public void 주문서비스의_취소이벤트(){
          order.cancelOrder();
      }
      ```
    - Builder
      - 어느 필드에 어떤 값을 패워야할지 명확하게 인지할 수 있다.
      ```java
      Example.builder()
          .a(a)
          .b(b)
          .build();
      ```

  
> **머스테치로 화면 구성**
- 템플릿 엔진
  - 지정된 템플릿 양식과 데이터가 합쳐져 HTML 문서를 출력하는 소프트웨어
  - 서버 템플릿 엔진(예전): JSP, Freemarker 
    - 서버에서 구동
  - 클라이언트 템플릿 엔진(요즘): 리액트, 뷰 등
    - 브라우저에서 화면을 생성, 서버에서 이미 코드가 벗어난 경우
  - 머스테치 이외의 템플릿 엔진들의 단점
    - JSP,Velocity
      - 스프링 부트에서는 권장하지 않는 템플릿 엔진
    - Freemarker
      - 템플릿 엔진으로는 너무 과하제 많은 기능 지원, 높은 자유도로 인해 순련도가 낮을 수록 비즈니스 로직이 추가될 확률이 큼
    - Thymeleaf
      - 스프링 진영에서 적극적으로 밀고 있지만 문법이 어려움.
- 머스테치
  - 수많은 언어를 지원하는 가장 심플한 템플릿 엔진
    - 루비, 자바스크립트, 파이썬, PHP, 자바, 펄, Go, ASP 등
  - 장점
    - 문법이 다른 템플릿 엔진보다 심플
    - 로직 코드를 사용할 수 없어 View 역할과 서버의 역할이 명확하게 분리
    - .js와 .java 2가지가 다 있어, 하나의 문법으로 클라이언트/서버 템플릿을 모두 사용 가능
    - Intellij 커뮤니티 버전 지원 (Thymeleaf, JSP 등 지원X)
  - 특징
    - 앞의 경로와 뒤의 파일 확장자는 자동으로 지정
      - 앞: src/main/resources/templates
      - 확장자: .mustache
  - 프론트엔드 라이브러리 사용 방법
    - 외부 CDN
    - 직접 라이브러리를 받아서 사용
  - HTML
    - css는 header에, js는 footer에
      - HTML은 위에서부터 코드가 실행되기 때문에 head가 다 실행되고서 body가 실행
      - 즉, head가 다 불러지지 않으면 사용자 쪽에선 백지 화면만 노출되므로
      - js 용량이 클수록 body 부분의 실행이 늦어지기 때문에 js는 body 하단에 두어 화면이 다 그려진 뒤에 호출하는 것이 좋음.
- **데이터 조회**
  - FK의 조인, 복잡한 조건 등으로 인해 Entity 클래스만으로 처리하기 어려워 조회용 프레임워크를 추가로 사영
    - querydsl, jooq, MyBatis
    - querydsl 장점
      - 타입 안정성 보장
        - 단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드를 기반으로 쿼리를 생성
        - 오타나 존재하지 않는 컬럼명 명시하면 IDE에서 자동 검출
      - 국내 많은 회사에서 사용
        - 쿠팡, 배민 등 JPA를 적극적으로 사용하는 회사
      - 레퍼런스가 많음
        - 많은 회사와 개발자가 사용하다보니 국내 자료가 많음.
- 데이터 등록/수정/삭제
  - SpringDataJpa

> **스프링 시큐리티와 OAuth2.0 로그인**

- **스프링 시큐리티**
  - 막강한 인증과 인가 기능을 가진 프레임워크
  - 인터셉터, 필터 기반의 보안 기능을 구현하는 것보다 적극 권장하고 있음.
  - 소셜 로그인을 사용하는 이유
    - 직접 구현해야 하는 것들이 많아짐.
      - 로그인 시 보안
      - 회원가입 시 이메일 혹은 전화번호 인증
      - 비밀번호 찾기, 변경
      - 회원정보 변경
  - 라이브러리
    - spring-security-oauth2-autoconfigure 라이브러리
      - 스프링 부트 1.5에서 쓰던 설정을 2에서도 그대로 사용 가능
    - **Spring Security Oauth2 Client 라이브러리**
      - 소셜 로그인 등 클라이언트 입장에서 소셜 기능 구현 시 필요한 의존성
      - 장점
        - 스프링 부트용 라이브러리(starter) 출시
        - 확장 포인트를 고려해서 설계됨
        - 스프링 부트2 방식의 자료를 찾고 싶은 경우 2가지만 확인하면 됨.
          - spring-security-oauth2-autoconfigure 라이브러리를 사용하는지
          - application.properties(yml)
            - 스프링 부트 1.5: url 주소 모두 명시
            - 스프링 부트 2.0: client 인증 정보만 입력, enum 대체
              - _CommonOAuth2Provider_ 이라는 enum이 새롭게 추가
                - 구글, 깃허브, 페이스북, 옥타의 기본 설정값을 모두 여기서 제공
  - Enum 클래스 (Role)
    - 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 함.
      - ROLE_GUEST, ROLE_USER 등
  
            



  
  

    