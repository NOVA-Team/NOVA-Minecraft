/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nova.minecraft.wrapper.mc.forge.v1_8.wrapper.redstone.backward;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nova.core.block.Block;
import nova.core.util.Direction;
import nova.core.wrapper.mc.forge.v18.wrapper.block.backward.BWBlock;
import nova.core.wrapper.mc.forge.v18.wrapper.block.world.BWWorld;
import nova.internal.core.Game;
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
			Block otherBlock = ((Block)otherRedstone.getProvider());
			Vector3D otherPos = otherBlock.position();
			Vector3D thisPos = block().position();
			return block().mcBlock.canConnectRedstone(mcWorld(), Game.natives().toNative(thisPos), Game.natives().toNative(Direction.fromVector(thisPos.crossProduct(otherPos))));
		};
	}

	@Override
	public void onInputPowerChange(Consumer<Redstone> action) {}

	@Override
	public int getOutputStrongPower() {
		BlockPos pos = new BlockPos(block().x(), block().y(), block().z());
		return IntStream.range(0, 6).map(side -> block().mcBlock.isProvidingStrongPower(mcWorld(), pos, mcWorld().getBlockState(pos), EnumFacing.values()[side])).max().orElse(0);
	}

	@Override
	public void setOutputStrongPower(int power) {}

	@Override
	public int getWeakPower(int side) {
		return mcWorld().getRedstonePower(new BlockPos(block().x(), block().y(), block().z()), EnumFacing.values()[side]);
	}

	@Override
	public int getInputWeakPower() {
		return mcWorld().isBlockIndirectlyGettingPowered(new BlockPos(block().x(), block().y(), block().z()));
	}

	@Override
	public int getInputStrongPower() {
		return mcWorld().getStrongPower(new BlockPos(block().x(), block().y(), block().z()));
	}

	@Override
	public int getOutputWeakPower() {
		BlockPos pos = new BlockPos(block().x(), block().y(), block().z());
		return IntStream.range(0, 6).map(side -> block().mcBlock.isProvidingWeakPower(mcWorld(), pos, mcWorld().getBlockState(pos), EnumFacing.values()[side])).max().orElse(0);
	}

	@Override
	public void setOutputWeakPower(int power) {}
}
