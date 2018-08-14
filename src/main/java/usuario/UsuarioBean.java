package usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import usuario.Usuario;
import usuario.UsuarioRN;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.util.List;




@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {

	private Usuario usuario	= new Usuario();
    private String confirmarSenha;
    private List<Usuario> lista;
    private String destinoSalvar;
        
        public String novo() {
            this.destinoSalvar = "usuariosucesso";
        	this.usuario = new Usuario();
            this.usuario.setAtivo(true);
            return "/restrito/usuario";
        }
        public String editar() {
        	this.confirmarSenha = this.usuario.getSenha();
        	return "/restrito/usuario";
        }
        
        public String salvar() {
            FacesContext context = FacesContext.getCurrentInstance();
            
            String senha = this.usuario.getSenha();
            if (!senha.equals(this.confirmarSenha)) {
                FacesMessage facesMessage = new FacesMessage ("A senha n�o foi confirmada corretamente");
                context.addMessage(null, facesMessage);
                return null;
            }
            UsuarioRN usuarioRN = new UsuarioRN();
            usuarioRN.salvar(this.usuario);
            return this.destinoSalvar;
        }
        
        public String excluir() {
        	UsuarioRN usuarioRN = new UsuarioRN();
        	usuarioRN.excluir(this.usuario);
        	this.lista = null;
        	return null;
        }
        
        public String ativar() {
          if (this.usuario.isAtivo())
        	  this.usuario.setAtivo(false);
          else
        	  this.usuario.setAtivo(true);
        	  
          UsuarioRN usuarioRN = new UsuarioRN();
          usuarioRN.salvar(this.usuario);
          return null;
        }
        
        public List<Usuario> getLista() {
        	if (this.lista == null) {
        		UsuarioRN usuarioRN = new UsuarioRN();
        		this.lista = usuarioRN.listar();
        	}
        	return this.lista;
        }
        
        
        

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
    
    public String getDestinoSalvar() {
    	return destinoSalvar;
    }
    
    public  void setDestinoSalvar(String destinoSalvar) {
    	this.destinoSalvar = destinoSalvar;
    }
	
}
