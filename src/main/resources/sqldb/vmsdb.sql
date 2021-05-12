USE vmsdb
GO


INSERT INTO employee
VALUES	('11','Ha Noi',NULL,'1993-04-23','nguyenvana@gmail.com','Nguyen Van A',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0167776034','DEV',1,'anv','HN')
		,('12','Hai Phong',NULL,'2001-03-22','buithib@gmail.com','Bui Thi B',1,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0961326454','DEV',1,'bbt','HN')
		,('13','Ha Nam',NULL,'2000-06-17','trandinhc@gmail.com','Tran Dinh C',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0923776056','DEV',1,'ctd','HN')
		,('14','Hai Duong',NULL,'1992-02-15','hoangvand@gmail.com','Hoang Van D',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0943437893','DEV',1,'dhv','HN')
		,('15','Thai Binh',NULL,'1993-09-27','dangthie@gmail.com','Dang Thi E',1,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0984324576','DEV',1,'edt','HN')
		,('16','Hung Yen',NULL,'1997-04-21','dodinhf@gmail.com','Do Dinh F',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0964567342','DEV',1,'fdd','HN')
		,('17','Nam Dinh',NULL,'1990-06-03','nguyendinhg@gmail.com','Nguyen Dinh G',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0986763218','DEV',1,'gnd','HN')
		,('18','Nghe An',NULL,'1998-01-08','nongvanh@gmail.com','Nong Van H',0,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0942458984','DEV',1,'hnv','HN')
		,('19','Thanh Hoa',NULL,'1996-08-19','tranthii@gmail.com','Tran Thi I',1,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','0954864432','DEV',1,'itt','HN')
		,('20','Yen Bai',NULL,'1999-05-29','dothiq@gmail.com','Do Thi Q',1,NULL,'$2a$10$WyJVLK/AXHPwnRjsE9v8..vodUB6gSV6HpN7onY3iXBSlqh9ixuc2','09764321872','DEV',1,'qdt','HN')

INSERT INTO customer
VALUES	('Ha noi','1993-04-22','nguyenvanna@gmail.com','Nguyen Van A',1,'123456','$2a$10$khF6Dd/HkUhbFc1YY9gXP.GBM5LOM0UhBJXhVUNc1OrOhW3oAVGMK','0123456789',0,'anv')
		,('Hai Duong','1995-03-24','buivanb@gmail.com','Bui Van B',1,'234567','$2a$10$khF6Dd/HkUhbFc1YY9gXP.GBM5LOM0UhBJXhVUNc1OrOhW3oAVGMK','0456756789',0,'bvb')
		,('Ha noi','1994-09-12','tranthic@gmail.com','Tran Thi C',0,'345678','$2a$10$khF6Dd/HkUhbFc1YY9gXP.GBM5LOM0UhBJXhVUNc1OrOhW3oAVGMK','0123678989',0,'ctt')
		,('Ha noi','1999-05-28','nguyenvand@gmail.com','Nguyen Van D',1,'456789','$2a$10$khF6Dd/HkUhbFc1YY9gXP.GBM5LOM0UhBJXhVUNc1OrOhW3oAVGMK','012343259',0,'dnv')
		,('Ha noi','2002-08-03','nguyenthie@gmail.com','Nguyen Thi E',0,'123678','$2a$10$khF6Dd/HkUhbFc1YY9gXP.GBM5LOM0UhBJXhVUNc1OrOhW3oAVGMK','0123456754',0,'ent')

INSERT INTO vaccine_type
VALUES	('11',NULL,'Good',NULL,'COVAC1',1)
		,('12',NULL,'Good',NULL,'COVAC2',0)
		,('13',NULL,'Good',NULL,'COVAC3',1)
		,('14',NULL,'Good',NULL,'COVAC4',1)
		,('15',NULL,'Good',NULL,'COVAC5',0)
		,('16',NULL,'Good',NULL,'COVAC6',0)
		,('17',NULL,'Good',NULL,'COVAC7',1)
		,('18',NULL,'Good',NULL,'COVAC8',0)
		,('19',NULL,'Good',NULL,'COVAC9',0)
		,('20',NULL,'Good',NULL,'COVAC10',1)

INSERT INTO vaccine
VALUES	('11',1,NULL,NULL,100,'OK',0,'2020-03-04','2021-03-02',NULL,'VCA','12')
		,('12',1,NULL,NULL,200,'OK',0,'2020-05-06','2021-09-08',NULL,'VCB','13')
		,('13',1,NULL,NULL,10,'OK',0,'2020-01-02','2021-03-03',NULL,'VCC','17')
		,('14',1,NULL,NULL,150,'OK',0,'2020-07-09','2021-07-12',NULL,'VCD','15')
		,('15',1,NULL,NULL,120,'OK',0,'2020-06-18','2021-6-20',NULL,'VCE','16')
		,('16',1,NULL,NULL,180,'OK',0,'2020-09-22','2021-09-23',NULL,'VCF','11')
		,('17',1,NULL,NULL,120,'OK',0,'2020-08-28','2021-4-21',NULL,'VCG','11')
		,('18',1,NULL,NULL,150,'OK',0,'2020-04-09','2021-11-18',NULL,'VCH','19')
		,('19',1,NULL,NULL,190,'OK',0,'2020-05-02','2021-10-23',NULL,'VCI','16')
		,('20',1,NULL,NULL,130,'OK',0,'2020-07-08','2021-05-05',NULL,'VCK','15')

INSERT INTO injection_schedule
VALUES	('Good','2021-05-07','HN','2020-05-06','12')
		,('Good','2021-06-07','HN','2020-07-07','16')
		,('Good','2021-08-13','HN','2020-01-08','13')
		,('Good','2021-11-14','HN','2020-10-16','15')
		,('Good','2021-05-17','HN','2020-03-17','12')
		,('Good','2021-09-07','HN','2020-05-06','19')
		,('Good','2021-04-27','HN','2020-01-23','16')
		,('Good','2021-10-18','HN','2020-11-13','14')
		,('Good','2021-12-12','HN','2020-02-26','13')
		,('Good','2021-10-18','HN','2020-9-3','11')

INSERT INTO injection_result
VALUES	(NULL,'2021-3-14','HN','2021-3-16',3,'abc',0,3,'15')
		,(NULL,'2022-10-12','HN','2022-10-16',2,'abc',0,5,'13')
		,(NULL,'2021-1-15','HN','2021-1-18',4,'abc',0,1,'18')
		,(NULL,'2020-12-14','HN','2020-12-20',2,'abc',0,2,'14')
		,(NULL,'2021-6-14','HN','2021-11-14',3,'abc',0,5,'13')
		,(NULL,'2020-8-14','HN','2020-11-14',6,'abc',0,1,'15')
		,(NULL,'2021-6-14','HN','2021-11-14',5,'abc',0,1,'16')
		,(NULL,'2020-4-14','HN','2020-11-14',2,'abc',0,3,'11')
		,(NULL,'2022-5-14','HN','2022-11-14',2,'abc',0,4,'19')
		,(NULL,'2021-7-14','HN','2021-11-14',3,'abc',0,2,'18')