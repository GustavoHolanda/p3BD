CREATE DATABASE BackupAutomatico
USE BackupAutomatico

SELECT * FROM sys.sysdatabases

SELECT name, crdate FROM sys.sysdatabases 

SELECT dbid, name,crdate, CONVERT(VARCHAR(5),crdate,114) AS hora FROM sys.sysdatabases


CREATE PROCEDURE sp_fullBackup(@caminho VARCHAR(MAX))
AS
DECLARE @nome VARCHAR(MAX),
		@caminhoTotal VARCHAR(MAX)
DECLARE c_backup CURSOR FOR 
	SELECT DISTINCT name FROM sys.sysdatabases 
	OPEN c_backup
	FETCH NEXT FROM c_backup INTO @nome
	WHILE @@FETCH_STATUS = 0
	BEGIN
		IF(@nome <> 'tempdb')
		BEGIN
		SET @caminhoTotal = @caminho + '\' + @nome + '.bak'
		BACKUP DATABASE @nome TO DISK =  @caminhoTotal
		PRINT(@nome)
		PRINT(@caminho)
		PRINT(@caminhoTotal)
		END
	FETCH NEXT FROM c_backup INTO @nome
			END
			CLOSE c_backup
			DEALLOCATE c_backup

	EXEC sp_fullBackup 'C:\Users\RF411-SD4\Desktop\Teste2'
	
	DROP PROCEDURE sp_backup 
	/*
	DECLARE @nome VARCHAR(100),
			@caminho VARCHAR(MAX),
			@caminhoTotal VARCHAR(MAX)
			
	SET @nome = 'BackupAutomatico'
	SET	@caminho = 'C:\Users\RF411-SD4\Desktop'
	
	SET @caminhoTotal = @caminho + '\' + @nome + '.bak'
		BACKUP DATABASE @nome TO DISK =  @caminhoTotal
		PRINT(@nome)
			PRINT(@caminho)
			PRINT(@caminhoTotal)
	*/
	
CREATE PROCEDURE sp_backup(@nomeCaminho VARCHAR(MAX),@caminho VARCHAR(MAX))
AS
DECLARE @nome VARCHAR(MAX),
		@caminhoTotal VARCHAR(MAX)
DECLARE c_backup CURSOR FOR 
	SELECT DISTINCT name FROM sys.sysdatabases 
	OPEN c_backup
	FETCH NEXT FROM c_backup INTO @nome
	WHILE @@FETCH_STATUS = 0
	BEGIN
		IF(@nome <> 'tempdb' AND @nome = @nomeCaminho)
		BEGIN
		SET @caminhoTotal = @caminho + '\' + @nome + '.bak'
		BACKUP DATABASE @nome TO DISK =  @caminhoTotal
		PRINT(@nome)
		PRINT(@caminho)
		PRINT(@caminhoTotal)
		END
	FETCH NEXT FROM c_backup INTO @nome
			END
			CLOSE c_backup
			DEALLOCATE c_backup

	EXEC sp_backup 'master ', 'C:\Users\RF411-SD4\Desktop\Teste2'
	/*
	RESTORE 
	
Completo da Base de Dados -- 2º Caso (Restrições de SO)
RESTORE WITH REPLACE só deve ser utilizado quando se tem certeza da database que será substituída
Operação não permite BEGIN TRANSACTION


--RESTORE DATABASE nome_da_database
--FROM DISK = 'caminho_do_arquivo_de_backup'
--WITH RECOVERY,
--REPLACE
--*/

----Exemplo
--RESTORE DATABASE procedimento
--FROM DISK = 'c:\procedimento.bak' 
--WITH RECOVERY,
--REPLACE
	
	*/

RESTORE DATABASE Paulistao FROM DISK = 'C:\Users\RF411-SD4\workspace\BackupBancoDados\WebContent\bak\Paulistao.bak'
   
   
   CREATE ALTER PROCEDURE sp_recovery(@nome VARCHAR(MAX), @caminho VARCHAR(MAX),@SAIDA VARCHAR(MAX) OUTPUT)
   AS
   DECLARE @caminhoTotal VARCHAR(MAX)
	IF EXISTS (SELECT * FROM  sys.sysdatabases  WHERE name = @nome)
	BEGIN
		SET @SAIDA = 'Tabela exite'
		PRINT 'Tabela existe'
		
	END
	ELSE
	BEGIN
		SET @SAIDA = 'Restore realizado com sucesso'
		PRINT 'Tabela não exite'
		SET @caminhoTotal = @caminho + '\' + @nome
		PRINT(@caminhoTotal)
		RESTORE DATABASE @nome
		FROM DISK = @caminhoTotal
	END
	
	declare @SAIDA VARCHAR(MAX)
	EXEC sp_recovery 'Paulistao.bak', 'C:\Users\RF411-SD4\workspace\BackupBancoDados\WebContent\bak', @SAIDA OUTPUT
	SELECT @SAIDA
	
	
	DROP DATABASE Paulistao
	
	CREATE ALTER PROCEDURE sp_recoveryForcado(@nome VARCHAR(MAX), @caminho VARCHAR(MAX))
	AS
	DECLARE @caminhoTotal VARCHAR(MAX)
	SET @caminhoTotal = @caminho + '\' + @nome
		PRINT(@caminhoTotal)
		RESTORE DATABASE @nome
		FROM DISK = @caminhoTotal
	
	EXEC sp_recoveryForcado 'Paulistao.bak', 'C:\Users\RF411-SD4\workspace\BackupBancoDados\WebContent\bak'
	
	
	
	DROP DATABASE Paulistao

