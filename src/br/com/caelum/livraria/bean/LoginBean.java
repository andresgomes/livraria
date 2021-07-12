package br.com.caelum.livraria.bean;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}
	
	
	public String efetuarLogin() {
		System.out.println("Fazendo login do usuario: " + this.usuario.getEmail());
		FacesContext context = FacesContext.getCurrentInstance();
		
		boolean existe = new UsuarioDao().existe(this.usuario);
		if (existe) {
			//Adicionando usuario logado na sessao
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			
			return "livro?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Email ou senha invalidos"));
		
		return "login?faces-redirect=true";
	}
	
	public String sair() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "livro?faces-redirect=true";
	}


}
