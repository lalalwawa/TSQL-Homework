/*4 參考 SQL-IMP作業2-3 程式,
  建立一個 stored proedure: gen_seats  包含一個功能 存成檔名 sp_gen_seats.sql
  輸入　放映時間, 電影代號, 廳院 等參數
  參考廳院座位表, 產生(新增)指定場次的電影座次表 到 seats表格

  輸入  exec  gen_seats '2016-12-25 13:00', 1, 'A廳'
  */
/*
 * 全部執行
 * 程式中第一部份 建立stored procedure
 * 程式中第二部份 清空table以便觀察，執行procedure，展示結果
 */



CREATE PROCEDURE gen_seats ( @p_time DATETIME,
                             @p_num int,
                             @p_room VARCHAR(10)
)
AS
BEGIN

DECLARE @x_ptime DATETIME
DECLARE @x_num int
DECLARE @x_room VARCHAR(10)
DECLARE @x_row int
DECLARE @x_col int
DECLARE @i INT
DECLARE @j INT 
DECLARE @seat VARCHAR(10) 

/*查詢影廳大小*/
SET @x_ptime = @p_time
SET @x_num = @p_num
SET @x_room = @p_room
SET @x_row = (SELECT seat_row FROM m_room WHERE roomid= @x_room)
SET @x_col = (SELECT seat_col FROM m_room WHERE roomid= @x_room)
SET @i = 1
SET @j = 1

	WHILE (@i<=@x_row)
	    BEGIN
		    WHILE (@j<=@x_col)
		        BEGIN
			        SET @seat = CAST(@i as VARCHAR(2))+ '-' +CAST(@j as VARCHAR(2))
		            --PRINT '加入一筆訂單'+@seat
		            
			        INSERT INTO seats(ptime, movie, seat_num, sold) VALUES (@x_ptime, @x_num, @seat, '0')
			        SET @j +=1
			    END
		SET @j=1
		SET @i = @i +1
	    END
END
GO

/*
TRUNCATE TABLE seats
SELECT * from seats
EXEC gen_seats '2016-12-25 13:00', 1, 'A'
SELECT * FROM seats WHERE movie=1
GO
*/
