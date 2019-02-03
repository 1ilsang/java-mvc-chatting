Java Chatting Application
===

<a href="https://1ilsang.blog.me/"><img src="https://img.shields.io/badge/blog-1ilsang.blog.me-red.svg" /></a>
<a href="#"><img src="https://img.shields.io/github/last-commit/1ilsang/java-mvc-chatting.svg?style=flat" /></a>
<a href="#"><img src="https://img.shields.io/github/languages/top/1ilsang/java-mvc-chatting.svg?colorB=yellow&style=flat" /></a>
<a href="#"><img src="https://img.shields.io/badge/license-MIT-green.svg" /></a>

멀티 채팅 토이 프로젝트.

- 핵심 목표: 스프링 공부 전에 다형성, MVC, 쓰레드, 소켓을 짚어보자.
- 제작 기간: 약 일주일
- Keyword: MVC, Thread, Socket, GCP, Polymorphism, Serializable, AWT, Singleton

더 나은 코드를 위해 많은 코멘트 부탁드립니다!

샘플 화면
---
~~AWT로 작성되었기 때문에 윈도우에서는 참혹한 뷰를 보셔야 합니다.~~

- [Read more!](#)

한눈에 보는 전체 구성
---
<img src="./markdown/img/simplePackageDiagram.png" />

- Client: 
  1. 모든 요청은 ```DispatcherController```를 거치며 ```HandlerMapping``` 객체가 해당 비지니스의 적절한 ```Controller```를 찾아 메서드를 실행한다.
  2. 각 ```Controller```는 실제 비지니스 수행을 위해 자신의 ```Service```에 요청 객체를 넘긴다.
  3. ```Service```에서 서버와 통신하는 Login, Chatting 소켓이 열린다. 통신은 모두 ```직렬화```된 객체를 사용한다.
  4. Chatting 소켓은 연결마다 ```ChatAcceptThread``` 쓰레드 객체로 파생해 진행한다.
  5. 각각의 요청은 ```ModelAndView``` 객체에 담겨 레이어층(컨트롤러/뷰/서비스 등)을 거친다.
  6. Service 를 통해 처리된 데이터는 ```return``` 되어 ```DispatcherController```에게 전달되며 이때 ```ViewResolver``` 가 유저에게 보여줄 화면객체를 찾는다.
  7. 보여줄 화면객체를 찾았다면  ```View``` 객체를 통해 유저에게 화면을 보여준다.
  8. 모든 컨트롤러와 서비스, 뷰는 싱글톤으로 구성되어 있다.

- Server:
  1. 서버는 실행과 동시에 ```LogThread```, ```LoginSocketThread```, ```ChatRoomSocketThread``` 3가지 쓰레드를 파생시킨다.
  2. ```LogThread```는 서버에 들어오는 모든 요청/메시지 등을 로그에 기록 및 텍스트 파일에 저장한다.
  3. 이때 저장 순서를 지켜주기 위해 ```queue```에 담아 순차적으로 저장한다.
  4. ```LoginSocketThread```는 로그인 처리를 하는 *단일* 쓰레드로, DB를 사용하지 않으므로 Map 자료구조를 사용해 "이름":"유저객체"로 저장한다.
  5. ```ChatRoomSocketThread```는 클라이언트의 요청소켓을 받아 각 소켓마다 ```ChatSocketThread```를 파생시킨다.
  6. ```ChatSocketThread``` 객체는 클라이언트의 소켓을 분석해 채팅방별로 관리해 적절하게 메시지를 뿌려준다.

- [Read more!](#)

어떻게 시작하나요?
---
- [로컬 환경에서 실행하기](#)
- [클라우드 환경(GCP)으로 서비스 해보기](#)

클래스 단위로 알아보기
---
- client
  - 
  - 
  - 
  - 
  
- server
  - 
  -
  

맺으며
---
- [Come to my blog!](https://1ilsang.blog.me)
