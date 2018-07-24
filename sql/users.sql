use visualnutrion;

GRANT ALL privileges on *.* to 'admin'@'%' identified by 'senhaadmin';
flush privileges;
CREATE USER 'gerente'@'%' identified by 'senhagerente';
flush privileges;
CREATE USER 'vendedor'@'%' identified by 'senhavendedor';
flush privileges;
GRANT ALL privileges on visualnutrion.* to gerente@localhost identified by 'senhagerente';
flush privileges;
GRANT ALL privileges on visualnutrion.* to vendedor@localhost identified by 'senhavendedor';
flush privileges;