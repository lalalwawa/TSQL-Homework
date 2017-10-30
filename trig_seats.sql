/* 6.建立一個 Trigger trig_seats 檔名 trig_seats.sql 包含一個功能
  當新增一筆 放映時間, 電影代號, 廳院 到放映表格 playlist時
  參考廳院座位表, 產生(新增)指定場次的電影座次表 到 seats表格
  輸入 insert into playlist values ('2016-12-25 13:00', 1, 'A廳');
  輸出 select * from seats;
*/



CREATE TRIGGER tr_gen_seats ON playlist
FOR INSERT
AS
BEGIN
	DECLARE @v_time DATETIME
	DECLARE @v_movie INT
	DECLARE @v_room VARCHAR(10)
	
	SELECT @v_time = ptime, @v_movie = movie, @v_room = roomid
	FROM INSERTED
	
	EXEC gen_seats2 @v_time, @v_movie, @v_room
	
END 
GO


INSERT INTO playlist VALUES ('2016-12-25 13:00', 5, 'B廳')
GO

SELECT * FROM seats where movie=5
GO
