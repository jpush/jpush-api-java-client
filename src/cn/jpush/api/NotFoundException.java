package cn.jpush.api;

/**
 * 
 * HTTP 404 status code ("not found")
 * 
 * @see http://en.wikipedia.org/wiki/HTTP_404
 * 
 */
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4332595867950578616L;

	public NotFoundException(String msg) {
		super(msg);
	}

}