/**
 * generated by Xtext 2.10.0-SNAPSHOT
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature;

import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelReference;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.SignatureImpl#getDeclaredPackage <em>Declared Package</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.SignatureImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignatureImpl extends MinimalEObjectImpl.Container implements Signature
{
  /**
   * The cached value of the '{@link #getDeclaredPackage() <em>Declared Package</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDeclaredPackage()
   * @generated
   * @ordered
   */
  protected MetamodelReference declaredPackage;

  /**
   * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected EList<ModelElement> elements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SignatureImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return MappingLanguagePackage.Literals.SIGNATURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MetamodelReference getDeclaredPackage()
  {
    return declaredPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDeclaredPackage(MetamodelReference newDeclaredPackage, NotificationChain msgs)
  {
    MetamodelReference oldDeclaredPackage = declaredPackage;
    declaredPackage = newDeclaredPackage;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE, oldDeclaredPackage, newDeclaredPackage);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDeclaredPackage(MetamodelReference newDeclaredPackage)
  {
    if (newDeclaredPackage != declaredPackage)
    {
      NotificationChain msgs = null;
      if (declaredPackage != null)
        msgs = ((InternalEObject)declaredPackage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE, null, msgs);
      if (newDeclaredPackage != null)
        msgs = ((InternalEObject)newDeclaredPackage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE, null, msgs);
      msgs = basicSetDeclaredPackage(newDeclaredPackage, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE, newDeclaredPackage, newDeclaredPackage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ModelElement> getElements()
  {
    if (elements == null)
    {
      elements = new EObjectContainmentEList<ModelElement>(ModelElement.class, this, MappingLanguagePackage.SIGNATURE__ELEMENTS);
    }
    return elements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE:
        return basicSetDeclaredPackage(null, msgs);
      case MappingLanguagePackage.SIGNATURE__ELEMENTS:
        return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE:
        return getDeclaredPackage();
      case MappingLanguagePackage.SIGNATURE__ELEMENTS:
        return getElements();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE:
        setDeclaredPackage((MetamodelReference)newValue);
        return;
      case MappingLanguagePackage.SIGNATURE__ELEMENTS:
        getElements().clear();
        getElements().addAll((Collection<? extends ModelElement>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE:
        setDeclaredPackage((MetamodelReference)null);
        return;
      case MappingLanguagePackage.SIGNATURE__ELEMENTS:
        getElements().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case MappingLanguagePackage.SIGNATURE__DECLARED_PACKAGE:
        return declaredPackage != null;
      case MappingLanguagePackage.SIGNATURE__ELEMENTS:
        return elements != null && !elements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //SignatureImpl
