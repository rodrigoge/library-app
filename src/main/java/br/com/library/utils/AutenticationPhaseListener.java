package br.com.library.utils;

import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
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
		String pageLogin = ui.getViewId();
		String pageUser = ui.getViewId();
		String pageRecover = ui.getViewId();
		String pageUpdate = ui.getViewId();
		
		boolean pageAuthLogin = pageLogin.contains("/pages/users/Login.xhtml");
		boolean pageAuthUser = pageUser.contains("/pages/users/User.xhtml");
		boolean pageAuthRecover = pageRecover.contains("/pages/users/Recover.xhtml");
		boolean pageAuthUpdate = pageUpdate.contains("/pages/users/UpdatePassword.xhtml");
		
		if(!pageAuthLogin && !pageAuthUser && !pageAuthRecover && !pageAuthUpdate) {
			ExternalContext externalContext = context.getExternalContext();
			Map<String, Object> map = externalContext.getSessionMap();
			LoginController loginController = (LoginController) map.get("loginController");
			List<User> allUsers = loginController.getAllUsers();
			
			if(allUsers.isEmpty()) {
				Application app = context.getApplication();
				NavigationHandler navigation = app.getNavigationHandler();
				navigation.handleNavigation(context, null, "/pages/users/Login.xhtml?faces-redirect=true");
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
