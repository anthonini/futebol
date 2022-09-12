package br.com.anthonini.futebol.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.futebol.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> result;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result, FotoStorage fotoStorage) {
		this.files = files;
		this.result = result;
		this.fotoStorage = fotoStorage;
	}

	@Override
	public void run() {
		String name = this.fotoStorage.salvar(files);
		String contentType = files[0].getContentType();
		result.setResult(new FotoDTO(name, contentType, fotoStorage.getUrl(name)));
	}

}
