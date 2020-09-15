### 2020.09.01 스터디 내용

#### 1.gradle 프로젝트를 스프링부트 프로젝트로 변경하기
+ ##### build.gradle 추가 내용 - 플러그인 의존성 관리를 위한 설정작업
```
buildscript {
  ext {
    springBootVersion = 2.1.7.RELEASE
  }
   repositoies {
    mavenCentral() 
    jcenter()
  }
  dependencies {
    classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
  }
}
```
 *  ext : 전역변수
 *  repositories : 의존성 라이러리 원격장소 지정  
    - 기본 : mavenCentral()
    - 라이러리 업로드 난이도로 인해 jcenter()도 많이 사용한다.
 *  dependencies : 프로젝트에 필요한 의존성을 선언
 
### 2020.09.02 스터디 내용

#### 1.SpringBootApplication 

> 스프링 부트의 자동 설정 , 스프링 Bean 읽기와 생성을 모두 자동으로 설정한다.
>> SpringBootApplication이 있는 위치부터 설정을 읽어 가기 때문에 프로젝스 상당에 위치

#### 2.SpringApplication.run
> 내장 was 실행 (Web Application Service)
>> 스프링 부트는 내장 was 사용을 권장함 - 언제 어디서나 동일 환경에서 스프링부트를 배포할 수 있기 때문에

```
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController  {
    @GetMapping("/helloWorld")
    public String hello() {
        return "Hello! World";
    }
}
```
* RestController
  + 컨트롤러를 json으로 반환
  + ResponseBody를 각 메서드마다 선언했는데 한번에 사용할 수 있게 해준다.
* GetMapping
  + get 요청을 받을 수 있는 api를 만들어 준다
  + RequestMapping(method=RequestMethod.GET)으로 사용. 


### 2020.09.03 스터디
#### 1. Junit Test 추가


```
@RunWith(SpringRunner.class)
@WebMvcTest(controllers =  HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void returnHello() throws Exception {
        String helloWorld = "Hello! World";
        mvc.perform(get("/helloWorld"))
                .andExpect(status().isOk())
                .andExpect(content().string(helloWorld));
    }
}
```

* RunWith(SpringRunner.class) 
  + Junit의 내장된 실행자가 아닌 다른 실행자를 실행
  + 스프링부트와 Junit 사이의 연결자 역할
* WebMvcTest
  + Web(Spring MVC)에 집중할 수 있는 어노테이션
  + 선언시 Controller , ControllerAdvice 사용할 수 있지만 Service, Component, Repository 사용 불가
* Autowired
  + 스프링이 관리하는 Bean 주입
* MocMvc 
  + 웹API를 테스트할 때 사용 / 스프링MVC 테스트 시작점
  +  Http get, post api 테스트
* andExpect(status().isOk())
  + Http Header의 Status를 검증한다. 
  + 200, 404, 500 등의 상태를 검증한다.
  + 여기서는 200 여부를 검증
* andExpect(content.string(helloWorld))  
  + mvc.perform의 결과를 검증
  + Controller의 결과 값과 동일한지 검증
    
* 결과값이 동일하지 않은 경우
    > Tests failed: 1, passed: 0
* 결과값과 동일한 경우
    > Tests passed: 1

### 2020.09.04 스터디 내용
#### 1. lombok 추가

> build.gradle 
>> compile('org.projectlombok:lombok')

#### 2. lombok을 통한 VO 혹은 DTO 
```
@Getter
@RequiredArgsConstructor
public class HelloWordDto {
  private final String name;
  private final int age;
}
```

* Getter
  + 선언된 변수의 get메서드 생성
* RequiredArgsConstructor
  + final 변수만 포함한 생성자(Contstuctor)를 생성

#### 3. Test 작성

```
assertThat(dto.getName()).isEqualsTo(name);
assertThat(dto.getAge()).isEqualsTo(age);
```

