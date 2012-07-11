package org.mule.transport.mina.events;

public abstract class MinaEvent {

	protected final String minaSessionId;

	public MinaEvent(String minaSessionId) {
		this.minaSessionId = minaSessionId;
	}

	/**
	 * @return the minaSessionId
	 */
	public String getMinaSessionId() {
		return minaSessionId;
	}

}