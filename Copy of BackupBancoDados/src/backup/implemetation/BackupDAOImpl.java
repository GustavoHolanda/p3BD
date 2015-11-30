package backup.implemetation;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import backup.interfaces.BackupDAO;
import backup.model.Backup;

public class BackupDAOImpl implements BackupDAO, Serializable {
	private static final long serialVersionUID = -168920585599484778L;
	Connection connection;

	public BackupDAOImpl() {
		GenericDAO gDao = new GenericDAO();
		connection = gDao.getConnection();
	}

	@Override
	public List<Backup> listarBackup() throws SQLException {
		List<Backup> lista = new ArrayList<Backup>();
		String sql = "SELECT dbid, name,crdate, CONVERT(VARCHAR(5),crdate,114) AS hora FROM sys.sysdatabases";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Backup bkup = new Backup();
			bkup.setId(rs.getLong("dbid"));
			bkup.setNome(rs.getString("name"));
			bkup.setCriacao(rs.getDate("crdate"));
			bkup.setHora(rs.getString("hora"));
			lista.add(bkup);
			System.out.println(bkup.getId() + " " + bkup.getNome() + " " + bkup.getCriacao());
		}
		ps.close();
		rs.close();
		return lista;
	}

	@Override
	public void fullBackup() throws SQLException {
		String sql = "{call sp_fullBackup(?)}";

		CallableStatement cs1 = connection.prepareCall(sql);
		cs1.setString(1, "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
		cs1.execute();
		cs1.close();
		System.out.println(
				"executou sp_fullBackup " + "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
	}

	@Override
	public void backup(String database) throws SQLException {
		String sql = "{call sp_backup(?,?)}";

		CallableStatement cs1 = connection.prepareCall(sql);
		cs1.setString(1, database);
		cs1.setString(2, "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
		cs1.execute();
		cs1.close();
		System.out.println(database + " executou sp_backup "
				+ "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
	}
	
	@Override
	public String verificaExistenciaTabela(String nome) throws SQLException{
		String sql = "{call sp_recovery(?,?,?)}";
		CallableStatement cs1 = connection.prepareCall(sql);
		cs1.setString("nome", nome);
		cs1.setString("caminho", "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
		cs1.registerOutParameter("SAIDA", java.sql.Types.VARCHAR);
		cs1.execute();
		String retorno =  cs1.getString("SAIDA");
		System.out.println("Retorno: " + cs1.getString("SAIDA"));
		return retorno;
	}
	
	@Override
	public void restoreForcado(String nome) throws SQLException{
		String sql = "{call sp_recoveryForcado(?,?)}";
		CallableStatement cs1 = connection.prepareCall(sql);
		cs1.setString(1, nome);
		cs1.setString(2, "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak");
		cs1.execute();
	}
	
}
