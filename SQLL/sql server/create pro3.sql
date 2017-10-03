use quanlyhocsinh
go
if exists (select name from sysobjects 	where name='nhapdiem' and type ='P')
drop procedure nhapdiem
go
create procedure nhapdiem @batdau int
as
declare @bat_dau int, @nhay int
set @bat_dau=@batdau
set @nhay=0
while (@nhay<5)
begin
insert into diem values( @bat_dau,8,7)
set @bat_dau=@bat_dau+1
set @nhay=@nhay +1
end 
go
exec nhapdiem 1