* assertThat    
  + 테스트 검증 라이브러리의 검증 메서드
  + 검증 하고 싶은 대상을 검증 메서드 인자로 받음.
  + 메서드 체이닝이 지원되어 isEqualTo 메서드를 이어서 사용 할 수 있다.
    > 메서드 객체를 반환하게 되면 메서드 반환값인 객체를 통해 다른 함수를 호출
    > 이런 프로그래밍 패턴을 메서드 체이닝이라고 한다.
* isEqualTo
  + assertj의 비교 메서드
  + assertThat에 있는 값과 isEqualTo의 값을 비교해 같을 때만 성공임.
    > assertj가 Junit의 assertThat보다 편한 이유는 라이브러리가 필요하지 않다.
    > 자동완성이 좀더 확실이 지원된다.
    
    
### 2020.09.05 스터디 내용
#### 1. 테스트 

```
@Test
public void rtnHelloDto throws Exception  {
    String name ="Test";
    int age= 30
    mvc.perform(get("helloWorld/dto)
        .param("name",name)
        .param("age", String.valueOf(age))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(name)))
        .andExpect(jsonPath("$.age", is(age)));
       
}
```
* param 
  + api 테스트할 때 사용될 요청 파라미터를 설정함.
  + 스트링 값만 허용된다.
  + 다른 포맷의 경우 문자열이 가능합니다.
  
* jsonPath
  + json 응답값을 변수별로 검증할 수 있는 메서드
  + $를 기준으로 변수명을 명시함
  
### 2020.09.06 스터디내용
#### 1. JPA
  > 패러다임 불일치
   >>  관계형 데이터 베이스와 객체지향 프로그래밍 언어의 패러다임이 서로 다른데 객체를 데이터베이스에 저장하려니 여러 문제가 발생한다. 
   >>  서로 지향하는 바가 다른 영역의 중간에서 패러다임을 일치시켜 주는 기술-JPA의 역할이다.
 * Spring Data JPA
   + JPA는 인터페이스로서 자바 표준명세서이다. 
   + 대표적인 구현체틑 Hibernate, Eclipselink 등이 있다
   + 구현체를 좀더 쉽게 사용하고자 추상화 시킨 Spring Data JPA모듈을 이용하여  JPA를 다룬다.

### 2020.09.07 스터디
#### 1.JPA
* Hibernate vs Spring Data JPA
  + 사용여부에 큰 차이가 없음
  + Spring Data JPA는 스프링진영에서 개발했고 이를 권장함.

* Spring Data JPA 사용이유
  + 구현체 교체의 용이성 
    - Spring Data JPA 내부에서 구현체 맵핑을 지원해 주고 있기 때문에 
   
  + 저장소 교체의 용이성
    - 관계형 데이터 베이스 외에 다른 저장소로 쉽게 교체하기 위함.
    - 관계형 데이터 베이스에서 nosql등으로 옮겨 갈 때 Spring Data JPA -> Spring Data Mongo로 의존성 교체만 하면 된다.
    - Spring Data 하위 프로젝트들은 기본적인 CRUD의 인터페이스가 동일하다. 
    - save(), findAll(), findOne() 등의 인터페이스를 동일하게 사용하기 때문에 저장소가 교체 되어도 기본적인 기능은 변경이 필요없다.

* JPA 특징
  + 높은 러닝커브(학습곡선 : 무엇을 배우는데 드는 시간[비용])으로 접근성이 어려움
  + 여러 성능 이슈해결책이 준비 되어 있어 네이티브 쿼리만큼의 퍼포먼스를 낼 수 있다.

#### 2. gradle에 JPA 추가하기
* build.gradle
```
dependencies{
 ...
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('com.h2database:h2')
 ...  
}
```

* spring-boot-starter-data-jpa
  +  스프링부트용 jpa 추상화 라이브러리
* h2
  +  인메모리 관계형 데이터베이스
  +  별도의 설치 없이 사용되며 어플리케이션 재시작시마다 초기화 된다.
  +  테스트용으로 많이 사용 


