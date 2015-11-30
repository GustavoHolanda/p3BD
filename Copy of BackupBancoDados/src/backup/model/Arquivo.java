package backup.model;

public class Arquivo {
	private long id;
	private String nome;
	private String diretorio;
	private String diretorioCompleto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getDiretorioCompleto() {
		return diretorioCompleto;
	}

	public void setDiretorioCompleto(String diretorioCompleto) {
		this.diretorioCompleto = diretorioCompleto;
	}

}
