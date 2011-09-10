package suncertify.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import suncertify.log.AppLogger;

/**
 * Logger to use into <code>suncertify.gui</code> package.
 * 
 * @author Leo Gutierrez
 */
public class GUILogger {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = AppLogger.getLogger("suncertify.gui");
	
	/**
	 * Logs a method entry. 
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of method that is being entered.
	 */
	public static void entering(final String sourceClass, 
			final String sourceMethod) {
		LOGGER.entering(sourceClass, sourceMethod);
	}
	
	/**
	 * Logs a method entry.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of method that is being entered.
	 * @param params Array of parameters to the method being entered.
	 */
	public static void entering(final String sourceClass, 
			final String sourceMethod, final Object ... params) {
		LOGGER.entering(sourceClass, sourceMethod, params);
	}
	
	/**
	 * Logs a method return.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 */
	public static void exiting(final String sourceClass, 
			final String sourceMethod) {
		LOGGER.exiting(sourceClass, sourceMethod);
	}
	
	/**
	 * Logs a method return.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param result Object that is being returned.
	 */
	public static void exiting(final String sourceClass, 
			final String sourceMethod, final Object result) {
		LOGGER.exiting(sourceClass, sourceMethod, result);
	}
	
	/**
	 * Logs a message with the level FINEST.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void finest(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.FINEST, sourceClass, sourceMethod, msg);
	}
	
	/**
	 * Logs a message with the level FINER.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void finer(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.FINER, sourceClass, sourceMethod, msg);
	}
	
	/**
	 * Logs a message with the level FINE.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void fine(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.FINE, sourceClass, sourceMethod, msg);
	}
	
	/**
	 * Logs a message with the level INFO.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void info(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.INFO, sourceClass, sourceMethod, msg);
	}
	
	/**
	 * Logs a message with the level WARNING.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void warning(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.WARNING, sourceClass, sourceMethod, msg);
	}
	
	/**
	 * Logs a message with the level SEVERE.
	 * 
	 * @param sourceClass Name of class that issued the logging request.
	 * @param sourceMethod Name of the method.
	 * @param msg Message to log.
	 */
	public static void severe(final String sourceClass, 
			final String sourceMethod, final String msg) {
		LOGGER.logp(Level.SEVERE, sourceClass, sourceMethod, msg);
	}
	
}
