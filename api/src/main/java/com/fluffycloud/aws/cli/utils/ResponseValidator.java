package com.fluffycloud.aws.cli.utils;

import org.springframework.stereotype.Component;

import com.fluffycloud.aws.response.entity.AllocateAddressReponse;
import com.fluffycloud.aws.response.entity.CreateSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.CreateSubnetResponse;
import com.fluffycloud.aws.response.entity.CreateVPCResponse;
import com.fluffycloud.aws.response.entity.RunInstanceResponse;

@Component
public class ResponseValidator
{
	public boolean isValidCreateVPCResponse(final CreateVPCResponse createVPCResponse)
	{
		return null == createVPCResponse.getVpc() ? false : null == createVPCResponse.getVpc().getVpcId() ? false
				: true;
	}

	public boolean isValidCreateSecurityGroupResponse(final CreateSecurityGroupResponse createSecurityGroupResponse)
	{
		return createSecurityGroupResponse.isActionPerformed() ? null != createSecurityGroupResponse.getGroupId() ? true
				: false
				: false;
	}

	public boolean isValidCreateSubnetResponse(final CreateSubnetResponse createSubnetResponse)
	{
		return null == createSubnetResponse.getSubnet() ? false : null != createSubnetResponse.getSubnet()
				.getSubnetId() ? true : false;
	}

	public boolean isValidRunInstanceResponse(final RunInstanceResponse runInstanceResponse)
	{
		return null == runInstanceResponse.getInstances() ? false : null != runInstanceResponse.getInstances().get(0)
				.getInstanceId() ? true : false;
	}

	public boolean isValidAllocateAddressReponse(final AllocateAddressReponse allocateAddressReponse)
	{
		return null == allocateAddressReponse.getPublicIp() ? false : true;
	}
}
