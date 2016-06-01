package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class ChangeParameterTypeEffect extends AbstractEffectRealization {
  public ChangeParameterTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OrdinaryParameter javaParameter;
  
  private DataType parameterType;
  
  private boolean isJavaParameterSet;
  
  private boolean isParameterTypeSet;
  
  public void setJavaParameter(final OrdinaryParameter javaParameter) {
    this.javaParameter = javaParameter;
    this.isJavaParameterSet = true;
  }
  
  public void setParameterType(final DataType parameterType) {
    this.parameterType = parameterType;
    this.isParameterTypeSet = true;
  }
  
  public boolean allParametersSet() {
    return isJavaParameterSet&&isParameterTypeSet;
  }
  
  private EObject getCorrepondenceSourceJavaParameterTypeClass(final OrdinaryParameter javaParameter, final DataType parameterType) {
    return parameterType;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeEffect with input:");
    getLogger().debug("   OrdinaryParameter: " + this.javaParameter);
    getLogger().debug("   DataType: " + this.parameterType);
    
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaParameterTypeClass(javaParameter, parameterType), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	true, false, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.ChangeParameterTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	javaParameter, parameterType, javaParameterTypeClass);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OrdinaryParameter javaParameter, final DataType parameterType, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(parameterType, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
  }
}