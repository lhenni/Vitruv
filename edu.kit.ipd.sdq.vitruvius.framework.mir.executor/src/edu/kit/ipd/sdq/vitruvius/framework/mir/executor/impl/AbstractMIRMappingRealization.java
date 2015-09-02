package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * {@link AbstractMIRMappingRealization} is extended by the code generated from
 * the intermediate language.
 * <p>
 * A mapping is instantiated for two model instances.
 * 
 * @author Dominik Werle
 *
 */
public abstract class AbstractMIRMappingRealization implements MIRMappingRealization {
	private final static Logger LOGGER = Logger.getLogger(AbstractMIRMappingRealization.class);

	/**
	 * Returns the {@link EClass} mapped by this mapping.
	 * 
	 * @return the {@link EClass} mapped by this mapping
	 */
	protected abstract EClass getMappedEClass();

	/**
	 * Check if the conditions of the mapping hold for the given {@link EObject}
	 * .
	 * 
	 * @param eObject
	 *            the object to check
	 * @return true if the mapping holds for the given object.
	 */
	protected abstract boolean checkConditions(EObject eObject, Blackboard blackboard);

	/**
	 * Ensure that the postconditions ("where") still hold for the mapping.
	 * <p>
	 * The given {@link EChange} can be used to select the conditions that have
	 * to be checked.
	 * 
	 * @param eObject
	 *            the {@link EObject} that has been changed
	 * @param target
	 *            the {@link EObject} that <code>eObject</code> is corresponding
	 *            to
	 * @param eChange
	 *            the change that was applied
	 * @return
	 */
	protected abstract void restorePostConditions(EObject eObject, EObject target, EChange change,
			Blackboard blackboard);

	/**
	 * Creates a corresponding object for <code>eObject</code> and a
	 * correspondence in the mapped meta model and registers it
	 * 
	 * @return the created objects
	 */
	protected abstract Collection<Pair<EObject, VURI>> createCorresponding(EObject eObject, Blackboard blackboard);

	/**
	 * Deletes the corresponding object to <code>eObject</code>, which is <code>target</code>,
	 * (and its children) and the correspondence itself. This method does not delete <code>eObject</code>.
	 * 
	 * @param source
	 * @param transformationResult 
	 * @param correspondenceInstance
	 */
	protected void deleteCorresponding(EObject source, EObject target, Blackboard blackboard, TransformationResult transformationResult) {
		final MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		final Set<Correspondence> removedCorrespondences = correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(target);
		final TUID sourceTUID = correspondenceInstance.calculateTUIDFromEObject(source);
		
		for (final Correspondence correspondence : removedCorrespondences) {
			final Collection<TUID> eObjectsInCorrespondence = getTUIDsInCorrespondence(correspondence, correspondenceInstance);
			eObjectsInCorrespondence.stream()
				.filter(it -> !(it.equals(sourceTUID)))
				.forEach(it -> {
					final EObject itEObject = correspondenceInstance.resolveEObjectFromTUID(it);
					deleteEObjectAndResourceIfRoot(itEObject, transformationResult);
				});
		}
	}
	
	private void deleteEObjectAndResourceIfRoot(EObject eObjectToDelete, TransformationResult transformationResult) {
		LOGGER.trace("Deleting " + eObjectToDelete.toString());
		if (EcoreHelper.isRoot(eObjectToDelete)) {
			transformationResult.addVURIToDelete(VURI.getInstance(eObjectToDelete.eResource()));
		}
		EcoreUtil.delete(eObjectToDelete);	}

	private Collection<TUID> getTUIDsInCorrespondence(Correspondence correspondence, CorrespondenceInstance correspondenceInstance) {
		Set<TUID> result = new HashSet<TUID>();
		if (correspondence instanceof EObjectCorrespondence) {
			final EObjectCorrespondence eObjectCorrespondence = (EObjectCorrespondence) correspondence;
			result.add(eObjectCorrespondence.getElementATUID());
			result.add(eObjectCorrespondence.getElementBTUID());
		} else {
			LOGGER.info("Correspondence is not EObjectCorrespondence. Returning empty set.");
		}
		
		return result;
	}

