package backup.managedbean;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import backup.implemetation.BackupDAOImpl;
import backup.interfaces.BackupDAO;
import backup.model.Arquivo;
import backup.model.Backup;

@ManagedBean
@SessionScoped
public class BackupMB {

	private List<Backup> listaBackup;
	private Backup backupAtual;
	private BackupDAO bkupDao;
	List<Arquivo> listaArquivos;

	public BackupMB() {
		backupAtual = new Backup();
		bkupDao = new BackupDAOImpl();
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setListaBackup(bkupDao.listarBackup());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Foram encontrados " + listaBackup.size() + " Bancos de Dados.", null);
			fc.addMessage("", msg);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Erro ao acessar Banco de Dados" + e.getMessage(), null);
			fc.addMessage("", msg);
			e.printStackTrace();
		}

		listaArquivos = this.carregarArquivosDoDiretorio();
	}

	public void atualizarLista() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			setListaBackup(bkupDao.listarBackup());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Foram encontrados " + listaBackup.size() + " Bancos de Dados.", null);
			fc.addMessage("", msg);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Erro ao acessar Banco de Dados" + e.getMessage(), null);
			fc.addMessage("", msg);
			e.printStackTrace();
		}
	}

	public void fullBackup() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			bkupDao.fullBackup();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sucesso ao fazer backup de todos os bancos de dados", null);
			fc.addMessage("", msg);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Erro ao acessar Banco de Dados" + e.getMessage(), null);
			fc.addMessage("", msg);
			e.printStackTrace();
		}
	}

	public void backup(Backup up) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			bkupDao.backup(up.getNome());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sucesso ao fazer backup do bancos de dados: " + up.getNome(), null);
			fc.addMessage("", msg);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Erro ao acessar Banco de Dados" + e.getMessage(), null);
			fc.addMessage("", msg);
			e.printStackTrace();
		}
	}

	public List<Arquivo> carregarArquivosDoDiretorio() {
		List<Arquivo> lista = new ArrayList<Arquivo>();
		String diretorio = "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak";
		File file = new File(diretorio);
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			File arquivos = afile[i];
			Arquivo arq = new Arquivo();
			arq.setNome(arquivos.getName());
			arq.setDiretorio(diretorio);
			arq.setDiretorioCompleto(diretorio + "\\" + arquivos.getName());
			System.out.println(arq.getNome() + arq.getDiretorio() + arq.getDiretorioCompleto());
			lista.add(arq);
		}
		return lista;
	}
	
	public void recovery(Arquivo arq){
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			bkupDao.restoreForcado(arq.getNome());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void recoveryCompleto() {
		String diretorio = "C:\\Users\\RF411-SD4\\workspace\\BackupBancoDados\\WebContent\\bak";
		File file = new File(diretorio);
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			File arquivos = afile[i];
			Arquivo arq = new Arquivo();
			arq.setNome(arquivos.getName());
			arq.setDiretorio(diretorio);
			arq.setDiretorioCompleto(diretorio + "\\" + arquivos.getName());
			System.out.println(arq.getNome() + arq.getDiretorio() + arq.getDiretorioCompleto());
			try {
				bkupDao.restoreForcado(arq.getNome());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public List<Arquivo> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivo> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public List<Backup> getListaBackup() {
		return listaBackup;
	}

	public void setListaBackup(List<Backup> listaBackup) {
		this.listaBackup = listaBackup;
	}

	public Backup getBackupAtual() {
		return backupAtual;
	}

	public void setBackupAtual(Backup backupAtual) {
		this.backupAtual = backupAtual;
	}

	public BackupDAO getBkupDao() {
		return bkupDao;
	}

	public void setBkupDao(BackupDAO bkupDao) {
		this.bkupDao = bkupDao;
	}

}
