package nova.minecraft.wrapper.mc.forge.v1_11.wrapper.redstone.forward;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import nova.core.block.Block;
import nova.core.wrapper.mc.forge.v1_11.wrapper.block.world.BWWorld;
import nova.minecraft.redstone.Redstone;

import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * A Minecraft implementation that wraps Redstone to a Node
 * @author Calclavia
 */
//TODO: Create NodeVirtualRedstone (for MC blocks that are redstone, but don't implement NOVA)
public class FWRedstone extends Redstone {
	private boolean init = false;
	private int inputStrongPower = 0;
	private int inputWeakPower = 0;
	private int[] inputSidedWeakPower = { 0, 0, 0, 0, 0, 0 };
	private int outputStrongPower = 0;
	private int outputWeakPower = 0;
	private Consumer<Redstone> onPowerChange = redstone -> {
	};

	private Block block() {
		return (Block) getProvider();
	}

	@Override
	public void onProviderChange() {
		//Hook into the block's events.
		getProvider().events.on(Block.NeighborChangeEvent.class).bind(evt -> recache());
	}

	@Override
	public int getInputWeakPower() {
		return inputWeakPower;
	}

	@Override
	public int getInputStrongPower() {
		return inputStrongPower;
	}

	@Override
	public void onInputPowerChange(Consumer<Redstone> action) {
		onPowerChange = action;
	}

	@Override
	public int getOutputStrongPower() {
		if (!init) {
			recache();
		}
		return outputStrongPower;
	}

	@Override
	public void setOutputStrongPower(int power) {
		outputStrongPower = power;
		block().world().markChange(block().position());
	}

	@Override
	public int getWeakPower(int side) {
		if (!init) {
			recache();
		}
		return inputSidedWeakPower[side];
	}

	@Override
	public int getOutputWeakPower() {
		if (!init) {
			recache();
		}
		return outputWeakPower;
	}

	@Override
	public void setOutputWeakPower(int power) {
		outputWeakPower = power;
		block().world().markChange(block().position());
	}

	/**
	 * Recaches the Redstone state.
	 */
	public void recache() {
		init = true;
		boolean hasChanged = false;

		int newInputStrongPower = mcWorld().getStrongPower(new BlockPos(block().x(), block().y(), block().z()));

		if (inputStrongPower != newInputStrongPower) {
			inputStrongPower = newInputStrongPower;
			hasChanged = true;
		}

		int newInputWeakPower = mcWorld().isBlockIndirectlyGettingPowered(new BlockPos(block().x(), block().y(), block().z()));
		if (inputWeakPower != newInputWeakPower) {
			inputWeakPower = newInputWeakPower;
			hasChanged = true;
		}

		int[] newInputSidedWeakPower = IntStream.range(0, 6).map(i -> mcWorld().getRedstonePower(new BlockPos(block().x(), block().y(), block().z()), EnumFacing.values()[i])).toArray();

		if (inputSidedWeakPower != newInputSidedWeakPower) {
			inputSidedWeakPower = newInputSidedWeakPower;
			hasChanged = true;
		}

		if (hasChanged) {
			onPowerChange.accept(this);
		}
	}

	private net.minecraft.world.World mcWorld() {
		return ((BWWorld) block().world()).world();
	}
}
