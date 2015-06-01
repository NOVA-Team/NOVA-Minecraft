package com.calclavia.minecraft.redstone;

import nova.core.block.Block;
import nova.core.component.ComponentManager;
import nova.core.loader.Loadable;
import nova.core.loader.NativeLoader;
import nova.core.util.transform.vector.Vector3i;
import nova.core.world.World;
import nova.wrapper.mc1710.util.WrapperEventManager;

import java.util.Optional;

/**
 * The Minecraft native loader
 * @author Calclavia
 */
@NativeLoader(forGame = "minecraft")
public class RedstoneAPI implements Loadable {

	private final ComponentManager componentManager;

	public RedstoneAPI(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}

	@Override
	public void preInit() {
		//TODO: -100000 style points
		Block dummy = new Block() {
			@Override
			public String getID() {
				return "dummy";
			}
		};

		//Registers Redstone Node
		componentManager.register(args -> args.length > 0 ? new NodeRedstone((Block) args[0]) : new NodeRedstone(dummy));

		WrapperEventManager.instance.onCanConnect.add(evt -> evt.canConnect = getRedstoneNode(evt.world, evt.position).map(n -> n.canConnect.apply(null)).orElseGet(() -> false));

		WrapperEventManager.instance.onStrongPower.add(evt -> evt.power = getRedstoneNode(evt.world, evt.position).map(n -> n.getOutputStrongPower()).orElseGet(() -> 0));

		WrapperEventManager.instance.onWeakPower.add(evt -> evt.power = getRedstoneNode(evt.world, evt.position).map(n -> n.getOutputWeakPower()).orElseGet(() -> 0));
	}

	public Optional<Redstone> getRedstoneNode(World world, Vector3i pos) {
		Optional<Block> blockOptional = world.getBlock(pos);
		return blockOptional.map(block -> block.get(Redstone.class));
	}
}
