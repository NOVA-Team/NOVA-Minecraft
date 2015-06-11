package nova.minecraft.wrench;

import nova.core.component.Component;
import nova.core.entity.Entity;
import nova.core.event.EventBus;

/**
 * Add to blocks that can be configured (via wrench)
 * @author Calclavia
 */
public class Configurable extends Component {

	/**
	 * Called when the block is configured by a configurator.
	 */
	public EventBus<ConfigureEvent> onConfig = new EventBus<>();

	public static class ConfigureEvent {
		public final Entity player;

		public ConfigureEvent(Entity player) {
			this.player = player;
		}
	}
}
