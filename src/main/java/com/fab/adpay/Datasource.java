	package com.fab.adpay;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Datasource {
	private static final Logger LOGGER = LoggerFactory.getLogger(Datasource.class);
	private static HikariConfig config;
	private static HikariDataSource hikariDataSource;

	static {
		try {
			LOGGER.info("Loading DB properties");
			Properties hikariProperties = new Properties();
//			hikariProperties.setProperty("dataSourceClassName", "com.microsoft.sqlserver.jdbc.SQLServerDataSource");
			hikariProperties.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			hikariProperties.setProperty("jdbcUrl", System.getenv("jdbcUrl"));
			hikariProperties.setProperty("dataSource.user", System.getenv("dbUser"));
			String encryptedPassword = System.getenv("encryptedDbPassword");
			hikariProperties.setProperty("dataSource.password", CipherUtils.decrypt(encryptedPassword));
//			hikariProperties.setProperty("dataSource.databaseName", System.getenv("dbName"));
//			hikariProperties.setProperty("dataSource.portNumber", System.getenv("dbPort"));
//			hikariProperties.setProperty("dataSource.serverName", System.getenv("dbServerName"));
			config = new HikariConfig(hikariProperties);
		} catch (InvalidKeyException e) {
			LOGGER.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			LOGGER.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			LOGGER.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			LOGGER.error(e.getMessage());
		} catch (BadPaddingException e) {
			LOGGER.error(e.getMessage());
		}
		hikariDataSource = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		return hikariDataSource.getConnection();
	}

	private Datasource() {
	}

}
