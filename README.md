# MVCBoard

오라클에 추가 할 쿼리
이름:springmvc 비밀번호:0000

ceate table mvc_board(
 bId NUMBER(4) PRIMARY KEY,
 bName VARCHAR2(20),
 bTitle VARCHAR2(100),
 bContent varchar2(300),
 bDate DATE DEFAULT SYSDATE,
 bHit NUMBER(4) DEFAULT 0,
 bGroup NUMBER(4),
 bStep number(4),
 bIndent NUMBER(4)
 );
 CREATE sequence mvc_board_seq;
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
