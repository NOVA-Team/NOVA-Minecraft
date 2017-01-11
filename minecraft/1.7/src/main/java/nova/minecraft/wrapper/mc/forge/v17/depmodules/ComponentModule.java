package nova.minecraft.wrapper.mc.forge.v17.depmodules;

import nova.minecraft.redstone.Redstone;
import nova.minecraft.wrapper.mc.forge.v17.wrapper.redstone.forward.FWRedstone;
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
