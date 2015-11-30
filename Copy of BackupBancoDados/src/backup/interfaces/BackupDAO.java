package backup.interfaces;

import java.sql.SQLException;
import java.util.List;

import backup.model.Backup;

public interface BackupDAO {

	public List<Backup> listarBackup() throws SQLException;
	public void fullBackup() throws SQLException;
	public void backup(String database) throws SQLException;
	public String verificaExistenciaTabela(String nome) throws SQLException;
	public void restoreForcado(String nome) throws SQLException;
}
