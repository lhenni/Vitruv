package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider

/**
 * Factory for {@link UserInteraction}s and {@link InteractionResultProvider}.
 * 
 * @author Heiko Klare
 * @author Nico Kopp
 */
class UserInteractionFactoryImpl implements UserInteractionFactory {
	
	private static var UserInteractionFactory instance;

	private new() {
	}


	/**
	 * Returns the singleton instance of the {@link UserInteractionFactoryImpl}
	 */
	static def UserInteractionFactory getInstance() {
		if (instance === null) {
			instance = new UserInteractionFactoryImpl();
		}
		return instance;
	}

	
	/**
	 * Creates a {@link InternalUserInteractor} with the given {@link InteractionResultProvider}.
	 * 
	 * @param interactionResultProvider - the provider for results of an interaction
	 */
	override InternalUserInteractor createUserInteractor(InteractionResultProvider interactionResultProvider) {
		return new UserInteractorImpl(interactionResultProvider);
	}

	/**
	 * Creates a {@link InternalUserInteractor} with the a result provider based on UI dialogs.
	 */
	override InternalUserInteractor createDialogUserInteractor() {
		return new UserInteractorImpl(createDialogInteractionResultProvider());
	}

	/**
	 * Creates a dummy {@link InternalUserInteractor}, which does nothing and would throw an exception if an interaction is requested.
	 */
	override InternalUserInteractor createDummyUserInteractor() {
		return new UserInteractorImpl(createPredefinedInteractionResultProvider(null));
	}

	/**
	 * Creates a {@link InteralResultProvider} based on UI dialogs.
	 */
	override InteractionResultProvider createDialogInteractionResultProvider() {
		return new DialogInteractionResultProviderImpl();
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	override PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider) {
		return new PredefinedInteractionResultProviderImpl(fallbackResultProvider);
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which the given {@link UserInteractionBase}s are used as predefined inputs. 
	 * The given {@link InteractionResultProvider} is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	override PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, UserInteractionBase... predefinedUserInputs) {
		val result = createPredefinedInteractionResultProvider(fallbackResultProvider);
		result.addUserInteractions(predefinedUserInputs);
		return result;
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined and which simulates a think time everytime an 
	 * interaction is performed. This can, for example, be performed for performance evaluations. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 * @param minWaittime - the minimum time to wait in milliseconds
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	override PredefinedInteractionResultProvider createPredefinedThinktimeSimulatingInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, int minWaittime, int maxWaittime) {
		return new PredefinedThinktimeSimulatingInteractionResultProviderImpl(fallbackResultProvider, minWaittime,
			maxWaittime);
	}
	
}
