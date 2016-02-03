/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Update EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage#getUpdateEReference()
 * @model abstract="true"
 * @generated
 */
public interface UpdateEReference<A extends EObject> extends EFeatureChange<A, EReference> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model kind="operation" required="true"
     * @generated NOT
     */
    default boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

} // UpdateEReference