use visualnutrion;

grant all privileges on *.* to 'admin'@'%' identified by 'senhaadmin';
flush privileges;

create user 'gerente'@'%' identified by 'senhagerente';
flush privileges;

create user 'vendedor'@'%' identified by 'senhavendedor';
flush privileges;
