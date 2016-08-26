/**
 * generated by Xtext 2.10.0
 */
package edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage;

import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.NamedJavaElement;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Routine Input</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.RoutineInput#getModelInputElements <em>Model Input Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.RoutineInput#getJavaInputElements <em>Java Input Elements</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getRoutineInput()
 * @model
 * @generated
 */
public interface RoutineInput extends EObject
{
  /**
   * Returns the value of the '<em><b>Model Input Elements</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Model Input Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Model Input Elements</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getRoutineInput_ModelInputElements()
   * @model containment="true"
   * @generated
   */
  EList<ModelElement> getModelInputElements();

  /**
   * Returns the value of the '<em><b>Java Input Elements</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.NamedJavaElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Java Input Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Java Input Elements</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getRoutineInput_JavaInputElements()
   * @model containment="true"
   * @generated
   */
  EList<NamedJavaElement> getJavaInputElements();

} // RoutineInput