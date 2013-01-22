package com.gamelib.transport.xmpp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class XMPPGameController implements PacketListener {

	private static XMPPGameController instance;

	private XMPPGameListener listener;
	
	private UIListener uiListener;
	
	public static final int GAME_CHAT = 1;
	public static final int GAME_MOVE = 2;
	public static final int GAME_START_REQUEST = 3;
	public static final int GAME_START_ACCEPTED = 4;
	public static final int GAME_DRAW_REQUEST = 5;
	public static final int GAME_DRAW_ACCEPTED = 6;
	public static final int GAME_ABORT_REQUEST = 7;
	public static final int GAME_ABORT_ACCEPTED = 8;
	public static final int GAME_RESIGN = 10;
	public static final int GAME_UI_CLOSED = 11;

	public static final String GAME_COMMAND_PAYLOAD = "gcmp";
	public static final String GAME_COMMAND = "gcm";
	public static final String GAME_WHITE_PLAYER = "wp";
	public static final String GAME_BLACK_PLAYER = "bp";

	private static final String TAG = "XMPPGameController";

	private XMPPGameController() {

		if (XMPPConnectionManager.getInstance().getXmppConnection() != null) {
			XMPPConnectionManager
					.getInstance()
					.getXmppConnection()
					.addPacketListener(this,
							new PacketTypeFilter(Message.class));
		}
	}

	public static synchronized XMPPGameController getController(
			boolean initialize) {
		if (initialize) {
			XMPPGameController.instance = new XMPPGameController();
		}

		return XMPPGameController.instance;
	}

	public static XMPPGameController getController() {
		if (XMPPGameController.instance == null) {
			return getController(true);
		} else {
			return getController(false);
		}
	}

	@Override
	public void processPacket(Packet arg0) {
		if (arg0 instanceof Message) {
			Message message = (Message) arg0;
			Object gameCommand = message.getProperty(GAME_COMMAND);

			if (gameCommand != null) {
				int command = Integer.valueOf(gameCommand.toString());

				switch (command) {
				case GAME_START_REQUEST:
					this.handleGameStartRequest(arg0.getFrom(),
							message.getProperty(GAME_WHITE_PLAYER),
							message.getProperty(GAME_BLACK_PLAYER));
					break;
				case GAME_START_ACCEPTED:
					this.handleGameStarted(arg0.getFrom(),
							message.getProperty(GAME_WHITE_PLAYER),
							message.getProperty(GAME_BLACK_PLAYER));
					break;
				case GAME_ABORT_REQUEST:
					this.handleGameAbortRequest(arg0.getFrom());
					break;
				case GAME_ABORT_ACCEPTED:
					this.handleGameAbortAccepted(arg0.getFrom());
					break;
				case GAME_MOVE:
					this.handleGameMove(arg0.getFrom(),
							message.getProperty(GAME_COMMAND_PAYLOAD));
					break;
				case GAME_CHAT:
					this.handleGameChat(arg0.getFrom(),
							message.getProperty(GAME_COMMAND_PAYLOAD));
					break;
				case GAME_DRAW_REQUEST:
					this.handleGameDrawRequest(arg0.getFrom());
					break;
				case GAME_DRAW_ACCEPTED:
					this.handleGameDrawAccepted(arg0.getFrom());
					break;
				case GAME_RESIGN:
					this.handleGameResign(arg0.getFrom());
					break;

				default:
			
					break;
				}
			} else {
			
			}
		} else {
			
		}
	}

	public void setGameListener(XMPPGameListener listener) {
		this.listener = listener;
	}

	public void removeGameListener(XMPPGameListener listener) {
		this.listener = null;
	}

	public UIListener getUiListener() {
		return uiListener;
	}

	public void setUiListener(UIListener uiListener) {
		this.uiListener = uiListener;
	}
	
	public void sendGameStartAcceptedCommand(String jabberID,
			String whitePlayer, String blackPlayer) {
		
		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_START_ACCEPTED);
		message.setProperty(GAME_WHITE_PLAYER, whitePlayer);
		message.setProperty(GAME_BLACK_PLAYER, blackPlayer);

		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}
	
	public void sendGameStartRequestCommand(String jabberID,
			String whitePlayer, String blackPlayer) {		

		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_START_REQUEST);
		message.setProperty(GAME_WHITE_PLAYER, whitePlayer);
		message.setProperty(GAME_BLACK_PLAYER, blackPlayer);

		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}

	public void sendGameMoveCommand(String jabberID, String move) {

		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_MOVE);
		message.setProperty(GAME_COMMAND_PAYLOAD, move);
		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}

	public void sendGameAbortRequestCommand(String jabberID) {

		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_ABORT_REQUEST);
		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}

	public void sendGameResignCommand(String jabberID) {		

		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_RESIGN);
		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}

	public void sendGameChatCommand(String jabberID, String text) {

		Message message = new Message();
		message.setTo(jabberID);
		message.setProperty(GAME_COMMAND, GAME_CHAT);
		message.setProperty(GAME_COMMAND_PAYLOAD, text);
		XMPPConnectionManager.getInstance().getXmppConnection()
				.sendPacket(message);
	}

	private void handleGameResign(String from) {

		if (this.listener != null) {
			listener.resignReceived(from);
		} else {
			
		}

	}

	private void handleGameDrawAccepted(String from) {

		if (this.listener != null) {
			this.listener.drawAccepted(from);
		} else {
			
		}
	}

	private void handleGameDrawRequest(String from) {
		

		if (this.listener != null) {
			listener.drawRequestReceived(from);
		} else {
		
		}
	}

	private void handleGameChat(String from, Object commandPayload) {
		
		if (commandPayload != null) {

			if (this.listener != null) {
				listener.chatReceived(from, commandPayload.toString());
			} else {
		
			}

		} else {
		
		}

	}

	private void handleGameMove(String from, Object commandPayload) {
		
		if (commandPayload != null) {

			if (this.listener != null) {
				listener.moveReceived(from, commandPayload.toString());
			} else {
		
			}
		} else {
		
		}
	}

	private void handleGameAbortAccepted(String from) {
		
		if (this.listener != null) {
			listener.abortRequestAccepted(from);
		} else {
		
		}
	}

	private void handleGameAbortRequest(String from) {
		

		if (this.listener != null) {
			listener.abortRequestReceived(from);
		} else {
		
		}
	}

	private void handleGameStarted(String from, Object whitePlayer,
			Object blackPlayer) {

		if (whitePlayer != null && blackPlayer != null) {
			if (this.listener != null) {
				listener.gameStarted(whitePlayer.toString(),
						blackPlayer.toString());
			} else {
			
			}
		} else {
			
		}
	}

	private void handleGameStartRequest(String from, Object whitePlayer,
			Object blackPlayer) {

		if (whitePlayer != null && blackPlayer != null) {
			

			if (this.listener != null) {			
				listener.startGameRequestReceived(from, whitePlayer.toString(),
						blackPlayer.toString());
			} else {
				
				if (this.uiListener != null) {
					this.uiListener.gameStartRequest(from, whitePlayer.toString(), blackPlayer.toString());					
				}
				else{
					
				}
			}
		} else {
			
		}
	}
}
