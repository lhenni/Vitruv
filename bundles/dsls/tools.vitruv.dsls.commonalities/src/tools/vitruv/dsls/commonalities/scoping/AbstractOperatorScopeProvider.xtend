package tools.vitruv.dsls.commonalities.scoping

import com.google.common.base.Predicate
import com.google.inject.Inject
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmStringAnnotationValue
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.common.types.xtext.AbstractTypeScopeProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.xtext.scoping.impl.FilteringScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorName

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

abstract class AbstractOperatorScopeProvider implements IGlobalScopeProvider {

	static val Logger logger = Logger.getLogger(AbstractOperatorScopeProvider)

	@Inject AbstractTypeScopeProvider typeScopeProvider
	@Inject IJvmTypeProvider.Factory typeProviderFactory
	@Inject extension IQualifiedNameConverter qualifiedNameConverter

	Map<QualifiedName, JvmDeclaredType> operators = null

	protected abstract def String getOperatorTypeName()

	protected abstract def Iterable<String> getDefaultOperatorImports()

	private def isNamespaceImport(String operatorImport) {
		return operatorImport.endsWith('.*')
	}

	private def getDefaultOperatorTypeImports() {
		return defaultOperatorImports.filter[!isNamespaceImport]
	}

	private def getDefaultOperatorNamespaceImports() {
		return defaultOperatorImports.filter[isNamespaceImport]
	}

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		val commonalityFile = resource.optionalContainedCommonalityFile
		if (commonalityFile === null) {
			throw new IllegalStateException("resource must contain a commonality file")
		}

		val importedTypes = (defaultOperatorTypeImports + commonalityFile.operatorTypeImports.map [
			importedType.getQualifiedName('.')
		]).map[toQualifiedName].toList
		val importedNamespaces = (defaultOperatorNamespaceImports + commonalityFile.operatorTypeImports.map [
			importedNamespace
		]).map[toQualifiedName.skipLast(1)].toList // skips the '.*' segment at the end of each namespace import

		val resourceSet = resource.resourceSet
		return new FilteringScope(resourceSet.allOperatorsScope) [
			val qualifiedName = (it.EObjectOrProxy as JvmDeclaredType).getQualifiedName('.').toQualifiedName
			if (importedTypes.exists[qualifiedName.equals(it)]) {
				return true
			}
			if (importedNamespaces.exists[qualifiedName.startsWith(it)]) {
				return true
			}
			return false
		]
	}

	def private getAllOperatorsScope(ResourceSet resourceSet) {
		if (operators === null) {
			operators = findOperators(resourceSet)
			logger.debug('''Found «operatorTypeName» operators: «operators.keySet.map[toString].toList»''')
		}
		return new SimpleScope(operators.entrySet.map[EObjectDescription.create(key, value)].toList)
	}

	private def findOperators(ResourceSet resourceSet) {
		// TODO This assumes that we only use this scope provider for a single ResourceSet. Remove the caching?
		// TODO Use OnChangeEvictingCache.CacheAdapter to cache the results for a specific resource?
		// See also DefaultGlobalScopeProvider
		val extension typeProvider = typeProviderFactory.findOrCreateTypeProvider(resourceSet)
		val typeScope = typeScopeProvider.createTypeScope(typeProvider, null)
		return newHashMap(typeScope.allElements
			.map[EcoreUtil2.resolve(it.EObjectOrProxy, resourceSet)]
			.filter(JvmDeclaredType)
			.filter[isOperatorType]
			.map [
				val operatorName = operatorName
				if (operatorName.isNullOrEmpty) {
					return null
				} else {
					operatorName.toQualifiedName -> it
				}
			].filterNull)
	}

	protected abstract def Class<?> getOperatorBaseType()

	protected def boolean isOperatorType(JvmDeclaredType type) {
		val operatorBaseTypeName = operatorBaseType.name
		return type.superTypes.exists [
			it.qualifiedName == operatorBaseTypeName
		] || type.superTypes.map[it.type].filter(JvmDeclaredType).exists[isOperatorType]
	}

	protected def getOperatorName(JvmDeclaredType operatorType) {
		val operatorNameAnnotationName = OperatorName.name
		operatorType.annotations
			.filter[annotation.qualifiedName == operatorNameAnnotationName].head
			?.explicitValues
			?.filter[operation.simpleName == 'value']
			?.filter(JvmStringAnnotationValue)?.head
			?.values?.head
	}
}