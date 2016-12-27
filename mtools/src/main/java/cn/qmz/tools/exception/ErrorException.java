package cn.qmz.tools.exception;

public class ErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorException() {
		super();
	}

	public ErrorException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ErrorException(String detailMessage) {
		super(detailMessage);
	}

	public ErrorException(Throwable throwable) {
		super(throwable);
	}

}
