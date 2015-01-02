package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StackPolicyBody
{
	@SerializedName("Statement")
	private List<Statement> statements;

	public List<Statement> getStatements()
	{
		return statements;
	}

	public void setStatements(List<Statement> statements)
	{
		this.statements = statements;
	}

}
