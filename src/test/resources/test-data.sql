INSERT INTO organizational_units VALUES (0,'RG','162 HV-insatskompaniet',0,-1);
INSERT INTO organizational_units VALUES (1,'AR','1621 HV-insatsplutonen',1,0);
INSERT INTO organizational_units VALUES (2,'EA','1:e grp (understöd)',2,1);
INSERT INTO organizational_units VALUES (3,'FA','2:a grp (patrull)',2,1);
INSERT INTO organizational_units VALUES (4,'GA','3:e grp (fältarbeten)',2,1);
INSERT INTO organizational_units VALUES (5,'HA','4:e grp (spaning)',2,1);

INSERT INTO items VALUES (0,'F1449-000174','HJÄLPRAM','ST','');
INSERT INTO items VALUES (1,'F1468-000115','KOLV KORT','ST','');
INSERT INTO items VALUES (2,'F1579-000016','STÖTSKYDD GASFL 2,5','ST','');
INSERT INTO items VALUES (3,'F3200-020270','STRÖMFÖRSDON RA 180','ST','');
INSERT INTO items VALUES (4,'F5944-000388','HÖGTALARE','ST','');

INSERT INTO holdings VALUES (0,94);
INSERT INTO holdings VALUES (0,100);
INSERT INTO holdings VALUES (1,101);
INSERT INTO holdings VALUES (1,102);

INSERT INTO item_application VALUES (0,1,'A',3);
INSERT INTO item_application VALUES (0,1,'B',33);
INSERT INTO item_application VALUES (0,1,'C',33);

INSERT INTO stock_locations VALUES (0,'A','1','');
INSERT INTO stock_locations VALUES (1,'A','2','');
INSERT INTO stock_locations VALUES (2,'A','3','');

INSERT INTO actual_inventory VALUES (0,0,0,1,'123456','Kommentar','2019-02-27 23:00:00');
INSERT INTO actual_inventory VALUES (1,0,0,0,'','','2019-02-27 23:00:00');
INSERT INTO actual_inventory VALUES (2,0,0,27,'','','2019-02-27 23:00:00');

INSERT INTO master_inventory VALUES (0,0,'A0JG','Kompaniförråd',1,'ZXY123','Utlånad','2019-04-17 07:18:47');
INSERT INTO master_inventory VALUES (1,1,'A0JG','Kompaniförråd',1,'','','2019-04-17 07:18:47');
INSERT INTO master_inventory VALUES (2,2,'A0JG','Kompaniförråd',5,'','','2019-04-17 07:18:47');