package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import java.util.Collections;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.AbstractCorrespondenceWrapper;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.ElementProvider;

public class EmptyDefaultWrapper extends AbstractWrapper {
	public enum Side { A, B }

	public EmptyDefaultWrapper(ElementProvider elementProvider) {
		super(Collections.emptyList(), elementProvider);
	}

	public static EmptyDefaultWrapper createHalfMappedCorrespondence(AbstractCorrespondenceWrapper correspondenceWrapper, Side side) {
		return new EmptyDefaultWrapper(() ->
			(side == Side.A)
			? correspondenceWrapper.getCorrespondence().getAs()
			: correspondenceWrapper.getCorrespondence().getBs());
	}
}
