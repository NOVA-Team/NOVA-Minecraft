package com.calclavia.minecraft.redstone;

import com.calclavia.graph.api.Node;
import nova.core.block.Block;
import nova.core.util.Direction;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A node that can handle Redstone integer-based energy.
 *
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface Redstone extends Node<Redstone> {

	/**
	 * A callback when redstone power changes in this node.
	 * @param action - The callback method.
	 */
	void onInputPowerChange(Consumer<Redstone> action);

	/**
	 * Sets which side can connected
	 * @return Self
	 */
	Redstone setCanConnect(Function<Direction, Boolean> canConnect);

	/**
	 * @param dir Direction of connection
	 * @return Can this Redstone node connect to another?
	 */
	boolean canConnect(Direction dir);

	/**
	 * @return Gets the strong input power to this node
	 */
	int getOutputStrongPower();

	/**
	 * Sets the block to output strong Redstone power.
	 */
	void setOutputStrongPower(int power);

	/**
	 * @return The Redstone power powered to a specific side of this block.
	 */
	int getWeakPower(int side);

	/**
	 * @return The greatest Redstone energy indirectly powering this block.
	 */
	int getOutputWeakPower();

	/**
	 * Sets the block to output weak Redstone power.
	 */
	void setOutputWeakPower(int power);
}
