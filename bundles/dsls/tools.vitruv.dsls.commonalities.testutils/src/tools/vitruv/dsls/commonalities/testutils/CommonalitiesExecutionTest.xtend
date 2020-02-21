package tools.vitruv.dsls.commonalities.testutils

import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Root
import tools.vitruv.testutils.VitruviusApplicationTest

abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}

	/*def protected Resource intermediateResource(String conceptName) {
		// TODO move metadata key calculation into helper class in commonalities (or runtime) project
		return metadataResource(#["commonalities", conceptName + "." + conceptName.toFirstLower])
	}

	def protected Root intermediateRoot(String conceptName) {
		val contents = intermediateResource(conceptName).contents
		if (contents.size < 1) {
			throw new IllegalStateException("Intermediate model for concept '" + conceptName + "' has no root")
		}
		return contents.get(0) as Root
	}*/

	// TODO better support for intermediate model matching
}
