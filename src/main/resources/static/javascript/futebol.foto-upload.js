var Futebol = Futebol || {};

Futebol.FotoUpload = (function() {
	
	function FotoUpload() {
		this.inputNomeFoto = $('input[name=arquivoFoto\\.nome]');
		this.inputContentType = $('input[name=arquivoFoto\\.contentType]');
		this.inputUrlFoto = $('input[name=arquivoFoto\\.url');
		this.novoNome = $('input[name=arquivoFoto\\.novoNome');
		
		this.htmlFotoTemplate = $('#foto-escudo').html();
		this.template = Handlebars.compile(this.htmlFotoTemplate);
		
		this.uploadDrop = $('#upload-drop');
		this.fotoContainer = $('.js-foto-container');
		this.imgLoading = $('.js-img-loading');		
	}
	
	FotoUpload.prototype.iniciar = function() {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.fotoContainer.data('url-foto'),
				complete: onUploadComplete.bind(this),
				loadstart: onLoadStart.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select'),settings);
		UIkit.uploadDrop($('#upload-drop'),settings);
		
		if(this.inputNomeFoto.val()) {
			renderFoto.call(this, {
				name: this.inputNomeFoto.val(), 
				contentType: this.inputContentType.val(), 
				url: this.inputUrlFoto.val()
				}
			);
		}
	}
	
	function onLoadStart() {
		this.imgLoading.removeClass('d-none');
	}
	
	function onUploadComplete(resposta) {
		this.novoNome.val('true');
		this.inputUrlFoto.val(resposta.url);
		this.imgLoading.addClass('d-none');
		renderFoto.call(this, resposta);
	}
	
	function renderFoto(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('d-none');
		
		var htmlFotoEscudo = this.template({url: resposta.url});
		this.fotoContainer.append(htmlFotoEscudo);
		
		$('.js-foto-remove').on('click', onFotoRemove.bind(this));
	}
	
	function onFotoRemove() {
		$('.js-foto-escudo').remove();
		this.uploadDrop.removeClass('d-none');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
		this.novoNome.val('false');
	}
	
	return FotoUpload;
	
}());

$(function() {
	var fotoUpload = new Futebol.FotoUpload();
	fotoUpload.iniciar();
})