package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.AttributeEquivalenceExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBooleanLiteral
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintLiteral
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintNullLiteral
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintNumberLiteral
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintStringLiteral
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ContextVariable
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.EqualsLiteralExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.InExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.EMFHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*
import static extension java.util.Objects.*
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NotNullExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement

class ConstraintLanguageGenerator {
	private static final Logger LOGGER = Logger.getLogger(ConstraintLanguageGenerator)
	private final extension EMFGeneratorHelper emfGeneratorHelper

	new(EMFGeneratorHelper emfGeneratorHelper) {
		this.emfGeneratorHelper = emfGeneratorHelper
	}

	def dispatch restoreBodyConstraintFrom(ImportHelper importHelper, Map<List<?>, String> localContext,
		ConstraintExpression constraint, EPackage pkg) '''
		// unknown body constraint expression type to restore
	'''

	def dispatch restoreBodyConstraintFrom(extension ImportHelper importHelper, Map<List<?>, String> localContext,
		AttributeEquivalenceExpression constraint, EPackage pkg) {
		val mapping = constraint.getContainerOfType(Mapping)
		val source = #[constraint.left, constraint.right].claimExactlyOneInPackage(pkg)
		val target = #[constraint.left, constraint.right].findFirst[it != source].
			requireNonNull

//		'''
//			// «target.context.targetClass.name».«target.feature.name» := «source.context.targetClass.name».«source.feature.name»
//			«typeRef(source.context.targetClass.type)» source = «getJavaExpressionThatReturns(localContext, source.context, mapping)»;
//			«typeRef(target.context.targetClass.type)» target = «getJavaExpressionThatReturns(localContext, target.context, mapping)»;
//			«eRefSet(importHelper, "target", target.feature, eRefGet(importHelper, "source", source.feature))»;
//		'''

		'''
			// «target.context.targetClass.name».«target.feature.name» := «source.context.targetClass.name».«source.feature.name»
			«eRefSet(importHelper, getJavaExpressionThatReturns(localContext, target.context, mapping), target.feature, eRefGet(importHelper, getJavaExpressionThatReturns(localContext, source.context, mapping), source.feature))»;
		'''

	}

	def dispatch checkSignatureConstraint(ImportHelper importHelper, Map<List<?>, String> localContext,
		ConstraintExpression constraint)
	{
		null
	}

	def dispatch checkSignatureConstraint(ImportHelper importHelper, Map<List<?>, String> localContext,
		InExpression constraint) {
		val mapping = constraint.getContainerOfType(Mapping)

		val source = constraint.source.context
		val feature = constraint.source.feature
		val target = constraint.target

		eContainsOrIsEqual(importHelper, getJavaExpressionThatReturns(localContext, source, mapping), feature,
			getJavaExpressionThatReturns(localContext, target, mapping))
	}

	def dispatch checkSignatureConstraint(ImportHelper importHelper, Map<List<?>, String> localContext,
		EqualsLiteralExpression constraint) {
		val mapping = constraint.getContainerOfType(Mapping)

		val feature = constraint.target.feature
		val target = constraint.target.context

		eContainsOrIsEqual(importHelper, getJavaExpressionThatReturns(localContext, target, mapping), feature,
			getJavaExpressionThatReturns(constraint.value))
	}
	
	def dispatch checkSignatureConstraint(ImportHelper importHelper, Map<List<?>, String> localContext,
		NotNullExpression constraint) {
		val mapping = constraint.getContainerOfType(Mapping)

		val target = constraint.feature.context
		val feature = constraint.feature.feature

		'''«eRefGet(importHelper, getJavaExpressionThatReturns(localContext, target, mapping), feature)» != null'''
	}

	def dispatch checkSignatureConstraint(ImportHelper importHelper, Map<List<?>, String> localContext,
		DefaultContainExpression constraint) {
		// default contain expression does not need check
		null
	}

