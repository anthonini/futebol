package br.com.anthonini.futebol.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public final String THUMBNAIL_PREFIX = "thumbnail.";

	public String salvar(MultipartFile[] files);

	public byte[] recuperar(String foto);

	public byte[] recuperarThumbnail(String foto);

	public void remover(String foto);

	public String getUrl(String foto);
	
	default String renomearArquivo(String nomeArquivoOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeArquivoOriginal;
	}
}