### 2020.09.08 스터디 내용
```
@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private  String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}

```

* Entity
  + 테이블과 링크될 클래스임을 나타내고 클래스명의 카멜케이스 -> 언더스코어 네이밍으로 매칭한다
* Id
  + 해당테이블의 PK
* Column
  + 클래스의 변수는 모두 컬럼이다.
  + 필요 옵션이 있으면 사용한다. (사이즈(기본255) 500 or 컬럼타입 TEXT)
* NoArgsConstructor
  + 기본생성자 자동추가 
* Builder 
  + 해당 클래스의 빌더 패턴 클래스를 생성
  + 생성자 상단에 선언시 생성자에 포함된 변수만 빌더에 포함
  
### 2020.09.09 스터디 내용
````
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void selectList() {
        String title ="테스트 글";
        String content ="테스트 본문";

        postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("writer")
            .build());
        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
    }
}
````


* After
  + 단위 테스트가 끝날 때 수행되는 메서드 지정
  
* postsRepository.save
  +  posts 테이블에 데이터 저장
  +  id 값이 있으면 update, 아니면 insert 
* postsRepository.findAll()
  + 테이블 모든 데이터 조회
  
### 2020.09.10 스터디 내용
#### 1.application.properties 설정 - jpa

/src/resources/application.properties 작성
```
spring.jap.hibernate.ddl-auto=none //create,create-drop,validate, update, none
spring.jpa.ganerate-ddl=false
spring.jpa.show-sql=true
spring.jpa.database=H2 //mysql
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate=info
```

* spring.jap.hibernate.ddl-auto
  + create - 테이블 없으면 생성
  + create-drop - 테스트 환경에 적합 (mock Data를 위해 table 생성 테스트 종료 후 drop)
  + validate - @Entity와 테이블이 제대로 맵핑되지 않으면 application 실행안함.
  + update - 테이블이 존재하는 상황에서 @Entity가 추가되면 기존테이블에 변경없이 새로운 column추가
  + none - 아무것도 안함
* spring.jpa.ganerate-ddl : DDL생성시 데이터 베이스 고유기능 사용유무
* spring.jpa.show-sql : jpa or hibernate통해  CRUD 실행시 sql 로깅여부 
* spring.jpa.database : 데이터 베이스 지정
* spring.jpa.database-platform 
  + Spring Data JPA는 기본적으로 hibernate 라는 JPA구현체를 사용한다.
  + hibernate 는 내부적으로 지정되는 DB에 맞게 SQL문을 생성하는 Dialect가 존재한다.
  + Dialect는 hibernate의 다양햔 데이터베이스 처리를 위해 각 DB에 맞는 sql문법을 처리하기 위해 존재한다.
    - MySQL5Dialect, MySQL5InnoDBDialect,Oracle10gDialect,OracleDialect 등
  + spring.jpa.properties.hibernate.format_sql : 로깅에 표시되는 sql을 보기좋게 표시
 * logging.level.org.hibernate : hibernate 로깅레벨 설정(info 보다 debug가 상세하게 표시) 
    
```
HHH000400: Using dialect: org.hibernate.dialect.MySQL5InnoDBDialect
Hibernate: 
    
    drop table if exists posts
Hibernate: 
    
    create table posts (
       id bigint not null auto_increment,
        author varchar(255),
        content TEXT not null,
        title varchar(500) not null,
        primary key (id)
    ) engine=InnoDB
Hibernate: 
    insert 
    into
        posts
        (author, content, title) 
    values
        (?, ?, ?)

Hibernate: 
    select
        posts0_.id as id1_0_,
        posts0_.author as author2_0_,
        posts0_.content as content3_0_,
        posts0_.title as title4_0_ 
    from
        posts posts0_
Hibernate: 
    select
        posts0_.id as id1_0_,
        posts0_.author as author2_0_,
        posts0_.content as content3_0_,
        posts0_.title as title4_0_ 
    from
        posts posts0_
Hibernate: 
    delete 
    from
        posts 
    where
        id=?

```
> hibernate 형식으로 sql 표시유무를 선택하면 위와 같은 결과를 볼 수 있다.

