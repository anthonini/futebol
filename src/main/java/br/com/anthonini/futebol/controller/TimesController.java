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
import br.com.anthonini.futebol.model.Time;
import br.com.anthonini.futebol.repository.helper.time.filter.TimeFilter;
import br.com.anthonini.futebol.service.TimeService;
import br.com.anthonini.futebol.service.exception.NaoEPossivelRemoverEntidadeException;

/**
 * 
 * @author Anthonini
 *
 */
@Controller
@RequestMapping("/times")
public class TimesController extends AbstractController  {
	
	@Autowired
	private TimeService service;

	@GetMapping("/cadastro")
	public ModelAndView cadastro(Time time, ModelMap model) {
		return new ModelAndView("time/Form");
	}
	
	@PostMapping({"/cadastro", "/{\\d+}"})
	public ModelAndView cadastro(@Valid Time time, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirect) {
		if(bindingResult.hasErrors()) {
			addMensagensErroValidacao(modelMap, bindingResult);
			return cadastro(time, modelMap);
		}

		String view = time.isNovo() ? "/times/cadastro" : "/times";
		
		service.cadastrar(time);
		addMensagemSucesso(redirect, getMessage("time.mensagem.sucesso"));
		
		return new ModelAndView("redirect:"+view);
	}
	
	@GetMapping
	public ModelAndView listar(TimeFilter filter, HttpServletRequest httpServletRequest, @PageableDefault(size = 10) @SortDefault(value="nome") Pageable pageable) {
		ModelAndView mv = new ModelAndView("time/List");
		PageWrapper<Time> paginaWrapper = new PageWrapper<>(service.filtrar(filter,pageable),httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable("id") Time time, ModelMap model, RedirectAttributes redirect) {
		if (time == null) {
            addMensagemErro(redirect, getMessage("time.mensagem.naoEncontrado"));
            return new ModelAndView("redirect:/times");
        }

		model.addAttribute("time", time);
        return cadastro(time, model);
    }
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") Time time) {
		if(time != null) {
			try {
				service.remover(time);
			} catch (NaoEPossivelRemoverEntidadeException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body("Time não encontrado!");
		}
	}
}
