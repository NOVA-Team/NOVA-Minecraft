package nova.minecraft.wrapper.mc.forge.v1_8.depmodules;

import nova.minecraft.wrapper.mc.forge.v1_8.wrapper.redstone.forward.FWRedstone;
import nova.minecraft.redstone.Redstone;
import se.jbee.inject.bind.BinderModule;

/**
 * @author Calclavia
 */
public class ComponentModule extends BinderModule {
	@Override
	protected void declare() {
		bind(Redstone.class).to(FWRedstone.class);
	}
}
