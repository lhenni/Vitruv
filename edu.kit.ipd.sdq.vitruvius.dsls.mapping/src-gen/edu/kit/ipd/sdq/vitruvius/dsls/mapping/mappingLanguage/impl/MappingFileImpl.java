/**
 * generated by Xtext 2.10.0-SNAPSHOT
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping;

import edu.kit.ipd.sdq.vitruvius.dsls.mirBase.MetamodelImport;

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
 * An implementation of the model object '<em><b>Mapping File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingFileImpl#getPluginName <em>Plugin Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingFileImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingFileImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingFileImpl#getDefaultRequirements <em>Default Requirements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingFileImpl extends MinimalEObjectImpl.Container implements MappingFile
{
  /**
   * The default value of the '{@link #getPluginName() <em>Plugin Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPluginName()
   * @generated
   * @ordered
   */
  protected static final String PLUGIN_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPluginName() <em>Plugin Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPluginName()
   * @generated
   * @ordered
   */
  protected String pluginName = PLUGIN_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImports()
   * @generated
   * @ordered
   */
  protected EList<MetamodelImport> imports;

  /**
   * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappings()
   * @generated
   * @ordered
   */
  protected EList<Mapping> mappings;

  /**
   * The cached value of the '{@link #getDefaultRequirements() <em>Default Requirements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultRequirements()
   * @generated
   * @ordered
   */
  protected EList<RequiredMapping> defaultRequirements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MappingFileImpl()
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
    return MappingLanguagePackage.Literals.MAPPING_FILE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPluginName()
  {
    return pluginName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPluginName(String newPluginName)
  {
    String oldPluginName = pluginName;
    pluginName = newPluginName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING_FILE__PLUGIN_NAME, oldPluginName, pluginName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MetamodelImport> getImports()
  {
    if (imports == null)
    {
      imports = new EObjectContainmentEList<MetamodelImport>(MetamodelImport.class, this, MappingLanguagePackage.MAPPING_FILE__IMPORTS);
    }
    return imports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getMappings()
  {
    if (mappings == null)
    {
      mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, MappingLanguagePackage.MAPPING_FILE__MAPPINGS);
    }
    return mappings;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RequiredMapping> getDefaultRequirements()
  {
    if (defaultRequirements == null)
    {
      defaultRequirements = new EObjectContainmentEList<RequiredMapping>(RequiredMapping.class, this, MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS);
    }
    return defaultRequirements;
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
      case MappingLanguagePackage.MAPPING_FILE__IMPORTS:
        return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING_FILE__MAPPINGS:
        return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS:
        return ((InternalEList<?>)getDefaultRequirements()).basicRemove(otherEnd, msgs);
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
      case MappingLanguagePackage.MAPPING_FILE__PLUGIN_NAME:
        return getPluginName();
      case MappingLanguagePackage.MAPPING_FILE__IMPORTS:
        return getImports();
      case MappingLanguagePackage.MAPPING_FILE__MAPPINGS:
        return getMappings();
      case MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS:
        return getDefaultRequirements();
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
      case MappingLanguagePackage.MAPPING_FILE__PLUGIN_NAME:
        setPluginName((String)newValue);
        return;
      case MappingLanguagePackage.MAPPING_FILE__IMPORTS:
        getImports().clear();
        getImports().addAll((Collection<? extends MetamodelImport>)newValue);
        return;
      case MappingLanguagePackage.MAPPING_FILE__MAPPINGS:
        getMappings().clear();
        getMappings().addAll((Collection<? extends Mapping>)newValue);
        return;
      case MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS:
        getDefaultRequirements().clear();
        getDefaultRequirements().addAll((Collection<? extends RequiredMapping>)newValue);
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
      case MappingLanguagePackage.MAPPING_FILE__PLUGIN_NAME:
        setPluginName(PLUGIN_NAME_EDEFAULT);
        return;
      case MappingLanguagePackage.MAPPING_FILE__IMPORTS:
        getImports().clear();
        return;
      case MappingLanguagePackage.MAPPING_FILE__MAPPINGS:
        getMappings().clear();
        return;
      case MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS:
        getDefaultRequirements().clear();
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
      case MappingLanguagePackage.MAPPING_FILE__PLUGIN_NAME:
        return PLUGIN_NAME_EDEFAULT == null ? pluginName != null : !PLUGIN_NAME_EDEFAULT.equals(pluginName);
      case MappingLanguagePackage.MAPPING_FILE__IMPORTS:
        return imports != null && !imports.isEmpty();
      case MappingLanguagePackage.MAPPING_FILE__MAPPINGS:
        return mappings != null && !mappings.isEmpty();
      case MappingLanguagePackage.MAPPING_FILE__DEFAULT_REQUIREMENTS:
        return defaultRequirements != null && !defaultRequirements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (pluginName: ");
    result.append(pluginName);
    result.append(')');
    return result.toString();
  }

} //MappingFileImpl
