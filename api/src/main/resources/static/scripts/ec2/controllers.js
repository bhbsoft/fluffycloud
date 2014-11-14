'use strict';

define([ 'angular', './controllers/ec2Ctrl', './controllers/instanceCtrl' ],
		function(angular, ec2Ctrl, instanceCtrl) {

			/* Controllers */
			var module = angular.module('fluffyCloud.ec2.controllers', []);
			module.controller('EC2CTRL', ec2Ctrl);
			module.controller('INSTANCECTRL', instanceCtrl);

			return module;
		});
