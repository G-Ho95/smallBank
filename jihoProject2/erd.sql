
/* Drop Tables */

DROP TABLE traninfo CASCADE CONSTRAINTS;
DROP TABLE inouttable CASCADE CONSTRAINTS;
DROP TABLE anumber CASCADE CONSTRAINTS;
DROP TABLE cinfo CASCADE CONSTRAINTS;
DROP TABLE trantypetable CASCADE CONSTRAINTS;


/* Create Tables */

CREATE TABLE anumber
(
	anum number(5) NOT NULL,
	cnum number(7) NOT NULL,
	pwd varchar2(4) NOT NULL,
	tot number(25) NOT NULL,
	adate date,
	PRIMARY KEY (anum)
);


CREATE TABLE cinfo
(
	cnum number(7) NOT NULL,
	cname varchar2(10) NOT NULL,
	jumin varchar2(13) NOT NULL UNIQUE,
	phone varchar2(13) NOT NULL,
	addr varchar2(100) NOT NULL,
	regdate date,
	PRIMARY KEY (cnum)
);


CREATE TABLE inouttable
(
	innum number(5) NOT NULL,
	cnum number(7) NOT NULL,
	anum number(5) NOT NULL,
	trannum number(3) NOT NULL,
	inmoy number(25),
	outmoy number(25),
	ddate date,
	PRIMARY KEY (innum)
);


CREATE TABLE traninfo
(
	innum number(5) NOT NULL,
	cnum number(7) NOT NULL,
	trannum number(5) NOT NULL,
	anum number(5) NOT NULL,
	inmoy number(25),
	outmoy number(25),
	balance number(25),
	ddate date
);


CREATE TABLE trantypetable
(
	trannum number(3) NOT NULL,
	tranname varchar2(20),
	PRIMARY KEY (trannum)
);



/* Create Foreign Keys */

ALTER TABLE inouttable
	ADD FOREIGN KEY (anum)
	REFERENCES anumber (anum)
;


ALTER TABLE anumber
	ADD FOREIGN KEY (cnum)
	REFERENCES cinfo (cnum)
;


ALTER TABLE inouttable
	ADD FOREIGN KEY (cnum)
	REFERENCES cinfo (cnum)
;


ALTER TABLE traninfo
	ADD FOREIGN KEY (cnum)
	REFERENCES cinfo (cnum)
;


ALTER TABLE traninfo
	ADD FOREIGN KEY (innum)
	REFERENCES inouttable (innum)
;


ALTER TABLE traninfo
	ADD FOREIGN KEY (trannum)
	REFERENCES inouttable (innum)
;


ALTER TABLE inouttable
	ADD FOREIGN KEY (trannum)
	REFERENCES trantypetable (trannum)
;


/*������*/
CREATE SEQUENCE cinfo_seq; --����ȣ ������
CREATE SEQUENCE inout_seq; --�ŷ���ȣ ������
CREATE SEQUENCE anum_seq START WITH 10000; --���¹�ȣ ������

/*�⺻����*/
insert into trantypetable VALUES(10,'�Ա�');
insert into trantypetable VALUES(20,'���');
insert into trantypetable VALUES(30,'��������');
insert into trantypetable VALUES(40,'������ü');
