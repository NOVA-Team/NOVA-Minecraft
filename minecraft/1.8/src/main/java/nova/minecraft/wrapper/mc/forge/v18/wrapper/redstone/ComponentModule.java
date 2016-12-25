package nova.minecraft.wrapper.mc.forge.v18.wrapper.redstone;

import nova.minecraft.redstone.Redstone;
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
