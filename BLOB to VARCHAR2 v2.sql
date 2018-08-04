
set serveroutput on size 256000000
declare 
a varchar(32767); 
b long raw; 
cursor c1 is select (B.PUBDATALONG)  FROM PSIBLOGHDR aa, PSIBLOGDATA b 
where
AA.IB_OPERATIONNAME='MYSERVICE'
and aa.guid=b.guid
and rownum<2; 
begin 
open c1; 
loop 
fetch c1 into b ; 
exit when c1%notfound; 
a:=UTL_RAW.CAST_TO_VARCHAR2(b); 
dbms_output.put_line(a); 
end loop; 
end;
