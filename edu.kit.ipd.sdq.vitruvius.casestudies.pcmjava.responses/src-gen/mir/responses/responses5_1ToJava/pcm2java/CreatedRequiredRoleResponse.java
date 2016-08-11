package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
class CreatedRequiredRoleResponse extends AbstractResponseRealization {
  public CreatedRequiredRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
    RequiredRole _newValue = change.getNewValue();
    return (_newValue instanceof OperationRequiredRole);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<InterfaceRequiringEntity, RequiredRole> typedChange = (InsertEReference<InterfaceRequiringEntity, RequiredRole>)change;
    mir.routines.pcm2java.CreatedRequiredRoleEffect effect = new mir.routines.pcm2java.CreatedRequiredRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
