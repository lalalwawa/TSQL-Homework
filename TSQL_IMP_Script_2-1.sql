create procedure loop99
		    BEGIN
			        SET @str += format(@i,'00') + '*' + format(@j,'00') +'='+ format(@i*@j,'00') + CHAR(9)
			        SET @j += 1
		        END
		    PRINT @str
		    SET @str = ''
		    SET @j = 1
		    SET @i += 1
	    END 
END 


EXEC loop99 9,9
