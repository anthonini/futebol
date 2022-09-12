package br.com.anthonini.futebol.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.anthonini.arquitetura.controller.AbstractController;
import br.com.anthonini.arquitetura.controller.page.PageWrapper;
import br.com.anthonini.futebol.model.Campeonato;
import br.com.anthonini.futebol.repository.helper.campeonato.filter.CampeonatoFilter;
import br.com.anthonini.futebol.service.CampeonatoService;
import br.com.anthonini.futebol.service.exception.NaoEPossivelRemoverEntidadeException;

/**
 * 
 * @author Anthonini
 *
 */
@Controller
@RequestMapping("/campeonatos")
public class CampeonatosController extends AbstractController  {
	
	@Autowired
	private CampeonatoService service;

	@GetMapping("/cadastro")
	public ModelAndView cadastro(Campeonato campeonato, ModelMap model) {
		return new ModelAndView("campeonato/Form");
	}
	
	@PostMapping({"/cadastro", "/{\\d+}"})
	public ModelAndView cadastro(@Valid Campeonato campeonato, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirect) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(modelMap, bindingResult);
			return cadastro(campeonato, modelMap);
		}

		String view = campeonato.isNovo() ? "/campeonatos/cadastro" : "/campeonatos";
		
		service.cadastrar(campeonato);
		addMensagemSucesso(redirect, getMessage("campeonato.mensagem.sucesso"));
		
		return new ModelAndView("redirect:"+view);
	}
	
	@GetMapping
	public ModelAndView listar(CampeonatoFilter filter, HttpServletRequest httpServletRequest, @PageableDefault(size = 10) @SortDefault(value="nome") Pageable pageable) {
		ModelAndView mv = new ModelAndView("campeonato/List");
		PageWrapper<Campeonato> paginaWrapper = new PageWrapper<>(service.filtrar(filter,pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable("id") Campeonato campeonato, ModelMap model, RedirectAttributes redirect) {
		if (campeonato == null) {
            addMensagemErro(redirect, getMessage("campeonato.mensagem.naoEncontrado"));
            return new ModelAndView("redirect:/campeonatos");
        }

		model.addAttribute("campeonato", campeonato);
        return cadastro(campeonato, model);
    }
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Campeonato campeonato) {
		if(campeonato != null) {
			try {
				service.remover(campeonato);
			} catch (NaoEPossivelRemoverEntidadeException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body("Campeonato não encontrado!");
		}
	}
}
