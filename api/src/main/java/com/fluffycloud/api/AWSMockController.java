package com.fluffycloud.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fluffycloud.api.Iservice.AWSService;
import com.fluffycloud.aws.cli.utils.PropertyReader;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@RestController
public class AWSMockController
{

	@Autowired
	PropertyReader propertyReader;

	@Autowired
	AWSService aWSService;

	@RequestMapping("/aws")
	public String mockAWSCloud(HttpServletRequest request)
	{
		Map<String, String[]> params = request.getParameterMap();

		/*
		 * TODO Request have the params present in command For eg: CLI command:
		 * aws ec2 describe-images --image-ids ami-xxxxx --endpoint-url
		 * http://127.0.1.1:8080/aws
		 * 
		 * Params in request are: ImageId.1 : ami-xxxxx Action : DescribeImages
		 * SignatureMethod : HmacSHA256 AWSAccessKeyId : AKIAIDGQBXU3XTSPTPOA
		 * SignatureVersion : 2 Version : 2013-10-15 Signature :
		 * olDIF1AjB06H6E7SlThtJC/6ojhWgBUtOmyUGZD+sqo= Timestamp :
		 * 2014-09-10T10:26:20.985215
		 * 
		 * 
		 * So the command can be formed using the params and can be used with
		 * CLIExecutor to process it on AWS server and get the response.
		 */
		for (String key : params.keySet())
		{
			System.out.println(key + " : " + params.get(key)[0]);
		}
		/*
		 * TODO 1. Need to set content type for the response.
		 * 
		 * 2. Using CLI use debug mode with any command then it will show the
		 * response XML which is returned by AWS APIs.
		 * 
		 * 3. When JSON returned in response with response content type
		 * application/json
		 * 
		 * String mockResponse =
		 * "{\"Images\": [{\"ImageId\": \"ami-xxxxx\",\"RootDeviceName\": \"/dev/sda1\",\"ImageType\": \"machine\",\"Description\": \"Amazon Linux AMI x86_64 PV EBS\",\"ImageOwnerAlias\": \"amazon\",\"Public\": true, \"RootDeviceType\": \"ebs\", \"State\":\"available\"}]}\""
		 * ; reponse.setContentType("application/json");
		 * 
		 * then the CLI client shows: Not well-formed(invalid token): line 1,
		 * column 0
		 * 
		 * 4. Issue is to create the XML response which can be parsed by CLI
		 * client.
		 */

		// Sample response for POC
		String mockResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<DescribeImagesResponse xmlns=\"http://ec2.amazonaws.com/doc/2013-10-15/\">\n    <requestId>fd0fa9ac-a547-4497-8263-407781bbbe57</requestId>\n    <imagesSet>\n        <item>\n            <imageId>ami-xxxxxx</imageId>\n            <imageLocation>amazon/amzn-ami-pv-2014.03.2.x86_64-ebs</imageLocation>\n            <imageState>available</imageState>\n            <imageOwnerId>137112412989</imageOwnerId>\n            <isPublic>true</isPublic>\n            <architecture>x86_64</architecture>\n            <imageType>machine</imageType>\n            <kernelId>aki-xxxxxxxxx</kernelId>\n            <imageOwnerAlias>amazon</imageOwnerAlias>\n            <name>amzn-ami-xxxx-2014.03.2.x86_64-ebs</name>\n            <description>Amazon Linux AMI x86_64 PV EBS</description>\n            <rootDeviceType>ebs</rootDeviceType>\n            <rootDeviceName>/dev/sda1</rootDeviceName>\n            <blockDeviceMapping>\n                <item>\n                    <deviceName>/dev/sda1</deviceName>\n                    <ebs>\n                        <snapshotId>snap-8347b454</snapshotId>\n                        <volumeSize>8</volumeSize>\n                        <deleteOnTermination>true</deleteOnTermination>\n                        <volumeType>standard</volumeType>\n                    </ebs>\n                </item>\n            </blockDeviceMapping>\n            <virtualizationType>paravirtual</virtualizationType>\n            <hypervisor>xen</hypervisor>\n        </item>\n    </imagesSet>\n</DescribeImagesResponse>";

		System.out.println(propertyReader.getEnv().getProperty("host"));

		return mockResponse;
	}

	@RequestMapping("/aws/create/scenario1")
	public String createScenario1(@Valid CommonRequestParams params) throws IOException, FluffyCloudException,
			InterruptedException
	{
		return aWSService.createScenario1(params);
	}

	@RequestMapping("/aws/create/scenario2")
	public String createScenario2(@Valid CommonRequestParams params,
			@RequestHeader(value = "content-type") String contentType) throws IOException, FluffyCloudException,
			InterruptedException
	{

		return aWSService.createScenario1(params);
	}

	/* Sample Implementation to show configured DB */
	@RequestMapping("/aws/add/command")
	public String dBSample(@RequestHeader(value = "content-type") String contentType) throws IOException,
			FluffyCloudException, InterruptedException
	{
		return aWSService.addCommand();
	}

}