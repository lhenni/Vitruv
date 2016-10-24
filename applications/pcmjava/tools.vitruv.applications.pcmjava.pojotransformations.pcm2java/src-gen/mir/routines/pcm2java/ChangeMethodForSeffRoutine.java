package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeMethodForSeffRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeMethodForSeffRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod) {
      return oldClassMethod;
    }
    
    public EObject getCorrepondenceSourceOldClassMethod(final ResourceDemandingSEFF seff) {
      return seff;
    }
    
    public void callRoutine1(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createSEFF(seff);
    }
  }
  
  public ChangeMethodForSeffRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingSEFF seff) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.ChangeMethodForSeffRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.seff = seff;
  }
  
  private ResourceDemandingSEFF seff;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodForSeffRoutine with input:");
    getLogger().debug("   ResourceDemandingSEFF: " + this.seff);
    
    ClassMethod oldClassMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldClassMethod(seff), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(oldClassMethod);
    deleteObject(userExecution.getElement1(seff, oldClassMethod));
    
    userExecution.callRoutine1(seff, oldClassMethod, actionsFacade);
    
    postprocessElementStates();
  }
}