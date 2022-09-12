package br.com.anthonini.futebol.repository.listener;

import javax.persistence.PostLoad;

import br.com.anthonini.futebol.ApplicationContextUtil;
import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.storage.FotoStorage;

public class TimeEntityListener {
	
	@PostLoad
	public void postLoad(final Time time) {
		FotoStorage fotoStorage = ApplicationContextUtil.getBean(FotoStorage.class);
		
		time.getArquivoFoto().setUrl(fotoStorage.getUrl(time.getFotoOuMock()));
		time.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + time.getFotoOuMock()));
	}
}
