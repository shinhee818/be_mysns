# mysns

## 프로젝트 기간
2024-01 ~ 2024-02
<br><br>
## 목차
[- 프로젝트 소개](#프로젝트-소개)
[- 기술 구성](#기술-구성)
[- ERD](#ERD)
- 고려사항
<br><br>
## 프로젝트 소개
메인페이지/포스트페이지/마이페이지<br>
프론트: https://github.com/shinhee818/fe_mysns<br>
백엔드: 게시글, 멤버, 댓글, 좋아요 기능 등 sns 관련 기능<br>
<br><br>
## 기술 구성
백엔드<br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
 <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <br>
 프론트<br>
 <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
 <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white">
<br><br>
## ERD
![image](https://github.com/shinhee818/be_mysns/assets/153713451/2cd4e2d9-01bc-4ac4-b535-fba75f1f8f4a)
<br><br>
## 고려사항
- **레이어드 아키텍처**
- **조회 구현**
  - 고려한 문제: n+1
  - 해결: default Batch Size, fetch join 이용
- **테스트**
  - @SpringBootTest를 이용한 통합테스트
  - @DataJpaTest, @WebMvcTest를 이용한 슬라이드 테스트



