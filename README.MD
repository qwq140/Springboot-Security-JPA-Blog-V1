# 스프링부트 블로그 프로젝트 V1

## 의존성
- Spring Web
- Lombok
- Spring Dev Tool
- OAuth
- JPA
- Security
- MySQL Driver
```xml
<!-- 추가 라이브러리  -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
</dependency>


<!-- JSP 템플릿 엔진 -->
<dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.43</version>
</dependency>

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
	<version>1.2</version>
</dependency>
```
## DB 설정

```sql
create user 'pos'@'%' identified by 'pos1234';
GRANT ALL PRIVILEGES ON *.* TO 'pos'@'%';
create database pos;
```
## 디자인
![녹화_2021_03_25_23_55_31_809](https://user-images.githubusercontent.com/74044232/112493889-e41b4c00-8dc5-11eb-8369-43e988bc5761.gif)
