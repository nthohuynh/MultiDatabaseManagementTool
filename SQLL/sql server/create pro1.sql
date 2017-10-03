use quanlyhocsinh
go
if exists (select name from sysobjects 	where name='diempr' and type ='P')
drop procedure diempr
go
create procedure diempr 
as
select * from diem 
go