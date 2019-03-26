package tools.vitruv.framework.variability

import org.eclipse.emf.ecore.resource.Resource
import java.io.IOException
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List
import org.eclipse.emf.common.util.EList
import variabilitymodel.Feature
import variabilitymodel.AttributedFeatureModel
import variabilitymodel.VariabilitymodelFactory
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

class VariabilityModelImpl implements VariabilityModel {
	
	AttributedFeatureModel root;

	new(Resource variabilityResource) {
		root = loadVaVe(variabilityResource)
	}
	
	def private AttributedFeatureModel loadVaVe(Resource vaveResource) {
		try {
			vaveResource.load(null)
		} catch (IOException e) {
		  }
		root = EcoreResourceBridge::getResourceContentRootIfUnique(vaveResource)?.dynamicCast(AttributedFeatureModel,
			"variability model")
		if (root === null) {
			root = VariabilitymodelFactory::eINSTANCE.createAttributedFeatureModel()
			vaveResource.getContents().add(root) // add root to the vave model
		}
		return root
	}
	
	override public addInitialVersion(List<PropagatedChange> propagatedChange) {
		val initVersion = VariabilitymodelFactory::eINSTANCE.createVersion
		initVersion.change.addAll((propagatedChange.toArray) as EList)		
	}
	
}