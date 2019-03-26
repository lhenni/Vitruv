package tools.vitruv.framework.variability

import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List

interface VariabilityModel {
	def public addInitialVersion(List<PropagatedChange> propagatedChange) {
	}
	
	def public addVersion(List<PropagatedChange> propagatedChange) {
	}
	
	def public addVariationPoint(String variationpoint) {
	}
	
	def public addVariant(List<PropagatedChange> propagatedChange) {	
	}
}