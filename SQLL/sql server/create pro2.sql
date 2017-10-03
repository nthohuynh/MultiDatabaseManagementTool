use quanlyhocsinh
go
if exists (select name from sysobjects 	where name='diempr' and type ='P')
drop procedure diempr
go
create procedure diempr @mon1 char(8)
as
select * from diem where toan=@mon1
go