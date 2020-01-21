package com.ai.st.microservice.ili.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Ili2pgIntegrationCadastreRegistrationDto", description = "Ili2pg Integration Cadastre-Registration")
public class Ili2pgIntegrationCadastreRegistrationWithoutFilesDto implements Serializable {

	private static final long serialVersionUID = -2379043053790590513L;

	@ApiModelProperty(required = true, notes = "Database host")
	private String databaseHost;

	@ApiModelProperty(required = true, notes = "Database port")
	private String databasePort;

	@ApiModelProperty(required = true, notes = "Database schema")
	private String databaseSchema;

	@ApiModelProperty(required = true, notes = "Database username")
	private String databaseUsername;

	@ApiModelProperty(required = true, notes = "Database password")
	private String databasePassword;

	@ApiModelProperty(required = true, notes = "Database name")
	private String databaseName;

	@ApiModelProperty(required = true, notes = "Cadastre - Path file XTF")
	private String cadastrePathXTF;

	@ApiModelProperty(required = true, notes = "Registration - Path file XTF")
	private String registrationPathXTF;

	public Ili2pgIntegrationCadastreRegistrationWithoutFilesDto() {

	}

	public String getDatabaseHost() {
		return databaseHost;
	}

	public void setDatabaseHost(String databaseHost) {
		this.databaseHost = databaseHost;
	}

	public String getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

	public String getDatabaseSchema() {
		return databaseSchema;
	}

	public void setDatabaseSchema(String databaseSchema) {
		this.databaseSchema = databaseSchema;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getCadastrePathXTF() {
		return cadastrePathXTF;
	}

	public void setCadastrePathXTF(String cadastrePathXTF) {
		this.cadastrePathXTF = cadastrePathXTF;
	}

	public String getRegistrationPathXTF() {
		return registrationPathXTF;
	}

	public void setRegistrationPathXTF(String registrationPathXTF) {
		this.registrationPathXTF = registrationPathXTF;
	}

}