### 2020.09.11 스터디 내용
#### 1. API 작성용
* DTO
* Controller
* Service

> Service는 트랜잭션 도메인간 순서 보장 역할

![다운로드](https://user-images.githubusercontent.com/45908835/92929648-dcc0d080-f47b-11ea-8f94-8b883833c418.png)


* Web Layer 
  + 컨트롤러 와 Jsp 등 뷰 템플릿 영역
  + 이외 필터, 인터셉터등 외부요청과 응답 전반에 걸친 영역
  
* Service Layer
  + Controller 와 DAO의 중간영역
  + Trnasactional 이 사용되는 영역
* Repository Layer
  + 데이터 저장소에 접근는 영역
  + DAO 영역
* DTOs
  + Data Trnasfer Object : 계층간에 데이터 교환을 위한 객체
* Domain Model
  + 도메인이라는 개발대상을 단순화 시키는 것 
  + VO처럼 값 객체들도 이 영역에 해당됨
  
### 2020.09.13 스터디내용
#### 1. 어노테이션 추가 내용

* RequiredArgsConstructor : final로 선언된 모든 변수를 롬복이 대신 생성해줌.
* 롬복 어노테이션 사용이유 : 의존성 관계가 변할 때 마다 생성자 코드를 수정하는 번거로움을 해결하기 위해서임.

* Entity클래스는Request/Response 클래스로 사용해서는 안된다. 데이터 베이스와 맞닿는 핵심클래스이므로 
  Entity 클래스를 기준으로 테이블이 생성되고 스키마도 변경된다. view Layer와 DB Layer를 분리하여 사용
  하는 것이 좋다.
  
  Controller에서 결과값으로 여러 테이블을 조인해서 사용해야 할경우 Entity 클래스만으로 표현이 어렵다.
  Entity 클래스와 Controller에서 사용할 dto는 분리해서 사용해야 한다.
* WebMvcTest의 경우 JPA기능이 작동하지 않기 때문에 외부연동관련 부분만 활성화된다.
  JPA 기능 테스트를 할 경우, SpringBootTest와 TestRestTemplate만 사용하면 된다.

### 2020.09.14 스터디 내용
#### 1.update 추가 및 

```
 @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() ->new IllegalArgumentException("해당 글이 없음. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
```

> update 기능에 쿼리를 쓰지 않는데 JPA의 영속성 컨텍스트 때문이다.
> 영속성 컨텍스트란 엔티티를 영구저장하는 환경이다. 일종의 논리적 개념으로 엔티티가 영속성 컨텍스트에 포함되느냐 마느냐로 갈린다.
> JPA의 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스 에서 데이터를 가지고 오면 데이터는 영속성 컨텍스트가 유지된 상태입니다.
> 이 상태에서 데이터 값을 변경하려면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영합니다.
> 엔티티 객체값만 변경하면 별도로 update 쿼리가 필요 없습니다. 이 개념을 더티체킹이라고 합니다.

### 2020.09.15 스터디
#### 1. H2 DB 접근하기

* application.properties
  + spring.h2.console.enabled=true 추가
  
* h2 db 접속

![20200915_1](https://user-images.githubusercontent.com/45908835/93186210-53f6ad00-f779-11ea-8806-e6e28f020f16.PNG)

* insert 실행

![20200915_2](https://user-images.githubusercontent.com/45908835/93186289-6ec92180-f779-11ea-9509-c7aaf57aec30.PNG)

* 로컬 확인

![20200915_3](https://user-images.githubusercontent.com/45908835/93186304-74bf0280-f779-11ea-812b-637149bc04bf.PNG)


