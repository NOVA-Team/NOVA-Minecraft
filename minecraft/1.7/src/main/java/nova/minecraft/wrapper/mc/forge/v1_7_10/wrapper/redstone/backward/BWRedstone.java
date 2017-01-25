/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nova.minecraft.wrapper.mc.forge.v1_7_10.wrapper.redstone.backward;

import nova.core.block.Block;
import nova.core.util.Direction;
import nova.core.wrapper.mc.forge.v17.wrapper.block.backward.BWBlock;
import nova.core.wrapper.mc.forge.v17.wrapper.block.world.BWWorld;
import nova.minecraft.redstone.Redstone;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 *
 * @author ExE Boss
 */
public class BWRedstone extends Redstone {

	private BWBlock block() {
		return (BWBlock) getProvider();
	}

	private net.minecraft.world.World mcWorld() {
		return ((BWWorld) block().world()).world();
	}

	public BWRedstone() {
		this.canConnect = (Redstone otherRedstone) -> {
			Block otherBlock = ((Block) otherRedstone.getProvider());
			Vector3D otherPos = otherBlock.position();
			Vector3D thisPos = block().position();
			return block().mcBlock.canConnectRedstone(mcWorld(), otherBlock.x(), otherBlock.y(), otherBlock.z(), Direction.fromVector(thisPos.crossProduct(otherPos)).ordinal());
		};
	}

	@Override
	public void onInputPowerChange(Consumer<Redstone> action) {}

	@Override
	public int getOutputStrongPower() {
		return IntStream.range(0, 6).map(side -> block().mcBlock.isProvidingStrongPower(mcWorld(), block().x(), block().y(), block().z(), side)).max().orElse(0);
	}

	@Override
	public void setOutputStrongPower(int power) {}

	@Override
	public int getWeakPower(int side) {
		return mcWorld().getIndirectPowerLevelTo(block().x(), block().y(), block().z(), side);
	}

	@Override
	public int getInputWeakPower() {
		return mcWorld().getStrongestIndirectPower(block().x(), block().y(), block().z());
	}

	@Override
	public int getInputStrongPower() {
		return mcWorld().getBlockPowerInput(block().x(), block().y(), block().z());
	}

	@Override
	public int getOutputWeakPower() {
		return IntStream.range(0, 6).map(side -> block().mcBlock.isProvidingWeakPower(mcWorld(), block().x(), block().y(), block().z(), side)).max().orElse(0);
	}

	@Override
	public void setOutputWeakPower(int power) {}
}
