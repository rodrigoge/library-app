package br.com.library.utils;

import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.library.controllers.LoginController;
import br.com.library.models.User;

@SuppressWarnings("serial")
public class AutenticationPhaseListener implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		UIViewRoot ui = context.getViewRoot();
		String pageAtual = ui.getViewId();
		
		boolean pageAuth = pageAtual.contains("/Login.xhtml");
		
		if(!pageAuth) {
			ExternalContext externalContext = context.getExternalContext();
			Map<String, Object> map = externalContext.getSessionMap();
			LoginController loginController = (LoginController) map.get("loginController");
			
			List<User> allUsers = loginController.getAllUsers();
			
			if(allUsers.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não autenticado", "Erro"));
				
				Application app = context.getApplication();
				NavigationHandler navigation = app.getNavigationHandler();
				navigation.handleNavigation(context, null, "/Login?faces-redirect=true");
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
