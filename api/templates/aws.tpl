ec2-run-instances : 
    {
    default: {
       ami : "ami-xxxxx",
       instanceType : "t1-micro",
       keyPair : "$my-key-pair",
       securityGgroup : "$my-security-group"
       }
	}