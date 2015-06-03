package com.calclavia.minecraft.redstone;

import nova.core.block.Block;
import nova.core.block.component.Connectable;

import java.util.function.Consumer;

/**
 * A node that can handle Redstone integer-based energy.
 *
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public abstract class Redstone extends Connectable<Redstone> {

	/**
	 * A callback when redstone power changes in this node.
	 * @param action - The callback method.
	 */
	public abstract void onInputPowerChange(Consumer<Redstone> action);

	/**
	 * @return Gets the strong input power to this node
	 */
	public abstract int getOutputStrongPower();

	/**
	 * @param power The power value between 0 to 16
	 * Sets the block to output strong Redstone power.
	 */
	public abstract void setOutputStrongPower(int power);

	/**
	 * @param side The direction of the power
	 * @return The Redstone power powered to a specific side of this block.
	 */
	public abstract int getWeakPower(int side);

	/**
	 * @return The greatest Redstone energy indirectly powering this block.
	 */
	public abstract int getInputWeakPower();

	/**
	 * @return The greatest Redstone energy directly powering this block.
	 */
	public abstract int getInputStrongPower();

	/**
	 * @return The greatest Redstone energy produced by this block.
	 */
	public abstract int getOutputWeakPower();

	/**
	 * Sets the block to output weak Redstone power.
	 */
	public abstract void setOutputWeakPower(int power);
}
