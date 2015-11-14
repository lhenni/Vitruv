/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CorrespondencePackageImpl extends EPackageImpl implements CorrespondencePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass correspondencesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass correspondenceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType tuidCalculatorAndResolverEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType tuidEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CorrespondencePackageImpl() {
        super(eNS_URI, CorrespondenceFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link CorrespondencePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CorrespondencePackage init() {
        if (isInited) return (CorrespondencePackage)EPackage.Registry.INSTANCE.getEPackage(CorrespondencePackage.eNS_URI);

        // Obtain or create and register package
        CorrespondencePackageImpl theCorrespondencePackage = (CorrespondencePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CorrespondencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CorrespondencePackageImpl());

        isInited = true;

        // Create package meta-data objects
        theCorrespondencePackage.createPackageContents();

        // Initialize created meta-data
        theCorrespondencePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCorrespondencePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, theCorrespondencePackage);
        return theCorrespondencePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCorrespondences() {
        return correspondencesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCorrespondences_Correspondences() {
        return (EReference)correspondencesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCorrespondences_TuidCARForAs() {
        return (EAttribute)correspondencesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCorrespondences_TuidCARForBs() {
        return (EAttribute)correspondencesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCorrespondence() {
        return correspondenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCorrespondence_Parent() {
        return (EReference)correspondenceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCorrespondence_DependsOn() {
        return (EReference)correspondenceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCorrespondence_DependedOnBy() {
        return (EReference)correspondenceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCorrespondence_ATUIDs() {
        return (EAttribute)correspondenceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCorrespondence_BTUIDs() {
        return (EAttribute)correspondenceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTUIDCalculatorAndResolver() {
        return tuidCalculatorAndResolverEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTUID() {
        return tuidEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CorrespondenceFactory getCorrespondenceFactory() {
        return (CorrespondenceFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        correspondencesEClass = createEClass(CORRESPONDENCES);
        createEReference(correspondencesEClass, CORRESPONDENCES__CORRESPONDENCES);
        createEAttribute(correspondencesEClass, CORRESPONDENCES__TUID_CAR_FOR_AS);
        createEAttribute(correspondencesEClass, CORRESPONDENCES__TUID_CAR_FOR_BS);

        correspondenceEClass = createEClass(CORRESPONDENCE);
        createEReference(correspondenceEClass, CORRESPONDENCE__PARENT);
        createEReference(correspondenceEClass, CORRESPONDENCE__DEPENDS_ON);
        createEReference(correspondenceEClass, CORRESPONDENCE__DEPENDED_ON_BY);
        createEAttribute(correspondenceEClass, CORRESPONDENCE__ATUI_DS);
        createEAttribute(correspondenceEClass, CORRESPONDENCE__BTUI_DS);

        // Create data types
        tuidCalculatorAndResolverEDataType = createEDataType(TUID_CALCULATOR_AND_RESOLVER);
        tuidEDataType = createEDataType(TUID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(correspondencesEClass, Correspondences.class, "Correspondences", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCorrespondences_Correspondences(), this.getCorrespondence(), this.getCorrespondence_Parent(), "correspondences", null, 0, -1, Correspondences.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCorrespondences_TuidCARForAs(), this.getTUIDCalculatorAndResolver(), "tuidCARForAs", null, 1, 1, Correspondences.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCorrespondences_TuidCARForBs(), this.getTUIDCalculatorAndResolver(), "tuidCARForBs", null, 1, 1, Correspondences.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(correspondenceEClass, Correspondence.class, "Correspondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCorrespondence_Parent(), this.getCorrespondences(), this.getCorrespondences_Correspondences(), "parent", null, 1, 1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCorrespondence_DependsOn(), this.getCorrespondence(), this.getCorrespondence_DependedOnBy(), "dependsOn", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        initEReference(getCorrespondence_DependedOnBy(), this.getCorrespondence(), this.getCorrespondence_DependsOn(), "dependedOnBy", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        initEAttribute(getCorrespondence_ATUIDs(), this.getTUID(), "aTUIDs", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCorrespondence_BTUIDs(), this.getTUID(), "bTUIDs", null, 0, -1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        addEOperation(correspondenceEClass, ecorePackage.getEObject(), "getAs", 0, -1, IS_UNIQUE, IS_ORDERED);

        addEOperation(correspondenceEClass, ecorePackage.getEObject(), "getBs", 0, -1, IS_UNIQUE, IS_ORDERED);

        addEOperation(correspondenceEClass, this.getTUID(), "getElementATUID", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(correspondenceEClass, this.getTUID(), "getElementBTUID", 0, 1, IS_UNIQUE, IS_ORDERED);

        EOperation op = addEOperation(correspondenceEClass, null, "setElementATUID", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getTUID(), "tuid", 1, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(correspondenceEClass, null, "setElementBTUID", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getTUID(), "tuid", 1, 1, IS_UNIQUE, IS_ORDERED);

        // Initialize data types
        initEDataType(tuidCalculatorAndResolverEDataType, TUIDCalculatorAndResolver.class, "TUIDCalculatorAndResolver", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(tuidEDataType, edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID.class, "TUID", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //CorrespondencePackageImpl
