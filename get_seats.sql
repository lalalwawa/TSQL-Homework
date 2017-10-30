/*3 建立一  T-SQL 程式 存成檔名 gen_seats.sql  包含一個功能
  宣告　放映時間, 電影代號, 廳院 等變數
  參考廳院座位表, 產生(新增)指定場次的電影座次表 到 seats表格

  輸入  sqlcmd -E -i gen_seats.sql     或在 SSMS 之 新增查詢視窗執行 
  */
  /*
DROP TABLE seats

CREATE TABLE seats
(   ptime DATETIME,
    movie INT,
    seat_num CHAR(5),
    sold CHAR(1),
    ordid INT
)
*/

Create procedure gen_seats
as

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
SET @x_ptime = '2016-12-25 13:00'
SET @x_num = 2
SET @x_room = 'A'
print @x_room
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



SELECT * FROM seats
