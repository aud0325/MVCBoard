# MVCBoard

ProjectExBoard	jdbc템플릿 적용 전
springMVCBoard jdbc템플릿 적용 

오라클에 추가 할 쿼리
이름:springmvc 비밀번호:0000

create table mvc_board( <br/>
 bId NUMBER(4) PRIMARY KEY,<br/>
 bName VARCHAR2(20),<br/>
 bTitle VARCHAR2(100),<br/>
 bContent varchar2(300),<br/>
 bDate DATE DEFAULT SYSDATE,<br/>
 bHit NUMBER(4) DEFAULT 0,<br/>
 bGroup NUMBER(4),<br/>
 bStep number(4),<br/>
 bIndent NUMBER(4)<br/>
 );<br/>
 CREATE sequence mvc_board_seq<br/>;
commit;

서버 - context.xml에 아래 추가  (< Resource 붙혀서)

 < Resource name="jdbc/Oracle11g"
auth="Container"
driverClassName="oracle.jdbc.driver.OracleDriver"
type="javax.sql.DataSource"
url="jdbc:oracle:thin:@localhost:1521:xe"
username="springmvc"
password="0000"
maxActive="50"
maxWait="1000" />