	def static dispatch String getJavaExpressionThatReturns(ConstraintLiteral literal) '''
		/* unknown literal: «literal.toString» */null
	'''

	def static dispatch String getJavaExpressionThatReturns(ConstraintBooleanLiteral literal) '''
		«IF literal.isTrue»true«ELSE»false«ENDIF»
	'''

	def static dispatch String getJavaExpressionThatReturns(ConstraintNullLiteral literal) '''
		null
	'''

	def static dispatch String getJavaExpressionThatReturns(ConstraintNumberLiteral literal) '''
		«literal.value»
	'''

	def static dispatch String getJavaExpressionThatReturns(ConstraintStringLiteral literal) '''
		"«literal.value»"
	'''

	static def getJavaExpressionThatReturns(Map<List<?>, String> localContext, ContextVariable variable,
		Mapping source) {

		val Map<List<?>, String> context = newHashMap(#['this'] -> 'this')
		context.putAll(localContext)

		println(context.toString)

		val mappingPath = variable.requiredMappingPath?.collectRecursive ?: #[]
		val elementPath = (#['this'] + mappingPath + #[variable.targetClass.import, variable.targetClass]).toList
		var reverseIndex = elementPath.size - 1

		val pathToElement = new ArrayList<String>
		var Object el = null
		var entryFound = false
		var List<?> previousElements = null
		do {
			entryFound = false
			if (reverseIndex >= 0) {
				el = elementPath.get(reverseIndex)
				previousElements = elementPath.subList(0, reverseIndex + 1)
				
				var String nextElement = null
				for (entry : context.entrySet) {
					if (entry.key.equals(previousElements)) {
						nextElement = entry.value
						entryFound = true
					}
				}
				
				nextElement = nextElement ?: '''get«el.tryGetName.toFirstUpper»()'''
				
				println('''  «el.toString» («previousElements.toString») --> «nextElement.toString»''')
				pathToElement += nextElement
				
				reverseIndex--
			}
		} while (!entryFound && (reverseIndex >= 0))

		pathToElement.reverse
		return pathToElement.filterNull.join(".")
	}

	private static def dispatch String tryGetName(Object object) {
		'''/* unnamed «object.toString» */'''
	}

	private static def dispatch String tryGetName(EObject object) {
		'''/* unnamed «object.toString» */'''
	}

	private static def dispatch String tryGetName(ModelElement object) {
		object.name
	}

	private static def dispatch String tryGetName(MetamodelImport object) {
		object.name
	}

	private static def dispatch String tryGetName(RequiredMapping object) {
		object.name
	}

	private static def dispatch String tryGetName(Mapping object) {
		object.name
	}

	/**
	 * Does a depth-first search for a path from source to target and
	 * returns it (including both).
	 */
	def static List<Mapping> findMappingPath(Mapping source, Mapping target) {
		if (source.equals(target)) {
			return #[target]
		}

		for (nextMapping : source.requires) {
			val path = nextMapping.mapping.findMappingPath(target)
			if (path != null)
				return #[#[source], path].flatten.toList
		}

		return null;
	}

	def dispatch establishSignatureConstraintOnCreate(ImportHelper importHelper, Map<List<?>, String> localContext,
		ConstraintExpression constraint) { null }

	def dispatch establishSignatureConstraintOnCreate(ImportHelper importHelper, Map<List<?>, String> localContext,
		InExpression constraint) {
		val manipulatedVariable = constraint.source.context
		val feature = constraint.source.feature
		val target = constraint.target

		val sourceMapping = constraint.getContainerOfType(Mapping).requireNonNull

		eSetOrAdd(importHelper, getJavaExpressionThatReturns(localContext, manipulatedVariable, sourceMapping), feature,
			getJavaExpressionThatReturns(localContext, target, sourceMapping))
	}

	def dispatch establishSignatureConstraintOnCreate(ImportHelper importHelper, Map<List<?>, String> localContext,
		EqualsLiteralExpression constraint) {
		val manipulatedVariable = constraint.target.context
		val feature = constraint.target.feature

		val sourceMapping = constraint.getContainerOfType(Mapping).requireNonNull

		eSetOrAdd(importHelper, getJavaExpressionThatReturns(localContext, manipulatedVariable, sourceMapping), feature,
			getJavaExpressionThatReturns(constraint.value))
	}

	def dispatch establishSignatureConstraintOnCreate(ImportHelper importHelper, Map<List<?>, String> localContext,
		DefaultContainExpression constraint) '''
		/* establish «constraint.toString»: do nothing */
	'''

	def checkAndCreateDefaultContainment(extension ImportHelper importHelper, Map<List<?>, String> localContext,
		DefaultContainExpression constraint) {

		val target = constraint.target
		val sourceMapping = constraint.getContainerOfType(Mapping).requireNonNull
		val targetJava = getJavaExpressionThatReturns(localContext, target, sourceMapping)

		val createContainmentExpression = if (constraint.source == null) {
				constraint.relativeResource.claim[it != null]

				val sourceJava = constraint.relativeResourceSource?.apply [
					getJavaExpressionThatReturns(localContext, it, sourceMapping)
				] ?:
					"null"

				'''
					final «typeRef(VURI)» resourceVURI = «typeRef(MIRMappingHelper)».resolveIfRelative(«sourceJava», "«constraint.relativeResource»");
					if (resourceVURI != null) {
						result.addRootEObjectToSave(«targetJava», resourceVURI);
					}
				'''
			} else {
				constraint.relativeResource.claim[it == null]

				val manipulatedVariable = constraint.source.context
				val feature = constraint.source.feature

				eSetOrAdd(
					importHelper,
					getJavaExpressionThatReturns(localContext, manipulatedVariable, sourceMapping),
					feature,
					targetJava
				) + ";"
			}

		'''
			if (!«typeRef(MIRMappingHelper)».hasContainment(«targetJava», result)) {
				«createContainmentExpression»
			}
		'''
	}

	def dispatch String[] getEObjectsWithPossiblyChangedTUID(extension ImportHelper importHelper,
		Map<List<?>, String> localContext, Object object, EPackage pkg) {
		#[]
	}

	def dispatch String[] getEObjectsWithPossiblyChangedTUID(extension ImportHelper importHelper,
		Map<List<?>, String> localContext, AttributeEquivalenceExpression constraint, EPackage pkg) {

		val mapping = constraint.getContainerOfType(Mapping)
		val source = #[constraint.left, constraint.right].claimExactlyOneInPackage(pkg)
		val target = #[constraint.left, constraint.right].findFirst[it != source].requireNonNull

		#[getJavaExpressionThatReturns(localContext, target.context, mapping)]
	}

	def dispatch String[] getEObjectsWithPossiblyChangedTUID(extension ImportHelper importHelper,
		Map<List<?>, String> localContext, Object object) {
		#[]
	}

	def dispatch String[] getEObjectsWithPossiblyChangedTUID(extension ImportHelper importHelper,
		Map<List<?>, String> localContext, DefaultContainExpression constraint) {

		val target = constraint.target
		val sourceMapping = constraint.getContainerOfType(Mapping).requireNonNull
		val targetJava = getJavaExpressionThatReturns(localContext, target, sourceMapping)

		return #[targetJava]
	}
	
	def dispatch String[] getEObjectsWithPossiblyChangedTUID(extension ImportHelper importHelper,
		Map<List<?>, String> localContext, InExpression constraint) {

		val sourceMapping = constraint.getContainerOfType(Mapping).requireNonNull

		val target = constraint.target
		val targetJava = getJavaExpressionThatReturns(localContext, target, sourceMapping)
		
		val source = constraint.source.context
		val sourceJava = getJavaExpressionThatReturns(localContext, source, sourceMapping)

		return #[sourceJava, targetJava]
	}
}