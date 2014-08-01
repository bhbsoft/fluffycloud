#create instance
#Valid values: m1.small | m1.medium | m1.large | m1.xlarge | m3.medium | m3.large | m3.xlarge | m3.2xlarge | c1.medium | c1.xlarge | c3.large | c3.xlarge | c3.2xlarge | c3.4xlarge | c3.8xlarge | cc2.8xlarge | m2.xlarge | m2.2xlarge | m2.4xlarge | r3.large | r3.xlarge | r3.2xlarge | r3.4xlarge | r3.8xlarge | cr1.8xlarge | hi1.4xlarge | hs1.8xlarge | i2.xlarge | i2.2xlarge | i2.4xlarge | i2.8xlarge | t1.micro | cg1.4xlarge | g2.2xlarge
#Default: m1.small

## remove this need to build data model with json basec on values to create as defaults
  "baseContext" : {
    "providerId" : "aws",
    "regionId" : "us-east-1",
    "accessKey" : "AKIAINUIAUWVXPBJADDQ",
    "accountId" : "354697460519"
  },
  
  "id" : "i-e46270ce",
  "instanceName" : null,
  "machineImageId" : "ami-484d8520",
  "machineImageQuery" : null,
  "machineTypeId" : "t1.micro",
  "availabilityZoneId" : "us-east-1c",
  "availabilityZoneIndex" : null,
  "keypairId" : null,
  "privateIpAddress" : null,
  "userData" : "",
  "subnetId" : null,
  "ioOptimized" : false,
  "ipForwardingAllowed" : false,
  "serviceAccountId" : null,
  "associatePublicIpAddress" : false,
  "placementGroupId" : null,
  "firewalls" : null,
  "labels" : null,
  "volumes" : [ ],
  "provisioner" : null,
  "resourceType" : "com.weather.grid.model.compute.Instance",
  "operationType" : "CREATE"
}
##

#ec2-run-instances ami-xxxxxxxx -t t1.micro -k my-key-pair -g my-security-group
ec2-run-instances $ami -t $instanceType -k $keyPair -g $securityGgroup
