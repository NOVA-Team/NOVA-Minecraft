package nova.minecraft.metadata;

import nova.core.component.Component;

/**
 * A component that represents Minecraft metadata values
 *
 * @author Calclavia
 */
public abstract class Metadata extends Component {

	public abstract int getMetadata();

	public abstract void setMetadata();
}
