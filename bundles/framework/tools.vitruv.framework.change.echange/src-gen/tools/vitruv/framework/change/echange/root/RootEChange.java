/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.echange.AtomicEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getResource <em>Resource</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange()
 * @model abstract="true"
 * @generated
 */
public interface RootEChange extends AtomicEChange {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(URI)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Uri()
	 * @model unique="false" dataType="tools.vitruv.framework.change.echange.root.URI"
	 * @generated
	 */
	URI getUri();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(URI value);

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' attribute.
	 * @see #setResource(Resource)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Resource()
	 * @model unique="false" dataType="tools.vitruv.framework.change.echange.root.Resource"
	 * @generated
	 */
	Resource getResource();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getResource <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(Resource value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Index()
	 * @model unique="false"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // RootEChange
