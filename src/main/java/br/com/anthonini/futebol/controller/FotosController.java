package br.com.anthonini.futebol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.anthonini.futebol.dto.FotoDTO;
import br.com.anthonini.futebol.storage.FotoStorage;
import br.com.anthonini.futebol.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(files, result, fotoStorage));
		thread.run();
		
		return result;
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] getFoto(@PathVariable String nome) {
		return fotoStorage.recuperar(nome);
	}
}
