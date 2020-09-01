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
 *  repositories : 의존성 라이러리들을 어떤 원격장소에서 받을 것인지 정한다. 
   - 기본 : mavenCentral()
   - 라이러리 업로드 난이도로 인해 jcenter()도 많이 사용한다.
 *  dependencies : 프로젝트에 필요한 의존성을 선언


