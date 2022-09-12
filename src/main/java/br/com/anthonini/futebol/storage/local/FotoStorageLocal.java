package br.com.anthonini.futebol.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.futebol.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("!prod")
@Component
public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	@Value("${futebol.foto-storage-local.local}")
	private Path path;
	
	@Value("${futebol.foto-storage-local.url-base}")
	private String urlBase;

	@Override
	public String salvar(MultipartFile[] files) {
		String newName = null;
		if (files != null && files.length > 0) {
			MultipartFile file = files[0];
			newName = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(this.path.toAbsolutePath().toString() + getDefault().getSeparator() + newName));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar a foto.", e);
			}
		}
		
		try {
			Thumbnails.of(this.path.resolve(newName).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar thumbnail", e);
		}
		
		return newName;
	}
	
	@Override
	public byte[] recuperar(String name) {
		try {
			return Files.readAllBytes(this.path.resolve(name));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error lendo a foto.", e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String foto) {
		return recuperar(THUMBNAIL_PREFIX + foto);
	}
	
	@Override
	public void remover(String foto) {
		try {
			Files.deleteIfExists(this.path.resolve(foto));
		} catch (IOException e) {
			logger.warn(String.format("Erro ao apagar foto %s. Mensagem: %s", foto, e.getMessage()));
		}
		
		try {
			Files.deleteIfExists(this.path.resolve(THUMBNAIL_PREFIX + foto));
		} catch (IOException e) {
			logger.warn(String.format("Erro ao apagar a thumbnail da foto %s. Mensagem: %s", foto, e.getMessage()));
		}
	}
	
	@Override
	public String getUrl(String foto) {
		return urlBase + foto;
	}
	
	@PostConstruct
	private void criarPastas() {
		try {
			Files.createDirectories(this.path);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Criado o diretório de fotos.");
				logger.debug("Diretório Padrão: " + this.path.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar diretório para savar fotos", e);
		}
	}
}