package nova.minecraft.redstone;

import nova.core.block.Block;
import nova.core.component.ComponentManager;
import nova.core.event.GlobalEvents;
import nova.core.loader.Loadable;
import nova.core.loader.NativeLoader;
import nova.core.world.World;
import nova.wrapper.mc18.util.WrapperEvents;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Optional;

/**
 * The Minecraft native loader
 * @author Calclavia
 */
@NativeLoader(forGame = "minecraft")
public class RedstoneAPI implements Loadable {

	private final ComponentManager componentManager;
	private final GlobalEvents events;

	public RedstoneAPI(ComponentManager componentManager, GlobalEvents events) {
		this.componentManager = componentManager;
		this.events = events;
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

		events.on(WrapperEvents.RedstoneConnectEvent.class).bind(evt -> evt.canConnect = getRedstoneNode(evt.world, evt.position).map(n -> n.canConnect.apply(null)).orElseGet(() -> false));

		events.on(WrapperEvents.StrongRedstoneEvent.class).bind(evt -> evt.power = getRedstoneNode(evt.world, evt.position).map(Redstone::getOutputStrongPower).orElseGet(() -> 0));

		events.on(WrapperEvents.WeakRedstoneEvent.class).bind(evt -> evt.power = getRedstoneNode(evt.world, evt.position).map(Redstone::getOutputWeakPower).orElseGet(() -> 0));
	}

	public Optional<Redstone> getRedstoneNode(World world, Vector3D pos) {
		Optional<Block> blockOptional = world.getBlock(pos);
		return blockOptional.flatMap(block -> block.getOp(Redstone.class));
	}
}