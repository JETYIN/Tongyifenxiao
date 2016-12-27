package cn.qmz.tools.server;

import cn.qmz.tools.server.impl.NetReceiver.NetState;

public interface INetEventHandle {
	/**
	 * 网络状态码
	 * @param netCode
	 */
	void netState(NetState netCode);
}

