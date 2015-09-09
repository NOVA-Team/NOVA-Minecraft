package nova.minecraft.redstone;

import se.jbee.inject.bind.BinderModule;

/**
 * @author Calclavia
 */
public class ComponentModule extends BinderModule {
	@Override
	protected void declare() {
		bind(Redstone.class).to(NodeRedstone.class);
	}
}
