package jbomberman.network;

public interface NetworkFacade {
	public void connect(String hostname);
	public void close();
	public boolean isOpen();
	public void sendMessage(byte[] message);
	public byte[] receiveMessage();
	public byte[] receiveMessage(int timeout);
}
