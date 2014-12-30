package com.fluffycloud.aws.constants;

public enum Action
{
	/*--Ec2 Commands--*/
	DESCRIBEROUTETABLES("describe-route-tables"),
	DESCRIBEVPCS("describe-vpcs"),
	CREATETAGS("create-tags"),
	DESCRIBESUBNETS("describe-subnets"),
	ATTACHVOLUME("attach-volume"),
	MODIFYINSTANCEATTRIBUTE("modify-instance-attribute"),
	DESCRIBEDHCPOPTIONS("describe-dhcp-options"),
	RESETIMAGEATTRIBUTE("reset-image-attribute"),
	CREATEROUTE("create-route"),
	REPLACENETWORKACLENTRY("replace-network-acl-entry"),
	REBOOTINSTANCES("reboot-instances"),
	ENABLEVOLUMEIO("enable-volume-io"),
	DESCRIBEIMAGEATTRIBUTE("describe-image-attribute"),
	RELEASEADDRESS("release-address"),
	GETPASSWORDDATA("get-password-data"),
	DELETEKEYPAIR("delete-key-pair"),
	CREATESECURITYGROUP("create-security-group"),
	AUTHORIZESECURITYGROUPINGRESS("authorize-security-group-ingress"),
	CREATEVPC("create-vpc"),
	DESCRIBEVOLUMEATTRIBUTE("describe-volume-attribute"),
	ASSOCIATEADDRESS("associate-address"),
	RESETINSTANCEATTRIBUTE("reset-instance-attribute"),
	CREATENETWORKACL("create-network-acl"),
	DESCRIBESECURITYGROUPS("describe-security-groups"),
	DESCRIBEKEYPAIRS("describe-key-pairs"),
	CREATEIMAGE("create-image"),
	DELETENETWORKACL("delete-network-acl"),
	RUNINSTANCES("run-instances"),
	CREATESUBNET("create-subnet"),
	CREATEINTERNETGATEWAY("create-internet-gateway"),
	REPLACEROUTE("replace-route"),
	DELETEROUTETABLE("delete-route-table"),
	DELETESECURITYGROUP("delete-security-group"),
	DELETESNAPSHOT("delete-snapshot"),
	DEREGISTERIMAGE("deregister-image"),
	MODIFYVOLUMEATTRIBUTE("modify-volume-attribute"),
	DESCRIBEINSTANCESTATUS("describe-instance-status"),
	CREATEDHCPOPTIONS("create-dhcp-options"),
	AUTHORIZESECURITYGROUPEGRESS("authorize-security-group-egress"),
	DESCRIBEINTERNETGATEWAYS("describe-internet-gateways"),
	DESCRIBESNAPSHOTATTRIBUTE("describe-snapshot-attribute"),
	DESCRIBESNAPSHOTS("describe-snapshots"),
	DISABLEVGWROUTEPROPAGATION("disable-vgw-route-propagation"),
	DESCRIBEINSTANCEATTRIBUTE("describe-instance-attribute"),
	ATTACHINTERNETGATEWAY("attach-internet-gateway"),
	REVOKESECURITYGROUPEGRESS("revoke-security-group-egress"),
	ASSOCIATEDHCPOPTIONS("associate-dhcp-options"),
	STOPINSTANCES("stop-instances"),
	ASSOCIATEROUTETABLE("associate-route-table"),
	REPLACEROUTETABLEASSOCIATION("replace-route-table-association"),
	DELETEINTERNETGATEWAY("delete-internet-gateway"),
	STARTINSTANCES("start-instances"),
	DESCRIBEINSTANCES("describe-instances"),
	DESCRIBENETWORKACLS("describe-network-acls"),
	DESCRIBEIMAGES("describe-images"),
	DISASSOCIATEADDRESS("disassociate-address"),
	REVOKESECURITYGROUPINGRESS("revoke-security-group-ingress"),
	DELETEDHCPOPTIONS("delete-dhcp-options"),
	DETACHINTERNETGATEWAY("detach-internet-gateway"),
	GETCONSOLEOUTPUT("get-console-output"),
	DESCRIBEVOLUMESTATUS("describe-volume-status"),
	DETACHVOLUME("detach-volume"),
	COPYSNAPSHOT("copy-snapshot"),
	DELETEVOLUME("delete-volume"),
	DESCRIBEVOLUMES("describe-volumes"),
	ENABLEVGWROUTEPROPAGATION("enable-vgw-route-propagation"),
	REGISTERIMAGE("register-image"),
	CREATEROUTETABLE("create-route-table"),
	TERMINATEINSTANCES("terminate-instances"),
	DISASSOCIATEROUTETABLE("disassociate-route-table"),
	CREATESNAPSHOT("create-snapshot"),
	DESCRIBEADDRESSES("describe-addresses"),
	MODIFYIMAGEATTRIBUTE("modify-image-attribute"),
	CREATEVOLUME("create-volume"),
	CREATEKEYPAIR("create-key-pair"),
	ALLOCATEADDRESS("allocate-address"),
	REPLACENETWORKACLASSOCIATION("replace-network-acl-association"),
	DELETEROUTE("delete-route"),
	DELETENETWORKACLENTRY("delete-network-acl-entry"),
	COPYIMAGE("copy-image"),
	IMPORTKEYPAIR("import-key-pair"),
	/*--Ec2 Commands--*/
	
	/*--CloudFormation Commands--*/
	CREATESTACK("create-stack"),
//	DELETESTACK("delete-stack"),
	DESCRIBESTACKEVENTS("describe-stack-events"),
	DESCRIBESTACKRESOURCES("describe-stack-resources"),
	DESCRIBESTACKRESOURCE("describe-stack-resource"),
	DESCRIBESTACKS("describe-stacks"),
	LISTSTACKRESOURCES("list-stack-resources"),
	LISTSTACKS("list-stacks");
	/*--CloudFormation Commands--*/


	private String action;

	private Action(String action)
	{
		this.action = action;
	}

	public String getAction()
	{
		return action;
	}
}
