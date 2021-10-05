package info.vladkolm.utils.reflection;

public class ReflectUtilsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReflectUtilsException() {
		super();
	}

	public ReflectUtilsException(String message, Throwable cause,
								 boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReflectUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectUtilsException(String message) {
		super(message);
	}

	public ReflectUtilsException(Throwable cause) {
		super(cause);
	}

}
