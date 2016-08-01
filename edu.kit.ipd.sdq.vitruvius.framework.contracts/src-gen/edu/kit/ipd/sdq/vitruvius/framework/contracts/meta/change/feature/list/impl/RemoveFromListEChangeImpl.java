/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromListEChange;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove From List EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class RemoveFromListEChangeImpl<A extends EObject, F extends EStructuralFeature> extends UpdateSingleListEntryEChangeImpl<A, F> implements RemoveFromListEChange<A, F> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RemoveFromListEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ListPackage.Literals.REMOVE_FROM_LIST_ECHANGE;
    }

} //RemoveFromListEChangeImpl
