drop table product;
create table product(
    product_id  number(10),
    pname       varchar(30),
    count    number(10),
    price       number(10)
);
--pk����
alter table product add constraint product_product_id_pk primary key(product_id);

--����������
drop sequence product_product_id_seq;
create sequence product_product_id_seq;


--��ǰ�߰�--
insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '����', 5, 2000);

insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '���', 5, 1500);

insert into product(product_id,pname,count,price)
     values(product_product_id_seq.nextval, '�ٳ���', 1, 3000);

--��ǰ��ȸ--
select product_id, pname, count, price
  from product
 where product_id = 2;

--��ǰ����--
update product
   set pname = 'Ű��',
       count = 10,
       price = 4000
 where product_id = 3;      

--��ǰ����
delete from product where product_id = 1;

--��ǰ��ü��ȸ-
select product_id,pname,count,price from product;

commit;