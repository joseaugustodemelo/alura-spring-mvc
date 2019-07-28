package br.com.caelum.usuario.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.contas.dao.UsuarioDAO;
import br.com.caelum.contas.modelo.Usuario;

@Controller
public class UsuarioController {

	@RequestMapping("/loginForm")
	public String fomulario() {

		return "usuario/login";
	}

	@RequestMapping("/efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession httpSession) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		if (usuarioDAO.existeUsuario(usuario)) {
			httpSession.setAttribute("usuarioLogado", usuario);
			return "menu";
		}

		return "redirect:loginForm";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:loginForm";
	}

}
