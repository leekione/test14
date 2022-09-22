drop table product;
create table product(
    product_id  number(10),
    pname       varchar(30),
    count    number(10),
    price       number(10)
);
--pk생성
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
drop sequence product_product_id_seq;
create sequence product_product_id_seq;


--상품추가--
insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '딸기', 5, 2000);

insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '사과', 5, 1500);

insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '바나나', 1, 3000);

--상품조회--
select product_id, pname, count, price
  from product
 where product_id = 2;

--상품수정--
update product
   set pname = '키위',
       count = 10,
       price = 4000
 where product_id = 3;      

--상품삭제
delete from product where product_id = 1;

--상품전체조회-
select product_id,pname,count,price from product;

commit;