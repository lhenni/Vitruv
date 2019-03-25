package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.variability.VariabilityModel

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel();
	def VariabilityModel getVariabilityModel();
	def void save();
	def void persistRootElement(VURI persistenceVuri, EObject rootElement);
	def void executeCommand(Callable<Void> command);
	def void addChangePropagationListener(ChangePropagationListener propagationListener);
	def void setUserInteractor(UserInteractor userInteractor);
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener);
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener);
}