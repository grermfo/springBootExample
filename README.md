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
