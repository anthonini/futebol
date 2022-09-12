var Futebol = Futebol || {};

Futebol.MascaraNumeros = (function(){
	function MascaraNumeros() {		
		this.numero = $('.js-numero');
	}
	
	MascaraNumeros.prototype.habilitar = function() {
		this.numero.mask('#0', {reverse: true});
	}
	
	return MascaraNumeros;
})();

$(function(){	
	var mascaraNumeros = new Futebol.MascaraNumeros();
	mascaraNumeros.habilitar();
});