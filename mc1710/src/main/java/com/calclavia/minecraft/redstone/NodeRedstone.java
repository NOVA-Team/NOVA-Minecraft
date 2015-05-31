package com.calclavia.minecraft.redstone;

import nova.core.block.Block;
import nova.core.component.Component;
import nova.core.util.Direction;
import nova.wrapper.mc1710.wrapper.block.world.BWWorld;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * A Minecraft implementation that wraps Redstone to a Node
 * @author Calclavia
 */
//TODO: Create NodeVirtualRedstone (for MC blocks that are redstone, but don't implement NOVA)
public class NodeRedstone extends Component implements Redstone {
	public final Block block;
	public Function<Direction, Boolean> canConnect = dir -> true;
	private boolean init = false;
	private int inputStrongPower = 0;
	private int inputWeakPower = 0;
	private int[] inputSidedWeakPower = { 0, 0, 0, 0, 0, 0 };
	private int outputStrongPower = 0;
	private int outputWeakPower = 0;
	private Consumer<Redstone> onPowerChange = redstone -> {
	};

	public NodeRedstone(Block block) {
		this.block = block;
		//Hook into the block's events.
		block.neighborChangeEvent.add(evt -> recache());
	}

	@Override
	public NodeRedstone setCanConnect(Function<Direction, Boolean> canConnect) {
		this.canConnect = canConnect;
		return this;
	}

	@Override
	public boolean canConnect(Direction dir) {
		return canConnect.apply(dir);
	}

	@Override
	public Set<Redstone> connections() {
		//TODO: Wrong implementation
		return Collections.emptySet();
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
		return inputStrongPower;
	}

	@Override
	public void setOutputStrongPower(int power) {
		outputStrongPower = power;
		block.world().markChange(block.position());
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
		return inputWeakPower;
	}

	@Override
	public void setOutputWeakPower(int power) {
		outputWeakPower = power;
		block.world().markChange(block.position());
	}

	/**
	 * Recaches the Redstone state.
	 */
	public void recache() {
		init = true;
		boolean hasChanged = false;

		int newInputStrongPower = mcWorld().getBlockPowerInput(block.x(), block.y(), block.z());

		if (inputStrongPower != newInputStrongPower) {
			inputStrongPower = newInputStrongPower;
			hasChanged = true;
		}

		int newInputWeakPower = mcWorld().getStrongestIndirectPower(block.x(), block.y(), block.z());
		if (inputWeakPower != newInputWeakPower) {
			inputWeakPower = newInputWeakPower;
			hasChanged = true;
		}

		int[] newInputSidedWeakPower = IntStream.range(0, 6).map(i -> mcWorld().getIndirectPowerLevelTo(block.x(), block.y(), block.z(), i)).toArray();

		if (inputSidedWeakPower != newInputSidedWeakPower) {
			inputSidedWeakPower = newInputSidedWeakPower;
			hasChanged = true;
		}

		if (hasChanged) {
			onPowerChange.accept(this);
		}
	}

	private net.minecraft.world.World mcWorld() {
		return ((BWWorld) block.world()).world();
	}
}
