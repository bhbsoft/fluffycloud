'use strict';

define([ 'angular', './service/constants', './service/vpc/vpcServices' ],
		function(angular, constants, vpcServices) {

			/* Services */
			var module = angular.module('fluffyCloud.vpc.services', []);

			module.value('Constants', constants);
			module.service('VPCSERVICE', vpcServices);

			return module;
		});
