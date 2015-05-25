package io.zonk.jbomberman.game.server;
import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;
import io.zonk.jbomberman.utils.RandomUtil;

public abstract class Map {
	protected char[][] map;
	
	private char get(int x, int y) {
		return map[y][x];
	}
	
	public void init(GameObjectManager manager, NetworkFacade network, Party party) {
		for (int y = 0; y < 13; ++y)
			for (int x = 0; x < 13; ++x) {
				Position position = new Position(x * 64, y * 64);
				Integer id;
				Action action;
				switch (get(x, y)) {
				case '#':
					id = IDGenerator.getId();
					manager.add(new GSolidBlock(position, id));
					action = new Action(ActionType.CREATE_SOLIDBLOCK,
							new Object[] { position, id });
					network.sendMessage(ActionSerializer.serialize(action));
					break;
				case ' ':
					if (RandomUtil.probability(70)) {
						id = IDGenerator.getId();
						manager.add(new GDestroyableBlock(position, id));
						action = new Action(ActionType.CREATE_DESTROYABLEBLOCK,
								new Object[] { position, id });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '1':
					if (party.get(1) != null) {
						manager.add(new GBomberman(position, 1));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 1 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '2':
					if (party.get(2) != null) {
						manager.add(new GBomberman(position, 2));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 2 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '3':
					if (party.get(3) != null) {
						manager.add(new GBomberman(position, 3));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 3 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				case '4':
					if (party.get(4) != null) {
						manager.add(new GBomberman(position, 4));
						action = new Action(ActionType.CREATE_BOMBERMAN,
								new Object[] { position, 4 });
						network.sendMessage(ActionSerializer.serialize(action));
					}
					break;
				default:
					break;
				}
			}
	}
}