	/**
	 * Returns {@link EObject EObjects} that are possibly affected by this
	 * change.
	 * 
	 * @param eChange
	 * @return
	 */
	protected Collection<EObject> getCandidates(EChange eChange) {
		final EClass mappedEClass = getMappedEClass();
		Collection<EObject> affectedObjects = MIRMappingHelper.getAllAffectedObjects(eChange);
		return toList(filter(affectedObjects, p -> p.eClass().equals(mappedEClass)));
	}

	// FIXME DW: protected? move to MappedCorrespondenceInstance?
	protected static MappedCorrespondenceInstance getMappedCorrespondenceInstanceFromBlackboard(Blackboard blackboard) {
		CorrespondenceInstance correspondenceInstance = blackboard.getCorrespondenceInstance();
		if (!(correspondenceInstance instanceof MappedCorrespondenceInstance)) {
			throw new IllegalArgumentException("The given correspondence instance " + correspondenceInstance
					+ " is not a " + MappedCorrespondenceInstance.class.getSimpleName());
		} else {
			return (MappedCorrespondenceInstance) correspondenceInstance;
		}
	}

	@Override
	public TransformationResult applyEChange(EChange eChange, Blackboard blackboard) {
		MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
		TransformationResult transformationResult = new TransformationResult();
		
		/*
		 * TODO: change to create candidates (EObject, PotentialTransition(new,
		 * still, remove), Mapping) in AbstractMIRChange2CommandTransforming.
		 */
		Collection<EObject> candidates = getCandidates(eChange);

		for (EObject candidate : candidates) {
			LOGGER.trace("Checking candidate " + candidate.toString());

			boolean mappedBefore = correspondenceInstance.checkIfMappedBy(candidate, this);
			boolean mappedAfter = checkConditions(candidate, blackboard);

			EObject mappingTarget = null;

			if (mappedBefore) {
				LOGGER.trace(candidate.toString() + " was already mapped.");
				mappingTarget = Objects.requireNonNull(correspondenceInstance.getMappingTarget(candidate, this));

				if (!mappedAfter) {
					LOGGER.trace("... and is not mapped anymore.");
					deleteCorresponding(candidate, mappingTarget, blackboard, transformationResult);
				}
			}

			if (!mappedBefore && mappedAfter) {
				LOGGER.trace("Create new correspondence for " + candidate.toString() + ":");
				final Collection<Pair<EObject, VURI>> newRootObjects = createCorresponding(candidate, blackboard);
				for (Pair<EObject, VURI> newRootObject : newRootObjects) {
					LOGGER.trace(" -- " + newRootObject.getFirst().toString() + " -> " + newRootObject.getSecond().toString());
				}
				transformationResult.getRootEObjectsToSave().addAll(newRootObjects);
				mappingTarget = Objects.requireNonNull(correspondenceInstance.getMappingTarget(candidate, this));
			}

			if (mappedAfter) {
				LOGGER.trace("Still mapped.");
				restorePostConditions(candidate, mappingTarget, eChange, blackboard);
			}
		}
		
		return transformationResult;
	}

	/**
	 * Asks the user and creates new resources for EObjects in
	 * <code>affectedEObjects</code> that do not have a container.
	 * @param blackboard TODO
	 * @param transformationResult 
	 */
	private void handleNonContainedEObjects(Collection<EObject> affectedEObjects, Blackboard blackboard, TransformationResult transformationResult) {
		final ModelProviding modelProviding = blackboard.getModelProviding();
		for (final EObject eObject : affectedEObjects) {
			if (eObject.eResource() == null) {
				VURI resourceVURI = VURI.getInstance(EclipseHelper.askForNewResource(eObject));
				transformationResult.addRootEObjectToSave(eObject, resourceVURI);
			}
		}
	}
}
