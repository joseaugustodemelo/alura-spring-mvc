package br.com.caelum.contas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.contas.dao.ContaDAO;
import br.com.caelum.contas.modelo.Conta;

@Controller
public class ContaController {

	private ContaDAO contaDao;

	@Autowired
	public ContaController(ContaDAO contaDao) {
		super();
		this.contaDao = contaDao;
	}

	@RequestMapping("/form")
	public String formulario() {
		return "formulario";
	}

	@RequestMapping("/adicionarConta")
	public String adiciona(@Valid Conta conta, BindingResult bindingResult) {

		/*
		 * if(bindingResult.hasErrors()) { return "formulario"; }
		 */

		if (bindingResult.hasFieldErrors("descricao")) {
			return "formulario";
		}

		this.contaDao.adiciona(conta);
		return "conta-adicionada";
	}

	@RequestMapping("/pagarConta")
	public void pagar(Conta conta, HttpServletResponse response) {

		this.contaDao.paga(conta.getId());

		response.setStatus(200);
		// return "redirect:listaContas";
	}

	@RequestMapping("/listaContas")
	public ModelAndView lista() {

		List<Conta> contas = this.contaDao.lista();

		ModelAndView mv = new ModelAndView("conta/lista");
		mv.addObject("contas", contas);
		return mv;
	}

	@RequestMapping("/removerConta")
	public String remove(Conta conta) {

		this.contaDao.remove(conta);

		// return "forward:listaContas";
		return "redirect:listaContas";
	}

	@RequestMapping("/mostraConta")
	public String mostra(Long id, Model model) {

		model.addAttribute("conta", this.contaDao.buscaPorId(id));
		return "conta/mostra";
	}

	@RequestMapping("/alteraConta")
	public String altera(Conta conta) {

		this.contaDao.altera(conta);
		return "redirect:listaContas";
	}
}
