package tools.vitruv.framework.userinteraction;

import tools.vitruv.framework.change.interaction.UserInteractionBase;

/**
 * Factory for {@link UserInteraction}s and {@link InteractionResultProvider}.
 * 
 * @author Heiko Klare
 * @author Nico Kopp
 */
public interface UserInteractionFactory {
	
	
	/**
	 * Creates a {@link InternalUserInteractor} with the given {@link InteractionResultProvider}.
	 * 
	 * @param interactionResultProvider - the provider for results of an interaction
	 */
	public InternalUserInteractor createUserInteractor(InteractionResultProvider interactionResultProvider);

	/**
	 * Creates a {@link InternalUserInteractor} with the a result provider based on UI dialogs.
	 */
	public InternalUserInteractor createDialogUserInteractor();

	/**
	 * Creates a dummy {@link InternalUserInteractor}, which does nothing and would throw an exception if an interaction is requested.
	 */
	public InternalUserInteractor createDummyUserInteractor();

	/**
	 * Creates a {@link InteralResultProvider} based on UI dialogs.
	 */
	public InteractionResultProvider createDialogInteractionResultProvider();

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	public PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider);

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which the given {@link UserInteractionBase}s are used as predefined inputs. 
	 * The given {@link InteractionResultProvider} is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	public PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, UserInteractionBase... predefinedUserInputs);

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined and which simulates a think time everytime an 
	 * interaction is performed. This can, for example, be performed for performance evaluations. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 * @param minWaittime - the minimum time to wait in milliseconds
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	public PredefinedInteractionResultProvider createPredefinedThinktimeSimulatingInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, int minWaittime, int maxWaittime);

}
