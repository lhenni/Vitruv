/*
 * generated by Xtext 2.9.0
 */
package tools.vitruv.dsls.reactions.validation

import com.google.inject.Inject
import org.eclipse.xtext.validation.Check
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import java.util.HashMap
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementReferenceChangeType
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementCreationAndInsertionChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementDeletionAndRemovalChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementDeletionAndCreationAndReplacementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import tools.vitruv.dsls.reactions.scoping.ReactionsImportScopeHelper
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ReactionsLanguageValidator extends AbstractReactionsLanguageValidator {

	@Inject ReactionsImportScopeHelper reactionsImportScopeHelper;

	@Check
	def checkReactionsFile(ReactionsFile reactionsFile) {
		// check for duplicate reactions segment names in same file:
		val alreadyCheckedSegments = new HashMap<String, ReactionsSegment>();
		for (reactionsSegment : reactionsFile.reactionsSegments) {
			val reactionsSegmentName = reactionsSegment.name;
			if (alreadyCheckedSegments.putIfAbsent(reactionsSegmentName, reactionsSegment) !== null) {
				val errorMessage = "Duplicate reactions segment name: " + reactionsSegmentName;
				error(errorMessage, reactionsSegment, ReactionsLanguagePackage.Literals.REACTIONS_SEGMENT__NAME);
				val duplicateNameSegment = alreadyCheckedSegments.get(reactionsSegmentName);
				error(errorMessage, duplicateNameSegment, ReactionsLanguagePackage.Literals.REACTIONS_SEGMENT__NAME);
			}
		}

		// check for duplicate reactions segment names globally:
		val resource = reactionsFile.eResource;
		for (reactionsSegment : reactionsFile.reactionsSegments) {
			val visibleReactionsSegmentDescs = reactionsImportScopeHelper.getVisibleReactionsSegmentDescriptions(reactionsSegment);
			val reactionsSegmentName = reactionsSegment.name;
			val duplicateNameSegmentDesc = visibleReactionsSegmentDescs.findFirst[name.toString.equals(reactionsSegmentName)];
			if (duplicateNameSegmentDesc !== null) {
				// path relative to current file:
				val pathToOtherSegment = duplicateNameSegmentDesc.EObjectURI.trimFragment.deresolve(resource.URI);
				warning(
					"Duplicate reactions segment name '" + reactionsSegmentName + "': Already defined in " + pathToOtherSegment,
					reactionsSegment,
					ReactionsLanguagePackage.Literals.REACTIONS_SEGMENT__NAME
				);
			}
		}
	}

	@Check
	def checkReactionsSegment(ReactionsSegment reactionsSegment) {
		// val reactionsSegmentName = reactionsSegment.name;

		// check for duplicate and cyclic reactions imports:
		val alreadyCheckedReactionsImports = new HashMap<String, ReactionsImport>();
		for (reactionsImport : reactionsSegment.reactionsImports) {
			val importedSegment = reactionsImport.importedReactionsSegment;
			val importedSegmentName = importedSegment.name;
			val duplicateReactionsImport = alreadyCheckedReactionsImports.putIfAbsent(importedSegmentName, reactionsImport);
			if (duplicateReactionsImport !== null) {
				// duplicate reactions import:
				val errorMessage = "Duplicate reactions import: " + importedSegmentName;
				error(errorMessage, reactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
				error(errorMessage, duplicateReactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
			}
		}

		// TODO cyclic imports
		// TODO duplicate transitive imports, issue? yes, because unclear then which overrides are used / which super class is extended
		// maybe let each segment use its own RoutinesFacade which extends from base RoutinesFacade: root.B extends B and not A.B (A uses its own variant of B)
		// and then allow overriding of routines inside the hierarchy by explicitly specifying it with a qualified name: import A.B, override A.B::doSth
		// -> root.B extends B, root.A.B extends A.B extends B, B::doSth -> call to B.doSth from root -> uses root.B.doSth()
		// but: root calls routine in A which calls routine in B (A.B) -> same B routine now has to use A.B instead of root.B
		// -> need to pass not only root executor, but also caller's reactions segment name, so that B can ask root.Executor for A.B instead of root.B facade

		/*for (reactionsImport : reactionsSegment.reactionsImports) {
			val directlyImportedSegment = reactionsImport.importedReactionsSegment;
			val allImportedSegments = directlyImportedSegment.allImportedReactionsSegments;
			allImportedSegments.add(directlyImportedSegment);
			for (importedSegment : allImportedSegments) {
				val importedSegmentName = importedSegment.name;
				if (reactionsSegmentName.equals(importedSegmentName)) {
					// cyclic import:
					val errorMessage = "Cyclic reactions import: Cannot transitively import itself";
					error(errorMessage, reactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
				} else {
					val duplicateReactionsImport = alreadyCheckedImportedSegments.putIfAbsent(importedSegmentName, reactionsImport);
					if (duplicateReactionsImport !== null) {
						// duplicate import:
						val errorMessage = "Duplicate reactions import (possibly transitive): " + importedSegmentName;
						error(errorMessage, reactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
						error(errorMessage, duplicateReactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
					}
				}
			}
		}*/

		// check for duplicate reaction names in same segment:
		val alreadyCheckedReactions = new HashMap<String, Reaction>();
		for (reaction : reactionsSegment.reactions) {
			val reactionName = reaction.formattedReactionName;
			if (alreadyCheckedReactions.putIfAbsent(reactionName, reaction) !== null) {
				val errorMessage = "Duplicate reaction name: " + reactionName;
				error(errorMessage, reaction, ReactionsLanguagePackage.Literals.REACTION__NAME);
				error(
					errorMessage,
					alreadyCheckedReactions.get(reactionName),
					ReactionsLanguagePackage.Literals.REACTION__NAME
				);
			}
		}

		// check for duplicate routine names in same segment:
		val alreadyCheckedRoutines = new HashMap<String, Routine>();
//		for (implicitRoutine : reactionSegment.reactions.map[routine]) {
//			alreadyCheckedEffects.put(implicitRoutine.routineClassNameGenerator.simpleName, implicitRoutine);
//		}
		for (routine : reactionsSegment.routines) {
			val routineName = routine.formattedRoutineName;
			if (alreadyCheckedRoutines.putIfAbsent(routineName, routine) !== null) {
				val errorMessage = "Duplicate routine name: " + routineName;
				error(errorMessage, routine, ReactionsLanguagePackage.Literals.ROUTINE__NAME);
				val duplicateNameRoutine = alreadyCheckedRoutines.get(routineName);
				error(errorMessage, duplicateNameRoutine, ReactionsLanguagePackage.Literals.ROUTINE__NAME);
			}
		}
	}

	@Check
	def checkRetrieveElementName(RetrieveModelElement element) {
		if (!element.name.nullOrEmpty && element.name.startsWith("_")) {
			error("Element names must not start with an underscore.",
				ReactionsLanguagePackage.Literals.RETRIEVE_MODEL_ELEMENT__NAME);
		}
	}
	
		@Check
	def checkCreateElementName(CreateModelElement element) {
		if (!element.name.nullOrEmpty && element.name.startsWith("_")) {
			error("Element names must not start with an underscore.",
				ReactionsLanguagePackage.Literals.CREATE_MODEL_ELEMENT__NAME);
		}
	}

//	@Check
//	def checkEffects(Effect effect) {
//		if (effect.impact.codeBlock === null && !effect.impact..filter(CorrespondingModelElementCreate).nullOrEmpty) {
//			warning("Created elements must be initialized and inserted into the target model in the execute block.",
//				ReactionsLanguagePackage.Literals.EFFECT__CODE_BLOCK);
//		}
//	}
	
//	@Check
//	def checkEffectInput(RoutineInput effectInput) {
//		if (!effectInput.javaInputElements.empty) {
//			warning("Using plain Java elements is discouraged. Try to use model elements and make list inputs to single valued input of other effect that is called for each element.",
//				ReactionsLanguagePackage.Literals.ROUTINE_INPUT__JAVA_INPUT_ELEMENTS);
//		}
//	}
	
	@Check
	def checkRoutine(Routine routine) {
		if (!Character.isLowerCase(routine.name.charAt(0))) {
			warning("Routine names should start lower case",
				ReactionsLanguagePackage.Literals.ROUTINE__NAME);
		}
	}
	
	@Check
	def checkRoutine(Reaction reaction) {
		if (!Character.isUpperCase(reaction.name.charAt(0))) {
			warning("Reaction names should start upper case",
				ReactionsLanguagePackage.Literals.REACTION__NAME);
		}
	}
	
	@Check
	def checkMetaclassFeature(ModelElementChange elementChange) {
		val elementType = elementChange?.elementType?.metaclass;
		val elementChangeType = elementChange?.changeType;
		// Only continue if element type is specified and its a feature change
		var ElementChangeType atomicChangeType = null;
		if (elementChangeType instanceof ElementReferenceChangeType) {
			atomicChangeType = elementChangeType;
		} else if (elementChangeType instanceof ElementCreationAndInsertionChangeType) {
			atomicChangeType = elementChangeType.insertChange;
		} else if (elementChangeType instanceof ElementDeletionAndRemovalChangeType) {
			atomicChangeType = elementChangeType.removeChange;
		} else if (elementChangeType instanceof ElementDeletionAndCreationAndReplacementChangeType) {
			atomicChangeType = elementChangeType.replacedChange;
		}
		if (atomicChangeType instanceof ElementReferenceChangeType) {
			val featureType = atomicChangeType.feature?.feature?.EType as EClass;
			if (elementType !== null && featureType !== null) {
				if (!elementType.equals(featureType) && !elementType.EAllSuperTypes.contains(featureType) && !featureType.EAllSuperTypes.contains(elementType)) {
					warning("Element of specified type cannot be contained in the specified features",
						elementChange, ReactionsLanguagePackage.Literals.MODEL_ELEMENT_CHANGE__ELEMENT_TYPE
					)
				}
			}
		}
	}

}
