package backup.implemetation;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import backup.interfaces.BackupDAO;
import backup.model.Backup;

public class Teste {
	
	
	public static void main(String[] args) {
		List<Backup> lista = new ArrayList<Backup>();
		BackupDAO bkupDao = new BackupDAOImpl();
		System.out.println(lista.size());
		
		try {
			lista = bkupDao.listarBackup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			String retorno = bkupDao.verificaExistenciaTabela("Paulistao.bak");
			System.out.println(retorno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try {
			bkupDao.restoreForcado("Paulistao.bak");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
