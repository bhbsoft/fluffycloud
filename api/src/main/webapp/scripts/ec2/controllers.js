'use strict';

define([ 'angular', './controllers/ec2Ctrl' ], function(angular, ec2Ctrl) {

	/* Controllers */

	var module = angular.module('fluffyCloud.ec2.controllers', []);

	module.controller('EC2CTRL', ec2Ctrl);

	return module;
});
